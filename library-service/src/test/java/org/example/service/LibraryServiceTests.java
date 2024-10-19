package org.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.dto.BookRequest;
import org.example.entity.LibraryBook;
import org.example.exception.BookNotFoundException;
import org.example.mapper.LibraryBookMapper;
import org.example.repository.LibraryBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTests {

    @Mock
    private LibraryBookRepository libraryBookRepo;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    public void test_add() {
        BookRequest bookReq = new BookRequest();
        bookReq.setId(1);

        LibraryBook book = LibraryBookMapper.INSTANCE.toLibraryBook(bookReq);
        LocalDate now = LocalDate.now();
        book.setBorrowedDate(now);
        book.setReturnDate(now.plusWeeks(1));

        when(libraryBookRepo.save(book)).thenReturn(book);

        assertEquals(LibraryBookMapper.INSTANCE.toLibraryBookResponse(book), libraryService.add(bookReq));
    }

    @Test
    public void test_getById() {
        LocalDate now = LocalDate.now();

        LibraryBook book = LibraryBook.builder()
            .id(1)
            .borrowedDate(now)
            .returnDate(now.plusWeeks(1))
            .build();

        when(libraryBookRepo.findById(Long.valueOf(1))).thenReturn(Optional.of(book));

        assertEquals(LibraryBookMapper.INSTANCE.toLibraryBookResponse(book), libraryService.getById(1));
    }

    @Test
    public void test_all() {
        LibraryBook book1 = LibraryBook.builder()
            .id(1)
            .borrowedDate(null)
            .returnDate(null)
            .build();

        LibraryBook book2 = LibraryBook.builder()
            .id(2)
            .borrowedDate(LocalDate.now())
            .returnDate(LocalDate.now().plusWeeks(1))
            .build();

        when(libraryBookRepo.findAll()).thenReturn((Iterable<LibraryBook>) List.of(book1, book2));

        assertThat(libraryService.all()).isEqualTo(
            List.of(book1, book2)
                .stream()
                .map(LibraryBookMapper.INSTANCE::toLibraryBookResponse)
                .toList()
        );
    }

    @Test
    public void test_available() {
        LibraryBook book1 = LibraryBook.builder()
            .id(1)
            .borrowedDate(null)
            .returnDate(null)
            .build();

        LibraryBook book2 = LibraryBook.builder()
            .id(2)
            .borrowedDate(LocalDate.now())
            .returnDate(LocalDate.now().plusWeeks(1))
            .build();

        when(libraryBookRepo.findAll()).thenReturn((Iterable<LibraryBook>) List.of(book1, book2));

        assertThat(libraryService.available()).allMatch(book -> book.getBorrowedDate() == null);
    }

    @Test
    public void test_getById_throwsBookNotFound() {
        assertThrows(BookNotFoundException.class, () -> libraryService.getById(4));
    }

    @Test
    public void test_delete() {
        LocalDate now = LocalDate.now();

        LibraryBook book = LibraryBook.builder()
            .id(1)
            .borrowedDate(now)
            .returnDate(now.plusWeeks(1))
            .build();

        when(libraryBookRepo.findById(Long.valueOf(1))).thenReturn(Optional.of(book));

        assertEquals(1, libraryService.delete(1));
    }

}
