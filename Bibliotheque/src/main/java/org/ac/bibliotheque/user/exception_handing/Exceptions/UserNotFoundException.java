package org.ac.bibliotheque.user.exception_handing.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND , reason = "User Not Found")
public class UserNotFoundException extends GenerateApiEcxeption {

    public UserNotFoundException(String message) {
        super(message);
    }
}
