package org.ac.bibliotheque.library.service.interfaces;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;

import java.util.List;


public interface LibraryService {

    LibraryDto save(LibraryDto library);
    List<LibraryDto> getAllLibraries();
    LibraryDto getLibraryById(Long id);
    List<LibraryDto> getLibrariesByLibrarianId(Long librarianId);
    LibraryDto update(LibraryDto library);
    void deleteById(Long id);

}
