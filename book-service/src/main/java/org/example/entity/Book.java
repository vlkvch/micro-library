package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {

    /**
     * Books's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    /**
     * Book's ISBN.
     */
    @Column(unique = true)
    @NotNull(message = "The ISBN must not be null.")
    @Pattern(regexp = "(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+")
    private String isbn;

    /**
     * Book's title.
     */
    @Column
    @NotNull(message = "The title must not be null.")
    private String title;

    /**
     * Book's author
     */
    @Column
    @NotNull(message = "The genre must not be null.")
    private String genre;

    /**
     * Book's description.
     */
    @Column
    @NotNull(message = "The description must not be null.")
    private String description;

    /**
     * Book's author.
     */
    @Column
    @NotNull(message = "The author must not be null.")
    private String author;

}
