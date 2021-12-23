package com.example.demo.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LendingRepository extends JpaRepository<Lending, Long> {

    @Query("SELECT COUNT(*) FROM Lending l WHERE l.bookObject.ID=:bookId")
    int getNumberOfBorrowedBooks(Long bookId);
}
