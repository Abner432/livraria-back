package com.api.bookstore.service;

import com.api.bookstore.entities.dtos.BookGet;
import com.api.bookstore.entities.dtos.BookPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    void create(BookPost bookPost);

    Page<BookGet> getAll(Pageable pageable);

    BookGet getById(Long id);

    BookGet update(Long id, BookPost bookPost);

    void deleteById(Long id);
}
