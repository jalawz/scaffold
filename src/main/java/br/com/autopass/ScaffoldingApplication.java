package br.com.autopass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ScaffoldingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ScaffoldingApplication.class, args);
	}

}
