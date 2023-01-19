package com.api.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer amount;

    @Column
    private String publisher;

    @Column(nullable = false)
    private LocalDate launchDate;

    @Column
    private Integer leaseQuantity;

    @JsonManagedReference
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<Rent> rents;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publishers;

}
