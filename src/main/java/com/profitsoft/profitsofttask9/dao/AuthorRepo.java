package com.profitsoft.profitsofttask9.dao;

import com.profitsoft.profitsofttask9.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

}
