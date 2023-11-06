package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.RecycleViewAdapter.CartRecycleView;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Service.ApiService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    CheckBox checkAllBox;
    RecyclerView recyclerView;
    CartRecycleView recycleViewAdapter;
    ApiService apiService;
    List<CartDetailResponse> cartDetailList;
    TextView totalPaymentView;
    Button purchaseBtn;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_cart);

//        toolbar = findViewById(R.id.toolbar);
        checkAllBox = findViewById(R.id.checkBox);
        recyclerView = findViewById(R.id.recycleView);
        totalPaymentView = findViewById(R.id.totalPaymentView);
        purchaseBtn = findViewById(R.id.purchaseButton);
        totalPaymentView.setText("Bạn chưa chọn sản phẩm nào để mua");
//        toolbar.setTitle("Bakasa");
//        setSupportActionBar(toolbar);
//        if(getIntent() != null){
//            Bundle bundle = getIntent().getExtras();
//            userId = bundle.getLong("userId");
//        }

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<CartDetailResponse>> call = apiService.getCartByCustomer(1);
        call.enqueue(new Callback<List<CartDetailResponse>>() {
            @Override
            public void onResponse(Call<List<CartDetailResponse>> call, Response<List<CartDetailResponse>> response) {
                if (response.isSuccessful()) {
                     cartDetailList = response.body();
                    recycleViewAdapter = new CartRecycleView(CartActivity.this, cartDetailList);
                    recyclerView.setAdapter(recycleViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                    recycleViewAdapter.setTotalPaymentView(totalPaymentView);
                    recycleViewAdapter.setPurchaseBtn(purchaseBtn);
                    for (CartDetailResponse cartDetail : cartDetailList) {
                        Log.d("Cart", "Cart Detail: " + cartDetail.getBookTitle() + " - " + cartDetail.getAmount());
                    }
                    // Do something with the cartDetailList, such as displaying it in your RecyclerView
                } else {
                    Log.d("Cart", "Connected to server but response fail");
                }
            }

            @Override
            public void onFailure(Call<List<CartDetailResponse>> call, Throwable t) {
                Log.e("Cart", "Failed to connect to server: " + t.getMessage());
                t.printStackTrace();
            }
        });


        checkAllBox.setOnCheckedChangeListener((compoundButton, b) -> {
            recycleViewAdapter.setCheckList(b);
            recycleViewAdapter.notifyDataSetChanged();
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        totalPaymentView.setText("Bạn chưa chọn sản phẩm nào để mua");
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<CartDetailResponse>> call = apiService.getCartByCustomer(1);
        call.enqueue(new Callback<List<CartDetailResponse>>() {
            @Override
            public void onResponse(Call<List<CartDetailResponse>> call, Response<List<CartDetailResponse>> response) {
                if (response.isSuccessful()) {
                    cartDetailList = response.body();
                   recycleViewAdapter.setCartDetailList(cartDetailList);
                    for (CartDetailResponse cartDetail : cartDetailList) {
                        Log.d("Cart", "Cart Detail: " + cartDetail.getBookTitle() + " - " + cartDetail.getAmount());
                    }
                    // Do something with the cartDetailList, such as displaying it in your RecyclerView
                } else {
                    Log.d("Cart", "Connected to server but response fail");
                }
            }

            @Override
            public void onFailure(Call<List<CartDetailResponse>> call, Throwable t) {
                Log.e("Cart", "Failed to connect to server: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}