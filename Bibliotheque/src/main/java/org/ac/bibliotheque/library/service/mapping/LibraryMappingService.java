package org.ac.bibliotheque.library.service.mapping;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.domain.entity.Library;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LibraryMappingService {

    @Mapping(target = "id", ignore = true)
    Library mapDtoToEntity(LibraryDto dto);

    LibraryDto mapEntityToDto(Library entity);

}
