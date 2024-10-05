package org.example.controller;

import java.util.List;

import org.example.dto.BookRequest;
import org.example.dto.LibraryBookRequest;
import org.example.dto.LibraryBookResponse;
import org.example.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private final LibraryService libraryService;

    @PostMapping
    public ResponseEntity<LibraryBookResponse> add(@RequestBody BookRequest bookReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.add(bookReq));
    }

    @GetMapping
    public ResponseEntity<List<LibraryBookResponse>> all() {
        return ResponseEntity.ok(libraryService.all());
    }

    @GetMapping("/available")
    public ResponseEntity<List<LibraryBookResponse>> available() {
        return ResponseEntity.ok(libraryService.available());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<LibraryBookResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(libraryService.getById(id));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<LibraryBookResponse> update(@PathVariable("id") long id, @RequestBody LibraryBookRequest updatedBook) {
        return ResponseEntity.ok(libraryService.update(id, updatedBook));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(libraryService.delete(id));
    }

}
