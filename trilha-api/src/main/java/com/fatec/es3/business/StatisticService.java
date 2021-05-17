package com.fatec.es3.business;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.model.Statistic;
import com.fatec.es3.model.User;
import com.fatec.es3.repository.StatisticRepository;
import com.fatec.es3.repository.UserRepository;

@Service
@Transactional
public class StatisticService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StatisticRepository statisticRepository;

	public Statistic getStatistic(User user) {

		if (userRepository.findById(user.getId()) != null) {
			Statistic statistic = statisticRepository.getStatisticByUser(user.getId());

			if (statistic != null) {
				return statistic;
			}
		}

		return new Statistic();
	}

}
