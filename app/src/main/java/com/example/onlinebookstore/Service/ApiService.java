package com.example.onlinebookstore.Service;

import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.Models.Customer;
import com.example.onlinebookstore.Models.Message;
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.Request.CartDetailRequest;
import com.example.onlinebookstore.Request.LoginRequest;
import com.example.onlinebookstore.Request.RegisterRequest;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Path;
import retrofit2.http.PUT;

public interface ApiService {
    // Đăng nhập
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("register")
    Call<LoginResponse> register(@Body RegisterRequest registerRequest);
    @GET("list")
    Call<List<Account>> getAllAccounts();
    @GET("api/v1/order/")
    Call<Order> getOrder(@Query("orderId") int orderId);
    @POST("send")
    Call<Void> sendMessage(@Body Message message);
    @GET("messages")
    Call<List<Message>> getChatMessages(
            @Query("customerId") Long customerId,
            @Query("sellerId") int sellerId
    );
    // Book List
    @GET("api/books")
    Call<List<Book>> getAllBooks();

    @GET("api/v1/cart-detail/")
    Call<List<CartDetailResponse>> getCartByCustomer(@Query("CustomerId") int customerId);
    @PUT("api/v1/cart-detail/")
    Call<CartDetail> updateCartDetail(@Query("cartDetailId") long cartDetailId, @Query("amount") long amount);
    @POST("api/v1/cart-detail/")
    Call<CartDetail> createCartDetail(@Body CartDetailRequest request);
    @DELETE("api/v1/cart-detail/")
    Call<Void> deleteCartDetail(@Query("cartDetailId") long cartDetailId);

}