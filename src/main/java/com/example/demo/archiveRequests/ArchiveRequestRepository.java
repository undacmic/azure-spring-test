package com.example.demo.archiveRequests;

import com.example.demo.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArchiveRequestRepository extends JpaRepository<ArchiveRequest, Long> {

    @Query("SELECT r FROM ArchiveRequest r WHERE r.person.id=:userId")
    ArchiveRequest getArchiveRequest(Long userId);

    @Modifying
    @Query("DELETE FROM ArchiveRequest r where r.idArchive=:id")
    @Transactional
    void deleteAssociatedArchiveRequests(Long id);

}
