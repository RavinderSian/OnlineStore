package com.personal.onlinestore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	@NotEmpty(message = "Please enter a valid first name")
	@Column(name = "first_name")
	private String firstName;
	
	@NotEmpty(message = "Please enter a valid last name")
	@Column(name = "last_name")
	private String lastName;
	
	@CreditCardNumber(message = "Please enter a valid card number")
	@Column(name = "credit_card_number")
	private String cardNumber;
	
	@NotEmpty(message = "Please enter a valid post code")
	@Column(name = "post_code")
	private String postCode;

}