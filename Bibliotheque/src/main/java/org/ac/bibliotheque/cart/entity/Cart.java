package org.ac.bibliotheque.cart.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.user.entity.UserData;

import java.util.List;

@Entity
@Data
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_data_id")
    @JsonBackReference
    private UserData userData;

    @ManyToMany
    @JoinTable(name = "cart-book",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> bookList;

    public void addBook(Book book) {
        bookList.add(book);
    }
    public void deleteBook(Book book){
        bookList.remove(book);
    }

    public void clearCart(){
        bookList.clear();
    }


}
