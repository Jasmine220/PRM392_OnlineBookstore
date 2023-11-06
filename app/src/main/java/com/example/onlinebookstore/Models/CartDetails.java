package com.example.onlinebookstore.Models;

public class CartDetails {
    private int cartDetailsId;
    private int customerId;
    private int bookId;
    private int quantity;

    public int getCartDetailsId() {
        return cartDetailsId;
    }

    public void setCartDetailsId(int cartDetailsId) {
        this.cartDetailsId = cartDetailsId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartDetails(int cartDetailsId, int customerId, int bookId, int quantity) {
        this.cartDetailsId = cartDetailsId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
