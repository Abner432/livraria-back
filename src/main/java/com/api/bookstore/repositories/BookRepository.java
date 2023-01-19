package com.api.bookstore.repositories;

import com.api.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {

    Optional<Book> findByName(String name);

    Optional<Book> findByPublisherId(Long id);

}
