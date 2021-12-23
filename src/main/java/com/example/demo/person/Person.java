package com.example.demo.person;
import com.example.demo.bookRequests.BookRequest;
import com.fasterxml.jackson.annotation.*;
import com.example.demo.lending.Lending;
import com.example.demo.role.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
//@JsonIdentityInfo(
        //generator = ObjectIdGenerators.PropertyGenerator.class,
        //property = "id")
public class Person {

    Person() {
    }
    Person(String username,
           String personPassword)
    {
        this.username=username;
        this.personPassword=personPassword;
        this.birthDate = LocalDate.now();
        this.email="";
        this.phoneNumber="";
        this.personAddress="";
        this.firstName="";
        this.lastName="";
    }
    public Person(String firstName,
                  String lastName,
                  LocalDate birthDate,
                  String personAddress,
                  String phoneNumber,
                  String email,
                  String username,
                  String personPassword) {

        this.firstName=firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.personAddress=personAddress;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.username=username;
        this.personPassword=personPassword;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String personAddress;

    private String phoneNumber;

    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personRole")
    private Role role;

    private String username;

    private String personPassword;

    private String publicKey;

    @OneToMany(mappedBy = "person",
            fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Lending> lendings = new HashSet<Lending>();

    @OneToMany(mappedBy = "person",
            fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<BookRequest> requests = new HashSet<BookRequest>();

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonPassword() {
        return personPassword;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
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
        if(!(o instanceof Person)) {
            return false;
        }
        return ID != null && ID.equals(((Person)o).ID);
    }
}
