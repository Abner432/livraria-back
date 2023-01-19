package com.api.bookstore.entities.validator;

import com.api.bookstore.entities.Rent;
import com.api.bookstore.entities.dtos.BookPost;
import com.api.bookstore.entities.status.StatusRent;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.repositories.BookRepository;
import com.api.bookstore.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public void validateForCreate(BookPost bookPost){
        validateName(bookPost.getName());
        validatePublisher(bookPost.getPublisherId());
    }

    private void validatePublisher(Long publisher) {

        if (publisherRepository.findById(publisher).isEmpty()){
            throw new BusinessException("Editora não encontrada");
        }
    }

    private void validateName(String name) {
        bookRepository.findByName(name).ifPresent(book -> {
            throw new BusinessException("Livro já registrado");
        });
    }

    public void validateRelationship(Long id) {
        List<Rent> rents = bookRepository.findById(id).orElseThrow().getRents();

        for (Rent rent : rents) {
            if (rent.getStatus().equals(StatusRent.LENDO)) {
                throw new BusinessException("Este livro está sendo alugado");
            }
        }
    }
}
