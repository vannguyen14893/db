package com.cmc.dashboard.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmc.dashboard.model.ProjectEvaluateLevel;


public interface EvaluateLevelRepository extends JpaRepository<ProjectEvaluateLevel, Integer>{
	
	Optional<ProjectEvaluateLevel> findById(int id);
	
}
