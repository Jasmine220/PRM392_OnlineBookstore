package com.example.onlinebookstore.Controller.Customer;

import androidx.activity.OnBackPressedCallback;
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
import com.bumptech.glide.Glide;
import com.example.onlinebookstore.R;

import java.util.Objects;

public class BookDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        Button addtocart = findViewById(R.id.add_to_cart);

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
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailsActivity.this, HomeActivity.class);
                intent.putExtra("addToCart", true); // Đánh dấu rằng một mục đã được thêm vào giỏ hàng
                startActivity(intent);
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
        return super.onOptionsItemSelected(item);
    }
}
