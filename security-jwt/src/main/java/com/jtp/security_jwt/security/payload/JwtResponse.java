package com.jtp.security_jwt.security.payload;


import java.util.Date;

public class JwtResponse {

    private String token;

    private Date expiresIn;

    public JwtResponse(){

    }

    public JwtResponse(String token){
        this.token = token;
    }

    public JwtResponse(String token, Date expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }
}
