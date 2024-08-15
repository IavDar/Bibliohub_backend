package org.ac.bibliotheque.books.exception_handling.exceptions;

public class BookIsbnNotFoundException extends RuntimeException {

    public BookIsbnNotFoundException(String isbn) {
        super(String.format("Book with ISBN %s not found", isbn));
    }
}
