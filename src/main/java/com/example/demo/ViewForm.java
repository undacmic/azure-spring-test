package com.example.demo;


public class ViewForm {

    ViewForm() {

    }
    ViewForm(String isbn,
             String accessKey)
    {
        this.isbn = isbn;
        this.accessKey = accessKey;
    }
    private String isbn;

    private String accessKey;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
