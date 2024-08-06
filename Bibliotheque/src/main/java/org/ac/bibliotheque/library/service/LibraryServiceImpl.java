package org.ac.bibliotheque.library.service;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.domain.entity.Library;
import org.ac.bibliotheque.library.repository.LibraryRepository;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.ac.bibliotheque.library.service.mapping.LibraryMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private LibraryRepository repository;
    private final LibraryMappingService mappingService;

    public LibraryServiceImpl(LibraryRepository repository, LibraryMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public LibraryDto save(LibraryDto dto) {
        Library entity = mappingService.mapDtoToEntity(dto);
        repository.save(entity);
        return mappingService.mapEntityToDto(entity);
    }

    @Override
    @Transactional
    public LibraryDto update(LibraryDto dto) {
        Library library = repository.findById(dto.getId()).orElse(null);

        if (library == null) {
            return null;
        }  if (dto.getName() != null) {
            library.setName(dto.getName());
        }  if (dto.getCountry() != null) {
            library.setCountry(dto.getCountry());
        }   if (dto.getCity() != null) {
            library.setCity(dto.getCity());
        }   if (dto.getStreet() != null) {
            library.setStreet(dto.getStreet());
        }   if (dto.getNumber() != null) {
            library.setNumber(dto.getNumber());
        }   if (dto.getZip() != null) {
            library.setZip(dto.getZip());
        }   if (dto.getPhone() != null) {
            library.setPhone(dto.getPhone());
        }   if (dto.getLibrarian_id() != null) {
            library.setLibrarian_id(dto.getLibrarian_id());
        }

        return mappingService.mapEntityToDto(library);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
