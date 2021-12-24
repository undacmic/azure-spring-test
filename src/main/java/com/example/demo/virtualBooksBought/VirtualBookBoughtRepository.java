package com.example.demo.virtualBooksBought;

import com.example.demo.lending.Lending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VirtualBookBoughtRepository extends JpaRepository<VirtualBookBought, Long> {

    @Query("SELECT v FROM VirtualBookBought v WHERE v.person.ID=:userId")
    List<VirtualBookBought> getBoughtBooks(Long userId);

    @Query("SELECT v FROM VirtualBookBought v WHERE v.isbn=:isbn")
    VirtualBookBought getByISBN(String isbn);

}
