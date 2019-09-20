package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.ProjectTypeLog;

@Repository
public interface ProjectTypeLogRepository  extends JpaRepository < ProjectTypeLog ,Integer>  {
  public List<ProjectTypeLog> findByProjectId(int projectId);
  public List<ProjectTypeLog> findByProjectTypeLogId(int projectTypeLogId);
  
  @Query(value="SELECT project_id, project_type_id, start_date, end_date FROM project_type_log \r\n" + 
			"WHERE project_id = :projectID AND start_date <= :endDate AND end_date >= :startDate", nativeQuery = true)
	public List<Object> getByProject(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("projectID") int projectID);
	
}
