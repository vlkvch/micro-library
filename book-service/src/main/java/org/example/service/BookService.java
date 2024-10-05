package org.example.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.example.client.LibraryClient;
import org.example.dto.BookRequest;
import org.example.dto.BookResponse;
import org.example.entity.Book;
import org.example.exception.BookNotFoundException;
import org.example.mapper.BookMapper;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepo;

    @Autowired
    private final LibraryClient libraryClient;

    /**
     * Returns a book found by its ID.
     *
     * @param id ID of a book
     * @return returned book
     * @throws BookNotFoundException if a book has not been found
     */
    public BookResponse getById(long id) {
        return BookMapper.INSTANCE.toBookResponse(
            bookRepo
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    /**
     * Returns a book found by its ISBN.
     *
     * @param isbn ISBN of a book
     * @return returned book
     * @throws BookNotFoundException if a book has not been found
     */
    public BookResponse getByIsbn(String isbn) {
        return BookMapper.INSTANCE.toBookResponse(
            bookRepo
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn))
        );
    }

    /**
     * Returns all the books.
     *
     * @return A list of all the books.
     */
    public List<BookResponse> getAll() {
        return StreamSupport.stream(bookRepo.findAll().spliterator(), false)
            .map(BookMapper.INSTANCE::toBookResponse)
            .toList();
    }

    /**
     * Adds a book to a database.
     *
     * @param bookReq book to add
     * @return added book
     */
    public BookResponse add(BookRequest bookReq) {
        Book book = bookRepo.save(BookMapper.INSTANCE.toBook(bookReq));

        libraryClient.add(BookMapper.INSTANCE.toLibraryBookRequest(book));

        return BookMapper.INSTANCE.toBookResponse(book);
    }

    /**
     * Updates a book with a given ID.
     *
     * @param id ID of a book to update
     * @param updatedBook Updated book object
     * @return The updated book
     * @throws BookNotFoundException if a book has not been found
     */
    public BookResponse update(long id, BookRequest updatedBook) {
        return BookMapper.INSTANCE.toBookResponse(
            bookRepo.findById(id)
                .map(book -> {
                    book.setIsbn(updatedBook.getIsbn());
                    book.setTitle(updatedBook.getTitle());
                    book.setGenre(updatedBook.getGenre());
                    book.setDescription(updatedBook.getDescription());
                    book.setAuthor(updatedBook.getAuthor());
                    return bookRepo.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    /**
     * Deletes a book from a database.
     *
     * @param id ID of a book to delete
     * @return ID of the deleted book
     */
    public long delete(long id) {
        if (bookRepo.findById(id).isEmpty()) {
            throw new BookNotFoundException(id);
        }

        bookRepo.deleteById(id);
        libraryClient.delete(id);

        return id;
    }

}
