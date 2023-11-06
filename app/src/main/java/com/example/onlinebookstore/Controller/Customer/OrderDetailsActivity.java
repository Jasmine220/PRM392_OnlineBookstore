package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Book;
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.Models.OrderDetails;
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
    TextView totalTextView;
    ImageView imageBook;
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
        totalTextView = findViewById(R.id.textView10);
        imageBook = findViewById(R.id.imageView3);
        // Khởi tạo ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Thực hiện cuộc gọi API để lấy dữ liệu đơn hàng
        fetchBooks();
    }
    private List<Book> booksList; // Biến lưu danh sách sách

    private void fetchBooks() {
        Call<List<Book>> call = apiService.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    booksList = response.body();
                    // Sau khi lấy danh sách sách, bạn có thể gọi fetchOrderDetails để lấy thông tin đơn hàng
                    fetchOrderDetails();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi mạng ở đây
                showToast("Failed to fetch book list. Please check your network connection.");
            }
        });
    }
    private void fetchOrderDetails() {
        int orderId = 4;
        Call<Order> call = apiService.getOrder(orderId);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order order = response.body();
                    String status = "";
                    String address = order.getAddress();
                    if (order.getOrderStatusId() == 1) {
                        status = "Chưa thanh toán";
                    } else {
                        status = "Đã thanh toán";
                    }
                    if (order.getAddress() == null){
                        address = "Chưa có địa chỉ";
                    }
                    // Kiểm tra xem danh sách có phần tử không
                    if (order.getOrderDetailsList() != null && !order.getOrderDetailsList().isEmpty()) {
                        // Lấy phần tử đầu tiên từ danh sách
                        OrderDetails orderDetails = order.getOrderDetailsList().get(0);
                        double unitPrice = orderDetails.getUnit_price();
                        int bookId = orderDetails.getBookId();
                        for (Book book : booksList) {
                            if (book.getBookId() == bookId) {
                                // Cập nhật TextView với dữ liệu từ đơn hàng
                                Glide.with(OrderDetailsActivity.this).load(book.getBookImage()).into(imageBook);
                                productNameTextView.setText(book.getBookTitle());
                                productPriceTextView.setText("Giá: " + unitPrice);
                                orderStatusTextView.setText("Trạng thái đơn hàng: " + status);
                                orderNumberTextView.setText("Mã Đơn Hàng: " + order.getOrderId());
                                addressTextView.setText("Địa chỉ: " + address);
                                orderTimeTextView.setText("Thời gian đặt hàng: " + order.getOrderDatetime());
                                totalTextView.setText(order.getPrice().toString());
                            }
                        }
                    } else {
                        // Xử lý trường hợp danh sách rỗng
                        showToast("No order details found.");
                    }
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