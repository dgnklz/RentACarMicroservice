package com.kodlamaio.inventoryService.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarStateResponse;

public interface CarService {
	List<GetAllCarsResponse> getAll();
	GetCarResponse getCarById(String id);
	CreateCarResponse add(CreateCarRequest createRequest);
	UpdateCarResponse update(UpdateCarRequest updateRequest);
	void delete(String id);
	
	UpdateCarStateResponse updateCarState(String id);
	
	/// Public Rules \\\
	
}
