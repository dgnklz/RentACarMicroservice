package com.kodlamaio.common.events.filter.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandCreatedEvent {
	private String message;
	private String brandId;
	private String brandName;
}