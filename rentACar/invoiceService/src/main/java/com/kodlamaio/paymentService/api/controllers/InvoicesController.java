package com.kodlamaio.paymentService.api.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.paymentService.business.abstracts.InvoiceService;
import com.kodlamaio.paymentService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.paymentService.business.responses.CreateInvoiceResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoicesController {
	private InvoiceService invoiceService;

	@PostMapping
	public CreateInvoiceResponse add(@Valid @RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return invoiceService.add(createInvoiceRequest);
	}
}
