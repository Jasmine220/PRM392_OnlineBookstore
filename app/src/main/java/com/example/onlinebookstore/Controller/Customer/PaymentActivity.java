package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.CartDetails;
import com.example.onlinebookstore.Models.Customer;
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.RecycleViewAdapter.CartDetailsAdapter;
import com.example.onlinebookstore.Request.CustomerRequest;
import com.example.onlinebookstore.Request.OrderRequest;
import com.example.onlinebookstore.Request.OrderDetailsRequest;
import com.example.onlinebookstore.Request.PaymentRequest;
import com.example.onlinebookstore.Response.CartDetailResponse;
import com.example.onlinebookstore.Response.CustomerResponse;
import com.example.onlinebookstore.Response.PaymentResponse;
import com.example.onlinebookstore.Service.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private ArrayList<CartDetailResponse> cartDetailsList = new ArrayList<>();
    private TextView tvNameCustomer;
    private TextView tvPhoneCustomer;
    private EditText tvAddressCustomer;
    private RadioButton rdShippingMethodNormal;
    private RadioButton rdShippingMethodFast;

    private RadioButton rdPaymentMethodDirect;
    private RadioButton rdPaymentMethodVNPay;
    private TextView tvRawPrice;
    private TextView tvPriceShipping;
    private TextView tvPriceTotal;
    private TextView tvPriceTotalFinal;
    private Button btnOrder;
    private RecyclerView rcvCartDetails;
    private CartDetailsAdapter cartDetailsAdapter;
    ApiService apiService; // Đối tượng dịch vụ API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
//        CartDetailResponse cartDetails1 = new CartDetailResponse(3, 1, 1 ,
//                "Búp Sen Xanh (Tái bản)",
//                "https://salt.tikicdn.com/cache/750x750/ts/product/68/fd/6b/5ac6cd389e8d93894c6a4d361629e9ab.jpg.webp",
//                90000,2);
//        CartDetailResponse cartDetails2 = new CartDetailResponse(9, 1, 5,
//                "Tôi Vỡ Tan Để Ánh Sáng Ngập Tràn",
//                "https://salt.tikicdn.com/cache/750x750/ts/product/24/22/1f/0fb5dd8fa18ea639e9c4d371138693c7.jpg.webp",
//                78000, 2);
//        cartDetailsList.add(cartDetails1);
//        cartDetailsList.add(cartDetails2);

        tvNameCustomer = findViewById(R.id.tv_name_customer);
        tvPhoneCustomer = findViewById(R.id.tv_phone_customer);
        tvAddressCustomer = findViewById(R.id.edt_address_customer);
        rdShippingMethodNormal = findViewById(R.id.rd_shipping_method_normal);
        rdShippingMethodFast = findViewById(R.id.rd_shipping_method_fast);
        rdPaymentMethodDirect = findViewById(R.id.rd_payment_method_direct);
        rdPaymentMethodVNPay = findViewById(R.id.rd_payment_method_vnpay);

        tvRawPrice = findViewById(R.id.tv_raw_price);
        tvPriceShipping = findViewById(R.id.tv_price_shipping);
        tvPriceTotal = findViewById(R.id.tv_price_total);
        tvPriceTotalFinal = findViewById(R.id.tv_price_total_final);
        btnOrder = findViewById(R.id.btn_order);
        rcvCartDetails = findViewById(R.id.rcv_cart_details);

        int customerId = 0;
        Bundle bundle = getIntent().getExtras();
        System.out.println("BUNDLE: " + bundle);
        if(bundle!= null){
            customerId = bundle.getInt("customerId");
            cartDetailsList = (ArrayList<CartDetailResponse>) bundle.get("cartDetailList");
            System.out.println("CUSTOMERID: " + customerId);
            System.out.println("LIST_CART_DETAILS: " + cartDetailsList.size());
        }
        double rawPrice = 0;
        double priceShipping = 0;
        int paymentMethodId = 1;
        int shippingMethodId = 1;
        for (int i = 0; i < cartDetailsList.size(); i++) {
            rawPrice += cartDetailsList.get(i).getBookPrice();
        }
        if(rdPaymentMethodVNPay.isChecked() == true){
            paymentMethodId = 2;
            priceShipping = 15000;
        }
        else{
            priceShipping = 18000;
        }
        if(rdShippingMethodFast.isChecked() == true){
            shippingMethodId = 2;
        };
        tvRawPrice.setText("Tạm tính: " + String.valueOf(rawPrice));
        tvPriceShipping.setText("Phí vận chuyển: " + String.valueOf(priceShipping));
        tvPriceTotal.setText("Tổng: " + String.valueOf(rawPrice + priceShipping));
        tvPriceTotalFinal.setText("Tổng: " + String.valueOf(rawPrice + priceShipping));

        apiService = ApiClient.getClient().create(ApiService.class); // Khởi tạo dịch vụ API
        //GET CUSTOMER INFO
        Call<Customer> callCustomer = apiService.getCustomerById(customerId);
        callCustomer.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.isSuccessful()){
                    Customer customerResponse = response.body();
                    System.out.println("CUSTOMER_RESPONSE: " + customerResponse);
                    tvNameCustomer.setText(customerResponse.getCustomerName());
                    tvPhoneCustomer.setText(customerResponse.getCustomerPhone());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                System.out.println("ERROR");
            }
        });
        String address = tvAddressCustomer.getText().toString();


        cartDetailsAdapter = new CartDetailsAdapter(this, cartDetailsList);
        rcvCartDetails.setAdapter(cartDetailsAdapter);
        rcvCartDetails.setLayoutManager(new LinearLayoutManager(this));

        int finalPaymentMethodId = paymentMethodId;
        int finalShippingMethodId = shippingMethodId;



        int finalCustomerId = customerId;
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATE ORDER
                List<OrderDetailsRequest> detailDTOList = new ArrayList<>();
                for (int i = 0; i < cartDetailsList.size(); i++) {
                    detailDTOList.add(new OrderDetailsRequest(cartDetailsList.get(i).getBookId(), cartDetailsList.get(i).getAmount(), (int) cartDetailsList.get(i).getCartDetailId()));
                }
                OrderRequest order = new OrderRequest(finalPaymentMethodId, finalCustomerId, finalShippingMethodId, address, detailDTOList);
                Call<Order> callOrder = apiService.createOrder(order);
                callOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> callOrder, Response<Order> response) {
                        if(response.isSuccessful()){
                            Order orderResponse = response.body();
                            double amount = orderResponse.getPrice();
                            System.out.println("AMOUNT1: " + amount);
                            System.out.println("ORDER_ID: " + orderResponse.getOrderId());
                                //CREATE PAYMENT
                                Call<PaymentResponse> callPayment = apiService.createPayment(amount, "10.33.30.177", orderResponse.getOrderId());
                                if(rdPaymentMethodVNPay.isChecked() == true){
                                    callPayment.enqueue(new Callback<PaymentResponse>() {
                                        @Override
                                        public void onResponse(Call<PaymentResponse> callPayment, Response<PaymentResponse> response) {
                                            if(response.isSuccessful()){
                                                PaymentResponse paymentResponse = response.body();
                                                System.out.println("RESPONSE: " + paymentResponse.getStatus() + "-" + paymentResponse.getUrlpayment() + "-" + paymentResponse.getMessage());
                                                // Lấy URL từ phản hồi API
                                                String paymentUrl = paymentResponse.getUrlpayment();

                                                // Mở trình duyệt với URL đã lấy được
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(paymentUrl));
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PaymentResponse> callPayment, Throwable t) {
                                            System.out.println("ERROR");
                                        }
                                    });
                                }
                                else{
                                    Intent intent = new Intent(PaymentActivity.this, OrderSuccessfulActivity.class);
                                    Bundle bundle = new Bundle();
                                    System.out.println("ORDER_RESPONSE: " + orderResponse);
                                    bundle.putInt("orderId", orderResponse.getOrderId());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            }
                        }


                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        System.out.println("CREATE ORDER ERROR");
                    }
                });


            }
        });
    }
}