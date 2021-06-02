package com.fatec.es3.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.model.Product;
import com.fatec.es3.model.ProductPlusActive;
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

	public List<ProductPlusActive> listAllProducts(PurchasedProduct purchasedProduct) {
		List<Product> products = productRepository.findAll();

		User user = userRepository.findById(purchasedProduct.getUserId()).orElse(null);

		List<ProductPlusActive> purchasedProducts = new ArrayList<>();

		ProductPlusActive productPlusActive;

		for (Product product : products) {
			productPlusActive = new ProductPlusActive();
			productPlusActive.setId(product.getId());
			productPlusActive.setUrlImg(product.getPath());
			productPlusActive.setValue(product.getValue());

			if (user != null) {

				PurchasedProduct foundPurchasedProduct = purchasedProductRepository
						.getPurchasedProductByUserAndProductId(user.getId(), product.getId());

				if (foundPurchasedProduct != null) {
					productPlusActive.setEquipped(foundPurchasedProduct.isActive());
					productPlusActive.setUserOwns(true);
				} else {
					productPlusActive.setEquipped(false);
					productPlusActive.setUserOwns(false);
				}
			}

			purchasedProducts.add(productPlusActive);

		}

		return purchasedProducts;
	}

	public ProductPlusActive getProductById(PurchasedProduct purchasedProduct) {

		Product product = productRepository.findById(purchasedProduct.getProductId()).orElse(null);

		ProductPlusActive productPlusActive = new ProductPlusActive();

		if (product != null) {
			productPlusActive.setId(product.getId());
			productPlusActive.setUrlImg(product.getPath());
			productPlusActive.setValue(product.getValue());
			productPlusActive.setEquipped(false);
			productPlusActive.setUserOwns(false);

			User user = userRepository.findById(purchasedProduct.getUserId()).orElse(null);

			if (user != null) {
				PurchasedProduct foundPurchasedProduct = purchasedProductRepository
						.getPurchasedProductByUserAndProductId(user.getId(), product.getId());

				if (foundPurchasedProduct != null) {
					productPlusActive.setEquipped(foundPurchasedProduct.isActive());
					productPlusActive.setUserOwns(true);
				}
			}
		}

		return productPlusActive;
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
