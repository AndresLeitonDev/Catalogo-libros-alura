package com.alura.LiterAlura.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alura.LiterAlura.models.Autor;

public interface AutorRepository extends CrudRepository<Autor, Long> {
    

}
