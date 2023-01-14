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
public class BookFullDetailsDto {

    private Long id;

    private String name;

    private String description;

    private LocalDate publicationDate;

    private AuthorInfoDto author;

    public static BookFullDetailsDto of(Book book) {
        return new BookFullDetailsDto(
                book.getId(),
                book.getName(),
                book.getDescription(),
                book.getPublicationDate(),
                AuthorInfoDto.of(book.getAuthor())
        );
    }
}
