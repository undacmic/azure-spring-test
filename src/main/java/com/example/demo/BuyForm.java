package com.example.demo;

public class BuyForm {

    BuyForm() {

    }

    BuyForm(Long idUser,
            String isbn,
            String title,
            String author,
            String genre,
            int numberOfPages,
            Long idVirtualBook)
    {
        this.idUser = idUser;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
        this.idVirtualBook = idVirtualBook;
    }

    private Long idUser;
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int numberOfPages;
    private Long idVirtualBook;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Long getIdVirtualBook() {
        return idVirtualBook;
    }

    public void setIdVirtualBook(Long idVirtualBook) {
        this.idVirtualBook = idVirtualBook;
    }
}
