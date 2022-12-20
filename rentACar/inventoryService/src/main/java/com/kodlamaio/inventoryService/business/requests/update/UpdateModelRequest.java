package com.kodlamaio.inventoryService.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModelRequest {
	@NotNull
	private String id;
	
	@NotNull
	@NotBlank
	@Size(min = 1 ,max = 22)
	private String name;
	
	@NotNull
	@Min(0)
	private String brandId;
}
