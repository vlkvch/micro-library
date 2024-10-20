package org.example.client;

import org.example.dto.LibraryBookRequest;
import org.example.dto.LibraryBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

}
