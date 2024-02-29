/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.repository;

import com.klindziuk.rt.model.reposiotory.RtUser;

public interface UserRepository {

  RtUser getById(String uuid);
  RtUser getByApiKey(String apiKey);
  RtUser getByUsername(String username);
}
