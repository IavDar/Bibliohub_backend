package org.ac.bibliotheque.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(String msg) {
        super(msg);
    }
    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
