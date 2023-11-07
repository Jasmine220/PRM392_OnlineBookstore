package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.SearchView;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.MainActivity;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.RecyclerViewAdapter.BookListAdapter;
import com.example.onlinebookstore.Service.ApiService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rv_bookList;
    BookListAdapter rvBookListAdapter;
    private SearchView searchView;
    ArrayList<Book> books;
    private int accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        accountId = intent.getIntExtra("accountId", 0);
        populateBooks();
        books = new ArrayList<>();
        rv_bookList = findViewById(R.id.rv_bookList);
        rv_bookList.setLayoutManager(new GridLayoutManager(this, 2));
        rvBookListAdapter = new BookListAdapter(HomeActivity.this, books,accountId);
        rv_bookList.setAdapter(rvBookListAdapter);
        showNotification();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Set up the query text listener here
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rvBookListAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                rvBookListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_chat){
            Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
            if (accountId == 4) {
                intent.putExtra("sellerId", accountId);
            } else {
                intent.putExtra("customerId", accountId);
            }
            startActivity(intent);
        }
        if (id == R.id.navigation_cart){
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            intent.putExtra("customerId", accountId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification Channel";
            String description = "Description for My Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_id", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    public void showNotification() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra("addToCart", false)) {
            showCartNotification();
        }
        // Các bước tạo thông báo ở đây, giống như trong code của bạn.
    }
    void showCartNotification() {
        createNotificationChannel(); // Đảm bảo kênh thông báo đã được tạo
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Set the intent that will fire when the user taps the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                .setContentTitle("Your Cart")
                .setContentText("New Items Added")
                .setSmallIcon(R.drawable.ic_cart_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // Tự động đóng thông báo sau khi được ấn

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(getNotificationId(), notification);
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }
    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}