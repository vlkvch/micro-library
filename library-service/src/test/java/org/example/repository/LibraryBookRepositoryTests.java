package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.example.entity.LibraryBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LibraryBookRepositoryTests {

    @Autowired
    private LibraryBookRepository libraryBookRepo;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        LocalDate now = LocalDate.of(2024, 10, 19);

        LibraryBook b = LibraryBook.builder()
            .id(1)
            .borrowedDate(now)
            .returnDate(now.plusWeeks(1))
            .build();

        entityManager.persistAndFlush(b);
    }

    @Test
    public void test_findById() {
        Optional<LibraryBook> b = libraryBookRepo.findById(Long.valueOf(1));

        assertTrue(b.isPresent());
        assertEquals(b.get().getId(), 1);
    }

    @Test
    public void test_findAll() {
        LocalDate now = LocalDate.now();

        LibraryBook b = LibraryBook.builder()
            .id(2)
            .borrowedDate(now)
            .returnDate(now.plusWeeks(2))
            .build();

        entityManager.persistAndFlush(b);

        Iterable<LibraryBook> libraryBooks = libraryBookRepo.findAll();

        assertThat(libraryBooks).isNotEmpty();
        assertThat(libraryBooks).hasSize(2);
    }

    @Test
    public void test_update() {
        LibraryBook updated = libraryBookRepo.findById(Long.valueOf(1)).get();
        updated.setBorrowedDate(null);
        updated.setReturnDate(null);

        assertEquals(libraryBookRepo.save(updated).getBorrowedDate(), null);
    }

    @Test
    public void test_findByIdNonExistent() {
        Optional<LibraryBook> b = libraryBookRepo.findById(Long.valueOf(2));

        assertTrue(b.isEmpty());
    }

}
