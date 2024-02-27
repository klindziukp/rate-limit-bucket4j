/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.service;

import com.klindziuk.rt.model.reposiotory.RtUser;

public interface UserService {

  RtUser getById(String id);

  RtUser getByApiKey(String apikey);

  RtUser getByUsername(String username);
}
