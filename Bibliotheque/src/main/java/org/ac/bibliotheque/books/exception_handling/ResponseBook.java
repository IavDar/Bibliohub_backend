package org.ac.bibliotheque.books.exception_handling;

import java.util.Objects;

public class ResponseBook {

    private String message;

    public ResponseBook(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBook response = (ResponseBook) o;
        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }

    @Override
    public String toString() {
        return "Response:" + message;
    }
}
