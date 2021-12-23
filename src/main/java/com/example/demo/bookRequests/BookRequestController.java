package com.example.demo.bookRequests;


import com.example.demo.RegisterForm;
import com.example.demo.RequestForm;
import com.example.demo.ResponseHandler;
import com.example.demo.book.Book;
import com.example.demo.book.BookRepository;
import com.example.demo.lending.Lending;
import com.example.demo.lending.LendingRepository;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/requests")
public class BookRequestController {

    private final BookRequestRepository bookRequestRepository;
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final LendingRepository lendingRepository;

    public BookRequestController(BookRequestRepository bookRequestRepository,
                                 BookRepository bookRepository,
                                 PersonRepository personRepository,
                                 LendingRepository lendingRepository)
    {
        this.bookRequestRepository = bookRequestRepository;
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.lendingRepository = lendingRepository;
    }

    @GetMapping("/")
    public Iterable<BookRequest> getRequests() {
        return bookRequestRepository.findAll();
    }
    @PostMapping("/borrow")
    public ResponseEntity<Object> borrowBook(@RequestBody RequestForm requestForm)
    {
        try {
            BookRequest br = bookRequestRepository.selectRequest(requestForm.getIdUser(), requestForm.getIdBook(), requestForm.getType());
            int nrBorrowedBooks = lendingRepository.getNumberOfBorrowedBooks(requestForm.getIdBook());


            if (br == null) {
                Optional<Book> wantedBook = bookRepository.findById(requestForm.getIdBook());
                if(nrBorrowedBooks<wantedBook.get().getNrBooks()) {
                    BookRequest newBr = new BookRequest();
                    Optional<Person> requestingUser = personRepository.findById(requestForm.getIdUser());
                    newBr.setBookObject(wantedBook.get());
                    newBr.setPerson(requestingUser.get());
                    newBr.setRequestStatus("unapproved");
                    newBr.setRequestType(requestForm.getType());
                    newBr.setRequestTimestamp(LocalDate.now());
                    bookRequestRepository.save(newBr);
                    return ResponseHandler.buildBorrowRequest("New request enqueued and waiting for confirmation!", HttpStatus.OK);
                }
                else
                {
                    return ResponseHandler.buildBorrowRequest("No samples available!",HttpStatus.FORBIDDEN);
                }
            } else {
                if (br.getRequestStatus().equals("approved")) {
                    Lending userRequest = new Lending();
                    userRequest.setBookObject(br.getBookObject());
                    userRequest.setPerson(br.getPerson());
                    userRequest.setLendingDate(LocalDate.now());
                    userRequest.setReturnDate(LocalDate.now().plusDays(14));
                    lendingRepository.save(userRequest);
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