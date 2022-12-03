package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryService.entities.Brand;

public interface BrandService {
	List<GetAllBrandsResponse> getAll();
	GetBrandResponse getBrandById(String id);
	GetBrandResponse getBrandByName(String name);
	CreateBrandResponse add(CreateBrandRequest createRequest);
	UpdateBrandResponse update(UpdateBrandRequest updateRequest);
	void delete(String id);
	
	public Brand getBrandNameByBrandId(String id);
}
