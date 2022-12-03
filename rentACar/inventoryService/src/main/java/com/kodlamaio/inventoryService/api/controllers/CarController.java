package com.kodlamaio.inventoryService.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarController {
	private CarService carService;
	
	@GetMapping("/get")
	public List<GetAllCarsResponse> getAll() {
		return carService.getAll();
	}
	
	@PostMapping("/add")
	public CreateCarResponse add(@RequestBody CreateCarRequest createRequest) {
		return carService.add(createRequest);
	}
	
	@PutMapping("/update")
	public UpdateCarResponse update(@RequestBody UpdateCarRequest updateRequest) {
		return carService.update(updateRequest);
	}
	
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable String id) {
		carService.delete(id);
	}
	
	@GetMapping("/get/{id}")
	public void checkIfCarAvailable(@PathVariable String id) {
		carService.checkIfCarAvailable(id);
	}
	
}
