package com.example.demo.archive;

import com.example.demo.person.Person;
import com.example.demo.role.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Archive {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long ID;

    private String readAccess;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "archiveUser")
    private Person person;

    private LocalDate expirationDate;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getReadAccess() {
        return readAccess;
    }

    public void setReadAccess(String readAccess) {
        this.readAccess = readAccess;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
