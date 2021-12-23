package com.example.demo.lending;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lendings")
public class LendingController {

    private final LendingRepository lendingRepository;

    public LendingController(LendingRepository lendingRepository) { this.lendingRepository = lendingRepository; }

    @GetMapping("/")
    public Iterable<Lending> getLendings() {
        return lendingRepository.findAll();
    }

}
