package com.example.demo.virtualBook;

import com.example.demo.ResponseHandler;
import com.example.demo.Utils;
import com.example.demo.ViewForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/virtualbooks")
public class VirtualBookController {

    private final VirtualBookRepository virtualBookRepository;

    public VirtualBookController(VirtualBookRepository virtualBookRepository) { this.virtualBookRepository = virtualBookRepository; }

    @GetMapping("/")
    public Iterable<VirtualBook> getVirtualBooks() { return virtualBookRepository.findAll(); }

    @PostMapping("/verify")
    public ResponseEntity<Object> verifyKey(@RequestBody ViewForm viewForm)
    {
        String virtualKey = virtualBookRepository.getByISBN(viewForm.getIsbn());

        try {
            if (Utils.verifyHash(virtualKey, viewForm.getAccessKey()) == true) {
                return ResponseHandler.buildBorrowRequest("Verificarea s-a realizat cu succes!", HttpStatus.OK);
            }
            else
            {
                return ResponseHandler.buildBorrowRequest("Verificarea a esuat!",HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e)
        {
            return ResponseHandler.buildErrorResponse("A aparut o eroare in timpul procesului de verificare!",e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
