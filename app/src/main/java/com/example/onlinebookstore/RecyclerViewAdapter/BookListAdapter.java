package com.example.onlinebookstore.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Book> books;
    ArrayList<Book> booksFilter;
    int accountId;

    public BookListAdapter(Context context, ArrayList<Book> books, int accountId) {
        this.context = context;
        this.books = books;
        this.booksFilter = books;
        this.accountId = accountId;
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
                intent.putExtra("accountId", accountId);
                intent.putExtra("book_id", String.valueOf(book.getBookId()));
                intent.putExtra("Title", book.getBookTitle());
                intent.putExtra("Author", formatAuthors(book.getAuthors()));
                intent.putExtra("Price", String.valueOf((int)book.getBookPrice()) + " đ");
                intent.putExtra("Description", book.getBookDescription());
                intent.putExtra("Image", book.getBookImage());
                intent.putExtra("book_page_number", String.valueOf(book.getBookPageNumber()));
                intent.putExtra("book_cover_type", book.getBookCoverType());
                intent.putExtra("supplier_name", book.getSupplier().getSupplierName());
                intent.putExtra("publisher_name", book.getPublisher().getPublisherName());
                intent.putExtra("category_name", book.getCategory().getCategoryName());
                intent.putExtra("book_quantity", String.valueOf(book.getBookQuantity()));
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
        if(books == null) return 0;
        return books.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    books = booksFilter;
                } else {
                    ArrayList<Book> bookModelList = new ArrayList<>();
                    for (Book book : booksFilter) {
                        if(book.getBookTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            bookModelList.add(book);
                        }
                    }
                    books = bookModelList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = books;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                books = (ArrayList<Book>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
            price.setText(String.valueOf((int)book.getBookPrice()) + " đ");
            Glide.with(context).load(book.getBookImage()).into(image);
        }
    }
}
