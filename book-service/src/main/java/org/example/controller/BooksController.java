package org.example.controller;

import java.util.List;

import org.example.dto.BookRequest;
import org.example.dto.BookResponse;
import org.example.service.BookService;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> add(@RequestBody @Valid BookRequest book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.add(book));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> all() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<BookResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/{isbn:(?=(?:[^0-9]*[0-9]){10}(?:(?:[^0-9]*[0-9]){3})?$)[\\d-]+}")
    public ResponseEntity<BookResponse> getByIsbn(@PathVariable("isbn") @Valid String isbn) {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") long id, @RequestBody @Valid BookRequest updatedBook) {
        return ResponseEntity.ok(bookService.update(id, updatedBook));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

}
