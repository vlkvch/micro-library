package org.example.exception;

public class BookNotFoundException extends RuntimeException {

    /**
     * Exception which is thrown if a book with a specified ID has not been found.
     */
    public BookNotFoundException(long id) {
        super("Could not find a book with ID " + id);
    }

}
