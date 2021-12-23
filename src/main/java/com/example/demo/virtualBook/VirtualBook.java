package com.example.demo.virtualBook;

import com.example.demo.shelf.Shelf;

import javax.persistence.*;

@Entity
@Table(name = "virtualBooks")
public class VirtualBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String isbn;

    private String title;

    private String author;

    private String genre;

    private int numberOfPages;

    private String virtualKey;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public String getVirtualKey() {
        return virtualKey;
    }

    public void setVirtualKey(String virtualKey) {
        this.virtualKey = virtualKey;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof VirtualBook)) {
            return false;
        }
        return ID != null && ID.equals(((VirtualBook)o).ID);
    }
}
