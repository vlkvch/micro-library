package org.example.service;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.example.entity.Book;
import org.example.exception.BookNotFoundException;
import org.example.mapper.BookMapper;
import org.example.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @Mock
    private BookRepository bookRepo;

    @InjectMocks
    private BookService bookService;

    @Test
    public void test_getById() {
        Book book = Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .author("Andrew Tanenbaum")
            .build();

        when(bookRepo.findById(Long.valueOf(1))).thenReturn(Optional.of(book));

        Assertions.assertEquals(BookMapper.INSTANCE.toBookResponse(book), bookService.getById(1));
    }

    @Test
    public void test_getAll() {
        Book book1 = Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .author("Andrew Tanenbaum")
            .build();

        Book book2 = Book.builder()
            .id(2)
            .isbn("978-0321349606")
            .title("Java Concurrency in Practice")
            .description("A practical guide that provides in-depth insights into writing robust, high-performance concurrent applications in Java, focusing on the principles of concurrency, thread safety, and best practices for managing shared resources.")
            .author("Brian Goetz")
            .build();

        when(bookRepo.findAll()).thenReturn((Iterable<Book>) List.of(book1, book2));

        Assertions.assertEquals(List.of(BookMapper.INSTANCE.toBookResponse(book1), BookMapper.INSTANCE.toBookResponse(book2)), bookService.getAll());
    }

    @Test
    public void test_getByIsbn() {
        Book book = Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .author("Andrew Tanenbaum")
            .build();

        when(bookRepo.findByIsbn("978-0133591620")).thenReturn(Optional.of(book));

        Assertions.assertEquals(BookMapper.INSTANCE.toBookResponse(book), bookService.getByIsbn("978-0133591620"));
    }

    @Test
    public void test_getById_throwsBookNotFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getById(1));
    }

    @Test
    public void test_getByIsbn_throwsBookNotFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getByIsbn("978-0133591620"));
    }

}
