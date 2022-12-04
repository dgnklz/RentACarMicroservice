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
import com.kodlamaio.inventoryService.dataAccess.CarDetailRepository;
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.entities.Model;
import com.kodlamaio.inventoryService.entities.dtos.CarDetailDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService{
	private CarRepository carRepository;
	private CarDetailRepository carDetailRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = carRepository.findAll();
		List<GetAllCarsResponse> responses = cars.stream().map(car -> modelMapperService.forResponse()
				.map(car, GetAllCarsResponse.class))
				.toList();
		return responses;
	}
	
	@Override
	public GetCarResponse getCarById(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		
		GetCarResponse response = modelMapperService.forResponse().map(car, GetCarResponse.class);
		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createRequest) {
		checkIfPlateExists(createRequest.getPlate());
		checkIfModelExistById(createRequest.getModelId());
		
		Car car = modelMapperService.forRequest().map(createRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		car.setState(2);
		carRepository.save(car);
		
		CreateCarResponse response = modelMapperService.forResponse().map(car, CreateCarResponse.class);
		Model model = modelService.getModelNameByModelId(car.getModel().getId());
		response.setModelName(model.getName());
		
		CarDetailDto carDetailDto = modelMapperService.forRequest().map(createRequest, CarDetailDto.class);
		carDetailRepository.save(carDetailDto);
		
		return response;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateRequest) {
		checkIfCarExistsById(updateRequest.getId());
		checkIfModelExistById(updateRequest.getModelId());
		checkIfPlateExists(updateRequest.getPlate());
		
		Car car = modelMapperService.forRequest().map(updateRequest, Car.class);
		car.setState(2);
		carRepository.save(car);
		
		UpdateCarResponse response = modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		Model model = modelService.getModelNameByModelId(car.getModel().getId());
		response.setModelName(model.getName());
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
	}
	
	public void checkIfCarAvailable(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		if (car.getState() == 1) {
			throw new BusinessException(MessagesForCar.CarDoesNotAvailable);
		}
	}
	
	public void updateCarStateForRentalCreate(String id) {
		Car car = carRepository.findById(id).orElse(null);
		car.setState(1);
		carRepository.save(car);
	}
	
	public void updateCarStateForRentalUpdate(String oldCarId, String newCarId) {
		Car oldCar = carRepository.findById(oldCarId).orElse(null);
		oldCar.setState(2);
		carRepository.save(oldCar);
		
		Car newCar = carRepository.findById(newCarId).orElse(null);
		newCar.setState(1);
		carRepository.save(newCar);
		
//		UpdateCarStateForRentalUpdateResponse response = new UpdateCarStateForRentalUpdateResponse();
//		response.setOldCarId(oldCar.getId());
//		response.setOldCarState(oldCar.getState());
//		response.setNewCarId(newCar.getId());
//		response.setNewCarState(newCar.getState());
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
