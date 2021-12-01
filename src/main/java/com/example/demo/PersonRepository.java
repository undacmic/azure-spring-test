package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p.personPassword FROM Person p WHERE p.username = ?1")
    String findByUsername(String username);

    @Query("SELECT p.ID FROM Person p WHERE p.username = :username")
    int getUserIdentifier(String username);

    @Query("UPDATE Person p SET p.publicKey = :encodedString WHERE p.ID = :userId")
    void setUserKey(String encodedString, int userId);
}
