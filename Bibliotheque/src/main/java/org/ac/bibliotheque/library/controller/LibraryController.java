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


    @Operation(summary = "Register library", description = "Available to user with the library role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Library registered",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Library values cannot be null or empty",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public LibraryDto save(@RequestBody LibraryDto library) {

        return service.save(library);
    }


    @Operation(summary = "Search library by id", description = "Available to user with the library role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Library found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Library not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @GetMapping("/{id}")
    public LibraryDto getById(@PathVariable(name = "id") Long id) {
        return service.getLibraryById(id);
    }

    @Operation(summary = "List all available libraries", description = "Available to user with the library role")
    @GetMapping("/all")
    public List<LibraryDto> getAll() {
        return service.getAllLibraries();
    }

    @Operation(summary = "Search library by librarian id", description = "Available to user with the library role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found library(s) by librarian id",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No libraries found with this librarian id", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @GetMapping
    public List<LibraryDto> getByLibrarianId(@RequestParam Long librarianId) {
        return service.getLibrariesByLibrarianId(librarianId);
    }

    @Operation(summary = "Update library", description = "Available to user with the library role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Library information updated",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Library not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @PutMapping
    public LibraryDto update(@RequestBody LibraryDto library) {

        return service.update(library);
    }


    @Operation(summary = "Delete library", description = "Available to user with the library role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library deleted"),
            @ApiResponse(responseCode = "404", description = "Library not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Response.class)))})

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

    }

}
