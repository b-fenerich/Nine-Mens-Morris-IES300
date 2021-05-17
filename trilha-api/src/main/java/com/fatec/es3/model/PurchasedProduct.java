package com.fatec.es3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PurchasedProduct {

	@Id
	@Column(name = "purchased_product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private long userId;

	@Column(nullable = false)
	private long productId;

	@Column(nullable = false)
	private boolean active;

}
