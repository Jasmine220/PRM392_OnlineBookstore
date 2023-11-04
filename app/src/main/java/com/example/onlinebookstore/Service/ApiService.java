package com.example.onlinebookstore.Service;

import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.Models.Message;
import com.example.onlinebookstore.Request.LoginRequest;
import com.example.onlinebookstore.Request.RegisterRequest;
import com.example.onlinebookstore.Response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    // Đăng nhập
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("register")
    Call<LoginResponse> register(@Body RegisterRequest registerRequest);
    @GET("list")
    Call<List<Account>> getAllAccounts();
    @POST("send")
    Call<Void> sendMessage(@Body Message message);
    @GET("messages")
    Call<List<Message>> getChatMessages(
            @Query("customerId") Long customerId,
            @Query("sellerId") int sellerId
    );
}