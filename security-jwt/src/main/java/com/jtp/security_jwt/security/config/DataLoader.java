package com.jtp.security_jwt.security.config;

import com.jtp.security_jwt.domain.Role;
import com.jtp.security_jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0){
            Role userRole = new Role("ROLE_USER", "a normal user");
            Role adminRole = new Role("ROLE_ADMIN", "a user with admin powers");

            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }
    }
}
