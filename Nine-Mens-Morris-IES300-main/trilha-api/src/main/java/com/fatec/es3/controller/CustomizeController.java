package com.fatec.es3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.CustomizeService;
import com.fatec.es3.model.ProductPlusActive;
import com.fatec.es3.model.PurchasedProduct;
import com.fatec.es3.model.User;

@RestController
@RequestMapping("/personalizar")
public class CustomizeController {

	@Autowired
	CustomizeService customizeService;

	@PostMapping
	public List<ProductPlusActive> getAllProdcuts(@RequestBody User user) {
		// Retorna todos os produtos comprados pelo usuario
		return customizeService.listAllProducts(user);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductPlusActive buyProduct(@RequestBody PurchasedProduct purchasedProduct) {
		// Torna um produto comprado ativo

		return customizeService.activeProduct(purchasedProduct);
	}
}
