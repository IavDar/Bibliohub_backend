package org.ac.bibliotheque.reservedBooks.servise;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.BookIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.CartIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserForbidden;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.ac.bibliotheque.reservedBooks.repository.ReservedListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ReservedService {

    private final ReservedListRepository wishlistRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void reservedBook(Long userId) {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getCity() == null || user.getCountry() == null || user.getName() == null || user.getUsername() == null
                || user.getStreet() == null || user.getNumber() == null || user.getZip() == null || user.getPhone() == null) {
            throw new UserForbidden("Fill in the required fields");
        }
        List<Book> booksInCart = user.getCart().getBookList();
        if (booksInCart.isEmpty()) {
            throw new CartIsEmpty("The basket is empty");
        }
        for (Book book : booksInCart) {
            if (book.getAvailable() <= 0) {
                throw new BookIsEmpty(String.format("Book %s is out of stock", book.getTitle()));
            }
            user.getReservedList().addBook(book);
            book.setQuantity(book.getQuantity() - 1);
        }
        user.getCart().clearCart();
        userRepository.save(user);
        bookRepository.saveAll(booksInCart);
    }

    @Transactional
    public void reservedListBook(Long userId, List<Book> list) {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getCity() == null || user.getCountry() == null || user.getName() == null || user.getUsername() == null
                || user.getStreet() == null || user.getNumber() == null || user.getZip() == null || user.getPhone() == null) {
            throw new UserForbidden("Fill in the required fields");
        }

        for (Book book : list) {
            Long bookId = book.getId();
            Long libraryId = book.getLibraryId();

            book = bookRepository.findByIdAndLibraryId(bookId, libraryId).orElseThrow(
                    () -> new BookTitleNotFoundException("Book not found"));
            if (book.getAvailable() <= 0) {
                throw new BookIsEmpty(String.format("Book %s is out of stock", book.getTitle()));
            }
            user.getReservedList().addBook(book);
            book.setQuantity(book.getQuantity() - 1);
        }
        user.getCart().clearCart();
        userRepository.save(user);

    }


    public List<Book> checkUserReservedBook(Long userId) {
        UserData userData = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userId)));
        return userData.getReservedList().getBooks();
    }
}
