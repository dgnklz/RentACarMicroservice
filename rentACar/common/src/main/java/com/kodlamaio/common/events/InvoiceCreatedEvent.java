package com.kodlamaio.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreatedEvent {
	private String message;
	private String carId;
	private String rentalId;
	private double totalPrice;
}
