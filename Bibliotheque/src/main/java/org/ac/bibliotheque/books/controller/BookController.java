package org.ac.bibliotheque.books.controller;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

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

    @PutMapping
    public BookDto update(@RequestBody BookDto book) {
        return service.update(book);
    }

    @DeleteMapping
    public void delete(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long isbn) {
        if (isbn != null){
            service.deleteBookByIsbn(isbn);
        } else if (title != null) {
            service.deleteBookByBookName(title);
        }
    }


}
