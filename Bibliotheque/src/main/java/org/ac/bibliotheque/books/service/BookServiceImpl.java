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

        //Library ID check.
//        change only book by Librarian!!!

        if (repository.findByTitle(dto.getTitle()) != null) {
            System.out.println(dto.getTitle() + " already exists");
            Book book = repository.findByTitle(dto.getTitle());
            System.out.println(book);
        }

        Book book = new Book();

        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }
        if (dto.getIsbn() != null) {
            book.setIsbn(dto.getIsbn());
        }
        if (dto.getAuthorName() != null) {
            book.setAuthorName(dto.getAuthorName());
        }
        if (dto.getAuthorSurname() != null) {
            book.setAuthorSurname(dto.getAuthorSurname());
        }
        if (dto.getYear() != null) {
            book.setYear(dto.getYear());
        }
        if (dto.getPublisher() != null) {
            book.setPublisher(dto.getPublisher());
        }
        if (dto.getQuantity() != null) {
            book.setQuantity(dto.getQuantity());
        }
        if (dto.getAvailable() != null) {
            book.setAvailable(dto.getAvailable());
        }
        if (dto.getLibraryId() != null) {
            book.setLibraryId(dto.getLibraryId());
        }

        repository.save(book);

        return book;

    }

    @Override
    @Transactional
    public Book update(Book book) {
//        Book newBook = mappingService.mapDtoToEntity(book);

        Book newBook = getBookById(book.getId());

        if (newBook == null) {
            return null;
        }

        newBook.setTitle(book.getTitle());
        newBook.setAuthorName(book.getAuthorName());
        newBook.setAuthorSurname(book.getAuthorSurname());
        newBook.setYear(book.getYear());
        newBook.setIsbn(book.getIsbn());
        newBook.setPublisher(book.getPublisher());
        newBook.setQuantity(book.getQuantity());
        newBook.setAvailable(book.getAvailable());
        newBook.setLibraryId(book.getLibraryId());

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
                .filter(x -> x.getTitle().equals(title.trim()))
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
        // splitt into two parts
        if (arguments[1] == null || arguments[1].trim().isEmpty()) {
            return repository.findAll().stream()
                    .filter(book -> book.getAuthorSurname().equals(author)).toList();
        } else {
            return repository.findAll().stream()
                    .filter(book -> book.getAuthorName().equals(arguments[0])
                            && book.getAuthorSurname().equals(arguments[1]))
                    .toList();
        }
    }


//    @Override
//    public BookDto getBookByAuthorName(String authorName) {
//        Book book = repository.findByAuthorName(authorName);
//        return mappingService.mapEntityToDto(book);
//    }
//
//    @Override
//    public BookDto getBookByAuthorSurname(String authorSurname) {
//        Book book = repository.findByAuthorName(authorSurname);
//        return mappingService.mapEntityToDto(book);
//    }

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
        return repository.findAllByLibraryId(libraryId).stream()
                .toList();
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
            List<Book> books = mapper.readValue(new File(filePath), new TypeReference<List<Book>>() {});
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
