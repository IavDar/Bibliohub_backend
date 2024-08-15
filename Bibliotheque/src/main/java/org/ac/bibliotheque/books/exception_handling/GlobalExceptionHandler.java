package org.ac.bibliotheque.books.exception_handling;

import org.ac.bibliotheque.books.exception_handling.exceptions.BookAuthorNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookIsbnNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookTitleNotFoundException.class)
    public ResponseEntity<Response> handleException(BookTitleNotFoundException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookIsbnNotFoundException.class)
    public ResponseEntity<Response> handleException(BookIsbnNotFoundException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BookAuthorNotFoundException.class)
    public ResponseEntity<Response> handleException(BookAuthorNotFoundException                                                                ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
