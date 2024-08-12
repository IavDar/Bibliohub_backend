package org.ac.bibliotheque.security.Exceptions;

import org.ac.bibliotheque.user.exception_handing.Exceptions.GenerateApiEcxeption;

public class InvalidPassword extends GenerateApiEcxeption {
    public InvalidPassword(String message) {
        super(message);
    }
}
