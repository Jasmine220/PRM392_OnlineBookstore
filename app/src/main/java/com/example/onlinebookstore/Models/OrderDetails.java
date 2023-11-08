package com.example.onlinebookstore.Models;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    private int orderDetailsId;
    private int orderId;
    private int bookId;
    private int quantity;
    private double unit_price;

    public OrderDetails() {
    }

    public OrderDetails(int orderDetailsId, int orderId, int bookId, int quantity, double unit_price) {
        this.orderDetailsId = orderDetailsId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
