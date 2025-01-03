package org.ac.bibliotheque.books.repository;

import org.ac.bibliotheque.books.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
    Book findByIsbn(String isbn);
    Book findByAuthorName(String author);
    Book findByAuthorSurname(String author);
    Book findByAuthorNameAndAuthorSurname(String authorName, String authorSurname);

    List<Book> findAllByLibraryId(Long libraryId);
    Book findByTitleAndLibraryId(String title, Long libraryId);
    List<Book> findAllByYear(String year);


    void deleteById(Long id);
    void deleteByIsbn(String isbn);
    void deleteByTitle(String title);
    Optional<Book> findByIdAndLibraryId(Long id, Long libraryId);

}
