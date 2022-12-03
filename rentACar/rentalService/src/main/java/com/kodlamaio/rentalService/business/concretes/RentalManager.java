package com.kodlamaio.rentalService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentalService.business.abstracts.CarClientService;
import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.constants.MessagesForRental;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;
import com.kodlamaio.rentalService.kafka.RentalProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService{
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;
	private CarClientService carClientService;

	@Override
	public List<GetAllRentalsResponse> getAll() {
		List<Rental> rentals = rentalRepository.findAll();
		List<GetAllRentalsResponse> responses = rentals.stream().map(rental -> modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
				.toList();
		return responses;
	}
	
	@Override
	public CreateRentalResponse add(CreateRentalRequest createRequest) {
		checkIfCarIdAvailable(createRequest.getCarId());
		carClientService.getCar(createRequest.getCarId());
		
		Rental rental = modelMapperService.forRequest().map(createRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		double totalPrice = (createRequest.getDailyPrice() * createRequest.getRentedForDays());
		rental.setTotalPrice(totalPrice);
		
		rentalRepository.save(rental);
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rental.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		this.rentalProducer.sendMessage(rentalCreatedEvent);
		
		CreateRentalResponse response = modelMapperService.forResponse().map(rental, CreateRentalResponse.class);
		
		return response;
	}
	
	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRequest) {
		checkIfRentalExistById(updateRequest.getId());
		carClientService.getCar(updateRequest.getCarId());
		
		Rental tempRental = rentalRepository.findById(updateRequest.getId()).get();
		
		RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
		rentalUpdatedEvent.setOldCarId(tempRental.getCarId());
		
		Rental rental = rentalRepository.findById(updateRequest.getId()).get();
		rental.setCarId(updateRequest.getCarId());
		rental.setDailyPrice(updateRequest.getDailyPrice());
		rental.setRentedForDays(updateRequest.getRentedForDays());
		double totalPrice = (updateRequest.getDailyPrice() * updateRequest.getRentedForDays());
		rental.setTotalPrice(totalPrice);
		
		rentalRepository.save(rental);
		
		rentalUpdatedEvent.setNewCarId(rental.getCarId());
		this.rentalProducer.sendMessage(rentalUpdatedEvent);
		
		UpdateRentalResponse response = new UpdateRentalResponse();
		response.setId(rental.getId());
		response.setCarId(rental.getCarId());
		response.setDateStarted(rental.getDateStarted());
		response.setDailyPrice(rental.getDailyPrice());
		response.setRentedForDays(rental.getRentedForDays());
		response.setTotalPrice(rental.getTotalPrice());
		return response;
	}

	@Override
	public void delete(String id) {
		checkIfRentalExistById(id);
		rentalRepository.deleteById(id);
	}
	
	/// Private Rules \\\
	
	private void checkIfRentalExistById(String id) {
		if (rentalRepository.findById(id) == null) {
			throw new BusinessException(MessagesForRental.RentalNotExist);
		}
	}
	
	private void checkIfCarIdAvailable(String id) {
		if (rentalRepository.findByCarId(id) != null) {
			throw new BusinessException(MessagesForRental.CarIsNotAvailable);
		}
	}
	
}