package com.jtp.security_jwt.dto;

import java.util.Date;
import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String username;
    private Date createdAt;
    private List<String> roles;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, Date createdAt, List<String> roles) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
