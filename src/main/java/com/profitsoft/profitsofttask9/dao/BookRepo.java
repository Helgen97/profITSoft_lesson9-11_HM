package com.profitsoft.profitsofttask9.dao;

import com.profitsoft.profitsofttask9.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    Page<Book> findByName(String bookName, Pageable pageable);

    Page<Book> findByPublicationDate(LocalDate publicationDate, Pageable pageable);

    Page<Book> findByNameAndPublicationDate(String bookName, LocalDate publicationDate, Pageable pageable);
}
