package com.alura.LiterAlura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.LiterAlura.services.LiterAluraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LiterAluraService literAluraServices;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		literAluraServices.initApplication();
		
	}

}
