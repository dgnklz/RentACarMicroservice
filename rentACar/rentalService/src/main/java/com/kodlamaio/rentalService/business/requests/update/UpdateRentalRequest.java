package com.kodlamaio.rentalService.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	@NotBlank
	@NotNull
	private String id;
	
	@NotBlank
	@NotNull
	private String carId;
	
	@Min(1)
	@NotNull
	private int rentedForDays;
	
	@Min(0)
	@NotNull
	private double dailyPrice;
}
