package com.example.onlinebookstore.Models;

import java.util.Collection;

public class Author {
    private int authorId;

    private String authorName;
    private Collection<Book> books;

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Collection<Book> getBooks() {
        return books;
    }
}
