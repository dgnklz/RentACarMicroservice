package com.kodlamaio.inventoryService.business.responses.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarStateForRentalUpdateResponse {
	private String oldCarId;
	private int oldCarState;
	
	private String newCarId;
	private int newCarState;
}
