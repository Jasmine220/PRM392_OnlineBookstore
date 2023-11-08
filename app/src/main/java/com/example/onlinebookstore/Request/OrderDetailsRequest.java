package com.example.onlinebookstore.Request;

public class OrderDetailsRequest {
    private int bookId;
    private int quantity;
    private int cartDetailId;

    public int getCartDetailId() {
        return cartDetailId;
    }

    public OrderDetailsRequest(int bookId, int quantity, int cartDetailId) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.cartDetailId = cartDetailId;
    }

    public void setCartDetailId(int cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public int getBookId() {
        return bookId;
    }

    public OrderDetailsRequest(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
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
