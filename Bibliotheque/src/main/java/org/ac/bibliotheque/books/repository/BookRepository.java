package org.ac.bibliotheque.books.repository;

import org.ac.bibliotheque.books.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
