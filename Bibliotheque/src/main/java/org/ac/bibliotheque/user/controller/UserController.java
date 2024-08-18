package org.ac.bibliotheque.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.user.dto.UserEmailDto;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.dto.UserUpdateDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.user_service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Доступно всем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Пользователь зарегистрирован",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "Имеил уже используется", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация пароля", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.registerNewUser(requestDto);

    }


    @Operation(summary = "Удаление пользователя", description = "Доступно только Адмнистратору")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Пользователь удален"),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public UserResponseDto deleteUser(@RequestBody UserEmailDto requestDto) {
        return userService.deleteUser(requestDto);

    }

    @Operation(summary = "Вывести всех доступных пользователей", description = "Доступно только администратору")
    @GetMapping
    public List<UserData> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Назначение роли админом", description = "Доступно только Адмнистратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно назначен администратором", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "208", description = "Пользователь уже назначен администратором", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/appoint-as-admin")
    public ResponseEntity<UserResponseDto> setAdmin(@RequestBody UserEmailDto requestDto) {
        UserResponseDto responseDto = userService.changeRoleOnAdmin(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Блокировка Юзера", description = "Доступно только Адмнистратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно заблокирван"),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь уже заблокирован", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/block")
    public ResponseEntity<UserResponseDto> blockUser(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.blockUser(email);
        return ResponseEntity.ok(responseDto);
    }


    @Operation(summary = "Разблокировать Юзера", description = "Доступно только Адмнистратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно разблокирован"),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь не заблокирован", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/unlock")
    public ResponseEntity<UserResponseDto> unlock(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.unlockUser(email);
        return ResponseEntity.ok(responseDto);

    }

    @GetMapping("/{email}")
    public ResponseEntity<UserUpdateDto> findUserByEmail(@PathVariable(name = "email") UserEmailDto email) {
        UserUpdateDto userResponseDto = userService.findUsersByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Обновить данные Юзера", description = "Доступно только Пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновил данные"),
            @ApiResponse(responseCode = "422", description = "Не пройдена валидация имейл", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "Пустой имеил или null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping
    public ResponseEntity<UserData> updateUser(@RequestBody UserUpdateDto updateDto) {
        UserData userData = userService.updateUser(updateDto);
        return ResponseEntity.ok(userData);
    }





}
