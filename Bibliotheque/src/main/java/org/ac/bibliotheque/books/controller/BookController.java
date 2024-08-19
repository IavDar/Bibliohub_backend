package org.ac.bibliotheque.books.controller;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.ResponseBook;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookAuthorNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookIsbnNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    //  CRUD Create Read Update Delete // Post Get Put Delete
    //  localhost:8080

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) {
        Book book = service.addBook(bookDto);
        return ResponseEntity.ok(book);
    }

/*  JSON answer:
{
    "id": 1,
    "title": "History",
    "author": "Bergmann",
    "ISBN": "657-8-16",
    "year": 2010,
    "library": "Central Library"
    "quantity": 1,
    "available": 1
    }
*/

    @PutMapping
    public Book update(@RequestBody BookDto book) {
        return service.update(book);
    }

    @DeleteMapping("/{id}")
    public Book delete(@PathVariable String id) {
        if (id != null) {
            Book book = service.getBookById(Long.parseLong(id));
            service.deleteBookById(Long.parseLong(id));
            return book;
        }
        return null;
    }

//    @DeleteMapping("/isbn={isbn}")
//    public BookDto delete(String isbn) {
//        if (isbn != null) {
//            BookDto book = service.getBookByIsbn(isbn));
//            service.deleteBookByIsbn(isbn);
//            return book;
//        }
//        return null;
//    }

//    @DeleteMapping("/title={title}")
//    public BookDto delete(String title) {
//        if (title != null || title.trim() != "") {
//            BookDto book = service.getBookByTitle(title);
//            service.deleteBookByTitle(title);
//            return book;
//        }
//        return null;
//    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {

        return service.getAllBooks();
    }


    @GetMapping("/search?title={title}")
    public Book getBooksByTitle(@RequestParam String title){
        return service.getBookByTitle(title);
    }

    @GetMapping("/search?isbn={isbn}")
    public Book getBooksByIsbn(@RequestParam String isbn){
        return service.getBookByIsbn(isbn);
    }


    @GetMapping("/search?author={author}")
    public Book getBooksByAuthorSurname(@RequestParam String author){
        return service.getBookByAuthorSurname(author);
    }

    @ExceptionHandler(BookTitleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBook handleException(BookTitleNotFoundException e) {
        return new ResponseBook(e.getMessage());
    }

    @ExceptionHandler(BookIsbnNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBook handleException(BookIsbnNotFoundException e) {
        return new ResponseBook(e.getMessage());
    }

    @ExceptionHandler(BookAuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBook handleException(BookAuthorNotFoundException e) {
        return new ResponseBook(e.getMessage());
    }

    /*
    @GetMapping("/books")
    public BookDto getBooksByParameter(@RequestParam String param) {

        if (param != null) {
            return null;
            //  through exception of non prapameter TODO
        } else if (service.getBookByIsbn(param) != null) {
            return  service.getBookByIsbn(param);
        } else if (service.getBookByTitle(param) != null) {
            return service.getBookByTitle(param);
        } else if ( service.getBookByAuthorName(param) != null) {
            return service.getBookByAuthorName(param) ;
        } else {
            return null;
            // TODO Exception: no book has been found
        }
    }
*/

}
