package com.jtp.security_jwt.rest;

import com.jtp.security_jwt.domain.User;
import com.jtp.security_jwt.response.AllUsersResponse;
import com.jtp.security_jwt.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<AllUsersResponse> findAllUsers(){
        List<User> users = userService.findAll();
        AllUsersResponse<User> response = new AllUsersResponse(
            "command: find all users",
                users,
                200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        if(user != null){
            return ResponseEntity.ok().body(user);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

}
