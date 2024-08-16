package org.ac.bibliotheque.books.exception_handling.exceptions;

public class BookAuthorNotFoundException extends RuntimeException{

    public BookAuthorNotFoundException(String authorName) {
        super(String.format("Book with Author Name: %s not found", authorName));
    }
}
