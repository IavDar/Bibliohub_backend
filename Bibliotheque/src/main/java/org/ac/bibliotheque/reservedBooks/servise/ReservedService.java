package org.ac.bibliotheque.reservedBooks.servise;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookIdNotFoundException;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookTitleNotFoundException;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.library.domain.entity.Library;
import org.ac.bibliotheque.library.exception_handling.exceptions.LibraryNotFoundException;
import org.ac.bibliotheque.library.repository.LibraryRepository;
import org.ac.bibliotheque.reservedBooks.dto.BooksReservedDto;
import org.ac.bibliotheque.reservedBooks.entity.ReservedList;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.BookIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.CartIsEmpty;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserForbidden;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
import org.ac.bibliotheque.user.user_repository.UserRepository;
import org.ac.bibliotheque.reservedBooks.repository.ReservedListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class ReservedService {

    private final ReservedListRepository reservedListRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    public void reservedBook(Long userId) {
        UserData user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getCity() == null || user.getCountry() == null || user.getName() == null || user.getUsername() == null
                || user.getStreet() == null || user.getNumber() == null || user.getZip() == null || user.getPhone() == null) {
            throw new UserForbidden("Fill in the required fields");
        }
        List<Book> booksInCart = user.getCart().getBookList();
        if (booksInCart.isEmpty()) {
            throw new CartIsEmpty("The cart is empty");
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

    public List<BooksReservedDto> getReservedBookLibrary(Long libraryId) {
        List<UserData> users = userRepository.findAll();
        List<BooksReservedDto> result = new ArrayList<>();
        libraryRepository.findById(libraryId).orElseThrow(() ->
                new LibraryNotFoundException(String.format("Library %s not found", libraryId)));

        for (UserData user : users) {
            ReservedList reservedList = reservedListRepository.findByUserDataId(user.getId()).orElseThrow(
                    () -> new UserNotFoundException("User not found")
            );
            List<BookDto> reservedBooks = reservedList.getBooks().stream()
                    .filter(book -> book.getLibraryId().equals(libraryId))
                    .map(book -> {
                        BookDto bookDto = new BookDto();
                        bookDto.setTitle(book.getTitle());
                        bookDto.setAuthorName(book.getAuthorName());
                        bookDto.setAuthorSurname(book.getAuthorSurname());
                        bookDto.setYear(book.getYear());
                        bookDto.setIsbn(book.getIsbn());
                        bookDto.setPublisher(book.getPublisher());
                        bookDto.setLibraryId(book.getLibraryId());
                        bookDto.setQuantity(book.getQuantity());
                        bookDto.setAvailable(book.getAvailable());
                        return bookDto;
                    })
                    .toList();
            if (!reservedBooks.isEmpty()) {
                BooksReservedDto booksReservedDto = new BooksReservedDto();
                booksReservedDto.setName(user.getName());
                booksReservedDto.setEmail(user.getEmail());
                booksReservedDto.setUserId(user.getId());
                booksReservedDto.setReservedBooks(reservedBooks);
                result.add(booksReservedDto);
            }


        }
        return result;

    }

    @Transactional
    public void returnBook(Long userId, Long bookId, Long libraryId) {
        UserData userData = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userId)));

        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new LibraryNotFoundException(String.format("Library %s not found", libraryId)));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookTitleNotFoundException("Book not found"));

        if (!book.getLibraryId().equals(library.getId())) {
            throw new LibraryNotFoundException("The book does not belong to the specified library");
        }

        ReservedList reservedList = userData.getReservedList();
        List<Book> bookList = reservedList.getBooks();

        Optional<Book> bookToReturn = bookList.stream()
                .filter(b -> b.getId().equals(bookId) && b.getLibraryId().equals(libraryId))
                .findFirst();

        if (bookToReturn.isPresent()) {
            bookList.remove(bookToReturn.get());
            book.setQuantity(book.getQuantity() + 1);
        } else {
            throw new BookIsEmpty(String.format("Book %s is not reserved by the user or belongs to another library", book.getTitle()));
        }

        reservedListRepository.save(reservedList);
        userRepository.save(userData);
        bookRepository.save(book);
    }

}
