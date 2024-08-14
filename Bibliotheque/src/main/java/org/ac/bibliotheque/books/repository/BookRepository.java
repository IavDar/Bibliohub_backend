package org.ac.bibliotheque.books.repository;

import org.ac.bibliotheque.books.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
    Book findByIsbn(Long isbn);
    Book findByAuthorName(String authorName);
    Book findByAuthorNameAndAuthorSurname(String authorName, String authorSurname);

    List<Book> findAllByLibraryId(Long libraryId);
    List<Book> findAllByYear(String year);


    void deleteByIsbn(String isbn);
    void deleteByTitle(String title);


}
