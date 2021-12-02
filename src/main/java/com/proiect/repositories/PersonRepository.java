package com.proiect.repositories;

import com.proiect.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p.personPassword FROM Person p WHERE p.username = ?1")
    String findByUsername(String username);

    @Query("SELECT p.ID FROM Person p WHERE p.username = :username")
    int getUserIdentifier(String username);

    @Query("SELECT p.publicKey FROM Person p WHERE p.ID=:userId")
    String getPublicKey(Long userId);

    @Modifying
    @Query("UPDATE Person p SET p.publicKey=:encodedString WHERE p.ID=:userId")
    @Transactional
    void setUserKey(String encodedString, Long userId);
}
