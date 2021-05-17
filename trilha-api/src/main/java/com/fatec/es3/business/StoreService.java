package com.fatec.es3.business;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.model.Product;
import com.fatec.es3.model.PurchasedProduct;
import com.fatec.es3.model.User;
import com.fatec.es3.repository.ProductRepository;
import com.fatec.es3.repository.PurchasedProductRepository;
import com.fatec.es3.repository.UserRepository;

@Service
@Transactional
public class StoreService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	PurchasedProductRepository purchasedProductRepository;

	public List<Product> listAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(long id) {
		// Caso não encontre o produto devolve um Product vazio
		return productRepository.findById(id).orElse(new Product());
	}

	public Product buyProductById(PurchasedProduct purchasedProduct) {

		User user = userRepository.findById(purchasedProduct.getUserId()).orElse(null);
		Product product = productRepository.findById(purchasedProduct.getProductId()).orElse(null);

		if (user != null && product != null) {
			// Verifica se produto já foi comprado peo usuario
			PurchasedProduct selectedPurchasedProduct = purchasedProductRepository
					.getPurchasedProductByUserAndProductId(user.getId(), product.getId());

			if (selectedPurchasedProduct == null) {
				purchasedProductRepository.save(purchasedProduct);
				return product;
			}
		}

		// Se nao houver a compra, devolve Product vazio
		return new Product();
	}

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

}
