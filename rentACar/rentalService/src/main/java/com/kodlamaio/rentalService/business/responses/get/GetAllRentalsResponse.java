package com.kodlamaio.rentalService.business.responses.get;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRentalsResponse {
	private String id;
	private String carId;
	private LocalDate dateStarted;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice;
}
