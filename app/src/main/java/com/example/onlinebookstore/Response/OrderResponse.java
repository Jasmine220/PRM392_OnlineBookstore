package com.example.onlinebookstore.Response;

import com.example.onlinebookstore.Models.Order;

public class OrderResponse {
//    private String status;
    private Order order;

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
