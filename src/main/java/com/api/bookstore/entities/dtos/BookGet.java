package com.api.bookstore.entities.dtos;

import com.api.bookstore.entities.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookGet {
    private Long id;

    private String name;

    private String author;

    private LocalDate launchDate;

    private Integer amount;

    private Integer leaseQuantity;

    private Publisher publisher;
}
