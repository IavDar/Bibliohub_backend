package org.ac.bibliotheque.books.service.interfaces;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;

import java.util.List;

public interface BookService {


    Book addBook(BookDto book);
    Book update(Book book);
    Book getBookById(Long id);
    List<Book> getBookByTitle(String title);
    List<Book> getBookByIsbn(String isbn);
//    BookDto getBookByAuthor(String author);
//    BookDto getBookByAuthorName(String authorName);
    List<Book> getBookByAuthorSurname(String authorSurname);

    Book deleteBookById(Long id);
    Book deleteBookByIsbn(String isbn);
    Book deleteBookByTitle(String title);

    List<Book> getAllBooks();
    List<Book> getAllBooksByLibraryId(Long libraryId);

    void deleteAllBooksByLibraryId(Long libraryId);
    void importBooksFromJson(String filePath);
}
