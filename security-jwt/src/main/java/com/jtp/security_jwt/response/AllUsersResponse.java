package com.jtp.security_jwt.response;

import com.jtp.security_jwt.domain.User;

import java.util.List;

public class AllUsersResponse<User> {
    private String message;
    private List<User> data;
    private int code;

    public AllUsersResponse() {
    }

    public AllUsersResponse(String message, List<User> data, int code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
