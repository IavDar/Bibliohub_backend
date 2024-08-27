package org.ac.bibliotheque.reservedBooks.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ac.bibliotheque.books.domain.dto.BookDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksReservedDto {

    private Long userId;
    private String name;
    private String email;
    private List<BookDto> reservedBooks;





}
