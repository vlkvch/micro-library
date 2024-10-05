package org.example.exception;

import org.example.dto.ErrorResponse;
import org.example.mapper.ErrorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BookNotFoundException}.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException e) {
        ErrorResponse resp = ErrorMapper.INSTANCE.toErrorResponse(e);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}
