package com.g2.gromed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GromedApplication {

	public static void main(String[] args) {
		SpringApplication.run(GromedApplication.class, args);
	}

}
