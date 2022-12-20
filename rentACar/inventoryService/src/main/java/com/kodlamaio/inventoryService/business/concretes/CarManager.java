package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.filter.car.CarCreatedEvent;
import com.kodlamaio.common.events.filter.car.CarDeletedEvent;
import com.kodlamaio.common.events.filter.car.CarUpdatedEvent;
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
import com.kodlamaio.inventoryService.dataAccess.CarRepository;
import com.kodlamaio.inventoryService.entities.Car;
import com.kodlamaio.inventoryService.entities.Model;
import com.kodlamaio.inventoryService.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService{
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;
	private InventoryProducer inventoryProducer;

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
		checkIfModelExistsById(createRequest.getModelId());
		
		Car car = modelMapperService.forRequest().map(createRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		Car result = carRepository.save(car);
		
		CarCreatedEvent carCreatedEvent = this.modelMapperService.forRequest().map(result, CarCreatedEvent.class);
		carCreatedEvent.setId(result.getId());
		carCreatedEvent.setModelId(result.getModel().getId());
		carCreatedEvent.setModelName(result.getModel().getName());
		carCreatedEvent.setBrandId(result.getModel().getBrand().getId());
		carCreatedEvent.setBrandName(result.getModel().getBrand().getName());
		this.inventoryProducer.sendMessage(carCreatedEvent);
		
		CreateCarResponse response = modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return response;
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateRequest) {
		checkIfCarExistsById(updateRequest.getId());
		checkIfModelExistsById(updateRequest.getModelId());
		checkIfPlateExists(updateRequest.getPlate());
		
		Car car = modelMapperService.forRequest().map(updateRequest, Car.class);
		Car result = carRepository.save(car);
		
		CarUpdatedEvent carUpdatedEvent = this.modelMapperService.forRequest().map(result, CarUpdatedEvent.class);
		carUpdatedEvent.setId(result.getId());
		carUpdatedEvent.setModelName(result.getModel().getName());
		carUpdatedEvent.setBrandName(result.getModel().getBrand().getName());
		this.inventoryProducer.sendMessage(carUpdatedEvent);
		
		UpdateCarResponse response = modelMapperService.forResponse().map(car, UpdateCarResponse.class);
		Model model = modelService.getModelNameByModelId(car.getModel().getId());
		response.setModelName(model.getName());
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
		CarDeletedEvent carDeletedEvent=new CarDeletedEvent();
		carDeletedEvent.setCarId(id);		
		this.inventoryProducer.sendMessage(carDeletedEvent);
	}
	
	@Override
	public void updateCarState(String carId, int state) {
		Car car = carRepository.findById(carId).get();
		System.out.println(car.getDailyPrice());
		car.setState(state);
		carRepository.save(car);
	}
	
	@Override
	public void checkIfCarAvailable(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		if (car.getState() != 1) {
			throw new BusinessException(MessagesForCar.CarDoesNotAvailable);
		}
	}

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
	
	private void checkIfModelExistsById(String id) {
		modelService.getModelById(id);
	}
	
}
