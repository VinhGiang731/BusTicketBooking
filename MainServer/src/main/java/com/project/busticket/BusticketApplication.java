package com.project.busticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BusticketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusticketApplication.class, args);
	}

}
