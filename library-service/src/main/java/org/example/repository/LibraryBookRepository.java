package org.example.repository;

import org.example.entity.LibraryBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepository extends CrudRepository<LibraryBook, Long> {

}
