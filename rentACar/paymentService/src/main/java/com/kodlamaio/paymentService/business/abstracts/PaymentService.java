package com.kodlamaio.paymentService.business.abstracts;

import java.util.List;

import com.kodlamaio.paymentService.business.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentService.business.responses.get.GetAllPaymentResponse;
import com.kodlamaio.paymentService.business.responses.update.UpdatePaymentResponse;

public interface PaymentService {
	List<GetAllPaymentResponse> getAll();
	CreatePaymentResponse add(CreatePaymentRequest createRequest);
	UpdatePaymentResponse update(UpdatePaymentRequest updateRequest);
	void delete(String id);
}
