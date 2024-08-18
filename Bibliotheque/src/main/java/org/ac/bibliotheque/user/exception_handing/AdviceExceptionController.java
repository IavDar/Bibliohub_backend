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
        return new ResponseEntity<>(new ApiExceptionInfo(e.getMessage(),HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserForbidden.class)
    public ResponseEntity<ApiExceptionInfo> userBlockHandler(UserForbidden e) {
        return new ResponseEntity<>(new ApiExceptionInfo(e.getMessage(),HttpStatus.FORBIDDEN.toString()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiExceptionInfo> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.BAD_REQUEST.toString()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiExceptionInfo> invalidRoleException(InvalidRoleException ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.BAD_REQUEST.toString()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<ApiExceptionInfo> invalidPassword(InvalidPassword ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.BAD_REQUEST.toString()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiExceptionInfo> invalidToken(InvalidTokenException ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.UNAUTHORIZED.toString()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiExceptionInfo> TokenIsExpired(TokenExpiredException ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.UNAUTHORIZED.toString()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EmailIsUsingException.class)
    public ResponseEntity<ApiExceptionInfo> emailIsUsing(EmailIsUsingException ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.CONFLICT.toString()),HttpStatus.CONFLICT);

    }@ExceptionHandler(EmailIsNotValid.class)
    public ResponseEntity<ApiExceptionInfo> invalidEmail(EmailIsNotValid ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY.toString()),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(PasswordIsNotValid.class)
    public ResponseEntity<ApiExceptionInfo> invalidPassword(PasswordIsNotValid ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY.toString()),HttpStatus.UNPROCESSABLE_ENTITY);
    } @ExceptionHandler(UserAlredyIsBlockUnlock.class)
    public ResponseEntity<ApiExceptionInfo> blockUnlockUser(UserAlredyIsBlockUnlock ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.CONFLICT.toString()),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyAdmin.class)
    public ResponseEntity<ApiExceptionInfo> alreadyAdmin(AlreadyAdmin ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.ALREADY_REPORTED.toString()),HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(BookIsEmpty.class)
    public ResponseEntity<ApiExceptionInfo> bookIsEmpty(BookIsEmpty ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.GONE.toString()),HttpStatus.GONE);
    }

//    TODO Изменить статус
    @ExceptionHandler(CartIsEmpty.class)
    public ResponseEntity<ApiExceptionInfo> cartIsEmpty(CartIsEmpty ex) {
        return new ResponseEntity<>(new ApiExceptionInfo(ex.getMessage(),HttpStatus.GONE.toString()),HttpStatus.GONE);
    }


}



