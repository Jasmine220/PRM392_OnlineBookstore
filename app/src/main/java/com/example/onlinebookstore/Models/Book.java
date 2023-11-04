package com.example.onlinebookstore.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class Book {
    private int bookId;

    private double bookPrice;

    private String bookDescription;

    private String bookTitle;
    @SerializedName("bookImage")
    private String bookImage;

    private int bookPageNumber;

    private String bookCoverType;

    private Supplier supplier;

    private Publisher publisher;

    private Category category;

    private int bookQuantity;

    private Collection<Author> authors;

    public int getBookId() {
        return bookId;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public int getBookPageNumber() {
        return bookPageNumber;
    }

    public String getBookCoverType() {
        return bookCoverType;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Category getCategory() {
        return category;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }
    // Getter and setter methods
}
