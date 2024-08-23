package org.ac.bibliotheque.books.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.exception_handling.exceptions.BookIdNotFoundException;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.ac.bibliotheque.books.service.mapping.BookMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    private final BookRepository repository;
    private final BookMappingService mappingService;

    public BookServiceImpl(BookRepository repository, BookMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public Book addBook(BookDto dto) {

        System.out.println("================================================================");
        System.out.println(dto.toString());

        Book book = repository.findByLibraryIdAndTitle(dto.getLibraryId(), dto.getTitle());

        if (book == null) {
            System.out.println(dto.getTitle() + " doesn't exists");
            book = new Book();
        }

        if (dto.getLibraryId() != null) {
            book.setLibraryId(dto.getLibraryId());
        }

        if (checkString(dto.getTitle())) {
            book.setTitle(dto.getTitle());
        }
        if (checkDigits(dto.getIsbn())) {
            book.setIsbn(dto.getIsbn());
        }
        if (checkString(dto.getAuthorName())) {
            book.setAuthorName(dto.getAuthorName());
        }
        if (checkString(dto.getAuthorSurname())) {
            book.setAuthorSurname(dto.getAuthorSurname());
        }
        if (checkDigits(dto.getYear())) {
            if (Integer.parseInt(dto.getYear()) > 1000
                    && Integer.parseInt(dto.getYear()) < LocalDate.now().getYear()) {
                book.setYear(dto.getYear());
            }
        }
        if (checkString(dto.getPublisher())) {
            book.setPublisher(dto.getPublisher());
        }
        if (dto.getQuantity() != null && dto.getQuantity() >= 0) {
            book.setQuantity(dto.getQuantity());
        } else {
            if (book.getQuantity() == null) {
                book.setQuantity(1L);
            } else {
                book.setQuantity(book.getQuantity() + 1);
            }
        }
        if (dto.getAvailable() != null && dto.getAvailable() >= 0) {
            if (dto.getAvailable() > book.getQuantity()) {
                book.setAvailable(dto.getQuantity());
            } else {
                book.setAvailable(dto.getAvailable());
            }
        } else {
            if (book.getAvailable() == null) {
                book.setAvailable(1L);
            } else {
                book.setAvailable(book.getAvailable() + 1);
            }
        }
        if (checkString(dto.getPicture())) {
            book.setPicture(dto.getPicture());
        }

        repository.save(book);

        return book;

    }

    private boolean checkString(String text) {
        if (text != null && !text.trim().isEmpty()) {
            return true;
        } else return false;
    }

    private boolean checkDigits(String number) {
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Book update(Book book) {
//        Book newBook = mappingService.mapDtoToEntity(book);

        Book newBook = getBookById(book.getId());

        if (newBook == null) {
            System.out.println("nothing to update");
            return null;
        }
        newBook.setLibraryId(book.getLibraryId());

        if (checkString(newBook.getTitle())) {
            newBook.setTitle(book.getTitle());
        }
        if (checkString(newBook.getAuthorName())) {
            newBook.setAuthorName(book.getAuthorName());
        }
        if (checkString(newBook.getAuthorSurname())) {
            newBook.setAuthorSurname(book.getAuthorSurname());
        }
        if (checkDigits(newBook.getYear())) {
            newBook.setYear(book.getYear());
        }
        if (checkDigits(newBook.getIsbn())) {
            newBook.setIsbn(book.getIsbn());
        }
        if (checkString(newBook.getPublisher())) {
            newBook.setPublisher(book.getPublisher());
        }
        if (book.getQuantity() != null && book.getQuantity() >= 0) {
            newBook.setQuantity(book.getQuantity());
        } else {
            if (newBook.getQuantity() == null) {
                book.setQuantity(1L);
            }
        }
        if (book.getAvailable() != null && book.getAvailable() >= 0) {
            if (book.getAvailable() > newBook.getQuantity()) {
                newBook.setAvailable(newBook.getQuantity());
            } else {
                newBook.setAvailable(book.getAvailable());
            }
        } else {
            if (newBook.getAvailable() == null) {
                book.setAvailable(1L);
            }
        }
        newBook.setAvailable(book.getAvailable());
        if (checkString(book.getPicture())) {
            newBook.setPicture(book.getPicture());
        }
        repository.save(newBook);

        return newBook;
    }

    @Override
    public Book getBookById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new BookIdNotFoundException(id));
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        return repository.findAll().stream()
                .filter(x -> x.getTitle().contains(title.trim()))
                .toList();
    }

    @Override
    public List<Book> getBookByIsbn(String isbn) {
        return repository.findAll().stream()
                .filter(x -> x.getIsbn().equals(isbn.trim()))
                .toList();
    }

    @Override
    public List<Book> getBookByAuthorSurname(String author) {

        String[] arguments = author.split(" ");
        // split into two parts
        if (arguments.length == 1) {
            List<Book> bookList = repository.findAll().stream()
                    .filter(book -> book.getAuthorSurname().equalsIgnoreCase(arguments[0])).toList();
            if (bookList.isEmpty()) {
                return repository.findAll().stream()
                        .filter(book -> book.getAuthorName().equalsIgnoreCase(arguments[0])).toList();
            }
            return bookList;
        } else if (arguments.length >= 2) {
            return repository.findAll().stream()
                    .filter(book -> book.getAuthorName().equalsIgnoreCase(arguments[0])
                            && book.getAuthorSurname().equalsIgnoreCase(arguments[1]))
                    .toList();
        } else {
            return null;
        }
    }

    @Override
    public Book deleteBookById(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new BookIdNotFoundException(id));
        repository.deleteById(id);
        return book;
    }

    @Override
    public Book deleteBookByIsbn(String isbn) {
        Book book = repository.findByIsbn(isbn);
        repository.deleteByIsbn(isbn);
        return book;
    }

    @Override
    public Book deleteBookByTitle(String title) {
        Book book = repository.findByTitle(title);
        repository.deleteByTitle(title);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {

        return repository.findAll().stream()
                .filter(x -> x.getAvailable() > 0)
                .toList();

    }

    @Override
    public List<Book> getAllBooksByLibraryId(Long libraryId) {
        System.out.println(repository.findAllByLibraryId(libraryId));
        return repository.findAllByLibraryId(libraryId);
    }

    @Override
    public void deleteAllBooksByLibraryId(Long libraryId) {
        List<Book> allBooksByLibraryId = getAllBooksByLibraryId(libraryId);
        for (Book book : allBooksByLibraryId) {
            deleteBookById(book.getId());
        }
    }

    public void importBooksFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(filePath);
        try {
            List<Book> books = mapper.readValue(new File(filePath), new TypeReference<List<Book>>() {
            });
            repository.saveAll(books);
            System.out.println("List of books imported successfully");
        } catch (IOException e) {
            String message = e.getMessage();
            System.out.println("something went wrong, no data imported");
        }
    }

//    @Override
//    public void deleteByIsbn(Long isbn) {
//        repository.delete(isbn);
//    }


}
