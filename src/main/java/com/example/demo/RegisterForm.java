package com.example.demo;

import java.time.LocalDate;

public class RegisterForm {

    RegisterForm() {}
    RegisterForm(String username,
                 String password,
                 String firstName,
                 String lastName,
                 LocalDate birthDate,
                 String personAddress,
                 String phoneNumber,
                 String email) {
        this.username=username;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.personAddress=personAddress;
        this.phoneNumber=phoneNumber;
        this.email=email;
    }

    private String username;
    private String password;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String personAddress;
    private String phoneNumber;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
