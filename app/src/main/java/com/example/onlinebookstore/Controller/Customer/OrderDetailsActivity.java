package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView productNameTextView;
    TextView productPriceTextView;
    TextView orderStatusTextView;
    TextView orderNumberTextView;
    TextView addressTextView;
    TextView orderTimeTextView;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Lấy các TextView từ layout
        productNameTextView = findViewById(R.id.textView6);
        productPriceTextView = findViewById(R.id.textView7);
        orderStatusTextView = findViewById(R.id.status);
        orderNumberTextView = findViewById(R.id.textView4);
        addressTextView = findViewById(R.id.textView11);
        orderTimeTextView = findViewById(R.id.textView12);

        // Khởi tạo ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Thực hiện cuộc gọi API để lấy dữ liệu đơn hàng
        fetchOrderDetails();
    }

    private void fetchOrderDetails() {
        int orderId = 4;
        Call<Order> call = apiService.getOrder(orderId);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order order = response.body();
                    String status ="";
                    if (order.getOrderStatusId() == 1){
                        status = "Chưa thanh toán";
                    }
                    // Cập nhật TextView với dữ liệu từ đơn hàng
                    productNameTextView.setText("Tên sản phẩm: ");
                    productPriceTextView.setText("Giá sản phẩm: " + order.getOrderDetailsList().get(orderId).getUnit_price());
                    orderStatusTextView.setText("Trạng thái đơn hàng: " + status);
                    orderNumberTextView.setText("Mã Đơn Hàng: " + order.getOrderId());
                    addressTextView.setText("Địa chỉ: " + order.getAddress());
                    orderTimeTextView.setText("Thời gian đặt hàng: " + order.getOrderDatetime());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi mạng ở đây
                showToast("Failed to fetch order details. Please check your network connection.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}