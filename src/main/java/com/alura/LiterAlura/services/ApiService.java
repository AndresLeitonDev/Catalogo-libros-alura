package com.alura.LiterAlura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import com.alura.LiterAlura.models.Book;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void callAllBooksApi() {
        try {
            String url = "https://gutendex.com/books?sort=popular";

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
                    System.out.println("Total de Libros encontrados: " + books.size());
                    System.out.println("-------------------------------------------------");
                    for (Book book : books) {
                        System.out.println("Título: " + book.getTitle());
                        System.out.println("Autores: ");
                        for (int i = 0; i < book.getAuthors().size(); i++) {
                            System.out.println(" - " + book.getAuthors().get(i).getName());
                        }
                        System.out.println("Temas: " + book.getSubjects());
                        System.out.println("Lenguajes: " + book.getLanguages());
                        System.out.println("Número de descargas: " + book.getDownload_count());
                        System.out.println("-------------------------------------------------");
                    }
                }

            }
        } catch (InterruptedException | IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void callBooksByTitleApi(String query) {

        try {
            String url = "https://gutendex.com/books?languages=es&search=" + query.replaceAll(" ", "%20");
            //System.out.println(url);

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
                    System.out.println("-------------------------------------------------");
                    System.out.println("Título: " + books.get(0).getTitle());
                    System.out.println("Autores: ");
                    for (int i = 0; i < books.get(0).getAuthors().size(); i++) {
                        System.out.println(" - " + books.get(0).getAuthors().get(i).getName());
                    }
                    System.out.println("Temas: " + books.get(0).getSubjects());
                    System.out.println("Lenguajes: " + books.get(0).getLanguages());
                    System.out.println("Número de descargas: " + books.get(0).getDownload_count());
                    System.out.println("-------------------------------------------------");

                }else{
                    System.out.println("No se encontraron libros con el título: " + query);
                }

            }
        } catch (InterruptedException | IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

}
