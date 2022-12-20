package com.kodlamaio.rentalService.api.controllers;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import com.kodlamaio.rentalService.business.requests.create.PayMoneyRequest;

import feign.Headers;

@FeignClient(value="PaymentClient", url = "http://localhost:9011")
public interface PaymentClientService {
//	@RequestMapping(method = RequestMethod.POST, value = "/payment/api/payments/add")
//	@Headers(value = "Content-Type: application/json")
//	Object add(@RequestBody PayMoneyRequest payMoneyRequest);
	
	@RequestMapping(method = RequestMethod.POST, value = "/payment/api/payments/add")
	@Headers(value = "Content-Type: application/json")
	void add(@RequestParam String rentalId, 
				@RequestParam double totalPrice, 
					@RequestParam double balance, 
						@RequestParam String cardHolder, 
							@RequestParam String cardNo);
}