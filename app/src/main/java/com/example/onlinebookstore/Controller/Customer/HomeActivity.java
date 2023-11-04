package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.RecyclerViewAdapter.BookListAdapter;
import com.example.onlinebookstore.Service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rv_bookList;
    BookListAdapter rvBookListAdapter;
    ArrayList<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        populateBooks();
        books = new ArrayList<>();
        rv_bookList = findViewById(R.id.rv_bookList);
        rv_bookList.setLayoutManager(new GridLayoutManager(this, 2));
        rvBookListAdapter = new BookListAdapter(HomeActivity.this, books);
        rv_bookList.setAdapter(rvBookListAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }

    public void populateBooks() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Book>> call = apiService.getAllBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        List<Book> bookList = response.body();
                        if (bookList != null) {
                            books.clear();
                            books.addAll(bookList);
                            rvBookListAdapter.notifyDataSetChanged();
                        }
                    } else if (response.code() == 204) {
                        // Handle "No Content" response, e.g., show a message to the user
                        showToast("No books are available at the moment.");
                    } else {
                        // Handle other error responses, e.g., display an error message
                        showToast("An error occurred. Please try again.");
                    }
                } else {
                    // Handle network or other failures, e.g., show an error message
                    showToast("Network or server error. Please check your connection.");
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // Handle network or other failures, e.g., show an error message
                showToast("Network or server error. Please check your connection.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}