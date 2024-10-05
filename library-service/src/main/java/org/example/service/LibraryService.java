package org.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

import org.example.dto.BookRequest;
import org.example.dto.LibraryBookRequest;
import org.example.dto.LibraryBookResponse;
import org.example.entity.LibraryBook;
import org.example.exception.BookNotFoundException;
import org.example.mapper.LibraryBookMapper;
import org.example.repository.LibraryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibraryService {

    @Autowired
    private final LibraryBookRepository libraryBookRepo;

    /**
     * Adds a book to the database.
     *
     * @param bookReq book to be added
     * @return added book
     */
    public LibraryBookResponse add(BookRequest bookReq) {
        LibraryBook libraryBook = LibraryBookMapper.INSTANCE.toLibraryBook(bookReq);

        LocalDate now = LocalDate.now();

        libraryBook.setBorrowedDate(now);
        libraryBook.setReturnDate(now.plusWeeks(1));

        return LibraryBookMapper.INSTANCE.toLibraryBookResponse(libraryBookRepo.save(libraryBook));
    }

    /**
     * Returns a list of all the books.
     *
     * @return all books
     */
    public List<LibraryBookResponse> all() {
        return StreamSupport.stream(libraryBookRepo.findAll().spliterator(), false)
            .map(LibraryBookMapper.INSTANCE::toLibraryBookResponse)
            .toList();
    }

    /**
     * Returns a list of all the available books.
     *
     * @return available books
     */
    public List<LibraryBookResponse> available() {
        return StreamSupport.stream(libraryBookRepo.findAll().spliterator(), false)
            .filter(book -> book.getBorrowedDate() == null)
            .map(LibraryBookMapper.INSTANCE::toLibraryBookResponse)
            .toList();
    }

    /**
     * Returns a book found by its ID.
     *
     * @param id ID of a book
     * @return LibraryBookResponse returned book
     * @throws BookNotFoundException if a book has not been found
     */
    public LibraryBookResponse getById(long id) {
        return LibraryBookMapper.INSTANCE.toLibraryBookResponse(
            libraryBookRepo
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException(id))
        );
    }

    /**
     * Updates a book with the given ID.
     *
     * @param id ID of a book to update
     * @param updatedBook Updated book object
     * @return The updated book
     * @throws BookNotFoundException if a book has not been found
     */
    public LibraryBookResponse update(long id, LibraryBookRequest updatedBook) {
        return LibraryBookMapper.INSTANCE.toLibraryBookResponse(
            libraryBookRepo.findById(id)
                .map(book -> {
                    book.setBorrowedDate(updatedBook.getBorrowedDate());
                    book.setReturnDate(updatedBook.getReturnDate());
                    return libraryBookRepo.save(book);
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
        if (libraryBookRepo.findById(id).isEmpty()) {
            throw new BookNotFoundException(id);
        }

        libraryBookRepo.deleteById(id);
        return id;
    }

}
