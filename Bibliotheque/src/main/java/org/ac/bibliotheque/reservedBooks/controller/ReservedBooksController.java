package org.ac.bibliotheque.reservedBooks.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.books.domain.entity.Book;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.reservedBooks.servise.ReservedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class ReservedBooksController {

    private final ReservedService reservedService;


    @Operation(summary = "Пользователь бронирует книги, те что он добавил изначавльно в коризну PS если будете использовать этот метод, создайте нового пользователя и добавьте книги в корзину", description = "Доступно только Пользователю(USER_ROLE), у которых заполнены все поля в UpdateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно забронировал книги"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "Для того что бы забронировать книгу, юзер должен заполнить свои данные", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Книги нет в наличии", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "410", description = "Ваша Корзина пустая", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/{userId}")
    public ResponseEntity<String> reservedBook(@PathVariable(name = "userId") Long userId) {
        reservedService.reservedBook(userId);
        return ResponseEntity.ok("Вы успешно забронировали книги");
    }


    @Operation(summary = "Получить все книги которые забронировал пользователь", description = "Доступно Пользователю,Библиотеке и Админу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список книг пользователя успешно получен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @GetMapping("{userId}")
    public ResponseEntity<List<Book>> getBooksInUserCart(@PathVariable Long userId) {
        List<Book> books = reservedService.checkUserWishlist(userId);
        return ResponseEntity.ok(books);
    }
}
