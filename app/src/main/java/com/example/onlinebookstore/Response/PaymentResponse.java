package com.example.onlinebookstore.Response;

public class PaymentResponse {
    private String status;
    private String message;
    private String urlpayment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlpayment() {
        return urlpayment;
    }

    public void setUrlpayment(String urlpayment) {
        this.urlpayment = urlpayment;
    }
}
