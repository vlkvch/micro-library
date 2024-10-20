package org.example.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.example.dto.BookRequest;
import org.example.dto.BookResponse;
import org.example.entity.Book;
import org.example.exception.BookAlreadyExistsException;
import org.example.exception.BookNotFoundException;
import org.example.mapper.BookMapper;
import org.example.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BooksController.class)
@ExtendWith(MockitoExtension.class)
public class BooksControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void test_all() throws Exception {
        BookResponse bookResp1 = BookMapper.INSTANCE.toBookResponse(Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .genre("Technical literature")
            .author("Andrew Tanenbaum")
            .build()
        );

        BookResponse bookResp2 = BookMapper.INSTANCE.toBookResponse(Book.builder()
            .id(2)
            .isbn("978-0321349606")
            .title("Java Concurrency in Practice")
            .description("A practical guide that provides in-depth insights into writing robust, high-performance concurrent applications in Java, focusing on the principles of concurrency, thread safety, and best practices for managing shared resources.")
            .genre("Technical literature")
            .author("Brian Goetz")
            .build()
        );

        when(bookService.getAll()).thenReturn(List.of(bookResp1, bookResp2));

        mvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_getById() throws Exception {
        BookResponse bookResp = BookMapper.INSTANCE.toBookResponse(Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .genre("Technical literature")
            .author("Andrew Tanenbaum")
            .build()
        );

        when(bookService.getById(1)).thenReturn(bookResp);

        mvc.perform(get("/books/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".isbn").value("978-0133591620"));
    }

    @Test
    public void test_getById_returnsErrorResponse() throws Exception {
        when(bookService.getById(1)).thenThrow(BookNotFoundException.class);

        mvc.perform(get("/books/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_getByIsbn() throws Exception {
        BookResponse bookResp = BookMapper.INSTANCE.toBookResponse(Book.builder()
            .id(1)
            .isbn("978-0133591620")
            .title("Modern Operating Systems")
            .description("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.")
            .genre("Technical literature")
            .author("Andrew Tanenbaum")
            .build()
        );

        when(bookService.getByIsbn("978-0133591620")).thenReturn(bookResp);

        mvc.perform(get("/books/978-0133591620"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".isbn").value("978-0133591620"))
            .andExpect(jsonPath(".id").value(1));
    }

    @Test
    public void test_add() throws Exception {
        BookRequest bookReq = new BookRequest();
        bookReq.setIsbn("978-0133591620");
        bookReq.setTitle("Modern Operating Systems");
        bookReq.setDescription("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.");
        bookReq.setGenre("Technical literature");
        bookReq.setAuthor("Andrew Tanenbaum");

        Book book = BookMapper.INSTANCE.toBook(bookReq);
        book.setId(1);

        when(bookService.add(bookReq)).thenReturn(BookMapper.INSTANCE.toBookResponse(book));

        mvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(bookReq)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".isbn").value("978-0133591620"))
            .andExpect(jsonPath(".id").value(1));
    }

    @Test
    public void test_add_returnsErrorResponse() throws Exception {
        BookRequest bookReq = new BookRequest();
        bookReq.setIsbn("978-0133591620");
        bookReq.setTitle("Modern Operating Systems");
        bookReq.setDescription("A comprehensive textbook that explores the principles, design, and implementation of operating systems, covering topics such as process management, memory management, file systems, and security in a clear and accessible manner.");
        bookReq.setGenre("Technical literature");
        bookReq.setAuthor("Andrew Tanenbaum");

        Book book = BookMapper.INSTANCE.toBook(bookReq);
        book.setId(1);

        when(bookService.add(bookReq))
            .thenReturn(BookMapper.INSTANCE.toBookResponse(book))
            .thenThrow(BookAlreadyExistsException.class);

        mvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(bookReq)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(".isbn").value("978-0133591620"))
            .andExpect(jsonPath(".id").value(1));

        mvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonMapper.writeValueAsString(bookReq)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
