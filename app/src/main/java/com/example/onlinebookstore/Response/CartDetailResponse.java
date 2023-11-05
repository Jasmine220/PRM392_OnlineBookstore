package com.example.onlinebookstore.Response;

public class CartDetailResponse {
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
}
