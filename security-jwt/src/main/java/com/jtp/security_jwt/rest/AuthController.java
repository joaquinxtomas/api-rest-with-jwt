package com.jtp.security_jwt.rest;

import com.jtp.security_jwt.domain.Role;
import com.jtp.security_jwt.domain.User;
import com.jtp.security_jwt.repository.RoleRepository;
import com.jtp.security_jwt.repository.UserRepository;
import com.jtp.security_jwt.security.jwt.JWTUtility;
import com.jtp.security_jwt.security.payload.JwtResponse;
import com.jtp.security_jwt.security.payload.LoginRequest;
import com.jtp.security_jwt.security.payload.MessageResponse;
import com.jtp.security_jwt.security.payload.RegisterRequest;
import com.jtp.security_jwt.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtility jwtUtility;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public AuthController(PasswordEncoder passwordEncoder,
                          JWTUtility jwtUtility,
                          UserDetailsServiceImpl userDetailsService,
                          AuthenticationManager authManager,
                          UserRepository userRepo,
                          RoleRepository roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtility = jwtUtility;
        this.userDetailsService = userDetailsService;
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Role defaultRole = roleRepo.findRoleByName("USER");

        user.setRoles(Set.of(defaultRole));

        try{
            userDetailsService.save(user);
        } catch(Exception e){
            log.error("Error signing user: " + e.getMessage());
        }


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Only the admins can read this";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-users")
    public String adminUser(){
        return "Admins and users can read this";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-users")
    public String pingUsers(){
        return "Only users can read this";
    }


}
