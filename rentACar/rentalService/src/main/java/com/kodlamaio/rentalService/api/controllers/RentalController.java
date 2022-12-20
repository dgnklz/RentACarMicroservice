package com.kodlamaio.rentalService.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	private RentalService rentalService;
	
	@GetMapping("/get")
	List<GetAllRentalsResponse> getAll() {
		return rentalService.getAll();
	}
	
	@PostMapping("/add")
	public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest createRequest) {
		return rentalService.add(createRequest);
	}
	
	@PutMapping("/update")
	UpdateRentalResponse update(@Valid @RequestBody UpdateRentalRequest updateRequest) {
		return rentalService.update(updateRequest);
	}
	
	@DeleteMapping("delete/{id}")
	void delete(@PathVariable String id) {
		rentalService.delete(id);
	}
	
	@GetMapping("/get/totalpricebyid/{id}")
	public double getTotalPrice(@PathVariable String id) {
		return rentalService.getTotalPrice(id);
	}
}
