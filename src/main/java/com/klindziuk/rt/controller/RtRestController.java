/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.controller;

import com.klindziuk.rt.model.response.RtResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RtRestController {

  @GetMapping(value = "/api/v1/rt/api-key")
  public ResponseEntity<RtResponse> apiKeyResponse() {
    return ResponseEntity.ok().body(rtResponse("Api-Key-"));
  }

  @GetMapping(value = "/api/v1/rt/ip")
  public ResponseEntity<RtResponse> ipResponse() {
    return ResponseEntity.ok().body(rtResponse("Ip-"));
  }

  private RtResponse rtResponse(String prefix) {
    return new RtResponse()
        .setKey(prefix + RandomStringUtils.randomAlphanumeric(8))
        .setValue(prefix + RandomStringUtils.randomAlphanumeric(8));
  }
}
