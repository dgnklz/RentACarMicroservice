package com.kodlamaio.inventoryService.business.responses.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarStateForRentalCreateResponse {
	private String id;
	private int state;
}
