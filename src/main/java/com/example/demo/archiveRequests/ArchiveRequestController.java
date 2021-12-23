package com.example.demo.archiveRequests;

import com.example.demo.ArchiveForm;
import com.example.demo.ResponseHandler;
import com.example.demo.archive.Archive;
import com.example.demo.archive.ArchiveRepository;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/archivereqs")
public class ArchiveRequestController {

    private final ArchiveRequestRepository archiveRequestRepository;
    private final PersonRepository personRepository;
    private final ArchiveRepository archiveRepository;

    public ArchiveRequestController(ArchiveRequestRepository archiveRequestRepository,
                                    PersonRepository personRepository,
                                    ArchiveRepository archiveRepository)
    {
        this.archiveRequestRepository = archiveRequestRepository;
        this.personRepository = personRepository;
        this.archiveRepository = archiveRepository;
    }

    @GetMapping("/")
    public Iterable<ArchiveRequest> getArchiveRequests() { return archiveRequestRepository.findAll(); }

    @PostMapping("/add")
    public ResponseEntity<Object> createArchive( @RequestBody ArchiveForm archiveForm)
    {

        try {
            ArchiveRequest oldRequest = archiveRequestRepository.getArchiveRequest(archiveForm.getUserId());
            Optional<Person> requestingUser = personRepository.findById(archiveForm.getUserId());
            if (oldRequest == null) {
                ArchiveRequest makeRequest = new ArchiveRequest();
                Archive dummyEntry = archiveRepository.findAll().get(0);
                makeRequest.setPerson(requestingUser.get());
                makeRequest.setRequestStatus("unapproved");
                makeRequest.setRequestType("add");
                makeRequest.setRequestTimestamp(LocalDate.now());
                makeRequest.setArchive(dummyEntry);
                archiveRequestRepository.save(makeRequest);
                return ResponseHandler.buildBorrowRequest("New request enqueued and waiting for confirmation!", HttpStatus.OK);

            }
            else
            {
                if(oldRequest.getRequestStatus().equals("approved"))
                {
                    Archive newArchive = new Archive();
                    newArchive.setPerson(requestingUser.get());
                    newArchive.setReadAccess(archiveForm.getReadAccess());
                    newArchive.setExpirationDate(archiveForm.getExpirationDate());
                    oldRequest.setArchive(newArchive);
                    archiveRequestRepository.save(oldRequest);
                    archiveRepository.save(newArchive);
                    return ResponseHandler.buildBorrowRequest("Request has finished successfully!", HttpStatus.FOUND);
                } else {
                    return ResponseHandler.buildBorrowRequest("Request hasn't been validated yet!",HttpStatus.UNAUTHORIZED);
                }
            }
        }
        catch(Exception e)
        {
            return ResponseHandler.buildErrorResponse("An error has been detected during the borrowing process!",e.getMessage(),HttpStatus.NOT_FOUND);

        }

    }
}
