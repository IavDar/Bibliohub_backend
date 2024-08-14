package org.ac.bibliotheque.user.exception_handing.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN , reason = "User Is Block")
public class UserForbidden extends GenerateApiEcxeption{

    public UserForbidden(String message) {
        super(message);
    }
}
