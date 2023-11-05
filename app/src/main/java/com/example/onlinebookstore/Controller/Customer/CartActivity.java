package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
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
    List<CartDetailResponse> cartDetailList;
    ApiService apiService;
    long userId;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbar);
        checkAllBox = findViewById(R.id.checkBox);
        recyclerView = findViewById(R.id.recycleView);

        toolbar.setTitle("Bakasa");
//        setSupportActionBar(toolbar);
//        if(getIntent() != null){
//            Bundle bundle = getIntent().getExtras();
//            userId = bundle.getLong("userId");
//        }

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<CartDetailResponse>> call = apiService.getCartByCustomer(3);
        call.enqueue(new Callback<List<CartDetailResponse>>() {
            @Override
            public void onResponse(Call<List<CartDetailResponse>> call, Response<List<CartDetailResponse>> response) {
                if(response.isSuccessful()){
                    cartDetailList = response.body();
                    Log.d("Cart" , "connected to server");
                } else {
                    Log.d("Cart" , "connected to server but respone fail");
                }
            }

            @Override
            public void onFailure(Call<List<CartDetailResponse>> call, Throwable t) {
                Log.d("Cart" , "Not connected to server");
            }
        });
        recycleViewAdapter = new CartRecycleView(this, cartDetailList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkAllBox.setOnCheckedChangeListener((compoundButton, b) -> {
            recycleViewAdapter.setCheckList(b);
            recycleViewAdapter.notifyDataSetChanged();
        });


    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}