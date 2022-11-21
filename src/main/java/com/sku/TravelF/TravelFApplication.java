package com.sku.TravelF;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelFApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelFApplication.class, args);
	}

}
