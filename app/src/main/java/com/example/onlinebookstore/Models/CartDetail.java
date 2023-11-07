package com.example.onlinebookstore.Models;

public class CartDetail {
    private Long cartDetailsId;

    private int customerId;

    private int bookId;

    private int quantity;

    public Long getCartDetailsId() {
        return cartDetailsId;
    }

    public void setCartDetailsId(Long cartDetailsId) {
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
}
