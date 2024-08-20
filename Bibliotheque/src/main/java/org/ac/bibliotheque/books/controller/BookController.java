package org.ac.bibliotheque.books.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.dto.TitleDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.ResponseBook;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookApiExceptionInfo;
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


    @Operation(summary = "Add a new book", description = "visible for library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404",
                    description = "book could not be added",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
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
    @Operation(summary = "edit a book in the library", description = "visible for library: provide id to find changeable book and other parameter to change them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been changed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404",
                    description = "book could not be find",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
    @PutMapping
    public ResponseEntity<Book> update(@RequestBody Book book) {
        Book newBook = service.update(book);
        return ResponseEntity.ok(newBook);
    }

    @Operation(summary = "delete a book from the library", description = "only for library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "book could not be find",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

            return ResponseEntity.ok(service.deleteBookById(id));

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
    @Operation(summary = "Show all books", description = "visible for all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of books have been provided",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404",
                    description = "books have been found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(service.getAllBooks());
    }


    @Operation(summary = "Look for a book in the library by title", description = "visible for all user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been found"),
            @ApiResponse(responseCode = "404",
                    description = "book could not be find",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/title={title}")
    public Book getBooksByTitle(@PathVariable String title) {
        Book book = service.getBookByTitle(title);
        return (book);
    }

    @Operation(summary = "Look for a book in the library by ISBN", description = "visible for all user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "book could not be find",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
    @GetMapping("/isbn={isbn}")
    public ResponseEntity<Book> getBooksByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(service.getBookByIsbn(isbn));
    }

    @Operation(summary = "Look for a book in the library by Author Surname, or Name and Surname divided by SPACE", description = "visible for all user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "book has been found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "book could not be find",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookApiExceptionInfo.class))),

    })
    @GetMapping("/author={author}")
    public ResponseEntity<Book> getBooksByAuthorSurname(@PathVariable String author) {
        return ResponseEntity.ok(service.getBookByAuthorSurname(author));
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
