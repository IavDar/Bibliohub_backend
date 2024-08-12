package org.ac.bibliotheque.security.sec_service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.security.Exceptions.InvalidPassword;
import org.ac.bibliotheque.security.auth_dto.LoginRequestDto;
import org.ac.bibliotheque.security.auth_dto.LoginResponseDto;
import org.ac.bibliotheque.security.sec_dto.TokenResponseDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.UserForbidden;
import org.ac.bibliotheque.user.user_service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public LoginResponseDto login(LoginRequestDto requestDto) throws AuthException {
        String email = requestDto.getEmail();
        UserData foundUser = (UserData) userService.loadUserByUsername(email);
        if (!foundUser.isActive()){
            throw new UserForbidden("Вы заблокированы");
        } else if (bCryptPasswordEncoder.matches(requestDto.getPassword(), foundUser.getPassword())) {

            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);

            refreshStorage.put(email, refreshToken);
            return new LoginResponseDto(foundUser.getId(),foundUser.getEmail(),accessToken, refreshToken,"Успешно",foundUser.getRoles());
        } else {
            throw new InvalidPassword("Вы ввели неверный пароль");

        }



    }

    public TokenResponseDto getNewAccessToken(String inboundRefreshToken) {

        Claims refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);

        String email = refreshClaims.getSubject();
        String savedRefreshToken = refreshStorage.get(email);

        if (savedRefreshToken!=null && savedRefreshToken.equals(inboundRefreshToken)) {

            UserData user = (UserData) userService.loadUserByUsername(email);
            String accessToken = tokenService.generateAccessToken(user);
            return new TokenResponseDto(accessToken, null);
        }else {
            return new TokenResponseDto(null, null);
        }
    }








}


