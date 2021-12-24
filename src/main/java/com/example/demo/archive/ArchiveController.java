package com.example.demo.archive;

import com.example.demo.ResponseHandler;
import com.example.demo.archiveRequests.ArchiveRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/archives")
public class ArchiveController {

    private final ArchiveRepository archiveRepository;
    private final ArchiveRequestRepository archiveRequestRepository;

    public ArchiveController(ArchiveRepository archiveRepository,
                             ArchiveRequestRepository archiveRequestRepository)
    {
        this.archiveRepository = archiveRepository;
        this.archiveRequestRepository = archiveRequestRepository;
    }

    @GetMapping("/")
    public Iterable<Archive> getArchives() { return archiveRepository.findAll(); }

    @GetMapping("/private")
    public Iterable<Archive> getPrivateArchives() { return archiveRepository.getPrivateArchives(); }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteArchive(@PathVariable("id") Long id)
    {
        archiveRequestRepository.deleteAssociatedArchiveRequests(id);
        archiveRepository.deleteById(id);
        return ResponseHandler.buildBorrowRequest("Arhiva a fost stearsa!", HttpStatus.OK);
    }

}
