package com.fatec.es3.business;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.model.User;
import com.fatec.es3.repository.UserRepository;

@Service
@Transactional
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	public User validateLogin(User user) {

		User selectedUser = userRepository.getUserByUsername(user.getUsername());

		if (selectedUser != null && selectedUser.getPassword().contains(user.getPassword())) {
			return selectedUser;
		}

		// Se usuario nao encontrado ou senha invlaida, devolve User vazio.
		return new User();
	}

}
