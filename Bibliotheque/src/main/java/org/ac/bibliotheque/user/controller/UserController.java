package org.ac.bibliotheque.user.controller;


import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.user.dto.UserEmailDto;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
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


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.registerNewUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<UserResponseDto> deleteUser(@RequestBody UserEmailDto requestDto) {
        UserResponseDto responseDto = userService.deleteUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public List<UserData> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/appoint-as-admin")
    public ResponseEntity<UserResponseDto> setAdmin(@RequestBody UserEmailDto requestDto) {
        UserResponseDto responseDto = userService.changeRoleOnAdmin(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/block")
    public ResponseEntity<UserResponseDto> blockUser(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.blockUser(email);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/unblock")
    public ResponseEntity<UserResponseDto> unlock(@RequestBody UserEmailDto email) {
        UserResponseDto responseDto = userService.unlockUser(email);
        return ResponseEntity.ok(responseDto);

    }

    @GetMapping("/{email}")
    public ResponseEntity<UserUpdateDto> findUserByEmail(@PathVariable(name = "email") UserEmailDto email) {
        UserUpdateDto userResponseDto = userService.findUsersByEmail(email);
        return ResponseEntity.ok(userResponseDto);
    }


    @PutMapping
    public ResponseEntity<UserData> updateUser(@RequestBody UserUpdateDto updateDto) {
       UserData userData = userService.updateUser(updateDto);
        return ResponseEntity.ok(userData);
    }

    @PutMapping("/{userId}/books/{bookId}")
    public void addBookToCart(@PathVariable Long userId,
                              @PathVariable Long bookId){
       userService.addBookToUserCart(userId,bookId);


    }



}
