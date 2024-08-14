package org.ac.bibliotheque.security.Exceptions;


import org.ac.bibliotheque.user.exception_handing.Exceptions.GenerateApiEcxeption;

public class TokenExpiredException extends GenerateApiEcxeption {

    public TokenExpiredException(String msg) {
        super(msg);
    }

}
