package org.ac.bibliotheque.books.service;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.ac.bibliotheque.books.service.mapping.BookMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Book update(BookDto dto) {
        Book book = mappingService.mapDtoToEntity(dto);

        if (book == null) {
            return null;
        }
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


        return book;
    }

    @Override
    public Book getBookById(Long id) {
        Book book = repository.getById(id);

        return book;
    }

    @Override
    public Book getBookByTitle(String title) {
        Book book = repository.findByTitle(title);
        return (book);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = repository.findByIsbn(isbn);
        return (book);
    }

    @Override
    public Book getBookByAuthorSurname(String author) {
        String[] arguments = author.split(" ");
        // splitt into two parts
        if (arguments[1] == null) {
            Book book = repository.findByAuthorName(author);
            return (book);
        } else {
            Book book = repository.findByAuthorNameAndAuthorSurname(arguments[0], arguments[1]);
            return (book);
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
        Book book = repository.getById(id);
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

//    @Override
//    public void deleteByIsbn(Long isbn) {
//        repository.delete(isbn);
//    }


}
