package org.ac.bibliotheque.cart.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.cart.service.CartService;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.user.user_service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "User adds a book to cart", description = "Available only to Users (USER_ROLE) for whom all fields in UpdateUser are filled in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user successfully added the book to cart"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "In order to add a book to the cart, the user must fill in his data", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Book out of stock", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/{userId}/books/{bookId}")
    public ResponseEntity<String> addBookToCart(@PathVariable Long userId,
                                                @PathVariable Long bookId) {
        cartService.addBookToUserCart(userId, bookId);
        return ResponseEntity.ok("You have successfully added the book to your cart");
    }


    @Operation(summary = "Get all books in a user's cart", description = "Available only to the User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list of books in the user's cart was successfully received"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @GetMapping("{userId}")
    public ResponseEntity<List<Book>> getBooksInUserCart(@PathVariable Long userId) {
        List<Book> books = cartService.checkUserCart(userId);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Remove a book from the basket", description = "Available only to the User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @DeleteMapping("{userId}/book/{bookId}")
    public ResponseEntity<List<Book>> removeBookFromCart(@PathVariable Long userId, @PathVariable Long bookId) {
        List<Book> books = cartService.removeBookFromCart(userId, bookId);
        return ResponseEntity.ok(books);
    }


}
