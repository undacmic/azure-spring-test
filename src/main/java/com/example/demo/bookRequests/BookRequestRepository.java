package com.example.demo.bookRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.yaml.snakeyaml.events.Event;

import javax.transaction.Transactional;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {

    @Query("SELECT r FROM BookRequest r WHERE r.person.ID=:userId AND r.bookObject.ID=:bookId AND r.requestType=:type")
    BookRequest selectRequest(Long userId, Long bookId, String type);

    @Query("DELETE FROM BookRequest b where b.person.id=:id")
    void deleteAssociatedBookRequests(Long id);
}
