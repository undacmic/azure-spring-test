package com.example.demo.shelf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shelfs")
public class ShelfController {

    private final ShelfRepository shelfRepository;

    public ShelfController(ShelfRepository shelfRepository) { this.shelfRepository = shelfRepository; }

    @GetMapping("/")
    public Iterable<Shelf> getShelves() { return shelfRepository.findAll(); }
}
