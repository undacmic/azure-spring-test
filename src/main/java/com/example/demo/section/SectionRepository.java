package com.example.demo.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.sectionName=:name")
    Section getByISBN(String name);
}
