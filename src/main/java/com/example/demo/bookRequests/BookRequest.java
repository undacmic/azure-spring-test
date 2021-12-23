package com.example.demo.bookRequests;

import com.example.demo.book.Book;
import com.example.demo.lending.Lending;
import com.example.demo.person.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookRequests")
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    @JsonManagedReference
    //@JsonIdentityInfo(
    //generator = ObjectIdGenerators.PropertyGenerator.class,
    //property = "id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idBook")
    @JsonManagedReference
    //@JsonIdentityInfo(
    //generator = ObjectIdGenerators.PropertyGenerator.class,
    //property = "id")
    private Book bookObject;

    private String requestType;

    private String requestStatus;

    private LocalDate requestTimestamp;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Book getBookObject() {
        return bookObject;
    }

    public void setBookObject(Book bookObject) {
        this.bookObject = bookObject;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDate getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDate requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof BookRequest)) {
            return false;
        }
        return ID != null && ID.equals(((BookRequest)o).ID);
    }
}
