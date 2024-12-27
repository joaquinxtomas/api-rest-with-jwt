package com.jtp.security_jwt.service;

import com.jtp.security_jwt.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);
}
