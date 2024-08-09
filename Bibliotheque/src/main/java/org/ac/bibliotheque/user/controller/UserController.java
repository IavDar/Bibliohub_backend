package org.ac.bibliotheque.user.controller;


import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.user.dto.UserRequestDto;
import org.ac.bibliotheque.user.dto.UserResponseDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.user_service.UserService;
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

    @DeleteMapping("/delete")
    public ResponseEntity<UserResponseDto> deleteUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.deleteUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public List<UserData> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/appointasadmin")
    public ResponseEntity<UserResponseDto> setAdmin(@RequestBody UserRequestDto requestDto) {
     UserResponseDto responseDto=  userService.changeRoleOnAdmin(requestDto);
      return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/block")
    public ResponseEntity<UserResponseDto> blockUser(@RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto = userService.blockUser(requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @PutMapping("/unlock")
    public ResponseEntity<UserResponseDto> unlock(@RequestBody UserRequestDto requestDto){
        UserResponseDto responseDto = userService.unlockUser(requestDto);
        return ResponseEntity.ok(responseDto);

    }

    @GetMapping("/useremail")
    public ResponseEntity<UserResponseDto> findUserByEmail(@RequestBody UserRequestDto requestDto){
        UserResponseDto userResponseDto = userService.findUsersByEmail(requestDto.getEmail());
        return  ResponseEntity.ok(userResponseDto);


    }

}
