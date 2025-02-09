package com.jtp.security_jwt.rest;

import com.jtp.security_jwt.domain.User;
import com.jtp.security_jwt.response.AllUsersResponse;
import com.jtp.security_jwt.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Users managment controller")
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Operation(summary = "Get all users (admin action)", description = "This action allows admin to retrieve a list of all users registered in the system.",
    responses = {
            @ApiResponse(responseCode = "200", description = "List of all users retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "403", description = "Aunauthorized access, admin role required.")
    }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<AllUsersResponse> findAllUsers(){
        List<User> users = userService.findAll();
        AllUsersResponse<User> response = new AllUsersResponse(
            "command: find all users",
                users,
                200
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a specific user (admin action)", description = "This action allows admin to retrieve a user requiring the id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User finded and returned.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Aunauthorized access, admin role required.")
            }
    )
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
