package com.profitsoft.profitsofttask9.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitsoft.profitsofttask9.ProfitsoftTask9Application;
import com.profitsoft.profitsofttask9.dto.BookCreateDto;
import com.profitsoft.profitsofttask9.dto.BookFullDetailsDto;
import com.profitsoft.profitsofttask9.dto.BookSearchDto;
import com.profitsoft.profitsofttask9.exceptions.NotFoundException;
import com.profitsoft.profitsofttask9.service.implementations.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProfitsoftTask9Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BooksControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBook__whenExistingId_ThenStatusIs200AndBookReturned() throws Exception {
        mvc
                .perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Harry Potter and the Philosopher's Stone"))
                .andExpect(jsonPath("$.publicationDate").value("1997-06-26"))
                .andExpect(jsonPath("$.author.fullName").value("Joanne Rowling"));
    }

    @Test
    void getBook__whenNotExistingId_ThenStatusIs404AndBookReturned() throws Exception {
        mvc
                .perform(get("/api/books/100"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NotFoundException.class));
    }

    @Test
    void createBook__whenSaveBook__thenStatusIs201AndReturnedSavedBookId() throws Exception {
        BookCreateDto newBook = getNewBook("Test Book");

        mvc
                .perform(
                        post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newBook))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.result").isString());
    }

    @Test
    void createBook__whenSaveBookWithEmptyFields__thenStatusIs400() throws Exception {
        mvc
                .perform(
                        post("/api/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchBooks__whenSearchByOnlyByName__thenStatusIs200AndGetPageOfBooks() throws Exception {
        BookSearchDto query = BookSearchDto
                .builder()
                .name("Harry Potter and the Philosopher's Stone")
                .build();

        mvc
                .perform(
                        post("/api/books/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5));
    }

    @Test
    void searchBooks__whenSearchByOnlyByPublicationDate__thenStatusIs200AndGetPageOfBooks() throws Exception {
        BookSearchDto query = BookSearchDto
                .builder()
                .publicationDate(LocalDate.of(1997, 6, 26))
                .build();

        mvc
                .perform(
                        post("/api/books/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5));
    }

    @Test
    void searchBooks__whenSearchByAllFields__thenStatusIs200AndGetPageOfBooks() throws Exception {
        BookSearchDto query = BookSearchDto
                .builder()
                .name("Harry Potter and the Philosopher's Stone")
                .publicationDate(LocalDate.of(1997, 6, 26))
                .build();

        mvc
                .perform(
                        post("/api/books/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5));
    }

    @Test
    void searchBooks__whenSearchByAllFieldsWithPageParameters__thenStatusIs200AndGetPageOfBooks() throws Exception {
        BookSearchDto query = BookSearchDto
                .builder()
                .name("Harry")
                .publicationDate(LocalDate.of(1997, 6, 26))
                .page(1)
                .size(1)
                .build();

        mvc
                .perform(
                        post("/api/books/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber").value(1))
                .andExpect(jsonPath("$.pageable.pageSize").value(1));
    }

    @Test
    void searchBooks__whenSearchQueryIsEmpty__thenStatusIs200AndGetPageOfBooks() throws Exception {
        mvc
                .perform(
                        post("/api/books/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(5));
    }

    @Test
    void updateBook__whenUpdateExistingBook__thenStatusIs200() throws Exception {
        Long newBookId = bookService.createBook(getNewBook("Test book #1"));
        BookCreateDto bookWithNewName = getNewBook("Test book #2");

        mvc
                .perform(
                        put("/api/books/{id}", newBookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(bookWithNewName))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.result").value("OK"));

        BookFullDetailsDto updatedBook = bookService.getBook(newBookId);

        assertThat(updatedBook.getName()).isEqualTo(bookWithNewName.getName());
    }

    @Test
    void updateBook__whenUpdateNotExistingBook__thenStatusIs404() throws Exception {
        mvc
                .perform(
                        put("/api/books/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(getNewBook("Test")))
                )
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NotFoundException.class));

    }

    private BookCreateDto getNewBook(String name) {
        return BookCreateDto
                .builder()
                .name(name)
                .description("Test book description")
                .publicationDate(LocalDate.now())
                .authorId(1L)
                .build();
    }

    @Test
    void deleteBook__whenDeletePersonWithExistingId_thenStatusIs200() throws Exception {
        mvc
                .perform(delete("/api/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"));
    }

    @Test
    void deleteBook__whenDeletePersonWithNotExistingId_thenStatusIs404() throws Exception {
        mvc
                .perform(delete("/api/books/100"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NotFoundException.class));
    }
}