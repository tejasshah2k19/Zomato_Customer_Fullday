package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.CustomerEntity;
import com.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;

	@PostMapping
	public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {

		// validation
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customer);

		repository.save(customer);
//		return ResponseEntity.ok(customer);//status:200 , data:customer 
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@GetMapping
	public List<CustomerEntity> getAllCustomers() {
		return repository.findAll();// 200
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable Integer id) {
		Optional<CustomerEntity> customer = repository.findById(id);
		return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Integer id,
			@RequestBody CustomerEntity updatedCustomer) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		updatedCustomer.setCustomerId(id);
		return ResponseEntity.ok(repository.save(updatedCustomer));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
