package com.alura.LiterAlura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alura.LiterAlura.models.Author;
import com.alura.LiterAlura.models.Book;
import com.alura.LiterAlura.repositories.AuthorRepository;
import com.alura.LiterAlura.repositories.BookRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookApiServiceJpaImpl implements BookApiService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorApiService authorApiService;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void callBooksByTitleApi(String query) {

        try {
            String url = "https://gutendex.com/books?search=" + query.replaceAll(" ", "%20");
            // System.out.println(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                JsonElement jelement = JsonParser.parseString(response.body());
                JsonObject jobject = jelement.getAsJsonObject();

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                String jsonArrayString = jobject.getAsJsonArray("results").toString();
                List<Book> books = List.of(mapper.readValue(jsonArrayString, Book[].class));

                if (books.size() > 0) {
                    Book book = books.get(0);
                    System.out.println("------------------ LIBRO -----------------------");
                    System.out.println("Título: " + book.getTitle());
                    System.out.println("Autores: ");
                    for (int i = 0; i < book.getAuthors().size(); i++) {
                        System.out.println(" - " + book.getAuthors().get(i).getName());
                    }
                    System.out.println("Idiomas: ");
                    for (int i = 0; i < book.getLanguages().size(); i++) {
                        System.out.println(" - " + book.getLanguages().get(i));
                    }
                    System.out.println("Número de descargas: " + book.getDownload_count());
                    System.out.println("-------------------------------------------------");

                    Optional<Book> optBook = bookRepository.findById(book.getId());
                    if (!optBook.isPresent()) {
                        List<Author> authors = book.getAuthors();
                        List<Author> authorsInsert = new ArrayList<>();
                        for (Author author : authors) {
                            Optional<Author> optAuthor = authorRepository.findByName(author.getName());
                            if (!optAuthor.isPresent()) {
                                Author authorInsert = authorApiService.saveAuthor(author);
                                authorsInsert.add(authorInsert);
                            } else {
                                authorsInsert.add(optAuthor.orElseThrow());
                            }
                        }
                        book.setAuthors(authorsInsert);
                        saveBook(book);
                    }
                }
            } else {
                System.out.println("\nNo se encontraron libros con el título: " + query);
            }

        } catch (InterruptedException |

                IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public void searchAllBooksRegistered() {
        List<Book> books = bookRepository.findAll();
        if (books.size() > 0) {
            for (Book book : books) {
                System.out.println("\n------------------ LIBRO -----------------------");
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autores: ");
                for (int i = 0; i < book.getAuthors().size(); i++) {
                    System.out.println(" - " + book.getAuthors().get(i).getName());
                }
                System.out.println("Idiomas: ");
                for (int i = 0; i < book.getLanguages().size(); i++) {
                    System.out.println(" - " + book.getLanguages().get(i));
                }
                System.out.println("Número de descargas: " + book.getDownload_count());
            }
        } else {
            System.out.println("\nNo se encontraron libros registrados");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void searchBooksByLanguage(String language) {
        List<Book> books = bookRepository.findByLanguage(language);
        if (books.size() > 0) {
            for (Book book : books) {
                System.out.println("\n------------------ LIBRO -----------------------");
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autores: ");
                for (int i = 0; i < book.getAuthors().size(); i++) {
                    System.out.println(" - " + book.getAuthors().get(i).getName());
                }
                System.out.println("Idiomas: ");
                for (int i = 0; i < book.getLanguages().size(); i++) {
                    System.out.println(" - " + book.getLanguages().get(i));
                }
                System.out.println("Número de descargas: " + book.getDownload_count());
            }
        } else {
            System.out.println("\nNo se encontraron libros registrados en el idioma: " + language);
        }
    }
}
