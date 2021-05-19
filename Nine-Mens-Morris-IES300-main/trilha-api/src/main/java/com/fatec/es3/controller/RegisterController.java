package com.fatec.es3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.RegisterService;
import com.fatec.es3.model.User;

@RestController
@RequestMapping("/cadastro")
public class RegisterController {

	@Autowired
	RegisterService registerService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User doPost(@RequestBody User user) {

		return registerService.register(user);

	}

}
