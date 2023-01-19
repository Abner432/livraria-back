package com.api.bookstore.service.impl;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.dtos.BookGet;
import com.api.bookstore.entities.dtos.BookPost;
import com.api.bookstore.entities.mappers.BookMapper;
import com.api.bookstore.entities.validator.BookValidator;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.exceptions.IdFoundException;
import com.api.bookstore.repositories.BookRepository;
import com.api.bookstore.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookImpl implements BookService {
    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BookMapper bookMapper;

    @Autowired
    private final BookValidator bookValidator;


    @Override
    public void create(BookPost bookPost) {
        bookValidator.validateForCreate(bookPost);
        Book book = bookMapper.toBooks(bookPost);
        book.setLeaseQuantity(0);
        bookRepository.save(book);
    }

    @Override
    public Page<BookGet> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toBooksGet);
    }

    @Override
    public BookGet getById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toBooksGet).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public BookGet update(Long id, BookPost bookPost) {
        Optional<Book> optBook = bookRepository.findById(id);
        Optional<Book> optBookName = bookRepository.findByName(bookPost.getName());

        if (!optBookName.equals(optBook)){
            if (optBook.isPresent()){
                bookValidator.validateForCreate(bookPost);
            }
        }

        if (optBook.isEmpty()){
            throw new BusinessException("Livro não encontrado");
        }
        Book book = bookMapper.toBooks(bookPost);
        book.setId(id);
        bookRepository.save(book);

        return bookMapper.toBooksGet(book);
    }

    @Override
    public void deleteById(Long id) {
        bookValidator.validateRelationship(id);
        bookRepository.deleteById(id);
    }
}
