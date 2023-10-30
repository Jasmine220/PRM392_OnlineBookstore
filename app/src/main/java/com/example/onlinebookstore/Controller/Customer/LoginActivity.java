package com.example.onlinebookstore.Controller.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebookstore.Client.ApiClient;
import com.example.onlinebookstore.MainActivity;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Request.LoginRequest;
import com.example.onlinebookstore.Response.LoginResponse;
import com.example.onlinebookstore.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    ApiService apiService; // Đối tượng dịch vụ API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.LoginButton);
        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.Password);
        apiService = ApiClient.getClient().create(ApiService.class); // Khởi tạo dịch vụ API
        TextView registerButton = findViewById(R.id.registerLink);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                LoginRequest loginRequest = new LoginRequest(username, password);
                Call<LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            String token = loginResponse.getToken();

                            // Lưu token hoặc thực hiện các hành động khác sau khi đăng nhập thành công
                            // Ví dụ: chuyển sang màn hình chính (MainActivity)
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.putExtra("Name", username);
                            startActivity(intent);

                            showToast("Login successfully");
                        } else {
                            showToast("Login failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Xử lý lỗi kết nối hoặc lỗi mạng ở đây
                        showToast("Login failed. Please check your network connection.");
                    }
                });
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Clear the username and password fields when the app is minimized
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}