package com.profitsoft.profitsofttask9.service.interfaces;

import com.profitsoft.profitsofttask9.dto.BookCreateDto;
import com.profitsoft.profitsofttask9.dto.BookFullDetailsDto;
import com.profitsoft.profitsofttask9.dto.BookInfoDto;
import com.profitsoft.profitsofttask9.dto.BookSearchDto;
import org.springframework.data.domain.Page;

public interface BookService {

    BookFullDetailsDto getBook(Long id);

    Long createBook(BookCreateDto newBook);

    Page<BookInfoDto> searchBooks(BookSearchDto bookQuery);

    void updateBook(Long id, BookCreateDto updatedBook);

    void deleteBook(Long id);

}
