package com.alura.LiterAlura.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.LiterAlura.models.Author;
import com.alura.LiterAlura.repositories.AuthorRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorApiServiceJpaImpl implements AuthorApiService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public void searchAllAuthorsRegistered() {
        List<Author> authors = authorRepository.findAll();
        if (authors.size() > 0) {
            for (Author author : authors) {
                System.out.println("\n------------------ AUTOR -----------------------");
                System.out.println("Nombre: " + author.getName());
                System.out.println("Nacimiento: " + author.getBirthYear());
                System.out.println("Muerte: " + author.getDeathYear());
            }
        } else {
            System.out.println("\nNo hay autores registrados");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void searchAuthorsByRangeYear(int year){
        List<Author> authors = authorRepository.findByRangeBirthYear(year, year);
        if (authors.size() > 0) {
            for (Author author : authors) {
                System.out.println("\n------------------ AUTOR -----------------------");
                System.out.println("Nombre: " + author.getName());
                System.out.println("Nacimiento: " + author.getBirthYear());
                System.out.println("Muerte: " + author.getDeathYear());
            }
        } else {
            System.out.println("\nNo hay autores registrados");
        }
    }
}
