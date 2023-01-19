package com.api.bookstore.service.impl;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.Rent;
import com.api.bookstore.entities.User;
import com.api.bookstore.entities.dtos.RentGet;
import com.api.bookstore.entities.dtos.RentPost;
import com.api.bookstore.entities.mappers.RentMapper;
import com.api.bookstore.entities.status.StatusRent;
import com.api.bookstore.entities.validator.RentValidator;
import com.api.bookstore.exceptions.BusinessException;
import com.api.bookstore.exceptions.IdFoundException;
import com.api.bookstore.repositories.BookRepository;
import com.api.bookstore.repositories.RentRepository;
import com.api.bookstore.repositories.UserRepository;
import com.api.bookstore.service.RentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RentImpl implements RentService {
    @Autowired
    private final RentRepository rentRepository;

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RentMapper rentMapper;

    @Autowired
    private final RentValidator rentValidator;

    @Override
    public void create(@NotNull RentPost rentPost) {
        Book book = bookRepository.getReferenceById(rentPost.getBookId());
        rentValidator.validateForCreate(rentPost);
        rentValidator.validateBookRent(book);
        book.setLeaseQuantity(book.getLeaseQuantity() +1);
        book.setAmount(book.getAmount() -1);
        bookRepository.save(book);
        Rent rent = rentMapper.toRent(rentPost);
        rent.setStatus(StatusRent.LENDO);
        rentRepository.save(rent);

    }

    @Override
    public Page<RentGet> getAll(Pageable pageable) {
        return rentRepository.findAll(pageable).map(rentMapper::toRentGet);
    }

    @Override
    public RentGet getById(Long id) {
        return rentRepository.findById(id).map(rentMapper::toRentGet).orElseThrow(() -> new IdFoundException(id));
    }

    @Override
    public RentGet update(Long id, RentPost rentPost){
        Optional<Book> optBook = bookRepository.findById(rentPost.getBookId());
        Optional<User> optLogin = userRepository.findById(rentPost.getUserId());
        Optional<Rent> optRent = rentRepository.findById(id);

        if (optBook.isPresent()){
            if (optLogin.isPresent()){
                if (optRent.get().getStatus().equals(StatusRent.LENDO))
                    throw new BusinessException("Esse aluguel já foi feito e o livro não foi entregue");
            }
        }

        if (optRent.isEmpty()){
            throw new BusinessException("Aluguel não encontrado");
        }
        Rent rentModel = rentMapper.toRent(rentPost);
        rentModel.setId(id);
        rentRepository.save(rentModel);

        return rentMapper.toRentGet(rentModel);
    }

    @Override
    public void devolution(Long id){
        Rent rentModel = rentRepository.getReferenceById(id);
        Optional<Rent> rent = rentRepository.findById(id);
        Integer copies = rentModel.getBook().getAmount();


        if (rent.isEmpty()){
            throw new IdFoundException(id);
        }

        if (rent.get().getStatus().equals(StatusRent.ATRASADO)) {
            throw new BusinessException("this book has already been delivered");
        }

        if (rent.get().getStatus().equals(StatusRent.ENTREGUE)) {
            throw new BusinessException("this book has already been delivered");
        }

        if (rent.get().getPredictDate().isBefore(LocalDate.now())) {
            rentModel.setStatus(StatusRent.ATRASADO);
        }

        else {
            rentModel.setStatus(StatusRent.ENTREGUE);
        }
        rentModel.setDevolutionDate(LocalDate.now());
        rentModel.getBook().setAmount(copies + 1);

        rentRepository.save(rentModel);
    }

    @Override
    public void deleteById(Long id) {
        Rent rent = rentRepository.getReferenceById(id);
        Integer copies = rent.getBook().getLeaseQuantity();
        rentValidator.validateForDelete(id);
        rentRepository.deleteById(id);
        rent.getBook().setLeaseQuantity(copies -1);


    }
}
