/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RtResponse {

  private String key;
  private String value;
}
