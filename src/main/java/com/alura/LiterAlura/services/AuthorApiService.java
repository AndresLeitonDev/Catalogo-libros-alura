package com.alura.LiterAlura.services;

import com.alura.LiterAlura.models.Author;

public interface AuthorApiService {

    void searchAllAuthorsRegistered();
    void searchAuthorsByRangeYear(int year);
    Author saveAuthor(Author author);
}
