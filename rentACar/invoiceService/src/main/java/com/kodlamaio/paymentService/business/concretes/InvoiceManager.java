package com.kodlamaio.paymentService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.paymentService.business.abstracts.InvoiceService;
import com.kodlamaio.paymentService.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.paymentService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.paymentService.business.responses.GetInvoiceResponse;
import com.kodlamaio.paymentService.business.responses.UpdateInvoiceResponse;
import com.kodlamaio.paymentService.dataAccess.InvoiceRepository;
import com.kodlamaio.paymentService.entities.Invoice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService{
	private InvoiceRepository invoiceRepository;
	
	@Override
	public List<GetAllInvoicesResponse> getAll() {
		return null;
	}

	@Override
	public void add(InvoiceCreatedEvent event) {
		Invoice invoice = new Invoice();
		invoice.setId(UUID.randomUUID().toString());
		invoice.setRentalId(event.getRentalId());
		invoice.setCarId(event.getCarId());
		invoice.setTotalPrice(event.getTotalPrice());
		
		invoiceRepository.save(invoice);
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public UpdateInvoiceResponse update(UpdateInvoiceRequest updateRequest) {
		return null;
	}

	@Override
	public GetInvoiceResponse getById(String id) {
		return null;
	}
}
