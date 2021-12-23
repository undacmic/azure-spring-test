package com.example.demo.virtualBooksBought;

import com.example.demo.BuyForm;
import com.example.demo.ResponseHandler;
import com.example.demo.Utils;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import com.example.demo.virtualBook.VirtualBook;
import com.example.demo.virtualBook.VirtualBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/virtualbought")
public class VirtualBookBoughtController {

    private final VirtualBookBoughtRepository virtualBookBoughtRepository;
    private final VirtualBookRepository virtualBookRepository;
    private final PersonRepository personRepository;

    public VirtualBookBoughtController(VirtualBookBoughtRepository virtualBookBoughtRepository,
                                       VirtualBookRepository virtualBookRepository,
                                       PersonRepository personRepository)
    {
        this.virtualBookBoughtRepository = virtualBookBoughtRepository;
        this.virtualBookRepository = virtualBookRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public Iterable<VirtualBookBought> getVirtualBought() { return virtualBookBoughtRepository.findAll(); }

    @PostMapping("/buy")
    public ResponseEntity<Object> buy(@RequestBody BuyForm buyForm)
    {

        Optional<VirtualBook> boughtBook = virtualBookRepository.findById(buyForm.getIdVirtualBook());

        try {
            if (boughtBook.isPresent()) {

                Optional<Person> requestingUser = personRepository.findById(buyForm.getIdUser());
                String key = boughtBook.get().getVirtualKey();
                byte[] salt = Utils.getRandomBytes(20);
                String accesskey = Utils.getHash(key, salt);
                VirtualBookBought newlyBoughtBook = new VirtualBookBought();
                newlyBoughtBook.setAuthor(buyForm.getAuthor());
                newlyBoughtBook.setGenre(buyForm.getGenre());
                newlyBoughtBook.setIsbn(buyForm.getIsbn());
                newlyBoughtBook.setTitle(buyForm.getTitle());
                newlyBoughtBook.setNumberOfPages(buyForm.getNumberOfPages());
                newlyBoughtBook.setPerson(requestingUser.get());
                newlyBoughtBook.setAccessKey(accesskey);
                virtualBookBoughtRepository.save(newlyBoughtBook);
                return ResponseHandler.buildBorrowRequest("Cartea a fost cumparata cu succes!", HttpStatus.OK);
            } else {
                return ResponseHandler.buildBorrowRequest("Cartea dorita nu exista in format virtual!", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            return ResponseHandler.buildErrorResponse("A aparut o eroare in timpul procesului de cumparare!", e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
