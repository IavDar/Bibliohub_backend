package org.ac.bibliotheque.reservedBooks.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.user.entity.UserData;

import java.util.List;

@RequiredArgsConstructor
@Entity
@Table(name = "reserved")
@Data
public class ReservedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserData userData;

    @ManyToMany
    @JoinTable(
            name = "reserved_books",
            joinColumns = @JoinColumn(name = "reserved_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;


    public void addBook(Book book){
            books.add(book);
    }
    public void deleteBook(Book book){
            books.remove(book);
    }
}
