package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }
}
