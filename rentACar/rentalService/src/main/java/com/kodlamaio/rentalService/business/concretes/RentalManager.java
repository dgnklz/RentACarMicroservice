package com.kodlamaio.rentalService.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.configurations.kafka.RentalProducer;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService{
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRequest) {
		Rental rental = modelMapperService.forRequest().map(createRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		double totalPrice = (createRequest.getDailyPrice() * createRequest.getRentedForDays());
		rental.setTotalPrice(totalPrice);
		
		rentalRepository.save(rental);
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rental.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");
		this.rentalProducer.sendMessage(rentalCreatedEvent);
		
		return modelMapperService.forResponse().map(rental, CreateRentalResponse.class);
	}
}