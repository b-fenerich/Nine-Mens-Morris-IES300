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

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CustomizeService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	PurchasedProductRepository purchasedProductRepository;

	public List<ProductPlusActive> listAllProducts(User user) {
		List<PurchasedProduct> purchasedProducts = purchasedProductRepository.getPurchasedProductByUser(user.getId());

		List<ProductPlusActive> productsAvailable = new ArrayList<ProductPlusActive>();

		for (PurchasedProduct purchasedProduct : purchasedProducts) {
			Product selectedProduct = productRepository.findById(purchasedProduct.getProductId()).orElse(null);

			if (selectedProduct != null) {
				ProductPlusActive productPlusActive = new ProductPlusActive();
				productPlusActive.setId(selectedProduct.getId());
				productPlusActive.setUrlImg(selectedProduct.getPath());
				productPlusActive.setValue(selectedProduct.getValue());
				productPlusActive.setEquipped(purchasedProduct.isActive());
				productPlusActive.setUserOwns(true);

				productsAvailable.add(productPlusActive);
			}
		}

		return productsAvailable;
	}

	public ProductPlusActive activeProduct(PurchasedProduct purchasedProduct) {

		ProductPlusActive productPlusActive = new ProductPlusActive();

		PurchasedProduct selectedPurchasedProduct = purchasedProductRepository
				.getPurchasedProductByUserAndProductId(purchasedProduct.getUserId(), purchasedProduct.getProductId());

		// Se encontrou produto adquirido e já não estiver ativo
		if (selectedPurchasedProduct != null && !selectedPurchasedProduct.isActive()) {

			// Seleciona o produto equivalente na tabela de produtos a ser ativo
			Product selectedProduct = productRepository.findById(selectedPurchasedProduct.getProductId()).orElse(null);

			// Monta lista de produtos ativos para o usuario
			List<PurchasedProduct> activePurchasedProducts = purchasedProductRepository
					.getPurchasedProductActiveByUser(selectedPurchasedProduct.getUserId());

			// Para cada produto adiquirido ativo
			for (PurchasedProduct activepurchasedProduct : activePurchasedProducts) {

				// Busca o produto ativo equivalente
				Product product = productRepository.findById(activepurchasedProduct.getProductId()).orElse(null);

				// Se o tipo do produto ativo for igual a do produto a ser ativo, realiza a
				// troca
				if (product.getType().equals(selectedProduct.getType())) {
					selectedPurchasedProduct.setActive(true);
					activepurchasedProduct.setActive(false);

					// Preenche o response
					productPlusActive.setId(selectedProduct.getId());
					productPlusActive.setUrlImg(selectedProduct.getPath());
					productPlusActive.setValue(selectedProduct.getValue());
					productPlusActive.setEquipped(selectedPurchasedProduct.isActive());
					productPlusActive.setUserOwns(true);

					break;
				}
			}
		}

		return productPlusActive;
	}

}
