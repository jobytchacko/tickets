package com.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tickets.repository")
@EntityScan("com.tickets.entity")
public class TicketApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class,args);
		
	}

}
