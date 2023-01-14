package com.profitsoft.profitsofttask9.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookSearchDto {

    private String name;

    private LocalDate publicationDate;

    private int page;

    private int size;
}
