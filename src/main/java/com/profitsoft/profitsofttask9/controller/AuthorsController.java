package com.profitsoft.profitsofttask9.controller;

import com.profitsoft.profitsofttask9.dto.AuthorInfoDto;
import com.profitsoft.profitsofttask9.service.implementations.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorsController {

    private final AuthorServiceImpl authorService;

    @GetMapping
    public List<AuthorInfoDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
