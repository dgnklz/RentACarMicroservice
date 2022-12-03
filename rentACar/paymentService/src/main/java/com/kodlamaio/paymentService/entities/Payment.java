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
	
	@Column(name = "rentalId")
	private String rentalId;
	
	@Column(name = "cardNo")
	private String cardNo;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "balance")
	private double balance;
	
}
