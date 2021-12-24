package com.example.demo.book;

import com.example.demo.bookRequests.BookRequest;
import com.example.demo.role.Role;
import com.example.demo.shelf.Shelf;
import com.fasterxml.jackson.annotation.*;
import com.example.demo.lending.Lending;

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
                int nrBooks)
    {
        this.isbn=isbn;
        this.title=title;
        this.author=author;
        this.genre=genre;
        this.numberOfPages=numberOfPages;
        this.bookStatus=bookStatus;
        this.nrBooks=nrBooks;
    }

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Long ID;

    private String isbn;

    private String title;

    private String author;

    private String genre;

    private int numberOfPages;

    private String bookStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelfID")
    private Shelf shelf;

    private int nrBooks;

    @OneToMany(mappedBy = "bookObject",
                fetch = FetchType.LAZY,
                cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<Lending> lendings = new HashSet<Lending>();

    @OneToMany(mappedBy = "bookObject",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<BookRequest> requests = new HashSet<BookRequest>();

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

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
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

    public Set<BookRequest> getRequests() {
        return requests;
    }

    public void setRequests(Set<BookRequest> requests) {
        this.requests = requests;
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
