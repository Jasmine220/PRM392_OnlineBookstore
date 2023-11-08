package com.example.onlinebookstore.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.Models.OrderDetails;
import com.example.onlinebookstore.R;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    private List<OrderDetails> orderDetailsList; // Danh sách các mục đơn hàng
    private List<Book> booksList; // Danh sách sách
    Context context;

    public OrderItemAdapter(Context context, List<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
        this.orderDetailsList = new ArrayList<>();
    }

    public void setData(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderDetails orderDetails = orderDetailsList.get(position);
        int bookId = orderDetails.getBookId();
        for (Book book : booksList) {
            if (book.getBookId() == bookId) {
                holder.bind(book, orderDetails);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView orderItemImageView;
        TextView orderItemNameTextView;
        TextView orderItemPriceTextView;
        TextView orderQuantity;

        public OrderItemViewHolder(View itemView) {
            super(itemView);
            orderItemImageView = itemView.findViewById(R.id.orderItemImageView);
            orderItemNameTextView = itemView.findViewById(R.id.orderItemNameTextView);
            orderItemPriceTextView = itemView.findViewById(R.id.orderItemPriceTextView);
            orderQuantity = itemView.findViewById(R.id.quantity);
        }

        public void bind(Book book, OrderDetails orderDetails) {
            Glide.with(itemView.getContext()).load(book.getBookImage()).into(orderItemImageView);
            orderItemNameTextView.setText(book.getBookTitle());
            orderItemPriceTextView.setText("Giá: " + orderDetails.getUnit_price() + " VND");
            orderQuantity.setText("Số lượng: x" + orderDetails.getQuantity());
        }
    }
}
