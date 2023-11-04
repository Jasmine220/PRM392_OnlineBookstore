package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinebookstore.R;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        String title = getIntent().getStringExtra("Title");
        String author = getIntent().getStringExtra("Author");
        String price = getIntent().getStringExtra("Price");
        String description = getIntent().getStringExtra("Description");
        int image = getIntent().getIntExtra("Image", 0);

        TextView titleTextView = findViewById(R.id.book_title);
        TextView authorTextView = findViewById(R.id.book_author);
        TextView priceTextView = findViewById(R.id.book_price);
        TextView decriptionTextView = findViewById(R.id.book_description);
        ImageView bookImage = findViewById(R.id.book_image);

        titleTextView.setText(title);
        authorTextView.setText(author);
        priceTextView.setText(price);
        decriptionTextView.setText(description);
        bookImage.setImageResource(image);
    }
}