package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ProjectTask;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Integer> {

	@Query(value = "SELECT PRT.assignee_id AS user_id,PRT.project_task_id AS issue_id,PRT.project_id AS project_id,DATE_FORMAT(PRT.start_date,'%Y-%m-%d') AS start_date,DATE_FORMAT(PRT.end_date,'%Y-%m-%d') as end_date,IF(PRT.estimate_time IS NULL, 0, PRT.estimate_time) AS estimate_time\r\n"
			+ "	FROM project_task PRT \r\n"
			+ " WHERE (PRT.assignee_id IN (:userIds) AND PRT.project_id IN (:projectIds)\r\n"
			+ " AND !(DATE_FORMAT(PRT.start_date,'%Y-%m-%d') > :toDate OR DATE_FORMAT(PRT.end_date,'%Y-%m-%d') < :fromDate) AND PRT.start_date IS NOT NULL AND PRT.end_date IS NOT NULL)\r\n"
			+ " ORDER BY  PRT.assignee_id ASC, PRT.project_id ASC, PRT.start_date ASC\r\n", nativeQuery = true)
	public List<Object> getEstimateTimes(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("projectIds") List<Integer> projectIds, @Param("userIds") List<Integer> userIds);

	/**
	 * Get ActuallEffort By ProjectId
	 * 
	 * @param projectId
	 */
	@Query(value = "SELECT SUM(w.logWorkTime) AS actuallEffort FROM project_task AS p"
			+ " INNER JOIN worklog AS w ON w.project_task_id = p.project_task_id"
			+ " WHERE p.project_id = :projectId", nativeQuery = true)
	public Float getTotalLogWorkedByProjectId(@Param("projectId") int projectId);

	/**
	 * Get total bugs By ProjectId and issueType
	 */
	@Query(value = "SELECT COUNT(*) AS taskTotal" + " FROM project_task AS p"
			+ " WHERE p.project_id = :projectId AND i.issue_type = :issueType", nativeQuery = true)
	public Float getTotalTaskByTypeAndProjectId(@Param("projectId") int projectId,
			@Param("issueType") String issueType);

	/**
	 * Get Bugs Severity By ProjectId And Issue type.AND i.subject LIKE :prefixLeakage% 
	 * 
	 * @return List<BugDTO>
	 */
	@Query(value = "SELECT p.priority AS bugSeverity, COUNT(1) AS bugTotal" + " FROM project_task AS p"
			+ " WHERE p.project_id = :projectId AND p.issue_type = :issueType"
			+ " GROUP BY p.priority", nativeQuery = true)
	public List<Object> getTaskPriorityByProjectId(@Param("projectId") int projectId,
			@Param("issueType") String issueType);
	
	/**
	 * Get EstimatedTotal By ProjectId
	 * @param projectId
	 * @return Float
	 */
	@Query(value = "SELECT SUM(p.estimate_time) FROM project_task AS p WHERE p.project_id = :projectId", nativeQuery = true)
	public Float getEstimatedTotalByProjectId(@Param("projectId") int projectId);
	
	/**
	 * Get Bugs By Project Id.
	 * 
	 * @param projectId
	 * @return List<BugDTO>
	 */
//	@Query(value = "SELECT COUNT(*) AS bugTotal" + " FROM dashboard.project_task AS p"
//			+ " WHERE p.project_id = :projectId AND i.tracker_id = :trackerId", nativeQuery = true)
//	public List<Object> getTotalBugsByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId);

}
