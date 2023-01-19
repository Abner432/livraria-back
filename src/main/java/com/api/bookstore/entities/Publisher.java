package com.api.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String city;

    @JsonManagedReference
    @OneToMany(mappedBy = "publisher",cascade = CascadeType.ALL, fetch =FetchType.LAZY )
    private List<Book> books;
}
