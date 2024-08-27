package org.ac.bibliotheque.books.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Setter
@Getter
@EqualsAndHashCode
public class BookDto {


//    private Long id;
    private String title;
    private String authorName;
    private String authorSurname;
    private String year;
    private String isbn;
    private String publisher;
    private Long libraryId;
    private Long quantity;
    private Long available;
    private String picture;

    @Override
    public String toString() {
        return String.format("Book DTO: Book Name - %s, Author: - %s %s, ISBN: - %s, from Library: %d",
                title, authorName, authorSurname, isbn, libraryId );
    }
}
