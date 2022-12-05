package com.kodlamaio.paymentService.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.paymentService.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	
}
