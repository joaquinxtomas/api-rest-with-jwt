package com.jtp.security_jwt.rest;

import com.jtp.security_jwt.domain.Role;
import com.jtp.security_jwt.domain.User;
import com.jtp.security_jwt.dto.UserResponseDTO;
import com.jtp.security_jwt.repository.RoleRepository;
import com.jtp.security_jwt.repository.UserRepository;
import com.jtp.security_jwt.security.jwt.JWTUtility;
import com.jtp.security_jwt.security.payload.*;
import com.jtp.security_jwt.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Authentication controller (sign-up and sign-in)")
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

    @Operation(summary = "Login to the system",
            description = "This endpoint allows users to authenticate by providing their username and password. If they have successful auth, a JWT token is generated and returned.",
              responses = {
                    @ApiResponse(responseCode = "200", description = "Successfull login, JWT token returned."),
                      @ApiResponse(responseCode = "401", description = "Unauthorized to login (Bad credentials).")
              }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login( @RequestBody LoginRequest loginRequest){

        final Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtility.generateToken(authentication);
        Date expiration = jwtUtility.extractExpiration(token);
        return ResponseEntity.ok(new JwtResponse(token, expiration));

    }

    @Operation(summary = "Register a new user",
            description = "This endpoint allows a new user to register by providing a username, email, and password (the password is hashed before storing).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfull, the user has already registered."),
                    @ApiResponse(responseCode = "400", description = "Bad request. The username or email was already taken.")
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("error", "The username was already taken", null));
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("error", "Email is already in use!", null));
        }

        String hashedPass = passwordEncoder.encode(signUpRequest.getPassword());

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                hashedPass, new Date());

        Role defaultRole = roleRepo.findRoleByName("ROLE_USER");

        user.setRoles(Set.of(defaultRole));

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        try{
            userDetailsService.save(user);
        } catch(Exception e){
            log.error("Error signing user: " + e.getMessage());
        }


        return ResponseEntity.ok(new MessageResponse("success","User successfully registered.",
                new UserResponseDTO(user.getId(), user.getUsername(),user.getCreatedAt(), roles)));
    }




}
