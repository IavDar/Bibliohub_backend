package org.ac.bibliotheque.library.exception_handling;

import org.ac.bibliotheque.library.exception_handling.exceptions.LibraryNotFoundException;
import org.ac.bibliotheque.library.exception_handling.exceptions.ValueConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerLibrary {

    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<Response> handleException(LibraryNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValueConstraintException.class)
    public ResponseEntity<Response> handleException(ValueConstraintException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
