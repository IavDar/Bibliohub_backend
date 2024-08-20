package org.ac.bibliotheque.security.sec_controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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



    @Operation(summary = "logIn", description = "available to user library and admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "authorization was successful"),
            @ApiResponse(responseCode = "422", description = "email validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "403", description = "user is blocked", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "you entered the wrong password", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "400", description = "empty email or null", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class))),
            @ApiResponse(responseCode = "422", description = "password validation failed", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiExceptionInfo.class)))})
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





}
