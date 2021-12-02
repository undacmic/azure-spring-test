package com.example.demo.lending;

import com.example.demo.book.Book;
import com.example.demo.person.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="lendings")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lendingsUser")
    @JsonManagedReference
    //@JsonIdentityInfo(
            //generator = ObjectIdGenerators.PropertyGenerator.class,
            //property = "id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book")
    @JsonManagedReference
    //@JsonIdentityInfo(
            //generator = ObjectIdGenerators.PropertyGenerator.class,
            //property = "id")
    private Book bookObject;

    private LocalDate lendingDate;

    private LocalDate returnDate;


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

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(!(o instanceof Lending)) {
            return false;
        }
        return ID != null && ID.equals(((Lending)o).ID);
    }
}
