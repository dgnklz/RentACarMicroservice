package com.kodlamaio.paymentService.business.abstracts;

import java.util.List;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.common.events.PaymentCreatedEvent;
import com.kodlamaio.paymentService.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.paymentService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.paymentService.business.responses.GetInvoiceResponse;
import com.kodlamaio.paymentService.business.responses.UpdateInvoiceResponse;

public interface InvoiceService {
	List<GetAllInvoicesResponse> getAll();
	void add(InvoiceCreatedEvent event);
	void delete(String id);
	UpdateInvoiceResponse update(UpdateInvoiceRequest updateRequest);
	GetInvoiceResponse getById(String id);
}
