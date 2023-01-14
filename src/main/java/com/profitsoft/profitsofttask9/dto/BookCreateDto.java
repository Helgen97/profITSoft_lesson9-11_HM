package com.profitsoft.profitsofttask9.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Jacksonized
public class BookCreateDto {

    @NotBlank(message = "Book name is required")
    private String name;

    @NotBlank(message = "Book description is required")
    private String description;

    @NotNull(message = "Book publication date is required")
    private LocalDate publicationDate;

    @NotNull(message = "Book's author id is required")
    private Long authorId;
}
