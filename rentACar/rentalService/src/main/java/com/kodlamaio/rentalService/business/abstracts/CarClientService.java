package com.kodlamaio.rentalService.business.abstracts;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;

@FeignClient(value = "CarClient", url = "http://localhost:9011")
public interface CarClientService{
	@RequestMapping(method = RequestMethod.GET, value = "/stock/api/cars/get/{id}")
	@Headers(value = "Content-Type: application/json")
	Object getCar(@PathVariable String id);
}