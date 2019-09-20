package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.DUStatistic;
import com.cmc.dashboard.model.Group;

@Repository
public interface DUStatisticRepository extends JpaRepository<DUStatistic, Integer> {

//	@Query("SELECT du FROM DUStatistic du WHERE du.group_id =:groupId and du.month=:month")
	 public DUStatistic findByGroupIdAndMonth(int groupId, String month);

}
