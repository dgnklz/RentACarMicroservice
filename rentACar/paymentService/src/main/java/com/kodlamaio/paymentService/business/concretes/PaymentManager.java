package com.kodlamaio.paymentService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;
import com.kodlamaio.paymentService.business.constants.MessagesForPayment;
import com.kodlamaio.paymentService.business.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.get.GetAllPaymentResponse;
import com.kodlamaio.paymentService.business.responses.update.UpdatePaymentResponse;
import com.kodlamaio.paymentService.dataAccess.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;
import com.kodlamaio.rentalService.business.constants.MessagesForRental;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;

	@Override
	public List<GetAllPaymentResponse> getAll() {
		List<Payment> payments = paymentRepository.findAll();
		List<GetAllPaymentResponse> responses = payments.stream().map(payment -> modelMapperService.forResponse().map(payment, GetAllPaymentResponse.class))
				.toList();
		return responses;
	}

	@Override
	public CreatePaymentResponse add(CreatePaymentRequest createRequest) {
		checkIfRentalIdAvailable(createRequest.getRentalId());
		
		Payment payment = modelMapperService.forRequest().map(createRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		
		paymentRepository.save(payment);
		
		CreatePaymentResponse response = modelMapperService.forResponse().map(payment, CreatePaymentResponse.class);
		return response;
	}

	@Override
	public UpdatePaymentResponse update(UpdatePaymentRequest updateRequest) {
		checkIfPaymentExistById(updateRequest.getId());
		
		Payment payment = modelMapperService.forRequest().map(updateRequest, Payment.class);
		
		paymentRepository.save(payment);
		
		UpdatePaymentResponse response = modelMapperService.forResponse().map(payment, UpdatePaymentResponse.class);
		
		return response;
	}

	@Override
	public void delete(String id) {
		
	}
	
	/// Private Rules \\\
	
	private void checkIfPaymentExistById(String id) {
		if (paymentRepository.findById(id) == null) {
			throw new BusinessException(MessagesForPayment.PaymentIsNotAvailable);
		}
	}
	
	private void checkIfRentalIdAvailable(String id) {
		if (paymentRepository.findByRentalId(id) != null) {
			throw new BusinessException(MessagesForPayment.RentalIdIsTaken);
		}
	}

}