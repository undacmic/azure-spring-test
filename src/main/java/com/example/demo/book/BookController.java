package com.example.demo.book;

import org.springframework.data.domain.Sort;
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



}
