package com.kodlamaio.paymentService.business.responses.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponse {
	private String id;
	private String rentalId;
	private String cardNo;
	private int status;
	private double balance;
}
