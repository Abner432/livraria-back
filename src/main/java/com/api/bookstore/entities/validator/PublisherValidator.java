package com.api.bookstore.entities.validator;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.dtos.PublisherPost;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.exceptions.IdFoundException;
import com.api.bookstore.repositories.BookRepository;
import com.api.bookstore.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PublisherValidator {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    public void validateForCreate(@NotNull PublisherPost publisherPost) {
        validateName(publisherPost.getName());
    }

    public void validateForDelete(Long id) {
        validateRelationship(id);
    }

    private void validateName(String name) {
        publisherRepository.findByName(name).ifPresent(publisher -> {
            throw new BusinessException("Nome já registrado");
        });
    }

    public void validateId(Long id){
        if (publisherRepository.findById(id).isEmpty()){
            throw new IdFoundException(id);
        }
    }

    public void validateRelationship(Long id) {
        Optional<Book> book = bookRepository.findByPublisherId(id);
        if (book.isEmpty()) {
            throw new BusinessException("Existem livros registrados com essa editora");
        }
    }
}
