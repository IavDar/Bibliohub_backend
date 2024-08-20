package org.ac.bibliotheque.security.sec_service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.security.Exceptions.InvalidPassword;
import org.ac.bibliotheque.security.auth_dto.LoginRequestDto;
import org.ac.bibliotheque.security.auth_dto.LoginResponseDto;
import org.ac.bibliotheque.security.sec_dto.TokenResponseDto;
import org.ac.bibliotheque.user.entity.UserData;
import org.ac.bibliotheque.user.exception_handing.Exceptions.EmailIsNotValid;
import org.ac.bibliotheque.user.exception_handing.Exceptions.PasswordIsNotValid;
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
        if (email==null||email.isEmpty()){
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!isValidEmail(email)){
            throw new EmailIsNotValid(String.format("Your email %s was entered incorrectly", email));
        }
        UserData foundUser = (UserData) userService.loadUserByUsername(email);

        if (!foundUser.isActive()){
            throw new UserForbidden("you are blocked");
        }else if (!isValidPassword(requestDto.getPassword())){
            throw new PasswordIsNotValid("The password must contain 8 characters, 1 special character, a capital letter and 1 number");
        } else if (bCryptPasswordEncoder.matches(requestDto.getPassword(), foundUser.getPassword())) {

            String accessToken = tokenService.generateAccessToken(foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);

            refreshStorage.put(email, refreshToken);
            return new LoginResponseDto(foundUser.getId(),
                    foundUser.getEmail(),
                    foundUser.getName(),
                    foundUser.getSurname(),
                    foundUser.getCountry(),
                    foundUser.getCity(),
                    foundUser.getStreet(),
                    foundUser.getNumber(),
                    foundUser.getZip(),
                    foundUser.getPhone(),
                    foundUser.getRoles(),
                    accessToken,refreshToken,
                    "you have successfully logged in");
        } else {
            throw new InvalidPassword("you entered the wrong password");

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


    private boolean isValidEmail(String email) {
        String emailRegex = "^(?!.*\\.\\.)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!_*])(?!.*\\s).{8,32}$";
        return password.matches(passwordRegex);
    }



}


