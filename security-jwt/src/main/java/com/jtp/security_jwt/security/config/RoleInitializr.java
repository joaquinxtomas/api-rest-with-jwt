package com.jtp.security_jwt.security.config;

import com.jtp.security_jwt.domain.Role;
import com.jtp.security_jwt.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializr implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleInitializr(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findRoleByName("ROLE_USER") == null){
            Role userRole = new Role("ROLE_USER", "a normal user");
            roleRepository.save(userRole);
        }

        if(roleRepository.findRoleByName("ROLE_ADMIN") == null){
            Role adminRole = new Role("ROLE_ADMIN", "a user with admin permissions");
            roleRepository.save(adminRole);
        }
    }
}
