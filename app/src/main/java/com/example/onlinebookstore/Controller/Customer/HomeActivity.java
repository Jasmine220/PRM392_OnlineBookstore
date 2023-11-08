package com.example.onlinebookstore.Controller.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Service.ApiService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rv_bookList;
    BookListAdapter rvBookListAdapter;
    private SearchView searchView;
    ArrayList<Book> books;
    private int accountId;
    List<CartDetailResponse> cartDetailList;
    Bitmap bitmap;
    Boolean isNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(getIntent() != null){
            accountId = getIntent().getIntExtra("accountId", 0);
            isNotify = getIntent().getBooleanExtra("isNotify", false);
        }

        populateBooks();
        books = new ArrayList<>();
        rv_bookList = findViewById(R.id.rv_bookList);
        rv_bookList.setLayoutManager(new GridLayoutManager(this, 2));
        rvBookListAdapter = new BookListAdapter(HomeActivity.this, books,accountId);
        rv_bookList.setAdapter(rvBookListAdapter);

        if(!isNotify){
            createNotificationChannel();
            getCart();
        }



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
    private void getCart(){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<CartDetailResponse>> call = apiService.getCartByCustomer(accountId);
        call.enqueue(new Callback<List<CartDetailResponse>>() {
            @Override
            public void onResponse(Call<List<CartDetailResponse>> call, Response<List<CartDetailResponse>> response) {
                if(response.isSuccessful()){
                    cartDetailList = response.body();
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        createNotifications();
                    });

                    Log.d("Cart", "get cart successful");
                }else {
                    Log.e("Cart", "get cart fail");
                }
            }
            @Override
            public void onFailure(Call<List<CartDetailResponse>> call, Throwable t) {
                Log.e("Cart", "Failed to connect to server: " + t.getMessage());
                t.printStackTrace();
            }
        });

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_id", name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    public void createNotifications(){
        cartDetailList.stream().forEach(cartDetail -> {

            try {
                bitmap = Picasso.get().load(cartDetail.getBookImage()).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(this, CartActivity.class);
            intent.putExtra("customerId",(Integer) accountId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                    .setSmallIcon(R.drawable.pngwing_com)
                    .setContentTitle("Bạn đã thêm sản phẩm vào giỏ hàng")
                    .setContentText(cartDetail.getBookTitle() + " đã được thêm vào giỏ")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null))
                    .setTimeoutAfter(24 * 60 * 60 * 1000)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            showNotification(this ,cartDetail.getBookId(), builder);
        });

    }

    public void showNotification(Context context,int notification_id, NotificationCompat.Builder builder){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }else{
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notification_id, builder.build());
        }

    }

    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}