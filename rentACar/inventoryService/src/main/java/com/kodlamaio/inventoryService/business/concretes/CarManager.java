package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryService.business.abstracts.CarService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.MessagesForCar;
import com.kodlamaio.inventoryService.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateCarStateResponse;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService{
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = carRepository.findAll();
		return cars.stream().map(car -> modelMapperService.forResponse()
				.map(car, GetAllCarsResponse.class))
					.toList();
	}
	
	@Override
	public GetCarResponse getCarById(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		return modelMapperService.forResponse().map(car, GetCarResponse.class);
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createRequest) {
		checkIfPlateExists(createRequest.getPlate());
		checkIfModelExistById(createRequest.getModelId());
		Car car = modelMapperService.forRequest().map(createRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		carRepository.save(car);
		return modelMapperService.forResponse().map(car, CreateCarResponse.class);
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateRequest) {
		checkIfCarExistsById(updateRequest.getId());
		Car car = modelMapperService.forRequest().map(updateRequest, Car.class);
		carRepository.save(car);
		return modelMapperService.forResponse().map(car, UpdateCarResponse.class);
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
	}
	
	public UpdateCarStateResponse updateCarState(String id) {
		Car car = carRepository.findById(id).orElse(null);
		car.setState(1);
		carRepository.save(car);
		
		return modelMapperService.forResponse().map(car, UpdateCarStateResponse.class);
	}
	
	/// Public Rules \\\
	
	
	
	/// Private Rules \\\
	
	private void checkIfCarExistsById(String id) {
		if(carRepository.findById(id) == null) {
			throw new BusinessException(MessagesForCar.CarNotExist);
		}
	}
	
	private void checkIfPlateExists(String plate) {
		if(carRepository.findByPlate(plate) != null) {
			throw new BusinessException(MessagesForCar.PlateExist);
		}
	}
	
	private void checkIfModelExistById(String id) {
		modelService.getModelById(id);
	}
	
}
