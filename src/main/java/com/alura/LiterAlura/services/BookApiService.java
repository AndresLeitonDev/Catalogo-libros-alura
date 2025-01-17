package com.alura.LiterAlura.services;

import com.alura.LiterAlura.models.Book;

public interface BookApiService {

    void callBooksByTitleApi(String query);
    void searchAllBooksRegistered();
    void searchBooksByLanguage(String language);
    Book saveBook(Book book);
}