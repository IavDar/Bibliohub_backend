package org.ac.bibliotheque.books.controller;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.exception_handling.Response;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookAuthorNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookIsbnNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService service;

//    public BookController(BookService service) {
//        this.service = service;
//    }

    //  CRUD Create Read Update Delete // Post Get Put Delete
    //  localhost:8080

    @PostMapping
    public BookDto addBook(@RequestBody BookDto book) {
        return service.addBook(book);
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
    public BookDto update(@RequestBody BookDto book) {
        return service.update(book);
    }

    @DeleteMapping("/id={id}")
    public BookDto delete(String id) {
        if (id != null) {
            BookDto book = service.getBookById(Long.parseLong(id));
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
    public List<BookDto> getAllBooks() {

        return service.getAllBooks();
    }


    @GetMapping("/search?title={title}")
    public BookDto getBooksByTitle(@RequestParam String title){
        return service.getBookByTitle(title);
    }

    @GetMapping("/search?isbn={isbn}")
    public BookDto getBooksByIsbn(@RequestParam String isbn){
        return service.getBookByIsbn(isbn);
    }


    @GetMapping("/search?author={author}")
    public BookDto getBooksByAuthorSurname(@RequestParam String author){
        return service.getBookByAuthorSurname(author);
    }

    @ExceptionHandler(BookTitleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(BookTitleNotFoundException e) {
        return new Response(e.getMessage());
    }

    @ExceptionHandler(BookIsbnNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(BookIsbnNotFoundException e) {
        return new Response(e.getMessage());
    }

    @ExceptionHandler(BookAuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(BookAuthorNotFoundException e) {
        return new Response(e.getMessage());
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
