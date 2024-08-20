package org.ac.bibliotheque.library.service;

import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.books.repository.BookRepository;
import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.domain.entity.Library;
import org.ac.bibliotheque.library.exception_handling.exceptions.LibraryNotFoundException;
import org.ac.bibliotheque.library.exception_handling.exceptions.ValueConstraintException;
import org.ac.bibliotheque.library.repository.LibraryRepository;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.ac.bibliotheque.library.service.mapping.LibraryMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private LibraryRepository repository;
    private final LibraryMappingService mappingService;
    private BookRepository bookRepository;

    public LibraryServiceImpl(LibraryRepository repository, LibraryMappingService mappingService, BookRepository bookRepository) {
        this.repository = repository;
        this.mappingService = mappingService;
        this.bookRepository = bookRepository;
    }

    @Override
    public LibraryDto save(LibraryDto dto) {

        Library entity = mappingService.mapDtoToEntity(dto);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new ValueConstraintException(e.getMessage());
        }

        return mappingService.mapEntityToDto(entity);
    }

    @Override
    public List<LibraryDto> getAllLibraries() {
        return  repository.findAll()
                .stream()
                .map(mappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public LibraryDto getLibraryById(Long id) {

        Library library = repository.findById(id).orElse(null);
        if (library == null) {
            throw new LibraryNotFoundException(String.format("Library with id %d not found", id));
        }
        return mappingService.mapEntityToDto(library);
    }

    @Override
    public List<LibraryDto> getLibrariesByLibrarianId(Long librarianId) {
        List<LibraryDto> list = repository.findAll()
                .stream()
                .filter(x -> x.getLibrarian_id().equals(librarianId))
                .map(mappingService::mapEntityToDto)
                .toList();

        if (list.isEmpty()) {
            throw new LibraryNotFoundException(String.format("Library with librarianId %d not found", librarianId));
        }

        return list;

    }


    @Override
    @Transactional
    public LibraryDto update(LibraryDto dto) {
        Library library = repository.findById(dto.getId()).orElse(null);

        if (library == null) {
            throw new LibraryNotFoundException(String.format("Library with id %d not found", dto.getId()));
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
        repository.findById(id).orElseThrow(() -> new LibraryNotFoundException(String.format("Library with id %s not found", id)));
        List<Book> allByLibraryId = bookRepository.findAllByLibraryId(id);
        for (Book book : allByLibraryId) {
            bookRepository.delete(book);
        }
        repository.deleteById(id);
    }

}
