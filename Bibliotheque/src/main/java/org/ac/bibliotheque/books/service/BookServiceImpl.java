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
    public BookDto addBook(BookDto dto) {
        Book entity = mappingService.mapDtoToEntity(dto);
        repository.save(entity);

        return mappingService.mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public BookDto update(BookDto dto) {
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


        return mappingService.mapEntityToDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = repository.getById(id);

        return mappingService.mapEntityToDto(book);
    }

    @Override
    public BookDto getBookByTitle(String title) {
        Book book = repository.findByTitle(title);
        return mappingService.mapEntityToDto(book);
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        Book book = repository.findByIsbn(isbn);
        return mappingService.mapEntityToDto(book);
    }

    @Override
    public BookDto getBookByAuthorSurname(String author) {
        String[] arguments = author.split(" ");
        // splitt into two parts
        if (arguments[1] == null) {
            Book book = repository.findByAuthorName(author);
            return mappingService.mapEntityToDto(book);
        } else {
            Book book = repository.findByAuthorNameAndAuthorSurname(arguments[0], arguments[1]);
            return mappingService.mapEntityToDto(book);
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
    public void deleteBookById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteBookByIsbn(String isbn) {
        repository.deleteByIsbn(isbn);
    }

    @Override
    public void deleteBookByTitle(String title) {
        repository.deleteByTitle(title);
    }

    @Override
    public List<BookDto> getAllBooks() {

        return repository.findAll().stream()
                .filter(x -> x.getAvailable() > 0)
                .map(mappingService::mapEntityToDto).toList();

    }

    @Override
    public List<BookDto> getAllBooksByLibraryId(Long libraryId) {
        return repository.findAllByLibraryId(libraryId).stream()
                .map(mappingService::mapEntityToDto).toList();
    }

    @Override
    public void deleteAllBooksByLibraryId(Long libraryId) {
        List<BookDto> allBooksByLibraryId = getAllBooksByLibraryId(libraryId);
        for (BookDto bookDto : allBooksByLibraryId) {
            deleteBookById(bookDto.getId());
        }
    }

//    @Override
//    public void deleteByIsbn(Long isbn) {
//        repository.delete(isbn);
//    }


}
