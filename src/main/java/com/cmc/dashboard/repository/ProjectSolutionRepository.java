package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.Solution;

@Repository
public interface ProjectSolutionRepository extends JpaRepository<Solution, Long> {

}
