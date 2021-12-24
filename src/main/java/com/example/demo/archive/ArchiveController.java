package com.example.demo.archive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/archives")
public class ArchiveController {

    private final ArchiveRepository archiveRepository;

    public ArchiveController(ArchiveRepository archiveRepository) { this.archiveRepository = archiveRepository; }

    @GetMapping("/")
    public Iterable<Archive> getArchives() { return archiveRepository.findAll(); }

    @GetMapping("/private")
    public Iterable<Archive> getPrivateArchives() { return archiveRepository.getPrivateArchives(); }

}
