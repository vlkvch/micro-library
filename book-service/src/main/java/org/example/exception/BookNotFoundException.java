package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    /**
     * Exception which is thrown if a book with a specified ID has not been found.
     */
    public BookNotFoundException(long id) {
        super("Could not find a book with ID " + id);
    }

    /**
     * Exception which is thrown if a book with a specified ISBN has not been found.
     */
    public BookNotFoundException(String isbn) {
        super("Could not find a book with ISBN of " + isbn);
    }

}
