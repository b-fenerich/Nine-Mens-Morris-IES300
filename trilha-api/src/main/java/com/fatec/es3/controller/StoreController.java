package com.fatec.es3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.StoreService;
import com.fatec.es3.model.Product;
import com.fatec.es3.model.ProductPlusActive;
import com.fatec.es3.model.PurchasedProduct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/loja")
public class StoreController {

	@Autowired
	StoreService storeService;

	@GetMapping("/listar")
	public List<Product> getAll() {
		log.info("list products request!");
		return storeService.listAllProducts();
	}

	@PostMapping("/listar")
	public List<ProductPlusActive> getAllByUser(@RequestBody PurchasedProduct purchasedProduct) {
		log.info("list products request for userId: " + String.valueOf(purchasedProduct.getUserId()));
		// Retorna todos os produtos cadastrados na base de dados
		return storeService.listAllProducts(purchasedProduct);
	}

	@PostMapping("/consultar")
	public ProductPlusActive getById(@RequestBody PurchasedProduct purchasedProduct) {
		log.info("get product request: " + purchasedProduct.toString());
		// Retorna produto pelo id
		return storeService.getProductById(purchasedProduct);
	}

	@PostMapping("/comprar")
	@ResponseStatus(HttpStatus.CREATED)
	public Product buyProduct(@RequestBody PurchasedProduct purchasedProduct) {
		log.info("buy product request: " + purchasedProduct.toString());
		// Realiza compra de um produto para um determinado usuario.
		// ou seja cadastra um registro na tabela 'purchased_products'.
		return storeService.buyProductById(purchasedProduct);
	}

	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public Product addProduct(@RequestBody Product product) {
		log.info("register product request: " + product.toString());
		// End-point para cadastro de produtos no banco de dados.
		// Não utilizado pelo front.
		return storeService.addProduct(product);
	}

}
