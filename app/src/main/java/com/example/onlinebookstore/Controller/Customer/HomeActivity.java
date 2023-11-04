package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlinebookstore.R;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rv_bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv_bookList = findViewById(R.id.rv_bookList);
    }
}