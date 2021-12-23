package com.example.demo.virtualBook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/virtualbooks")
public class VirtualBookController {

    private final VirtualBookRepository virtualBookRepository;

    public VirtualBookController(VirtualBookRepository virtualBookRepository) { this.virtualBookRepository = virtualBookRepository; }

    @GetMapping("/")
    public Iterable<VirtualBook> getVirtualBooks() { return virtualBookRepository.findAll(); }
}
