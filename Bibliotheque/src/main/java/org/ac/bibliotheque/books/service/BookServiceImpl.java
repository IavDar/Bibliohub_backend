package org.ac.bibliotheque.books.service;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.books.service.interfaces.BookService;
import org.ac.bibliotheque.books.service.mapping.BookMappingService;
import org.springframework.transaction.annotation.Transactional;

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
    public BookDto update(BookDto dto){
        Book book = mappingService.mapDtoToEntity(dto);

        if (book == null){
            return null;
        }
        if (dto.getTitle() != null) {
            book.setTitle(dto.getTitle());
        }
        if (dto.getIsbn() != null){
            book.setIsbn(dto.getIsbn());
        }
        if (dto.getAuthorName()!= null){
            book.setAuthorName(dto.getAuthorName());
        }
        if (dto.getAuthorSurname()!= null){
            book.setAuthorSurname(dto.getAuthorSurname());
        }
        if (dto.getYear()!= null){
            book.setYear(dto.getYear());
        }
        if (dto.getPublisher()!= null){
            book.setPublisher(dto.getPublisher());
        }
        if (dto.getQuantity()!= null){
            book.setQuantity(dto.getQuantity());
        }
        if (dto.getAvailable()!= null){
            book.setAvailable(dto.getAvailable());
        }
        if (dto.getLibraryId()!= null){
            book.setLibraryId(dto.getLibraryId());
        }



        return mappingService.mapEntityToDto(book);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book dto = repository.getById(id);

        return mappingService.mapEntityToDto(dto);
    }

    @Override
    public BookDto getBookByBookName(String bookName) {
        return null;
    }

    @Override
    public BookDto getBookByIsbn(Long isbn) {
        return null;
    }

    @Override
    public BookDto getBookByAuthotName(String authorName) {
        return null;
    }

    @Override
    public BookDto getBookByAuthorSurname(String authorsurname) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteBookByIsbn(Long isbn) {

    }

    @Override
    public void deleteBookByBookName(String bookName) {

    }

//    @Override
//    public void deleteByIsbn(Long isbn) {
//        repository.delete(isbn);
//    }


}
