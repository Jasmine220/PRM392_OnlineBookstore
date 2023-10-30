package com.example.onlinebookstore.Controller.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.Models.Customer;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.RegisterRequest;
import com.example.onlinebookstore.Response.LoginResponse;
import com.example.onlinebookstore.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity2 extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        editTextName = findViewById(R.id.Name);
        editTextEmail = findViewById(R.id.Email);
        editTextPhone = findViewById(R.id.Phone);
        Button register = findViewById(R.id.RegisterDoneButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                String username = getIntent().getStringExtra("username").toString();
                String password = getIntent().getStringExtra("password").toString();

                if (!isValidate(email)) {
                    showToast("Please input a valid email address.");
                } else {
                    // Create an instance of the ApiService
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Account account = new Account(username, password);
                    Customer customer = new Customer(name, email, phone);
                    // Create a RegisterRequest object to send to the API
                    RegisterRequest registerRequest = new RegisterRequest(account, customer);
                    registerRequest.setAccount(account);
                    registerRequest.setCustomer(customer);

                    // Make the registration API call
                    Call<LoginResponse> call = apiService.register(registerRequest);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                // Registration was successful
                                Intent intent = new Intent(RegisterActivity2.this, LoginActivity.class);
                                startActivity(intent);
                                showToast("Registration Successful");
                                // You can handle success actions here, such as navigating to the main screen.
                            } else {
                                // Registration failed
                                showToast("Registration Failed");
                                Log.e("RegistrationResponse", "Code: " + response.code());
                                Log.e("RegistrationResponse", "Message: " + response.message());
                                Log.e("RegistrationResponse", "Body: " + response.errorBody());
                                // You can handle failure actions here.
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // Handle network or other errors
                            showToast("Registration Failed. Please check your network connection.");
                        }
                    });
                }
            }
        });
    }
    boolean isValidate(String email){
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
