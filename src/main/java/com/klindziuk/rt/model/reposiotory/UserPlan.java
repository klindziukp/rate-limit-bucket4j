/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.model.reposiotory;

public enum UserPlan {
  FREE("FREE"),
  BASIC("BASIC"),
  PRO("PRO");

  private final String value;

  public String getValue() {
    return this.value;
  }

  private UserPlan(String value) {
    this.value = value;
  }
}
