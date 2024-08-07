package org.ac.bibliotheque.library.service.interfaces;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;


public interface LibraryService {

    LibraryDto save(LibraryDto library);
    LibraryDto update(LibraryDto library);
    void deleteById(Long id);

}
