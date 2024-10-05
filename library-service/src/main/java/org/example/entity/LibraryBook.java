package org.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "library_books")
public class LibraryBook {

    /**
     * Books's ID.
     */
    @Id
    @Column
    private long id;

    /**
     * Date when a book was borrowed.
     */
    @Column
    private LocalDate borrowedDate;

    /**
     * Date when a book must be returned.
     */
    @Column
    private LocalDate returnDate;

}
