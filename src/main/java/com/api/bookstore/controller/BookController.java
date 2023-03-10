package com.api.bookstore.controller;

import com.api.bookstore.entities.dtos.BookGet;
import com.api.bookstore.entities.dtos.BookPost;
import com.api.bookstore.service.BookService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "API REST BOOK")
@RequestMapping("/bookstore")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody @Valid BookPost bookPost) {
        bookService.create(bookPost);
    }
    @GetMapping
    public ResponseEntity<Page<BookGet>> getAllBooks(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll(pageable));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<BookGet> getOneBook(@PathVariable(value = "id")Long id){
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<BookGet> updateBook(@PathVariable(value = "id")Long id, @RequestBody @Valid BookPost bookPost) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, bookPost));
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable(value = "id")Long id){
        bookService.deleteById(id);
    }
}
