package com.g2.gromed;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gromed API", version = "0.1", description = "L'api de gromed contenant tout les endpoints et les définitions des schémas"))
public class GromedApplication {

	public static void main(String[] args) {
		SpringApplication.run(GromedApplication.class, args);
	}

}
