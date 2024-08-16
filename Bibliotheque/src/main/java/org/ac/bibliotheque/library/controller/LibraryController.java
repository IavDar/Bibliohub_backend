package org.ac.bibliotheque.library.controller;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
public class LibraryController {

    private LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public LibraryDto save(@RequestBody LibraryDto library) {

        return service.save(library);
    }

    @GetMapping("/{id}")
    public LibraryDto getById(@PathVariable(name = "id") Long id) {
        return service.getLibraryById(id);
    }

    @GetMapping("/all")
    public List<LibraryDto> getAll() {
        return service.getAllLibraries();
    }

    @GetMapping
    public List<LibraryDto> getByLibrarianId(@RequestParam Long librarianId) {
        return service.getLibrariesByLibrarianId(librarianId);
    }

    @PutMapping
    public LibraryDto update(@RequestBody LibraryDto library) {

        return service.update(library);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

    }

}
