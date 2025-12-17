package com.example.stm.model;

public class SignupRequest {
    private String username;
    private String email;
    private String password;

    public SignupRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters can be added if needed, but are not strictly
    // necessary for Gson serialization.
}
