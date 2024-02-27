/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.service.impl;

import com.klindziuk.rt.config.PricingPlan;
import com.klindziuk.rt.model.reposiotory.UserPlan;
import com.klindziuk.rt.service.PricingPlanService;
import io.github.bucket4j.Bucket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PricingPlanServiceImpl implements PricingPlanService {

  private final Map<UserPlan, Bucket> PLAN_BUCKETS = new ConcurrentHashMap<>();
  private final Map<String, Bucket> IP_BUCKETS = new ConcurrentHashMap<>();

  @Override
  public Bucket resolveBucketByUserPlan(UserPlan userPlan) {
    return PLAN_BUCKETS.computeIfAbsent(userPlan, this::newBucket);
  }

  @Override
  public Bucket resolveBucketByIp(String ipAddress) {
    return IP_BUCKETS.computeIfAbsent(ipAddress, userPlan -> newBucket(UserPlan.FREE));
  }

  private Bucket newBucket(UserPlan userPlan) {
    final PricingPlan pricingPlan = PricingPlan.resolvePricingFromUserPlan(userPlan);
    return Bucket.builder().addLimit(pricingPlan.getLimit()).build();
  }
}
