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

    @PostMapping("/books")
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

    @PutMapping("/books")
    public BookDto update(@RequestBody BookDto book) {
        return service.update(book);
    }

    @DeleteMapping("/books")
    public void delete(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn) {
        if (isbn != null){
            service.deleteBookByIsbn(isbn);
        } else if (title != null) {
            service.deleteBookByTitle(title);
        }
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        // как соединить этот метод со следующим? TODO
        return service.getAllBooks();
    }


    @GetMapping("/books/title")
    public BookDto getBooksByTitle(@RequestParam String title){
        return service.getBookByTitle(title);
    }

    @GetMapping("/books/isbn")
    public BookDto getBooksByIsbn(@RequestParam String isbn){
        return service.getBookByIsbn(isbn);
    }


    @GetMapping("/books/author")
    public BookDto getBooksByAuthor(@RequestParam String author){
        return service.getBookByAuthor(author);
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
