package com.profitsoft.profitsofttask9.controller;

import com.profitsoft.profitsofttask9.ProfitsoftTask9Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProfitsoftTask9Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthorsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllAuthors__whenGetAllAuthors__thenStatusIs200andReturnArrayOfAuthors() throws Exception {
        mvc
                .perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].fullName").value("Joanne Rowling"));
    }
}