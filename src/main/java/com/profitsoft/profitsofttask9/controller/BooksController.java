package com.profitsoft.profitsofttask9.controller;

import com.profitsoft.profitsofttask9.dto.*;
import com.profitsoft.profitsofttask9.service.implementations.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookServiceImpl bookService;

    @GetMapping("/{id}")
    public BookFullDetailsDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createBook(@Valid @RequestBody BookCreateDto newBook) {
        Long idOfCreatedBook = bookService.createBook(newBook);
        return new RestResponse(String.valueOf(idOfCreatedBook));
    }

    @PostMapping("_search")
    public Page<BookInfoDto> searchBooks(@RequestBody BookSearchDto searchQuery) {
        return bookService.searchBooks(searchQuery);
    }

    @PutMapping("/{id}")
    public RestResponse updateBook(@PathVariable Long id,
                                   @Valid @RequestBody BookCreateDto updatedBook) {
        bookService.updateBook(id, updatedBook);
        return new RestResponse("OK");
    }

    @DeleteMapping("/{id}")
    public RestResponse deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new RestResponse("OK");
    }
}
