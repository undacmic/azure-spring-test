package com.example.demo.bookRequests;


import com.example.demo.lending.Lending;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requests")
public class BookRequestController {

    private final BookRequestRepository bookRequestRepository;

    public BookRequestController(BookRequestRepository bookRequestRepository) { this.bookRequestRepository = bookRequestRepository;}

    @GetMapping("/")
    public Iterable<BookRequest> getRequests() {
        return bookRequestRepository.findAll();
    }
}
