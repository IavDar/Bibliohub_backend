package org.ac.bibliotheque.library.controller;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bibliotek")
public class LibraryController {

    private LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public LibraryDto save(@RequestBody LibraryDto library) {

        return service.save(library);
    }

    @PutMapping
    public LibraryDto update(@RequestBody LibraryDto library) {

        return service.update(library);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {

            service.deleteById(id);

    }
}
