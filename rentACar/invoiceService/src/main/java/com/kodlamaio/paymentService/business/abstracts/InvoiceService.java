package com.kodlamaio.paymentService.business.abstracts;

import java.util.List;

import com.kodlamaio.paymentService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.paymentService.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.paymentService.business.responses.CreateInvoiceResponse;
import com.kodlamaio.paymentService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.paymentService.business.responses.GetInvoiceResponse;
import com.kodlamaio.paymentService.business.responses.UpdateInvoiceResponse;

public interface InvoiceService {
	List<GetAllInvoicesResponse> getAll();
	CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest);
	void delete(String id);
	UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest);
	GetInvoiceResponse getById(String id);
}
