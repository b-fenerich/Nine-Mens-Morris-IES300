package com.fatec.es3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.StatisticService;
import com.fatec.es3.model.Statistic;
import com.fatec.es3.model.User;

@RestController
@RequestMapping("/estatistica")
public class StatisticController {

	@Autowired
	StatisticService statisticService;

	@GetMapping
	public Statistic getStatistic(@RequestBody User user) {
		// Retorna estatisticas do usuario
		return statisticService.getStatistic(user);
	}
}
