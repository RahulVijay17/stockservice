package com.laderatech.stockservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.laderatech.stockservice.repository")
@EntityScan("com.laderatech.stockservice.model")
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "Stock Service REST APIs",
				description = "Stock Service REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Rahul",
						email = "frederickraghul@gmail.com",
						url = "https://rahul.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://rahul.com"

				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Stock Service Doc",
				url = "https://rahul.com"
		)
)
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

}
