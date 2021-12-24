package com.example.demo;

public class BookForm {

    BookForm() {

    }

    BookForm(String isbn,
             String title,
             String status,
             String author,
             String genre,
             int numberOfPages,
             int numberOfBooks,
             Long shelfID)
    {
        this.isbn = isbn;
        this.title=title;
        this.status = status;
        this.author = author;
        this.genre = genre;
        this.numberOfBooks = numberOfBooks;
        this.numberOfPages = numberOfPages;
        this.shelfID = shelfID;
    }

    private String isbn;
    private String title;
    private String status;
    private String author;
    private String genre;
    private int numberOfBooks;
    private int numberOfPages;
    private Long shelfID;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Long getShelfID() {
        return shelfID;
    }

    public void setShelfID(Long shelfID) {
        this.shelfID = shelfID;
    }

}
