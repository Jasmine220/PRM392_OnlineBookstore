package com.example.onlinebookstore.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinebookstore.Controller.Customer.BookDetailsActivity;
import com.example.onlinebookstore.Models.Author;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.R;

import java.util.ArrayList;
import java.util.Collection;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    Context context;
    ArrayList<Book> books;

    public BookListAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_booklistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("Title", book.getBookTitle());
                intent.putExtra("Author", formatAuthors(book.getAuthors()));
                intent.putExtra("Price", book.getBookPrice());
                intent.putExtra("Description", book.getBookDescription());
                intent.putExtra("Image", book.getBookImage());
                context.startActivity(intent);
            }
        });

    }

    private String formatAuthors(Collection<Author> authors) {
        // Implement a method to format the authors' names as needed.
        // For example, join the author names with commas.
        StringBuilder authorNames = new StringBuilder();
        for (Author author : authors) {
            authorNames.append(author.getAuthorName()).append(", ");
        }
        if (authorNames.length() > 2) {
            authorNames.setLength(authorNames.length() - 2); // Remove the trailing comma and space
        }
        return authorNames.toString();
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, price, author;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            author = itemView.findViewById(R.id.textAuthor);
            price = itemView.findViewById(R.id.textPrice);
            image = itemView.findViewById(R.id.imageView);
        }

        public void bind(Book book) {
            title.setText(book.getBookTitle());
            // Assuming getAuthors() returns a collection of authors, you may want to format it properly.
            author.setText(formatAuthors(book.getAuthors()));
            price.setText(String.valueOf(book.getBookPrice()));
            Glide.with(context).load(book.getBookImage()).into(image);
        }
    }
}
