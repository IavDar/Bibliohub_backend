package org.ac.bibliotheque.cart.service;


import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.BookIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserForbidden;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    @Transactional
    public void addBookToUserCart(Long userId, Long bookId) {
        UserData userData = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", userId))
        );
        if (userData.getCity() == null || userData.getCountry() == null || userData.getName() == null || userData.getUsername() == null
                || userData.getStreet() == null || userData.getNumber() == null || userData.getZip() == null || userData.getPhone() == null) {
            throw new UserForbidden("Fill in the required fields");
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookTitleNotFoundException("Book not found"));
        if (book.getAvailable() <= 0) {
            throw new BookIsEmpty(String.format("Book %s is out of stock", book.getTitle()));
        }
        userData.getCart().addBook(book);
        userRepository.save(userData);
    }


    public List<Book> checkUserCart(Long userId) {
        UserData userData = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", userId))
        );
        return new ArrayList<>(userData.getCart().getBookList());
    }

    public List<Book> removeBookFromCart(Long userId, Long bookId) {
        UserData userData = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("User %s not found", userId)));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookTitleNotFoundException("Book not found"));

        userData.getCart().deleteBook(book);
        userRepository.save(userData);
        return new ArrayList<>(userData.getCart().getBookList());
    }


}
