package com.api.bookstore.entities.dtos;

import com.api.bookstore.entities.Book;
import com.api.bookstore.entities.User;
import com.api.bookstore.entities.status.StatusRent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentGet {
    private Long id;

    private LocalDate rentDate;

    private LocalDate rentPredict;

    private LocalDate devolutionDate;

    private StatusRent status;

    private Book book;

    private User user;
}
