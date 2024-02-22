package com.fusionsauth;

import com.fusionsauth.entities.UserDetailsManager;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Rest Api Documentation",
				description = "Spring Boot Rest Api Documentation Description",
				version = "V1.0",
				contact = @Contact(
						name = "Santhosh",
						email = "santhoshbukka5@gmail.com",
						url = "http://localhost:8080/swagger-ui.html"
				),
				license = @License(
						name = "Santhosh 2.0",
						url = "jhyqbdeiuqwjka"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "iqu3hnediqwlk",
				url = "iuwq3hnediqk"
		)
)
public class FusionAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusionAuthApplication.class, args);
	}

}
