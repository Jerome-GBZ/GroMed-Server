package com.g2.gromed;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gromed API", version = "0.1", description = "L'api de gromed contenant tout les endpoints et les définitions des schémas"))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GromedApplication.class, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class GromedApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GromedApplication.class, args);
	}
	
}