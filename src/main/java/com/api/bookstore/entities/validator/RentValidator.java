package com.api.bookstore.entities.validator;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.dtos.RentPost;
import com.api.bookstore.entities.status.StatusRent;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.repositories.BookRepository;
import com.api.bookstore.repositories.RentRepository;
import com.api.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RentValidator {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void validateBookRent(Book book){
        validateAmount(book.getAmount());
    }

    private void validateAmount(Integer amount) {
        if (amount <= 0){
            throw new BusinessException("Não existem cópias deste livro");
        }
    }

    public void validateForCreate(RentPost rentPost){
        validateBook(rentPost.getBookId());
        validateUser(rentPost.getUserId());
        validateDevolution(rentPost.getRentDate(), rentPost.getRentPredict());
    }

    private void validateBook(Long id) {
        if (bookRepository.findById(id).isEmpty()){
            throw new BusinessException("Livro não encontrado");
        }
    }

    private void validateUser(Long id) {
        if (userRepository.findById(id).isEmpty()){
            throw new BusinessException("Leitor não encontrado");
        }
    }

    private void validateDevolution(LocalDate rentDate, LocalDate rentPredict) {
        if (rentDate.isAfter(rentPredict)){
            throw new BusinessException("A data de aluguel não pode ser depois da data de devolução");
        }
        if (rentDate.isAfter(LocalDate.now())){
            throw new BusinessException("O aluguel deve ser feito na data presente ou anterior");
        }
    }

    public void validateForDelete(Long id) {
        StatusRent status = rentRepository.findById(id).orElseThrow().getStatus();

        if (status.equals(StatusRent.LENDO)){
            throw new BusinessException("Este aluguel está em progresso");
        }
    }
}