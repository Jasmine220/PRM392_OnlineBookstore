package com.example.onlinebookstore.Models;

import java.util.ArrayList;
import java.util.Collection;

public class Book {
    private int bookId;

    private double bookPrice;

    private String bookDescription;

    private String bookTitle;

    private String bookImage;

    private int bookPageNumber;

    private String bookCoverType;

    private Supplier supplier;

    private Publisher publisher;

    private Category category;

    private int bookQuantity;
    private ArrayList<Author> authors;
}
