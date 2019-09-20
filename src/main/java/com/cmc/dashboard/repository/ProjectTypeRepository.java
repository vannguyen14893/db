package com.cmc.dashboard.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmc.dashboard.model.ProjectType;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {
 @Query(value="select p.name from project_type p",nativeQuery=true)
  Set<String> getListNameProjectType();
}
