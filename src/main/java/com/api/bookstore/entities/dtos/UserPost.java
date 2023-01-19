package com.api.bookstore.entities.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPost {
    @NotBlank
    @Size(min = 3, max = 70)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 70)
    private String name;

    @NotBlank
    @Size(min = 3, max = 70)
    private String address;

    @Email
    @NotBlank
    @Size(min = 3, max = 70)
    private String email;
}
