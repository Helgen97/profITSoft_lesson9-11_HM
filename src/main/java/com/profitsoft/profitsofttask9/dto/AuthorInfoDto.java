package com.profitsoft.profitsofttask9.dto;

import com.profitsoft.profitsofttask9.entity.Author;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorInfoDto {

    private Long id;

    private String fullName;

    public static AuthorInfoDto of(Author author) {
        return new AuthorInfoDto(
                author.getId(),
                "%s %s".formatted(author.getFirstName(), author.getSecondName())
        );
    }
}
