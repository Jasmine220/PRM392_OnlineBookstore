package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Response.CartDetailResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderSuccessfulActivity extends AppCompatActivity {

    private Button btnReturnHome;
    private Button btnOrderDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);
        btnReturnHome = findViewById(R.id.btnReturnHome);
        btnOrderDetails = findViewById(R.id.btnOrderDetails);
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderSuccessfulActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                int orderId = 0;
                System.out.println("BUNDLE: " + bundle);
                if(bundle!= null){
                    orderId = bundle.getInt("orderId");
                    System.out.println("CUSTOMERID: " + orderId);
                }
                Intent intent = new Intent(OrderSuccessfulActivity.this, OrderDetailsActivity.class);
                Bundle bundle2 = new Bundle();
                bundle.putInt("orderId", orderId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}