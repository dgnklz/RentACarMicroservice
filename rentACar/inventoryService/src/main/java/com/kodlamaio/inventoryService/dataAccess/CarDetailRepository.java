package com.kodlamaio.inventoryService.dataAccess;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kodlamaio.inventoryService.entities.dtos.CarDetailDto;

public interface CarDetailRepository extends MongoRepository<CarDetailDto, String>{
	
}
