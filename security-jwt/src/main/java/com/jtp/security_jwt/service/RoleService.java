package com.jtp.security_jwt.service;


import com.jtp.security_jwt.domain.Role;

public interface RoleService {
    Role findByName(String name);
}
