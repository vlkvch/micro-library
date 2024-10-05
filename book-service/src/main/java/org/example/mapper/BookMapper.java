package org.example.mapper;

import org.example.dto.BookRequest;
import org.example.dto.BookResponse;
import org.example.dto.LibraryBookRequest;
import org.example.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Mapper instance.
     */
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    /**
     * Maps an object of type {@link Book} to {@link BookResponse}.
     *
     * @param book book to be mapped
     * @return {@link BookResponse} object
     */
    BookResponse toBookResponse(Book book);

    /**
     * Maps an object of type {@link Book} to {@link LibraryBookRequest}.
     *
     * @param book book to be mapped
     * @return {@link LibraryBookRequest} object
     */
    LibraryBookRequest toLibraryBookRequest(Book book);

    /**
     * Maps an object of type {@link BookRequest} to {@link Book}.
     *
     * @param bookDto DTO to be mapped
     * @return {@link Book} onject
     */
    Book toBook(BookRequest bookReq);

}
