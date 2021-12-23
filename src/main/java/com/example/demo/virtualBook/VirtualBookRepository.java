package com.example.demo.virtualBook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualBookRepository extends JpaRepository<VirtualBook, Long> {
}
