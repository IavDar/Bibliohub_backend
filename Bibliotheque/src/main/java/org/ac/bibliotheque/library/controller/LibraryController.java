package org.ac.bibliotheque.library.controller;

import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.exception_handling.Response;
import org.ac.bibliotheque.library.exception_handling.exceptions.LibraryNotFoundException;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bibliotek")
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

    }

    @ExceptionHandler(LibraryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(LibraryNotFoundException e) {
        return new Response(e.getMessage());
    }
}
