package com.example.onlinebookstore.Request;

public class PaymentRequest {
    private double amount;
    private String vnp_IpAddr;

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getVnp_IpAddr() {
        return vnp_IpAddr;
    }

    public void setVnp_IpAddr(String vnp_IpAddr) {
        this.vnp_IpAddr = vnp_IpAddr;
    }



    public PaymentRequest(double amount, String vnp_IpAddr) {
        this.amount = amount;
        this.vnp_IpAddr = vnp_IpAddr;
    }
}
