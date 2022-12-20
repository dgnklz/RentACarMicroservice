package com.kodlamaio.paymentService.business.abstracts;

import java.util.List;

import com.kodlamaio.paymentService.business.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.get.GetAllPaymentResponse;
import com.kodlamaio.paymentService.business.responses.update.UpdatePaymentResponse;

public interface PaymentService {
	List<GetAllPaymentResponse> getAll();
	void add(String rentalId, double totalPrice, double balance, String cardHolder, String cardNo);
	UpdatePaymentResponse update(UpdatePaymentRequest updateRequest);
	void delete(String id);
}
