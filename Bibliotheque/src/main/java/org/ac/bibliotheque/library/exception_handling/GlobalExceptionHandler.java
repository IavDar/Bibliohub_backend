package org.ac.bibliotheque.library.exception_handling;

import org.ac.bibliotheque.library.exception_handling.exceptions.ValueConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Response> handleException(ValueConstraintException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
