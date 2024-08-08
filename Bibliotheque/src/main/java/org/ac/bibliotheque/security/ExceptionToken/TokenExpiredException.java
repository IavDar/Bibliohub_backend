package org.ac.bibliotheque.security.ExceptionToken;


import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

    public TokenExpiredException(String msg) {
        super(msg);
    }

    public TokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }
}
