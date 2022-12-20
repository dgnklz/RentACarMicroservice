package com.kodlamaio.paymentService.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.paymentService.business.abstracts.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {
	private PaymentService paymentService;
	
	@PostMapping("/add")
	public void add(@RequestParam String rentalId, @RequestParam double totalPrice, @RequestParam double balance, @RequestParam String cardHolder, @RequestParam String cardNo) {
		paymentService.add(rentalId, totalPrice, balance, cardHolder, cardNo);
	}
}
