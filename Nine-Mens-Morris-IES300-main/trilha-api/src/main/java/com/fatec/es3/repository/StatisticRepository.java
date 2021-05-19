package com.fatec.es3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.es3.model.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

	@Query("SELECT u FROM Statistic u WHERE u.userId = :userId")
	public Statistic getStatisticByUser(@Param("userId") long userId);
}
