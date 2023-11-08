package com.example.onlinebookstore.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private int orderId;
    private int paymentMethodId;
    private int sellerId;
    private int customerId;

    private Double price;

    private int orderStatusId;
    private Date orderDatetime;
    private Date shippedDatetime;
    private int shippingMethodId;

    private String address;

    private List<OrderDetails> orderDetailsList;

    public Order() {
    }

    public Order(int orderId, int paymentMethodId, int sellerId, int customerId, Double price, int orderStatusId, Date orderDatetime, Date shippedDatetime, int shippingMethodId, String address, List<OrderDetails> orderDetailsList) {
        this.orderId = orderId;
        this.paymentMethodId = paymentMethodId;
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.price = price;
        this.orderStatusId = orderStatusId;
        this.orderDatetime = orderDatetime;
        this.shippedDatetime = shippedDatetime;
        this.shippingMethodId = shippingMethodId;
        this.address = address;
        this.orderDetailsList = orderDetailsList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Date getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(Date orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public Date getShippedDatetime() {
        return shippedDatetime;
    }

    public void setShippedDatetime(Date shippedDatetime) {
        this.shippedDatetime = shippedDatetime;
    }

    public int getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(int shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
