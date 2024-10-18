package org.example.exception;

import org.example.dto.ErrorResponse;
import org.example.mapper.ErrorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BookAlreadyExistsException}.
     *
     * @param e an object of the {@link BookAlreadyExistsException} class
     * @return a {@link ResponseEntity} with {@link ErrorResponse} as a body
     */
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookAlreadyExists(BookAlreadyExistsException e) {
        ErrorResponse resp = ErrorMapper.INSTANCE.toErrorResponse(e);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    /**
     * Handles {@link BookNotFoundException}.
     *
     * @param e an object of the {@link BookNotFoundException} class
     * @return a {@link ResponseEntity} with {@link ErrorResponse} as a body
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException e) {
        ErrorResponse resp = ErrorMapper.INSTANCE.toErrorResponse(e);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}
