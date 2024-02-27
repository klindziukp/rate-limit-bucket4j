/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.config.interceptor;

import com.klindziuk.rt.config.RateLimitConfig;
import com.klindziuk.rt.constant.XHeader;
import com.klindziuk.rt.model.reposiotory.RtUser;
import com.klindziuk.rt.service.PricingPlanService;
import com.klindziuk.rt.service.UserService;
import com.klindziuk.rt.storage.RateLimitData;
import com.klindziuk.rt.storage.RateLimitStorage;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ApiKeyRateLimitRequestInterceptor implements HandlerInterceptor {

  private final RateLimitConfig rateLimitConfig;
  private final PricingPlanService pricingPlanService;
  private final UserService userService;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object object) {
    final String apiKey = request.getHeader(XHeader.X_API_KEY);
    if (Objects.isNull(apiKey)) {
      RateLimitErrorHandler.handleNoApiKeyErrorForbiddenError(response);
      return false;
    }
    if (rateLimitConfig.getNotAllowedKeys().contains(apiKey)) {
      RateLimitErrorHandler.handleForbiddenError(
          response, XHeader.X_RATE_LIMIT_USER_NOT_ALLOWED, apiKey);
      return false;
    }
    final RateLimitData rateLimitData = getRateLimitData(apiKey);
    if (Objects.isNull(rateLimitData)) {
      RateLimitErrorHandler.handleForbiddenError(
          response, XHeader.X_RATE_LIMIT_USER_NOT_ALLOWED, apiKey);
      return false;
    }

    final ConsumptionProbe consumptionProbe =
        rateLimitData.getBucket().tryConsumeAndReturnRemaining(1);
    if (consumptionProbe.isConsumed()) {
      response.addHeader(
          XHeader.X_RATE_LIMIT_REMAINING, String.valueOf(consumptionProbe.getRemainingTokens()));
      return true;
    }
    RateLimitErrorHandler.handleTooManyError(response, consumptionProbe);
    return false;
  }

  private RateLimitData getRateLimitData(String apiKey) {
    RateLimitData rateLimitData = RateLimitStorage.getRateLimitData(apiKey);
    if (Objects.isNull(rateLimitData)) {
      final RtUser userByApiKey = userService.getByApiKey(apiKey);
      if (Objects.isNull(userByApiKey)) {
        return null;
      }
      final Bucket bucket = pricingPlanService.resolveBucketByUserPlan(userByApiKey.getUserPlan());
      rateLimitData = new RateLimitData().setUser(userByApiKey).setBucket(bucket);
      RateLimitStorage.addRateLimitData(apiKey, rateLimitData);
    }
    return rateLimitData;
  }
}
