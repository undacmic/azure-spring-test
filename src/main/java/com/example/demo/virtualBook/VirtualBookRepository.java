package com.example.demo.virtualBook;

import com.example.demo.virtualBooksBought.VirtualBookBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VirtualBookRepository extends JpaRepository<VirtualBook, Long> {

    @Query("SELECT v.virtualKey FROM VirtualBook v WHERE v.isbn=:isbn")
    String getByISBN(String isbn);
}
