package org.ac.bibliotheque.library.service.mapping;

import javax.annotation.processing.Generated;
import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.domain.entity.Library;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-08T21:29:55+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class LibraryMappingServiceImpl implements LibraryMappingService {

    @Override
    public Library mapDtoToEntity(LibraryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Library library = new Library();

        library.setName( dto.getName() );
        library.setCountry( dto.getCountry() );
        library.setCity( dto.getCity() );
        library.setStreet( dto.getStreet() );
        library.setNumber( dto.getNumber() );
        library.setZip( dto.getZip() );
        library.setPhone( dto.getPhone() );
        library.setLibrarian_id( dto.getLibrarian_id() );

        return library;
    }

    @Override
    public LibraryDto mapEntityToDto(Library entity) {
        if ( entity == null ) {
            return null;
        }

        LibraryDto libraryDto = new LibraryDto();

        libraryDto.setId( entity.getId() );
        libraryDto.setName( entity.getName() );
        libraryDto.setCountry( entity.getCountry() );
        libraryDto.setCity( entity.getCity() );
        libraryDto.setStreet( entity.getStreet() );
        libraryDto.setNumber( entity.getNumber() );
        libraryDto.setZip( entity.getZip() );
        libraryDto.setPhone( entity.getPhone() );
        libraryDto.setLibrarian_id( entity.getLibrarian_id() );

        return libraryDto;
    }
}
