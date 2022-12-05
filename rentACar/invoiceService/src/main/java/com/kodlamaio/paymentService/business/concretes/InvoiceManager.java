package com.kodlamaio.paymentService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.paymentService.business.responses.CreateInvoiceResponse;
import com.kodlamaio.paymentService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.paymentService.dataAccess.InvoiceRepository;
import com.kodlamaio.paymentService.entities.Invoice;
import com.kodlamaio.paymentService.kafka.InvoiceProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager {
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private InvoiceProducer invoiceProducer;
	
	@Override
	public List<GetAllInvoicesResponse> getAll() {
		return null;
	}

	@Override
	public CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());

		Invoice createdInvoice = invoiceRepository.save(invoice);

		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setPaymentId(createdInvoice.getPaymentId());
		invoiceCreatedEvent.setMessage("Payment Created");

		this.invoiceProducer.sendMessage(invoiceCreatedEvent);

		CreateInvoiceResponse createPaymentResponse = modelMapperService.forResponse().map(invoice,
				CreateInvoiceResponse.class);

		return createPaymentResponse;
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest) {
		return null;
	}

	@Override
	public GetInvoiceResponse getById(String id) {
		return null;
	}
}
