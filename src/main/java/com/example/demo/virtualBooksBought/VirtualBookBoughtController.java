package com.example.demo.virtualBooksBought;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/virtualbought")
public class VirtualBookBoughtController {

    private final VirtualBookBoughtRepository virtualBookBoughtRepository;

    public VirtualBookBoughtController(VirtualBookBoughtRepository virtualBookBoughtRepository) { this.virtualBookBoughtRepository = virtualBookBoughtRepository; }

    @GetMapping("/")
    public Iterable<VirtualBookBought> getVirtualBought() { return virtualBookBoughtRepository.findAll(); }
}
