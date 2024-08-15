package org.ac.bibliotheque.books.exception_handling.exceptions;

public class BookTitleNotFoundException extends RuntimeException{

    public BookTitleNotFoundException(String title) {
        super(String.format("Book with title %s not found", title));
    }
}
