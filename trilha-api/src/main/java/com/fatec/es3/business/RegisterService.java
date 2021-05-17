package com.fatec.es3.business;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.model.PurchasedProduct;
import com.fatec.es3.model.Statistic;
import com.fatec.es3.model.User;
import com.fatec.es3.repository.PurchasedProductRepository;
import com.fatec.es3.repository.StatisticRepository;
import com.fatec.es3.repository.UserRepository;

@Service
@Transactional
public class RegisterService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PurchasedProductRepository purchasedProductRepository;

	@Autowired
	StatisticRepository statisticRepository;

	public User register(User user) {

		if (isValid(user)) {
			user.setEnabled(true);
			user.setCreated(new Date());
			User createdUser = userRepository.save(user);

			// Cadastra produtos default para o usuario na tabela PurchasedProduct
			for (long productId = 1; productId < 4; productId++) {
				// Produtos default:
				// Tabuleiro -> productId = 1
				// Peca1 -> productId = 2
				// Peca2 -> productId = 3
				registerDefaultProducts(createdUser, productId);
			}

			// Cadastra registro de estatisticas do usuário
			Statistic statistic = new Statistic();
			statistic.setUserId(createdUser.getId());
			statistic.setGameTime(0);
			statistic.setMatches(0);
			statistic.setWins(0);
			statistic.setLosses(0);
			statisticRepository.save(statistic);

			return createdUser;
		}

		// Retorna User vazio caso e-mail inválido
		return new User();
	}

	private boolean isValid(User user) {

		if (user.getEmail().contains("@")) {
			return true;
		}

		return false;
	}

	private void registerDefaultProducts(User user, long productId) {
		PurchasedProduct purchasedProduct = new PurchasedProduct();
		purchasedProduct.setUserId(user.getId());
		purchasedProduct.setProductId(productId);
		purchasedProduct.setActive(true);

		purchasedProductRepository.save(purchasedProduct);
	}

}
