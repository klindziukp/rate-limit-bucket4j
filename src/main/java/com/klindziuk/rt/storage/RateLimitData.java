/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.storage;

import com.klindziuk.rt.model.reposiotory.RtUser;
import io.github.bucket4j.Bucket;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RateLimitData {

  private RtUser user;
  private Bucket bucket;
}
