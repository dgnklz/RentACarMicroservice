package com.kodlamaio.rentalService.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
	private RentalService rentalService;

	@PostMapping
	public CreateRentalResponse add(@RequestBody CreateRentalRequest createRequest) {
		return rentalService.add(createRequest);
	}
}
