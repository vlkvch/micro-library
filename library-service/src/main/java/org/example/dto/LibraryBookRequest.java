package org.example.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LibraryBookRequest {

    private long id;
    private LocalDate borrowedDate;
    private LocalDate returnDate;

}
