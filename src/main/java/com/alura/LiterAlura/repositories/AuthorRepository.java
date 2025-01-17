package com.alura.LiterAlura.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.LiterAlura.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= ?1 AND a.deathYear <= ?1")
    List<Author> findByRangeBirthYear(int birthYear, int deathYear);
}
