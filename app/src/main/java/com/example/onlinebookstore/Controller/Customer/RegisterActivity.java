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
import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.R;
import com.example.onlinebookstore.Service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText username = findViewById(R.id.Username);
        EditText password = findViewById(R.id.Password);
        EditText confirm = findViewById(R.id.RePassword);
        Button register = findViewById(R.id.RegisterButton);
        TextView login = findViewById(R.id.Login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirm.getText().toString();

                if (userName != null) {  // Add a null check here
                    // Make an API call to get a list of all accounts
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<List<Account>> call = apiService.getAllAccounts();
                    call.enqueue(new Callback<List<Account>>() {
                        @Override
                        public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                            if (response.isSuccessful()) {
                                List<Account> accountList = response.body();

                                // Check if the desired username already exists in the list
                                boolean usernameExists = false;
                                for (Account account : accountList) {
                                    if (userName.equals(account.getAccountUsername())) {  // Safe to compare now
                                        usernameExists = true;
                                        break;
                                    }
                                }

                                if (usernameExists) {
                                    showToast("Username already exists");
                                } else if (!userName.isEmpty() && !pass.isEmpty() && pass.equals(confirmPass)) {
                                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                                    intent.putExtra("username", userName);
                                    intent.putExtra("password", pass);
                                    showToast("Register Successfully");
                                    startActivity(intent);
                                } else {
                                    showToast("Register Failed");
                                }
                            } else {
                                showToast("Failed to retrieve account list");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Account>> call, Throwable t) {
                            showToast("Failed to retrieve account list");
                        }
                    });
                } else {
                    showToast("Invalid username");
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}