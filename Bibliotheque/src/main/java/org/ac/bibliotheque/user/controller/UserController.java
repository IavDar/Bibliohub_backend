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

    @Operation(summary = "user-registration", description = "available to everyone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "user is registered",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "email is already in use", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "password validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.registerNewUser(requestDto);

    }


    @Operation(summary = "deleting a user", description = "available only to administrator")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "user deleted"),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public UserResponseDto deleteUser(@RequestBody UserEmailDto requestDto) {
        return userService.deleteUser(requestDto);

    }

    @Operation(summary = "list all available users", description = "only available to administrator")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserData> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "assigning a role as an admin", description = "available only to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been successfully assigned as an administrator", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "208", description = "the user is already assigned as an administrator", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/set-role-admin")
    public ResponseEntity<UserResponseDto> setAdmin(@RequestBody UserEmailDto requestDto) {
        UserResponseDto responseDto = userService.changeRoleOnAdmin(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "user blocking", description = "available only to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user successfully blocked"),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "user is already blocked", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/block")
    public ResponseEntity<UserResponseDto> blockUser(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.blockUser(email);
        return ResponseEntity.ok(responseDto);
    }


    @Operation(summary = "unblock user", description = "available only to administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user successfully unblocked"),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "409", description = "user is not blocked", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping("/unlock")
    public ResponseEntity<UserResponseDto> unlock(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.unlockUser(email);
        return ResponseEntity.ok(responseDto);

    }
//
//    @GetMapping("/{email}")
//    public ResponseEntity<UserUpdateDto> findUserByEmail(@PathVariable(name = "email") UserEmailDto email) {
//        UserUpdateDto userResponseDto = userService.findUsersByEmail(email);
//        return ResponseEntity.ok(userResponseDto);
//    }

    @Operation(summary = "update user data", description = "available only to the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the user has successfully updated the data"),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
    @PutMapping
    public ResponseEntity<UserData> updateUser(@RequestBody UserUpdateDto updateDto) {
        UserData userData = userService.updateUser(updateDto);
        return ResponseEntity.ok(userData);
    }





}
