package com.example.onlinebookstore.Response;

public class LoginResponse {
    private String token; // Token đăng nhập
    private int accountId;
    public String getToken() {
        return token;
    }

    public int getAccountId() {
        return accountId;
    }
}
