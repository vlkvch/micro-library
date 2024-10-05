package org.example.repository;

import java.util.Optional;

import org.example.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Finds a book by its ISBN.
     *
     * @param isbn ISBN of a desired book
     * @return Book object
     */
    @Query("SELECT b FROM Book b WHERE b.isbn = ?1")
    Optional<Book> findByIsbn(String isbn);

    /**
     * Returns whether a book with the given ISBN exists.
     *
     * @param isbn ISBN of a book
     * @return <code>true</code> if a book with the given ISBN exists, <code>false</code> otherwise
     */
    @Query("SELECT b FROM Book b WHERE b.isbn = ?1")
    boolean existsByIsbn(String isbn);

}
