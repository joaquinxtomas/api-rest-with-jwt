package com.jtp.security_jwt.service;

import com.jtp.security_jwt.domain.Role;
import com.jtp.security_jwt.domain.User;
import com.jtp.security_jwt.repository.UserRepository;

import com.jtp.security_jwt.security.payload.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.jtp.security_jwt.domain.User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), getAuthority(user)
        );
    }

    public boolean testPasswordValidation(String rawPassword, String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return matches;
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    public User save(User user) throws Exception {
        if(userRepo.existsByEmail(user.getEmail())){
            throw new Exception("Email already exists!");
        }

        Role role = roleService.findByName("ROLE_USER");

        if(role == null){
            log.error("Default role USER not found");
        } else {
            log.error("Default role is " + role.getName());
        }

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        user.setRoles(roleSet);

        return userRepo.save(user);

    }

    public List<User> findAll() {
        log.info("Executing find all users\n");
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        log.info("Executing find by id w/ id " + id);
        return userRepo.findById(id);
    }
}
