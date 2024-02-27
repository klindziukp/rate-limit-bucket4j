/*
 * Copyright (c) Dandelion development.
 */

package com.klindziuk.rt.service.impl;

import com.klindziuk.rt.model.reposiotory.RtUser;
import com.klindziuk.rt.repository.UserRepository;
import com.klindziuk.rt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public RtUser getById(String id) {
    return userRepository.getById(id);
  }

  public RtUser getByApiKey(String apiKey) {
    return userRepository.getByApiKey(apiKey);
  }

  public RtUser getByUsername(String username) {
    return userRepository.getByUsername(username);
  }
}
