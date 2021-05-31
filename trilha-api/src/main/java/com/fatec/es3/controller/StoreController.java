package com.fatec.es3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.StoreService;
import com.fatec.es3.model.Product;
import com.fatec.es3.model.ProductPlusActive;
import com.fatec.es3.model.PurchasedProduct;

@RestController
@RequestMapping("/loja")
public class StoreController {

	@Autowired
	StoreService storeService;

	@PostMapping("/listar")
	public List<ProductPlusActive> getAll(@RequestBody PurchasedProduct purchasedProduct) {
		// Retorna todos os produtos cadastrados na base de dados
		return storeService.listAllProducts(purchasedProduct);
	}

	@PostMapping("/consultar")
	public ProductPlusActive getById(@RequestBody PurchasedProduct purchasedProduct) {
		// Retorna produto pelo id
		return storeService.getProductById(purchasedProduct);
	}

	@PostMapping("/comprar")
	@ResponseStatus(HttpStatus.CREATED)
	public Product buyProduct(@RequestBody PurchasedProduct purchasedProduct) {
		// Realiza compra de um produto para um determinado usuario.
		// ou seja cadastra um registro na tabela 'purchased_products'.
		return storeService.buyProductById(purchasedProduct);
	}

	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public Product addProduct(@RequestBody Product product) {
		// End-point para cadastro de produtos no banco de dados.
		// Não utilizado pelo front.
		return storeService.addProduct(product);
	}

}
