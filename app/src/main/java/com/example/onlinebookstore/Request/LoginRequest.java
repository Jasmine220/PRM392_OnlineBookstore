package com.example.onlinebookstore.Request;

public class LoginRequest {
    private String accountUsername;
    private String accountPassword;

    public LoginRequest(String username, String password) {
        this.accountUsername = username;
        this.accountPassword = password;
    }
}