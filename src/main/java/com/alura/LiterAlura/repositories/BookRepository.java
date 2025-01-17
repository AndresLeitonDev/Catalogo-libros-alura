package com.alura.LiterAlura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alura.LiterAlura.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books WHERE :language = ANY(languages)", nativeQuery = true)
    List<Book> findByLanguage(@Param("language") String language);

}
