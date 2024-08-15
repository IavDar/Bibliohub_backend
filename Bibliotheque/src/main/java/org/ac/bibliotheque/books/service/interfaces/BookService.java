package org.ac.bibliotheque.books.service.interfaces;

import org.ac.bibliotheque.books.domain.dto.BookDto;

import java.util.List;

public interface BookService {


    BookDto addBook(BookDto book);
    BookDto update(BookDto book);
    BookDto getBookById(Long id);
    BookDto getBookByTitle(String title);
    BookDto getBookByIsbn(String isbn);
    BookDto getBookByAuthor(String author);
    BookDto getBookByAuthorName(String authorName);
    BookDto getBookByAuthorSurname(String authorSurname);

    void deleteBookById(Long id);
    void deleteBookByIsbn(String isbn);
    void deleteBookByTitle(String title);

    List<BookDto> getAllBooks();
}
