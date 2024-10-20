package org.example.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.example.dto.BookRequest;
import org.example.dto.LibraryBookResponse;
import org.example.entity.LibraryBook;
import org.example.exception.BookNotFoundException;
import org.example.mapper.LibraryBookMapper;
import org.example.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryController.class)
@ExtendWith(MockitoExtension.class)
public class LibraryControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void test_getById() throws Exception {
        LocalDate now = LocalDate.of(2024, 10, 19);

        LibraryBookResponse bookResp = LibraryBookMapper.INSTANCE.toLibraryBookResponse(
            LibraryBook.builder()
                .id(1)
                .borrowedDate(now)
                .returnDate(now.plusWeeks(1))
                .build()
        );

        when(libraryService.getById(1)).thenReturn(bookResp);

        mvc.perform(get("/library/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".returnDate").value("2024-10-26"));
    }

    @Test
    public void test_getById_returnsErrorResponse() throws Exception {
        when(libraryService.getById(2)).thenThrow(BookNotFoundException.class);

        mvc.perform(get("/library/2"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_add() throws Exception {
        BookRequest bookReq = new BookRequest();
        bookReq.setId(1);

        LibraryBook book = LibraryBookMapper.INSTANCE.toLibraryBook(bookReq);
        LocalDate now = LocalDate.of(2024, 10, 19);
        book.setBorrowedDate(now);
        book.setReturnDate(now.plusWeeks(1));

        LibraryBookResponse libraryBookResp = LibraryBookMapper.INSTANCE.toLibraryBookResponse(book);

        when(libraryService.add(bookReq)).thenReturn(libraryBookResp);

        mvc.perform(post("/library")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(bookReq)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".returnDate").value("2024-10-26"));
    }

}
