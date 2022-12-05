package com.kodlamaio.inventoryService.api.controllers;

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

import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/brands")
public class BrandController {
	private BrandService brandService;

	@GetMapping("/get")
	public List<GetAllBrandsResponse> getAll() {
		return brandService.getAll();
	}

	@PostMapping("/add")
	public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest createRequest) {
		return brandService.add(createRequest);
	}

	@PutMapping("/update")
	public UpdateBrandResponse update(@Valid @RequestBody UpdateBrandRequest updateRequest) {
		return brandService.update(updateRequest);
	}

	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable String id) {
		brandService.delete(id);
	}

}
