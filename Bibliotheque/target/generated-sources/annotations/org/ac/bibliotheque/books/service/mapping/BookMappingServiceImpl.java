package org.ac.bibliotheque.books.service.mapping;

import javax.annotation.processing.Generated;
import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-22T18:52:05+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class BookMappingServiceImpl implements BookMappingService {

    @Override
    public Book mapDtoToEntity(BookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setLibraryId( dto.getLibraryId() );
        book.setQuantity( dto.getQuantity() );
        book.setAvailable( dto.getAvailable() );
        book.setTitle( dto.getTitle() );
        book.setAuthorName( dto.getAuthorName() );
        book.setAuthorSurname( dto.getAuthorSurname() );
        book.setYear( dto.getYear() );
        book.setIsbn( dto.getIsbn() );
        book.setPublisher( dto.getPublisher() );

        return book;
    }

    @Override
    public BookDto mapEntityToDto(Book entity) {
        if ( entity == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setYear( entity.getYear() );
        bookDto.setIsbn( entity.getIsbn() );
        bookDto.setPublisher( entity.getPublisher() );
        bookDto.setLibraryId( entity.getLibraryId() );
        bookDto.setQuantity( entity.getQuantity() );
        bookDto.setAvailable( entity.getAvailable() );
        bookDto.setTitle( entity.getTitle() );
        bookDto.setAuthorName( entity.getAuthorName() );
        bookDto.setAuthorSurname( entity.getAuthorSurname() );

        return bookDto;
    }
}
