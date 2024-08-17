package org.ac.bibliotheque.user.exception_handing;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiExceptionInfo {
    private String message;
    private String errorCode ;
}
