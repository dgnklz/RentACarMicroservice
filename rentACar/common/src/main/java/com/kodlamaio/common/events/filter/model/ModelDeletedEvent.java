package com.kodlamaio.common.events.filter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDeletedEvent {
	private String message;
	private String modelId;
}