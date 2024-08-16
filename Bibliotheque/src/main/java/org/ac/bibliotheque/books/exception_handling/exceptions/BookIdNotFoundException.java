package org.ac.bibliotheque.books.exception_handling.exceptions;

public class BookIdNotFoundException extends RuntimeException {

    public BookIdNotFoundException(Long id) {
        super(String.format("Book with ID %d not found", id));
    }
}
