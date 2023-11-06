package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.CartDetails;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.PaymentRequest;
import com.example.onlinebookstore.Response.LoginResponse;
import com.example.onlinebookstore.Response.PaymentInforResponse;
import com.example.onlinebookstore.Response.PaymentResponse;
import com.example.onlinebookstore.Service.ApiService;

import org.w3c.dom.Text;

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
    private RadioButton rdPaymentMethodDirect;
    private RadioButton rdPaymentMethodVNPay;
    private RadioButton tvShippingMethodFast;
    private ImageView ivImageBook;
    private TextView tvTitleBook;
    private TextView tvAmountBook;
    private TextView tvPriceBook;
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
        System.out.println("LIST: "+ cartDetailsList.size());
        tvNameCustomer = findViewById(R.id.tv_name_customer);
        tvPhoneCustomer = findViewById(R.id.tv_phone_customer);
        tvAddressCustomer = findViewById(R.id.edt_address_customer);
        rdShippingMethodNormal = findViewById(R.id.rd_shipping_method_normal);
        rdPaymentMethodVNPay = findViewById(R.id.rd_payment_method_vnpay);
        btnOrder = findViewById(R.id.btn_order);
        tvPriceTotal = findViewById(R.id.tv_price_total);
        tvNameCustomer.setText("Dang Thuy Trang");
        tvPhoneCustomer.setText("0363965412");
        tvAddressCustomer.setText("Ha Noi");
        rdShippingMethodNormal.setText("true");
        rdPaymentMethodVNPay.setText("true");
        tvPriceTotal.setText("150000");

        apiService = ApiClient.getClient().create(ApiService.class); // Khởi tạo dịch vụ API
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentRequest paymentRequest = new PaymentRequest(150000, "10.33.30.177");
                Call<PaymentResponse> call = apiService.createPayment(150000, "10.33.30.177");
                call.enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
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
                    public void onFailure(Call<PaymentResponse> call, Throwable t) {
                        System.out.println("ERROR");
                    }
                });
            }
        });
    }
    public List<CartDetails> getListCartDetails(List<CartDetails> cartDetailsList){
        CartDetails cartDetails1 = new CartDetails(3, 1, 1 ,2);
        CartDetails cartDetails2 = new CartDetails(9, 1, 5, 4);
        cartDetailsList.add(cartDetails1);
        cartDetailsList.add(cartDetails2);
        return cartDetailsList;
    }
}