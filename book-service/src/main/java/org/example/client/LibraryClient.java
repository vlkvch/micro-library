package org.example.client;

import org.example.dto.BookRequest;
import org.example.dto.LibraryBookRequest;
import org.example.dto.LibraryBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "library-service", url = "http://api-gateway:8080/library-service/library")
public interface LibraryClient {

    /**
     * Sends a <code>POST</code> HTTP request to library-service.
     *
     * @param libraryBookReq {@link LibraryBookRequest} DTO
     * @return the added book
     */
    @PostMapping
    public ResponseEntity<LibraryBookResponse> add(LibraryBookRequest libraryBookReq);

    /**
     * Sends a <code>DELETE</code> HTTP request to library-service.
     *
     * @param id ID of a book to delete
     * @return library-service's response
     */
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id);

    /**
     * Sends a <code>PUT</code> HTTP request to library-service
     *
     * @param id ID of a book to update
     * @param libraryBookReq updated book
     * @return library-service's response
     */
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<BookRequest> update(@PathVariable("id") long id, LibraryBookRequest libraryBookReq);

}
