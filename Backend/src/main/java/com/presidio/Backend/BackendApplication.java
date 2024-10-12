package com.presidio.Backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Value("${email}")
    private String a;



	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(a+"----------------------------------------------------------------------------------------------------------------------------------");
	}
}
