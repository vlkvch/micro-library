package org.example.dto;

import lombok.Data;

@Data
public class BookResponse {

    private long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;

}
