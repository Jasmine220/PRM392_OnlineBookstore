package com.example.onlinebookstore.Service;

import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.Request.LoginRequest;
import com.example.onlinebookstore.Request.PaymentRequest;
import com.example.onlinebookstore.Request.RegisterRequest;
import com.example.onlinebookstore.Response.LoginResponse;
import com.example.onlinebookstore.Response.PaymentInforResponse;
import com.example.onlinebookstore.Response.PaymentResponse;

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
    @POST("api/payment/create_payment")
    Call<PaymentResponse> createPayment(@Query("amount") float amount, @Query("vnp_IpAddr") String vnp_IpAddr);

    @GET("api/payment/payment_information")
    Call<PaymentInforResponse> getPaymentInfo(@Query("vnp_Amount") String amount,
                                              @Query("vnp_BankCode") String bankCode,
                                              @Query("vnp_OrderInfo") String orderInfo,
                                              @Query("vnp_ResponseCode") String responseCode);
}