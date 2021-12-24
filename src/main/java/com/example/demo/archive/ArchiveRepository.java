package com.example.demo.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    @Modifying
    @Query("DELETE FROM Archive a where a.person.id=:id")
    @Transactional
    void deleteAssociatedArchives(Long id);
}
