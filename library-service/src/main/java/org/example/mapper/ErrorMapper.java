package org.example.mapper;

import org.example.dto.ErrorResponse;
import org.example.exception.BookNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public interface ErrorMapper {

    /**
     * Mapper instance.
     */
    ErrorMapper INSTANCE = Mappers.getMapper(ErrorMapper.class);

    /**
     * Maps {@link BookNotFoundException} to an {@link ErrorResponse} DTO.
     *
     * @param e thrown exception
     * @return {@link ErrorResponse} object
     */
    default ErrorResponse toErrorResponse(BookNotFoundException e) {
        ErrorResponse errResp = new ErrorResponse();
        errResp.setStatus(HttpStatus.NOT_FOUND.value());
        errResp.setMessage(e.getMessage());
        return errResp;
    }

}
