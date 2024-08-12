package org.ac.bibliotheque.books.service.interfaces;

import org.ac.bibliotheque.books.domain.dto.BookDto;

public interface BookService {


    BookDto addBook(BookDto book);
    BookDto update(BookDto book);
    BookDto getBookById(Long id);
    BookDto getBookByBookName(String bookName);
    BookDto getBookByIsbn(Long isbn);
    BookDto getBookByAuthotName(String authorName);
    BookDto getBookByAuthorSurname(String authorsurname);

    void deleteBookById(Long id);
    void deleteBookByIsbn(Long isbn);
    void deleteBookByBookName(String bookName);

}
