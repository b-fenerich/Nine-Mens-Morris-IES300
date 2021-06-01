package com.fatec.es3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.LoginService;
import com.fatec.es3.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public User doLogin(@RequestBody User user) {
		log.info("login request: " + user.toString());
		User loggedUser = loginService.validateLogin(user);
		return loggedUser;
	}
}
