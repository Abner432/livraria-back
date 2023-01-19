package com.api.bookstore.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGet {

    private Long id;

    private String name;

    private String address;

    private String email;

}
