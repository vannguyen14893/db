package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.ProjectRole;
@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole,Integer> {
//	@Query(value = "SELECT ")
}
