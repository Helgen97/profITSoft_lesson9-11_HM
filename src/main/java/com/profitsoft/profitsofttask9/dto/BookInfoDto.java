package com.profitsoft.profitsofttask9.dto;

import com.profitsoft.profitsofttask9.entity.Book;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookInfoDto {

    private Long id;

    private String name;

    private LocalDate publicationDate;

    public static BookInfoDto of(Book book) {
        return new BookInfoDto(book.getId(), book.getName(), book.getPublicationDate());
    }

}
