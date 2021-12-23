package com.example.demo.archiveRequests;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/archivereqs")
public class ArchiveRequestController {

    private final ArchiveRequestRepository archiveRequestRepository;

    public ArchiveRequestController(ArchiveRequestRepository archiveRequestRepository){ this.archiveRequestRepository = archiveRequestRepository; }

    @GetMapping("/")
    public Iterable<ArchiveRequest> getArchiveRequests() { return archiveRequestRepository.findAll(); }
}
