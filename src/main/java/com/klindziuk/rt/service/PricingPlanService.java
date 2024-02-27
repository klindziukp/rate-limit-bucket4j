/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.service;

import com.klindziuk.rt.model.reposiotory.UserPlan;
import io.github.bucket4j.Bucket;

public interface PricingPlanService {
  Bucket resolveBucketByUserPlan(UserPlan userPlan);

  Bucket resolveBucketByIp(String ipAddress);
}
