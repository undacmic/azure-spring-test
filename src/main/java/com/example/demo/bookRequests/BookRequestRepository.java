package com.example.demo.bookRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
}
