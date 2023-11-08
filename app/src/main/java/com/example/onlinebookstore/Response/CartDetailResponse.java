package com.example.onlinebookstore.Response;

import java.io.Serializable;

public class CartDetailResponse implements Serializable {
    private long cartDetailId;
    private int customerId;
    private int bookId;
    private String bookTitle;
    private String bookImage;
    private double bookPrice;
    private int amount;

    public long getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(long cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CartDetailResponse(long cartDetailId, int customerId, int bookId, String bookTitle, String bookImage, double bookPrice, int amount) {
        this.cartDetailId = cartDetailId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.bookPrice = bookPrice;
        this.amount = amount;
    }
}
