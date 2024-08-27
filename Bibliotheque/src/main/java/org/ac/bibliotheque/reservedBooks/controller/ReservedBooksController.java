package org.ac.bibliotheque.reservedBooks.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.reservedBooks.dto.BooksReservedDto;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.reservedBooks.servise.ReservedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserved")
@RequiredArgsConstructor
public class ReservedBooksController {

    private final ReservedService reservedService;


    @Operation(summary = "The user books the books that he initially added to the cart. PS if you use this method, create a new user and add books to the cart", description = "Доступно только Пользователю(USER_ROLE), у которых заполнены все поля в UpdateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has successfully booked books"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "In order to book a book, the user must fill in his details", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Book out of stock", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Your cart is empty", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/{userId}")
    public ResponseEntity<String> reservedBook(@PathVariable(name = "userId") Long userId) {
        reservedService.reservedBook(userId);
        return ResponseEntity.ok("You have successfully booked your books");
    }


    @Operation(summary = "Get all books that the user has booked", description = "Available to User, Library and Administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's book list successfully received"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> listUserReserved(@PathVariable Long userId) {
        List<Book> books = reservedService.checkUserReservedBook(userId);
        return ResponseEntity.ok(books);
    }


    @Operation(summary = "Method for booked books by user", description = "Available to User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has successfully booked books"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "In order to book a book, the user must fill in his details", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Book out of stock", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PostMapping("/user/{userId}")
    public ResponseEntity<String> reservedUserListBooks(@PathVariable(name = "userId") Long userId,
                                                    @RequestBody List<Book> bookIds) {
        reservedService.reservedListBook(userId, bookIds);
        return ResponseEntity.ok("You have successfully reserved your books");

    }

    @Operation(summary = "View booked books for a specific library", description = "Available to Library and Administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books of the booked user in this library"),
            @ApiResponse(responseCode = "404", description = "Library not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @GetMapping("/library/{libraryId}")
    public ResponseEntity<List<BooksReservedDto>> getUsersWithReservedBooksByLibrary(@PathVariable(name = "libraryId") Long libraryId) {
        List<BooksReservedDto> userReserveBooks = reservedService.getReservedBookLibrary(libraryId);
        return ResponseEntity.ok(userReserveBooks);
    }


}
