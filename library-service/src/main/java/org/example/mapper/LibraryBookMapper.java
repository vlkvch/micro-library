package org.example.mapper;

import org.example.dto.BookRequest;
import org.example.dto.LibraryBookRequest;
import org.example.dto.LibraryBookResponse;
import org.example.entity.LibraryBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LibraryBookMapper {

    /**
     * Mapper instance.
     */
    LibraryBookMapper INSTANCE = Mappers.getMapper(LibraryBookMapper.class);

    /**
     * Maps an object of type {@link LibraryBook} to {@link LibraryBookResponse}.
     *
     * @param libraryBook book to be mapped
     * @return {@link LibraryBookResponse} object
     */
    LibraryBookResponse toLibraryBookResponse(LibraryBook libraryBook);

    /**
     * Maps an object of type {@link LibraryBookRequest} to {@link LibraryBook}.
     *
     * @param libraryBookReq DTO to be mapped
     * @return {@link LibraryBook} object
     */
    LibraryBook toLibraryBook(LibraryBookRequest libraryBookReq);

    /**
     * Maps an object of type {@link BookRequest} to {@link LibraryBook}.
     *
     * @param bookReq DTO to be mapped
     * @return {@link LibraryBook} object
     */
    LibraryBook toLibraryBook(BookRequest bookReq);

}
