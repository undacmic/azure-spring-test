package com.example.demo.lending;

import com.example.demo.person.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lendings")
public class LendingController {

    private final LendingRepository lendingRepository;

    public LendingController(LendingRepository lendingRepository) { this.lendingRepository = lendingRepository; }

    @GetMapping("/")
    public Iterable<Lending> getLendings() {
        return lendingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Iterable<Lending> updateUser(@PathVariable("id") Long id)
    {
        return lendingRepository.getBorrowedBooks(id);
    }

}
