package org.ac.bibliotheque.books.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Book title cannot be null")
    @NotBlank(message = "Book title cannot be empty")
    @Pattern(
            regexp = ".{2,}",
            message = "Book title should be at least 3 character length "
    )
    @Column(name = "title")
    private String title;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_surname")
    private String authorSurname;

    @Pattern(
            regexp = "[0-9]{4}",
            message = "Book year should have only 4 digits"
    )
    @Column(name = "year")
    private String year;

    @NotNull(message = "ISBN cannot be null")
    @NotBlank(message = "ISBN cannot be empty")
    @Pattern(
            regexp = "[-0-9]{13,}",
            message = "Book ISBN should be at least 13 counts length "
    )
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "library_id")
    private Long libraryId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "available")
    private Long available;

    @Column(name = "picture")
    private String picture;

}
