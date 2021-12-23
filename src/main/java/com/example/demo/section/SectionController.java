package com.example.demo.section;

import com.example.demo.role.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {

    private final SectionRepository sectionRepository;

    public SectionController(SectionRepository sectionRepository) { this.sectionRepository = sectionRepository ;}

    @GetMapping("/")
    public Iterable<Section> getSections() { return sectionRepository.findAll(); }
}
