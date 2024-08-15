package org.ac.bibliotheque.security.Exceptions;

import org.ac.bibliotheque.user.exception_handing.Exceptions.GenerateApiEcxeption;

public class InvalidTokenException extends GenerateApiEcxeption {
    public InvalidTokenException(String msg) {
        super(msg);
    }
}
