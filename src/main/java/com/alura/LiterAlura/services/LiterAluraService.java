package com.alura.LiterAlura.services;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiterAluraService {

    @Autowired
    private ApiService apiService;

    public void initApplication(){
        
        Scanner s = new Scanner(System.in);

        System.out.println("###############################################");
        System.out.println("  Bienvenido al sistema de Catálogo de libros  ");
        System.out.println("###############################################\n");

        System.out.println("Por favor seleccione una opción...");

        for (int i = 0; i < 3; i++) {
            System.out.println("1. Consultar libros");
            System.out.println("2. Consultar autores");
            System.out.println("3. Insertar libros");
            System.out.println("4. Insertar autores");
            System.out.println("5. Salir");

            System.out.print("Opción: ");
            int option = Integer.parseInt(s.nextLine());

            switch (option) {
                case 1:
                    System.out.println(" ---- Consultando libros ----");
                    System.out.println("1. Consultar todos los libros");
                    System.out.println("2. Consultar libros por título o autor");
                    System.out.println("3. Regresar al menú principal");

                    System.out.print("Opción: ");
                    int optionBooks = Integer.parseInt(s.nextLine());

                    switch (optionBooks) {
                        case 1:
                            System.out.println("Consultando todos los libros...");
                            apiService.callAllBooksApi();
                            break;
                        case 2:
                            System.out.println("Ingrese el título o el autor del libro...");
                            String title = s.nextLine();
                            apiService.callBooksByTitleApi(title);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Consultando autores...");
                    //apiService.callAuthorsApi();
                    break;
                case 3:
                    System.out.println("Insertando libros...");
                    //apiService.insertBooksApi();
                    break;
                case 4:
                    System.out.println("Insertando autores...");
                    //apiService.insertAuthorsApi();
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema de Catálogo de libros");
                    s.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida");
                    initApplication();
                    break;
            }
        }
    }

}
