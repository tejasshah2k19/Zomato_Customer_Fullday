package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ZomatoCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoCustomerServiceApplication.class, args);
	}

	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().build();
	}
}
