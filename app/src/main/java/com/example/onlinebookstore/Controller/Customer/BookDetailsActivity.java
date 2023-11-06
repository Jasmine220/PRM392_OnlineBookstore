package com.example.onlinebookstore.Controller.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.onlinebookstore.Models.CartDetail;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.CartDetailRequest;
import com.example.onlinebookstore.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookDetailsActivity extends AppCompatActivity {
    private int accountId;
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

        // Trong phương thức onClick của nút "Add to Cart"
        Button addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int customerId = 1; // Điền ID của khách hàng tại đây
                int bookId = Integer.valueOf(book_id); // Điền ID của sách tại đây
                int amount = 1; // Số lượng sách bạn muốn thêm vào giỏ hàng

                // Tạo đối tượng CartDetailRequest
                CartDetailRequest request = new CartDetailRequest();
                request.setCustomerId(customerId);
                request.setBookId(bookId);
                request.setAmount(amount);

                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                // Gửi yêu cầu thêm sách vào giỏ hàng
                Call<CartDetail> call = apiService.createCartDetail(request);
                call.enqueue(new Callback<CartDetail>() {
                    @Override
                    public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                        if (response.isSuccessful()) {
                            // Xử lý khi thêm sách vào giỏ hàng thành công
                            // Hiển thị thông báo hoặc thực hiện các hành động khác
                            showToast("Add " + title + " successfully!");

                        } else {
                            // Xử lý khi có lỗi xảy ra trong quá trình thêm sách vào giỏ hàng
                            showToast("Add " + title + " failure!");
                        }
                    }

                    @Override
                    public void onFailure(Call<CartDetail> call, Throwable t) {
                        // Xử lý khi gửi yêu cầu thất bại
                    }
                });
            }
        });

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
        return super.onOptionsItemSelected(item);
    }
    private void showToast(String message) {
        Toast.makeText(BookDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
