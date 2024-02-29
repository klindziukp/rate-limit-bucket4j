/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.model.reposiotory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPlan {
  FREE("FREE"),
  BASIC("BASIC"),
  PRO("PRO");

  private final String value;
}
