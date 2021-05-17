package com.fatec.es3.model;

import lombok.Data;

@Data
public class ProductPlusActive {
	// Usado para o response do end point /personalizar, method=GET

	private long id;

	private Product product;

	private boolean status;

}
