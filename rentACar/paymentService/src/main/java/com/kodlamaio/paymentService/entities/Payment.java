package com.kodlamaio.paymentService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@Column(name = "rentalId")
	private String rentalId;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "cardNo")	
	private String cardNo;
	
	@Column(name = "cardHolder")
	private String cardHolder;
	
	@Column(name = "balance")
	private double balance;
}