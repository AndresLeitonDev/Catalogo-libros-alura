package com.alura.LiterAlura.services;

import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiterAluraService {

    @Autowired
    private BookApiService bookApiService;

    @Autowired
    private AuthorApiService authorApiService;

    public void initApplication() {

        Map<String, String> languajes = Map.of("1", "es", "2", "en", "3", "fr", "4", "de", "5", "it", "6", "pt");

        Scanner s = new Scanner(System.in);

        System.out.println("\n\n###############################################");
        System.out.println("  Bienvenido al sistema de Catálogo de libros  ");
        System.out.println("###############################################\n");

        System.out.println("Por favor seleccione una opción...");

        System.out.println("1. Consultar libros");
        System.out.println("2. Consultar autores");
        System.out.println("3. Salir");

        System.out.print("Opción: ");

        if (s.hasNextInt()) {
            int option = Integer.parseInt(s.nextLine());

            switch (option) {
                case 1:
                    System.out.println("\n---- Consultando libros ----");

                    System.out.println("1. Consultar libros por título");
                    System.out.println("2. Consultar libros por idioma");
                    System.out.println("3. Consultar libros registrados");
                    System.out.println("4. Regresar al menú principal");

                    System.out.print("Opción: ");
                    if (s.hasNextInt()) {
                        int optionBooks = Integer.parseInt(s.nextLine());
                        switch (optionBooks) {
                            case 1:
                                System.out.println("Ingrese el título del libro...");
                                String title = s.nextLine();
                                if(title.isEmpty()){
                                    System.out.println("\n--- Por favor, ingrese un título válido ---\n ");
                                }else{
                                    bookApiService.callBooksByTitleApi(title);
                                }
                                initApplication();
                                break;
                            case 2:
                                System.out.println("Por favor seleccione un idioma...");
                                System.out.println("1. Español (es)");
                                System.out.println("2. Inglés (en)");
                                System.out.println("3. Francés (fr)");
                                System.out.println("4. Alemán (de)");
                                System.out.println("5. Italiano (it)");
                                System.out.println("6. Portugués (pt)");
                                System.out.println("7. Regresar al menú principal");

                                System.out.print("Opción: ");
                                if (s.hasNextInt()) {
                                    int optionLanguage = Integer.parseInt(s.nextLine());
                                    if (optionLanguage == 7) {
                                        initApplication();
                                    } else {
                                        String language = languajes.get(String.valueOf(optionLanguage));
                                        bookApiService.searchBooksByLanguage(language);
                                        initApplication();
                                    }
                                } else {
                                    System.out.println("\n--- Por favor, ingrese una opción válida ---\n ");
                                    initApplication();
                                }
                                break;
                            case 3:
                                System.out.println("Consultando libros registrados...");
                                bookApiService.searchAllBooksRegistered();
                                initApplication();
                                break;
                            case 4:
                                initApplication();
                                break;
                            default:
                                System.out.println("Opción no válida");
                                initApplication();
                                break;
                        }

                    } else {
                        System.out.println("\n--- Por favor, ingrese una opción válida ---\n ");
                        initApplication();
                    }
                    break;
                case 2:
                    System.out.println("\n ---- Consultando autores ----");

                    System.out.println("1. Consultar autores registrados");
                    System.out.println("2. Consultar autores vivos en un rango de años");
                    System.out.println("3. Regresar al menú principal");

                    System.out.print("Opción: ");
                    if (s.hasNextInt()) {
                        int optionAuthors = Integer.parseInt(s.nextLine());

                        switch (optionAuthors) {
                            case 1:
                                System.out.println("Consultando autores registrados...");
                                authorApiService.searchAllAuthorsRegistered();
                                initApplication();
                                break;
                            case 2:
                                System.out.println("Ingrese el año que desea consultar...");
                                if (s.hasNextInt()) {
                                    int year = Integer.parseInt(s.nextLine());
                                    authorApiService.searchAuthorsByRangeYear(year);
                                    initApplication();
                                }else{
                                    System.out.println("\n--- Por favor, ingrese un año válido ---\n ");
                                }
                                break;
                            case 3:
                                initApplication();
                                break;
                            default:
                                System.out.println("Opción no válida");
                                break;
                        }
                    } else {
                        System.out.println("\n--- Por favor, ingrese una opción válida ---\n ");
                    }
                    break;
                case 3:
                    System.out.println("Gracias por usar el sistema de Catálogo de libros");
                    s.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida");
                    initApplication();
                    break;
            }

        } else {
            System.out.println("\n--- Por favor, ingrese una opción válida ---\n ");
            initApplication();
        }

        //s.close();
    }

}
