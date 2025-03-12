package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.entity.CustomerEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.repository.CustomerRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/session")
public class SessionController {
	@Autowired
	private CustomerRepository repository;

	@Autowired
	WebClient webClient;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody CustomerEntity customerEntity) {

		String email = customerEntity.getEmail();
		String password = customerEntity.getPassword();

		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			return ResponseEntity.badRequest().body("Email and password are required.");
		}

		Optional<CustomerEntity> customerOpt = repository.findByEmail(email);

		if (customerOpt.isEmpty()) {
			return ResponseEntity.status(401).body("Invalid email or password.");
		}

		CustomerEntity customer = customerOpt.get();

		if (!customer.getPassword().equals(password)) {
			return ResponseEntity.status(401).body("Invalid email or password.");
		}

		//
		// get all restaurant from restaurant service

//		String str = webClient.get().uri("http://localhost:9877/restaurants") // Replace with actual URL
//				.retrieve().bodyToMono(String.class) // Change to required response type
//				.block();
//		System.out.println(str);

		String str = webClient.get().uri("http://ZOMATO_RESTAURANT_SERVICE/restaurants") // Replace with actual URL
				.retrieve().bodyToMono(String.class) // Change to required response type
				.block();
		System.out.println(str);

		Gson gson = new Gson();

		List list = gson.fromJson(str, List.class);

		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("customer", customerOpt.get());
		map.put("restaurants", list);

		return ResponseEntity.ok(map);
	}
}
