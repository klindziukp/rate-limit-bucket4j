/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.config;

import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RateLimitConfig {

  @Value("${com.klindziuk.rate.limit.not.allowed.keys}") private List<String> notAllowedKeys;
  @Value("${com.klindziuk.rate.limit.not.allowed.ips}")  private List<String> notAllowedIps;
}
