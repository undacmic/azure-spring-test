package com.example.demo;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//@JsonIdentityInfo(
        //generator = ObjectIdGenerators.PropertyGenerator.class,
        //property = "id")
public class Book {

    public Book() {

    }

    public Book(String isbn,
                String title,
                String author,
                String genre,
                int numberOfPages,
                String bookStatus,
                int shelfID,
                int nrBooks)
    {
        this.isbn=isbn;
        this.title=title;
        this.author=author;
        this.genre=genre;
        this.numberOfPages=numberOfPages;
        this.bookStatus=bookStatus;
        this.shelfID=shelfID;
        this.nrBooks=nrBooks;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long ID;

    private String isbn;

    private String title;

    private String author;

    private String genre;

    private int numberOfPages;

    private String bookStatus;

    private int shelfID;

    private int nrBooks;

    @OneToMany(mappedBy = "bookObject",
                fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Lending> lendings = new HashSet<Lending>();

    public Long getID() {
        return ID;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public int getNrBooks() {
        return nrBooks;
    }

    public void setNrBooks(int nrBooks) {
        this.nrBooks = nrBooks;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Lending> getLendings() {
        return lendings;
    }

    public void setLendings(Set<Lending> lendings) {
        this.lendings = lendings;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Book)) {
            return false;
        }
        return ID != null && ID.equals(((Book)o).ID);
    }
}
