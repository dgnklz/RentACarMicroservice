package com.kodlamaio.paymentService.business.requests.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	@NotNull
	private String rentalId;
	
	@NotNull
	private String cardNo;
	
	@Min(1)
	@Max(3)
	@NotNull
	private int status;
	
	@NotNull
	@Min(0)
	private double balance;
}
