package com.jtp.security_jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordHashingTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testPasswordHashing(){
        String rawPassword = "123456";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        if(passwordEncoder.matches(rawPassword, hashedPassword)){
            System.out.println("COINCIDENNNNNNN\n");
        }

        assertTrue(passwordEncoder.matches(rawPassword,hashedPassword),
                "La contraseña no coincide con el hash generado");
    }

}
