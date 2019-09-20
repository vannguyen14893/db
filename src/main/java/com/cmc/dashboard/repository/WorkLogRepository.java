package com.cmc.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.model.WorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Integer> {
	   @Transactional
      void deleteByProjectTaskId
      (int projectTaskId);
	   @Query(value = "SELECT count(*) FROM worklog w inner join project_task pt on w.project_task_id=pt.project_task_id where pt.project_id=:projectId", nativeQuery = true)
	   Integer countWorkLogProject(@Param("projectId") int projectId);
}
