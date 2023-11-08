package com.example.onlinebookstore;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinebookstore.Models.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Message> messages; // Danh sách tin nhắn
    private Context context;

    public ChatAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messages.get(position);
        // Hiển thị thông tin của tin nhắn trong ViewHolder
        if (message.getType().equals("Seller")) {
            // Đây là tin nhắn từ Seller (người nhận), đặt màu cam
            holder.layout.setBackgroundResource(R.drawable.seller_background);
            holder.linearLayout.setGravity(Gravity.END);
            holder.frameLayout.setGravity(Gravity.END);

        } else {
            // Đây là tin nhắn từ Customer (người gửi), đặt màu nền mặc định (xanh)
            holder.layout.setBackgroundResource(R.drawable.customer_background);
            holder.linearLayout.setGravity(Gravity.START);
            holder.frameLayout.setGravity(Gravity.START);
        }
        holder.textViewMessageContent.setText(message.getMessageContent());
        holder.textViewDateTime.setText(message.getMessageDatetime().toString());

        // Thêm mã để hiển thị hình ảnh, ngày giờ, v.v.
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessageContent;
        TextView textViewDateTime;
        LinearLayout frameLayout;
        LinearLayout linearLayout;
        LinearLayout layout;
        // Thêm các phần tử giao diện khác tại đây

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frameLayoutMessage);
            layout = itemView.findViewById(R.id.layout);
            linearLayout = itemView.findViewById(R.id.layout_chat);
            textViewMessageContent = itemView.findViewById(R.id.textViewMessageContent);
            textViewDateTime = itemView.findViewById(R.id.textViewMessageTimestamp);
            // Khởi tạo các phần tử giao diện khác ở đây
        }
    }
}
