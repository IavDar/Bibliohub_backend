package org.ac.bibliotheque.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.ac.bibliotheque.library.domain.dto.LibraryDto;
import org.ac.bibliotheque.library.exception_handling.Response;
import org.ac.bibliotheque.library.service.interfaces.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
public class LibraryController {

    private LibraryService service;

    public LibraryController(LibraryService service) {
        this.service = service;
    }


    @Operation(summary = "Register library", description = "Registered user with role_library only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Required fields cannot be null or blank",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public LibraryDto save(@RequestBody LibraryDto library) {

        return service.save(library);
    }


    @Operation(summary = "Search library by id", description = "Registered user with role_library only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @GetMapping("/{id}")
    public LibraryDto getById(@PathVariable(name = "id") Long id) {
        return service.getLibraryById(id);
    }

    @Operation(summary = "List all available libraries", description = "Registered user with role_library only")
    @GetMapping("/all")
    public List<LibraryDto> getAll() {
        return service.getAllLibraries();
    }

    @Operation(summary = "Search library by librarian id", description = "Registered user with role_library only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @GetMapping
    public List<LibraryDto> getByLibrarianId(@RequestParam Long librarianId) {
        return service.getLibrariesByLibrarianId(librarianId);
    }

    @Operation(summary = "Update library", description = "Registered user with role_library only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Library data updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @PutMapping
    public LibraryDto update(@RequestBody LibraryDto library) {

        return service.update(library);
    }


    @Operation(summary = "Delete library", description = "Registered user with role_library only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

    }

}
