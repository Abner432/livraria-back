package com.api.bookstore.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookPost {
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String author;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate launchDate;

    @NotNull
    private Long publisherId;
}
