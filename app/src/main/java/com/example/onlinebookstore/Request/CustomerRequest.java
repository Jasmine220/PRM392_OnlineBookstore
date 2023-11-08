package com.example.onlinebookstore.Request;

public class CustomerRequest {
    private int customerId;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public CustomerRequest(int customerId) {
        this.customerId = customerId;
    }
}
