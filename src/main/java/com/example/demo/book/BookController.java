package com.example.demo.book;

import com.example.demo.BookForm;
import com.example.demo.ResponseHandler;
import com.example.demo.section.Section;
import com.example.demo.section.SectionRepository;
import com.example.demo.shelf.Shelf;
import com.example.demo.shelf.ShelfRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookRepository bookRepository;
    private final SectionRepository sectionRepository;
    private final ShelfRepository shelfRepository;

    public BookController(BookRepository bookRepository,
                          SectionRepository sectionRepository,
                          ShelfRepository shelfRepository) {
        this.bookRepository = bookRepository;
        this.sectionRepository = sectionRepository;
        this.shelfRepository = shelfRepository;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/")
    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{title}")
    public Iterable<Book> getBooksByName(@PathVariable("title") String title) { return bookRepository.findByTitleLike(title); }

    @GetMapping("/genre/{genre}")
    public Iterable<Book> getBooksByGenre(@PathVariable("genre") String genre) { return bookRepository.findByGenre(genre); }

    @GetMapping("/author/{author}")
    public Iterable<Book> getBooksByAuthor(@PathVariable("author") String author) { return bookRepository.findByAuthorLike(author); }

    @GetMapping("/isbn/{isbn}")
    public Book getBookByISBN(@PathVariable("isbn") String isbn) { return bookRepository.findByISBN(isbn).get(0); }

    @GetMapping("/{field}/sort/{type}")
    public Iterable<Book> getBooksByTitleSortedAsc(@PathVariable("field") String field, @PathVariable("type") int type)
    {
        switch (type) {
            case 1:
                return bookRepository.findAll(Sort.by(Sort.Direction.ASC, field));
            case 2:
                return bookRepository.findAll(Sort.by(Sort.Direction.DESC, field));
            default:
                return bookRepository.findAll();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") Long id)
    {
        bookRepository.deleteById(id);
        return ResponseHandler.buildBorrowRequest("Cartea a fost stearsa!",HttpStatus.OK);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") Long id, @RequestBody BookForm bookForm)
    {
        Book b = bookRepository.getById(id);
        b.setIsbn(bookForm.getIsbn());
        b.setBookStatus(bookForm.getStatus());
        b.setAuthor(bookForm.getAuthor());
        b.setGenre(bookForm.getGenre());
        b.setNrBooks(bookForm.getNumberOfBooks());
        b.setNumberOfPages(bookForm.getNumberOfPages());
        b.setTitle(bookForm.getTitle());

        Shelf wantedShelf = shelfRepository.getById(bookForm.getShelfID());
        b.setShelf(wantedShelf);
        bookRepository.save(b);

        return ResponseHandler.buildBorrowRequest(bookForm.getIsbn(),HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@RequestBody BookForm bookForm)
    {
        Book b = new Book();
        b.setIsbn(bookForm.getIsbn());
        b.setBookStatus(bookForm.getStatus());
        b.setAuthor(bookForm.getAuthor());
        b.setGenre(bookForm.getGenre());
        b.setNrBooks(bookForm.getNumberOfBooks());
        b.setNumberOfPages(bookForm.getNumberOfPages());
        b.setTitle(bookForm.getTitle());

        Shelf wantedShelf = shelfRepository.getById(bookForm.getShelfID());
        b.setShelf(wantedShelf);
        bookRepository.save(b);

        return ResponseHandler.buildBorrowRequest("Cartea a fost adaugata cu succes!",HttpStatus.OK);
    }



}
