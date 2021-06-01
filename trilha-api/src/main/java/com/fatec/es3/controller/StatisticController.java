package com.fatec.es3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.business.StatisticService;
import com.fatec.es3.model.Statistic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class StatisticController {

	@Autowired
	StatisticService statisticService;

	@GetMapping("/{id}")
	public Statistic getStatistic(@PathVariable long id) {
		log.info("statistic request for userId: " + String.valueOf(id));
		// Retorna estatisticas do usuario
		return statisticService.getStatistic(id);
	}
}
