package org.ac.bibliotheque.reservedBooks.servise;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.BookIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.CartIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserForbidden;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.ac.bibliotheque.reservedBooks.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ReservedService {

    private final WishListRepository wishlistRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void reservedBook(Long userId) {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        if (user.getCity() == null || user.getCountry() == null || user.getName() == null || user.getUsername() == null
                || user.getStreet() == null || user.getNumber() == null || user.getZip() == null || user.getPhone() == null) {
            throw new UserForbidden("Заполните нужные поля");
        }
        List<Book> booksInCart = user.getCart().getBookList();
        if (booksInCart.isEmpty()) {
            throw new CartIsEmpty("Коризина пустая");
        }
        for (Book book : booksInCart) {
            if (book.getAvailable() <= 0) {
                throw new BookIsEmpty(String.format("Книги %s нет в наличии", book.getTitle()));
            }
            user.getWishlist().addBook(book);
            book.setQuantity(book.getQuantity() - 1);
        }
        user.getCart().clearCart();
        userRepository.save(user);
        bookRepository.saveAll(booksInCart);
    }


    public List<Book> checkUserWishlist(Long userId){
        UserData userData = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(String.format("Пользователь %s не найден", userId)));
        return userData.getWishlist().getBooks();
    }
}
