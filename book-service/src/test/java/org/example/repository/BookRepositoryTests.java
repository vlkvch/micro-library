package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.example.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
        Book book = Book.builder()
            .isbn("978-0321349606")
            .title("Java Concurrency in Practice")
            .description("A practical guide that provides in-depth insights into writing robust, high-performance concurrent applications in Java, focusing on the principles of concurrency, thread safety, and best practices for managing shared resources.")
            .author("Andrew Tanenbaum")
            .genre("Technical literature")
            .build();

        testEntityManager.persistAndFlush(book);
    }

    @Test
    public void test_findById() {
        Optional<Book> b = bookRepo.findById(Long.valueOf(1));

        assertTrue(b.isPresent());
        assertEquals(b.get().getId(), 1);
    }

    @Test
    public void test_findByIsbn() {
        Optional<Book> b = bookRepo.findByIsbn("978-0321349606");

        assertTrue(b.isPresent());
        assertEquals(b.get().getIsbn(), "978-0321349606");
    }

    @Test
    public void test_findAll() {
        Book b = Book.builder()
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .genre("Technical literature")
            .author("Andrew Tanenbaum")
            .build();

        testEntityManager.persistAndFlush(b);

        Iterable<Book> books = bookRepo.findAll();

        assertThat(books).isNotEmpty();
        assertThat(books).extracting(Book::getIsbn).contains("978-0321349606");
    }

    @Test
    public void test_update() {
        Book updatedBook = bookRepo.findById(Long.valueOf(1)).get();
        updatedBook.setDescription("Description...");

        assertEquals(bookRepo.save(updatedBook).getDescription(), "Description...");
        assertThat(bookRepo.findById(Long.valueOf(1))).isNotEmpty();
    }

    @Test
    public void test_findByIdNonExistent() {
        Optional<Book> b = bookRepo.findById(Long.valueOf(3));

        assertTrue(b.isEmpty());
    }

}
