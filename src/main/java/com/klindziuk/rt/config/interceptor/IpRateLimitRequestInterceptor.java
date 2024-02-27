/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.config.interceptor;

import com.klindziuk.rt.config.RateLimitConfig;
import com.klindziuk.rt.constant.XHeader;
import com.klindziuk.rt.service.PricingPlanService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class IpRateLimitRequestInterceptor implements HandlerInterceptor {

  private final RateLimitConfig rateLimitConfig;
  private final PricingPlanService pricingPlanService;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object object) {
    final String ip = request.getRemoteAddr();
    if (rateLimitConfig.getNotAllowedIps().contains(ip)) {
      RateLimitErrorHandler.handleForbiddenError(response, XHeader.X_RATE_LIMIT_IP_NOT_ALLOWED, ip);
      return false;
    }

    final Bucket tokenBucket = pricingPlanService.resolveBucketByIp(ip);
    final ConsumptionProbe consumptionProbe = tokenBucket.tryConsumeAndReturnRemaining(1);
    if (consumptionProbe.isConsumed()) {
      response.addHeader(
          XHeader.X_RATE_LIMIT_REMAINING, String.valueOf(consumptionProbe.getRemainingTokens()));
      return true;
    }
    RateLimitErrorHandler.handleTooManyError(response, consumptionProbe);
    return false;
  }
}
