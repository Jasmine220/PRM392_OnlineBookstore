package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.onlinebookstore.R;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        // Lấy các TextView từ layout
        TextView productNameTextView = findViewById(R.id.textView6);
        TextView productPriceTextView = findViewById(R.id.textView7);
        TextView orderStatusTextView = findViewById(R.id.status);
        TextView orderNumberTextView = findViewById(R.id.textView4);
        TextView addressTextView = findViewById(R.id.textView11);
        TextView orderTimeTextView = findViewById(R.id.textView12);

        // Lấy dữ liệu sản phẩm từ nguồn dữ liệu (điều này cần được triển khai)
        String productName = "Tên sản phẩm"; // Thay bằng dữ liệu thực tế
        String productPrice = "Giá sản phẩm"; // Thay bằng dữ liệu thực tế
        String orderStatus = "Trạng thái đơn hàng"; // Thay bằng dữ liệu thực tế
        String orderNumber = "Mã đơn hàng"; // Thay bằng dữ liệu thực tế
        String address = "Địa chỉ giao hàng"; // Thay bằng dữ liệu thực tế
        String orderTime = "Thời gian đặt hàng"; // Thay bằng dữ liệu thực tế

        // Cập nhật các TextView với dữ liệu từ nguồn dữ liệu
        productNameTextView.setText(productName);
        productPriceTextView.setText(productPrice);
        orderStatusTextView.setText(orderStatus);
        orderNumberTextView.setText(orderNumber);
        addressTextView.setText(address);
        orderTimeTextView.setText(orderTime);
    }
}