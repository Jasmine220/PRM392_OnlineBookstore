package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.onlinebookstore.Models.Order;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.OrderRequest;
import com.example.onlinebookstore.Request.OrderDetailsRequest;
import com.example.onlinebookstore.Request.PaymentRequest;
import com.example.onlinebookstore.Response.PaymentResponse;
import com.example.onlinebookstore.Service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private List<CartDetails> cartDetailsList = new ArrayList<>();
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
    ApiService apiService; // Đối tượng dịch vụ API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        CartDetails cartDetails1 = new CartDetails(3, 1, 1 ,2);
        CartDetails cartDetails2 = new CartDetails(9, 1, 5, 4);
        cartDetailsList.add(cartDetails1);
        cartDetailsList.add(cartDetails2);

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

        tvNameCustomer.setText("Dang Thuy Trang");
        tvPhoneCustomer.setText("0363965412");
        tvAddressCustomer.setText("Ha Noi");
        rdShippingMethodNormal.setChecked(true);
        rdShippingMethodFast.setChecked(false);
        rdPaymentMethodVNPay.setChecked(true);
        rdPaymentMethodDirect.setChecked(false);
        tvPriceTotal.setText("150000");
        tvPriceShipping.setText("15000");
        tvPriceTotalFinal.setText("150000");

        int paymentMethodId = 1;
        int customerId = 1;
        int shippingMethodId = 1;
        String address = "Thạch Thất, Hà Nội";
        if(rdPaymentMethodVNPay.isChecked() == true){
            paymentMethodId = 2;
        }
        if(rdShippingMethodFast.isChecked() == true){
            shippingMethodId = 2;
        };
        int finalPaymentMethodId = paymentMethodId;
        int finalShippingMethodId = shippingMethodId;

        apiService = ApiClient.getClient().create(ApiService.class); // Khởi tạo dịch vụ API

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATE ORDER
                List<OrderDetailsRequest> detailDTOList = new ArrayList<>();
                for (int i = 0; i < cartDetailsList.size(); i++) {
                    detailDTOList.add(new OrderDetailsRequest(cartDetailsList.get(i).getBookId(), cartDetailsList.get(i).getQuantity()));
                }
                OrderRequest order = new OrderRequest(finalPaymentMethodId, customerId, finalShippingMethodId, address, detailDTOList);
                Call<Order> callOrder = apiService.createOrder(order);
                callOrder.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> callOrder, Response<Order> response) {
                        if(response.isSuccessful()){
                            Order orderResponse = response.body();
                            double amount = orderResponse.getPrice();
                            System.out.println("AMOUNT1: " + amount);
                                //CREATE PAYMENT
                                Call<PaymentResponse> callPayment = apiService.createPayment(amount, "10.33.30.177");
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