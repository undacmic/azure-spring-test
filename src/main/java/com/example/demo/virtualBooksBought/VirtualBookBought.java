package com.example.demo.virtualBooksBought;

import com.example.demo.person.Person;
import com.example.demo.virtualBook.VirtualBook;

import javax.persistence.*;

@Entity
@Table(name = "virtualBooksBought")
public class VirtualBookBought {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String isbn;

    private String title;

    private String author;

    private String genre;

    private int numberOfPages;

    private String accessKey;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private Person person;

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

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof VirtualBookBought)) {
            return false;
        }
        return ID != null && ID.equals(((VirtualBookBought)o).ID);
    }
}
