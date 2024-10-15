package com.presidio.Backend;

import com.presidio.Backend.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Value("${email}")
    private String a;
    @Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(a+"----------------------------------------------------------------------------------------------------------------------------------");
		
	}
}
