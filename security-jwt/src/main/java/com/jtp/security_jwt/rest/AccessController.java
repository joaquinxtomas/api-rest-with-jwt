package com.jtp.security_jwt.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Access controller")
@RequestMapping("/api/access")
public class AccessController {

    @Operation(summary = "Only those with an administrator role can access to this route.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Only the admins can read this";
    }

    @Operation(summary = "Everyone can access to this route.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUser(){
        return "Admins and normal users can read this";
    }

    @Operation(summary = "Only those with an normal user role can access to this route.")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String pingUsers(){
        return "Only normal users can read this";
    }

}
