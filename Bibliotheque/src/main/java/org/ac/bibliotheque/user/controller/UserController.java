package org.ac.bibliotheque.user.controller;


import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.entity.Users;
import org.ac.bibliotheque.user.user_service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
       return userService.registerNewUser(requestDto);
    }


}
