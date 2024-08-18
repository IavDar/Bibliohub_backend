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

    @Operation(summary = "Пользователь добавляет книгу в корзину", description = "Доступно только Пользователю(USER_ROLE), у которых заполнены все поля в UpdateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно добавил книгу в корзину"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "Для того что бы добавить книгу в корзину, юзер должен заполнить свои данные", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Книги нет в наличии", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/{userId}/books/{bookId}")
    public ResponseEntity<String> addBookToCart(@PathVariable Long userId,
                                                @PathVariable Long bookId) {
        cartService.addBookToUserCart(userId, bookId);
        return ResponseEntity.ok("Вы успешно добавили книгу в корзину");
    }


    @Operation(summary = "Получить все книги в корзине пользователя", description = "Доступно только Пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список книг в корзине пользователя успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @GetMapping("{userId}")
    public ResponseEntity<List<Book>> getBooksInUserCart(@PathVariable Long userId) {
        List<Book> books = cartService.checkUserCart(userId);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Удалить книгу с коризны", description = "Доступно только Пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @DeleteMapping("{userId}/book/{bookId}")
    public ResponseEntity<List<Book>> removeBookFromCart(@PathVariable Long userId, @PathVariable Long bookId) {
        List<Book> books = cartService.removeBookFromCart(userId, bookId);
        return ResponseEntity.ok(books);
    }


}
