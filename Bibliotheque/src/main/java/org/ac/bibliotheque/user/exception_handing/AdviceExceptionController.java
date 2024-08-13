package org.ac.bibliotheque.user.exception_handing;


import lombok.RequiredArgsConstructor;
import org.ac.bibliotheque.security.Exceptions.InvalidPassword;
import org.ac.bibliotheque.security.Exceptions.InvalidTokenException;
import org.ac.bibliotheque.security.Exceptions.TokenExpiredException;
import org.ac.bibliotheque.user.exception_handing.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class AdviceExceptionController {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiExceptionInfo> userNotFoundHebdler(UserNotFoundException e) {
        return new ResponseEntity<>(new ApiExceptionInfo(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserForbidden.class)
    public ResponseEntity<ApiExceptionInfo> userBlockHandler(UserForbidden e) {
        return new ResponseEntity<>(new ApiExceptionInfo(e.getMessage()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> invalidRoleException(InvalidRoleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<String> invalidPassword(InvalidPassword ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidPassword(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> invalidPassword(TokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(EmailIsUsingException.class)
    public ResponseEntity<String> emailIsUsing(EmailIsUsingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }@ExceptionHandler(EmailIsNotValid.class)
    public ResponseEntity<String> invalidEmail(EmailIsNotValid ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PasswordIsNotValid.class)
    public ResponseEntity<String> invalidPassword(PasswordIsNotValid ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}



