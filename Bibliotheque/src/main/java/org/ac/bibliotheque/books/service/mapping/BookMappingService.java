package org.ac.bibliotheque.books.service.mapping;

import org.ac.bibliotheque.books.domain.dto.BookDto;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMappingService {

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "active", constant = "true")
    Book mapDtoToEntity(BookDto dto);

    BookDto mapEntityToDto(Book entity);

//    BookSupplyDto mapEntityToSupplyDto(Book entity);
}
