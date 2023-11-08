package com.example.onlinebookstore.Controller.Customer;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.CartDetailRequest;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Service.ApiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDetailsActivity extends AppCompatActivity {
    private int accountId;
    private int bookId;
    private ApiService apiService = ApiClient.getClient().create(ApiService.class);
    List<CartDetailResponse> cartDetailResponseList;
    boolean check = false;
    int addCount;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();
        accountId = intent.getIntExtra("accountId", 0);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        String book_id = getIntent().getStringExtra("book_id");
        bookId = Integer.valueOf(book_id);
        String title = getIntent().getStringExtra("Title");
        String author = getIntent().getStringExtra("Author");
        String price = getIntent().getStringExtra("Price");
        String description = getIntent().getStringExtra("Description");
        String imageUrl = getIntent().getStringExtra("Image");
        String book_page_number = getIntent().getStringExtra("book_page_number");
        String book_cover_type = getIntent().getStringExtra("book_cover_type");
        String supplier_name = getIntent().getStringExtra("supplier_name");
        String publisher_name = getIntent().getStringExtra("publisher_name");
        String category_name = getIntent().getStringExtra("category_name");
        String book_quantity = getIntent().getStringExtra("book_quantity");

        TextView titleTextView = findViewById(R.id.book_title);
        TextView authorTextView = findViewById(R.id.book_author);
        TextView priceTextView = findViewById(R.id.book_price);
        TextView descriptionTextView = findViewById(R.id.book_description);
        ImageView bookImage = findViewById(R.id.book_image);
        TextView page_number = findViewById(R.id.book_page_number);
        TextView cover_type = findViewById(R.id.book_cover_type);
        TextView supplier = findViewById(R.id.supplier_name);
        TextView publisher = findViewById(R.id.publisher_name);
        TextView category = findViewById(R.id.category_name);
        TextView quantity = findViewById(R.id.book_quantity);
        Button addToCart = findViewById(R.id.add_to_cart);

        titleTextView.setText(title);
        authorTextView.setText(author);
        priceTextView.setText(price);
        descriptionTextView.setText(description);
        // Load the image with Glide
        Glide.with(this).load(imageUrl).into(bookImage);
        page_number.setText(book_page_number);
        cover_type.setText(book_cover_type);
        supplier.setText(supplier_name);
        publisher.setText(publisher_name);
        category.setText(category_name);
        quantity.setText(book_quantity);

        getCart();
        addCount = 0;
        Executor executor = Executors.newSingleThreadExecutor();
        createNotificationChannel();
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCount++;
                showToast("Add successfully!");
                executor.execute(() -> {
                    createNotification(imageUrl, title,Integer.valueOf(book_id) );
                });

            }
        });

    }

    @Override
    protected void onPause() {
        if(!isAddToCart())
            addToCart();
        super.onPause();
    }


    private void addToCart(){

        // Tạo đối tượng CartDetailRequest
        CartDetailRequest request = new CartDetailRequest();
        request.setCustomerId(accountId);
        request.setBookId(bookId);
        request.setAmount(1);

        // Gửi yêu cầu thêm sách vào giỏ hàng
        Call<CartDetail> call = apiService.createCartDetail(request);
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                if (response.isSuccessful()) {
                    Log.d("Cart", "add successful");

                } else {
                    Log.e("Cart", "add fail");
                }
            }

            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {
                // Xử lý khi gửi yêu cầu thất bại
            }
        });
    }

    private void updateToCart(long cartDetailId, long amount){
        Call<CartDetail> call = apiService.updateCartDetail(cartDetailId, amount);
        call.enqueue(new Callback<CartDetail>() {
            @Override
            public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
              if(response.isSuccessful()){
                  Log.d("Cart", "update successful");
              }else {
                  Log.e("Cart", "update fail");
              }
            }

            @Override
            public void onFailure(Call<CartDetail> call, Throwable t) {
                Log.e("Cart", "Failed to connect to server: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getCart(){
        Call<List<CartDetailResponse>> call = apiService.getCartByCustomer(accountId);
        call.enqueue(new Callback<List<CartDetailResponse>>() {
            @Override
            public void onResponse(Call<List<CartDetailResponse>> call, Response<List<CartDetailResponse>> response) {
                if(response.isSuccessful()){
                    cartDetailResponseList = response.body();
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

    private boolean isAddToCart(){
        check = false;
        cartDetailResponseList.stream().forEach(cartDetailResponse -> {
            if(cartDetailResponse.getBookId() == bookId){
                check = true;
                updateToCart(cartDetailResponse.getCartDetailId(), cartDetailResponse.getAmount() + addCount);
            }

        });
        return check;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        int id = item.getItemId();
        if (id == R.id.navigation_chat){
            Intent intent = new Intent(BookDetailsActivity.this, ChatActivity.class);
            if (accountId == 4) {
                intent.putExtra("sellerId", accountId);
            } else {
                intent.putExtra("customerId", accountId);
            }
            startActivity(intent);
        }
        if (id == R.id.navigation_cart){
            Intent intent = new Intent(BookDetailsActivity.this, CartActivity.class);
            intent.putExtra("customerId", accountId);
            startActivity(intent);
        }
        if (id == R.id.navigation_map){
            Intent intent = new Intent(BookDetailsActivity.this, MapActivity.class);
            startActivity(intent);
        }
        if (id == R.id.navigation_home){
            Intent intent = new Intent(BookDetailsActivity.this, HomeActivity.class);
            intent.putExtra("accountId", accountId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message) {
        Toast.makeText(BookDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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

    public void createNotification(String imageUrl, String bookTitle, int bookId){
        try {
            bitmap = Picasso.get().load(imageUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Intent intent = new Intent(this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_id")
                .setSmallIcon(R.drawable.pngwing_com)
                .setContentTitle("Bạn đã thêm sản phẩm vào giỏ hàng")
                .setContentText(bookTitle + " đã được thêm vào giỏ")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setTimeoutAfter(24 * 60 * 60 * 1000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        showNotification(this ,bookId, builder);
    }

    public static void showNotification(Context context, int notification_id, NotificationCompat.Builder builder){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }else{
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notification_id, builder.build());
        }

    }
}
