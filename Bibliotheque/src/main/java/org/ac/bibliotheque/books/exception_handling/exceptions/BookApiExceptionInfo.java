package org.ac.bibliotheque.books.exception_handling.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookApiExceptionInfo {

    private String message;
    private String errorCode ;

}
