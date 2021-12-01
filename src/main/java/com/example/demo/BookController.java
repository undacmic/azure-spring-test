package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    @GetMapping("/author/{author}")
    public Iterable<Book> getBooksByAuthor(@PathVariable("author") String author) { return bookRepository.findByAuthorLike(author); }
}
