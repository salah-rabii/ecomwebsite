package com.myecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @SpringBootApplication : Indique que cette classe est le point d'entrée de l'application Spring Boot.
// Elle active également le scan automatique des composants dans les sous-packages.
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.myecommerce.repository") // Active le scan des repositories JPA
@EntityScan(basePackages = "com.myecommerce.model")
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}