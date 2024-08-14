package org.ac.bibliotheque.security.sec_controller;


import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.security.auth_dto.LoginRequestDto;
import org.ac.bibliotheque.security.auth_dto.LoginResponseDto;
import org.ac.bibliotheque.security.sec_dto.RefreshRequestDto;
import org.ac.bibliotheque.security.sec_dto.TokenResponseDto;
import org.ac.bibliotheque.security.sec_service.AuthService;
import org.ac.bibliotheque.user.exception_handing.ApiExceptionInfo;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            return authService.login(loginRequestDto);
        } catch (AuthException e) {
            return null;
        }
    }


    @PostMapping("/refresh")
    public TokenResponseDto getAccessToken(@RequestBody RefreshRequestDto requestDto) {
        return authService.getNewAccessToken(requestDto.getRefreshToken());
    }


    @GetMapping("/authorize")
    public String authorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "User is authorized";
        } else {
            return "User is not authorized";
        }
    }




}
