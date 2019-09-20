package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.Solution;
@Repository
public interface SolutionRepository extends JpaRepository<Solution, Integer> {
	
	@Query("FROM Solution i WHERE i.riskId.riskId = :riskId  ORDER BY i.startDate DESC")
	public List<Solution> getSolutionByRisk(@Param("riskId") int riskId);
	

}
