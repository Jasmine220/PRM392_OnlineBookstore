package com.example.onlinebookstore.Request;

import com.example.onlinebookstore.Models.Account;
import com.example.onlinebookstore.Models.Customer;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("account")
    private Account account;
    @SerializedName("customer")
    private Customer customer;

    public RegisterRequest(Account account, Customer customer) {
        this.account = account;
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}