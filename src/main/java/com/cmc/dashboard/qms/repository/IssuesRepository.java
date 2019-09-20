/**
 * dashboard-phase2-backend- - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.qms.model.QmsIssues;

/**
 * @author: GiangTM
 * @Date: Feb 23, 2018
 */
@Repository
public interface IssuesRepository extends JpaRepository<QmsIssues, Integer> {
	/**
	 * Get Bugs By Project Id.
	 * @param projectId
	 * @return List<BugDTO>
	 * @author: GiangTM
	 */
	@Query(value = "SELECT c.value AS bugSeverity, COUNT(1) AS bugTotal" + " FROM redmine_db.issues AS i"
			+ " INNER JOIN redmine_db.custom_values AS c ON i.id = c.customized_id"
			+ " WHERE i.project_id = :projectId AND i.tracker_id = :trackerId"
			+ " GROUP BY c.value", nativeQuery = true)
	public List<Object> getBugsByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId);

	/**
	 * Get Leakages By ProjectId.
	 * @param projectId
	 * @param parentId
	 * @param trackerId
	 * @return List<LeakageDTO>
	 * @author: GiangTM
	 */
	@Query(value = "SELECT c.value AS leakageSeverity, COUNT(1) AS leakageTotal" + " FROM redmine_db.issues AS i"
			+ " INNER JOIN redmine_db.custom_values AS c ON i.id = c.customized_id"
			+ " WHERE i.project_id = :projectId AND i.subject LIKE :prefixLeakage% AND i.tracker_id = :trackerId"
			+ " GROUP BY c.value", nativeQuery = true)
	public List<Object> getLeakagesByProjectId(@Param("projectId") int projectId,
			@Param("prefixLeakage") String prefixLeakage, @Param("trackerId") int trackerId);

	/**
	 * Get StoryPointTotal By ProjectId.
	 * @param projectId
	 * @return Float
	 * @author: GiangTM
	 */
	@Query(value = "SELECT SUM(c.value) AS storyPointTotal FROM redmine_db.custom_values AS c"
			+ " INNER JOIN redmine_db.issues AS i ON c.customized_id = i.id"
			+ " WHERE c.custom_field_id = :customFieldId AND i.project_id = :projectId", nativeQuery = true)
	public Float getStoryPointTotalByProjectId(@Param("customFieldId") int customFieldId,
			@Param("projectId") int projectId);

	/**
	 * Get EstimatedTotal By ProjectId
	 * @param projectId
	 * @return Float
	 * @author: GiangTM
	 */
	@Query(value = "SELECT SUM(i.estimated_hours) FROM redmine_db.issues AS i WHERE i.project_id = :projectId", nativeQuery = true)
	public Float getEstimatedTotalByProjectId(@Param("projectId") int projectId);

	/**
	 * Get CommentTotal By ProjectId
	 * @param projectId
	 * @param prefixComment
	 * @param trackerId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT c.value AS commentSeverity, COUNT(1) AS commentTotal" + " FROM redmine_db.issues AS i"
			+ " INNER JOIN redmine_db.custom_values AS c ON i.id = c.customized_id"
			+ " WHERE i.project_id = :projectId AND i.subject LIKE :prefixReview% AND i.tracker_id = :trackerId"
			+ " GROUP BY c.value", nativeQuery = true)
	public List<Object> getCommentsByProjectId(@Param("projectId") int projectId,
			@Param("prefixReview") String prefixLeakage, @Param("trackerId") int trackerId);
	
	/**
	 * Get list tasks assigned to resource.
	 * 
	 * @return List<Object> 
	 * @author: HungNC
	 */
	@Query(value = "SELECT C.subject, C.start_date, C.due_date, C.estimated_hours, C.created_on, D.name "
			+ "FROM ( "
			+ "SELECT A.subject, A.start_date, A.due_date, A.estimated_hours, A.created_on, A.status_id "
			+ "FROM redmine_db.issues A, redmine_db.users B "
			+ "WHERE  A.assigned_to_id = B.id "
			+ "AND B.id = :resourceId "
			+ "AND A.id NOT IN ( "
			+ "SELECT A.issue_id FROM redmine_db.time_entries A "
			+ "WHERE A.user_id = :resourceId ) "
			+ ") C INNER JOIN redmine_db.issue_statuses D "
			+ "WHERE C.status_id = D.id "
			+ "AND created_on <= CURRENT_TIMESTAMP() "
			+ "ORDER BY created_on DESC LIMIT 20; ", nativeQuery = true)
	public List<Object> getListTasksAssignedToResource(@Param("resourceId") int resourceId);
	
	/**
	 * Get list tasks assigned to resource by time.
	 * 
	 * @return List<Object> 
	 * @author: HungNC
	 */
	@Query(value = "SELECT C.subject, C.start_date, C.due_date, C.estimated_hours, C.created_on, D.name, E.name as project "
			+ "FROM ( "
			+ "SELECT A.project_id, A.subject, A.start_date, A.due_date, A.estimated_hours, A.created_on, A.status_id "
			+ "FROM redmine_db.issues A, redmine_db.users B "
			+ "WHERE  A.assigned_to_id = B.id "
			+ "AND B.id = :resourceId "
			+ "AND A.id NOT IN ( "
			+ "SELECT A.issue_id FROM redmine_db.time_entries A "
			+ "WHERE A.user_id = :resourceId ) "
			+ ") C INNER JOIN redmine_db.issue_statuses D "
			+" JOIN redmine_db.projects E ON C.project_id = E.id "
			+ "WHERE C.status_id = D.id "
			+ "AND start_date >= STR_TO_DATE(:fromDate, '%Y-%m-%d') "
			+ "AND start_date <= STR_TO_DATE(:toDate, '%Y-%m-%d') "
			+ "ORDER BY start_date DESC LIMIT 20; ", nativeQuery = true)
	public List<Object> getListTasksAssignedToResourceByTime(@Param("resourceId") int resourceId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	/**
	 * Get FeatureTotal By ProjectId
	 * @param projectId
	 * @param trackerId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) FROM redmine_db.issues AS i WHERE i.project_id = :projectId AND i.tracker_id = :trackerId",
			nativeQuery = true)
	public int getFeatureTotalByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId);
	
	/**
	 * Get FeatureTotal By ProjectId And StatusId
	 * @param projectId
	 * @param trackerId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) FROM redmine_db.issues AS i WHERE i.project_id = :projectId"
			+ " AND i.tracker_id = :trackerId AND status_id = :statusId",
			nativeQuery = true)
	public int getFeatureTotalByProjectIdAndStatusId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("statusId") int statusId);
	
	/**
	 * Get total spend time by delivery unit
	 *
	 * @param time
	 * @return List<Object> 
	 * @author: HungNC
	 * @created: 2018-03-29
	 */
	@Query(value = "SELECT value AS delivery_unit, SUM(spent_time) total_spent_time FROM ( "
			+ "SELECT CV.customized_id AS project_id, CV.value "
			+ "FROM redmine_db.custom_values CV WHERE CV.custom_field_id = 38 "
			+ ") A INNER JOIN ( "
			+ "SELECT SUM(TE.hours) AS spent_time, TE.project_id, DATE_FORMAT(TE.created_on, '%m-%Y') AS created_on "
			+ "FROM redmine_db.time_entries TE GROUP BY TE.project_id "
			+ ") B ON A.project_id = B.project_id "
			+ "WHERE created_on = :time "
			+ "GROUP BY value; ", nativeQuery = true)
	public List<Object> getTotalSpentTimeByDeliveryUnit(@Param("time") String time);
	
  /**
   * get all Estimate time
   * 
   * @param fromDate
   * @param toDate
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    temp.user_id AS user_id, i.id AS issue_id,  temp.project_id AS project_id, i.start_date AS start_date, i.due_date AS due_date,\r\n" + 
      "      IF(i.estimated_hours IS NULL, 0, i.estimated_hours) AS estimate_time\r\n" + 
      "FROM     \r\n" + 
      "    -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
      "        (SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" + 
      "    FROM    redmine_db.users u1\r\n" + 
      "    LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
      "    WHERE     u1.id IN (:userIds) \r\n" + 
      "          -- Filter\r\n" + 
      "          AND m1.project_id IN (:projectIds)\r\n" + 
      "    GROUP BY  u1.id, m1.project_id\r\n" + 
      "    UNION\r\n" + 
      "    SELECT    u2.id as user_id, te2.project_id AS project_id\r\n" + 
      "    FROM    redmine_db.users u2\r\n" + 
      "    LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
      "    WHERE     u2.id IN (:userIds) \r\n" + 
      "          -- Filter\r\n" + 
      "          AND te2.project_id IN (:projectIds)\r\n" + 
      "    GROUP BY  u2.id, te2.project_id) temp \r\n" + 
      "JOIN      redmine_db.issues i ON (temp.user_id = i.assigned_to_id AND temp.project_id = i.project_id)\r\n" + 
      "-- filter từ fromDate --> toDate\r\n" + 
      "WHERE     !(i.due_date < :fromDate OR i.start_date > :toDate) AND i.start_date IS NOT NULL AND i.due_date IS NOT NULL\r\n" + 
      "ORDER BY  i.assigned_to_id ASC, i.project_id ASC, i.start_date ASC", nativeQuery = true)
  public List<Object> getEstimateTimes(@Param("fromDate") String fromDate, @Param("toDate") String toDate,  
      @Param("projectIds") List<Integer> projectIds, @Param("userIds") List<Integer> userIds);

	/**
	 * Count tasks: status = inprogress || new, due_date = today
	 * 
	 * @param projectId
	 * @param trackerId
	 * @param statusId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) FROM redmine_db.issues AS i WHERE i.project_id = :projectId"
			+ " AND i.tracker_id = :trackerId AND (i.status_id = :inprogressStatusId OR i.status_id = :newStatusId) AND CURDATE() = i.due_date",
			nativeQuery = true)
	public int getAttentionTasksTotalByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("inprogressStatusId") int inprogressStatusId, @Param("newStatusId") int newStatusId);
	
	/**
	 * Sum estimated time of task: status = inprogress || (status = new & due_date =
	 * today)
	 * @param projectId
	 * @param trackerId
	 * @param inprogressStatusId
	 * @param newStatusId
	 * @return float 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT IFNULL(SUM(i.estimated_hours), 0) FROM redmine_db.issues AS i\r\n" + 
			"WHERE i.project_id = :projectId AND i.tracker_id = :trackerId\r\n" + 
			"AND (i.status_id = :inprogressStatusId OR (i.status_id = :newStatusId AND i.due_date = CURDATE()))",
			nativeQuery = true)
	public float getTodayEstimatedTimeTotalByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("inprogressStatusId") int inprogressStatusId, @Param("newStatusId") int newStatusId);

	/**
	 * Count tasks: closed_on = yeterday
	 * 
	 * @param projectId
	 * @param trackerId
	 * @param statusId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1)\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"WHERE i.project_id = :projectId AND i.tracker_id = :trackerId\r\n" + 
			"AND i.status_id = :statusId AND DATE(i.closed_on) = SUBDATE(CURDATE(), 1)",
			nativeQuery = true)
	public int getYesterdayClosedTasksTotal(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("statusId") int statusId);
	
	/**
	 * Sum estimated time of task: status = closed, closed_on = yesterday
	 * 
	 * @param projectId
	 * @param trackerId
	 * @param statusId
	 * @return float 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT IFNULL(SUM(i.estimated_hours), 0)\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"WHERE i.project_id = :projectId AND i.tracker_id = :trackerId\r\n" + 
			"AND i.status_id = :statusId AND DATE(i.closed_on) = SUBDATE(CURDATE(), 1)",
			nativeQuery = true)
	public float getYesterdayClosedTasksTime(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("statusId") int statusId);

	/**
	 * Count bugs: status = open
	 * 
	 * @param projectId
	 * @param trackerId
	 * @param statusId
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) FROM redmine_db.issues AS i\r\n" + 
			"WHERE i.project_id = :projectId\r\n" + 
			"AND i.tracker_id = :trackerId AND (i.status_id = :newStatusId OR i.status_id = :inprogressStatusId)",
			nativeQuery = true)
	public int getOpenBugsTotalByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("newStatusId") int newStatusId, @Param("inprogressStatusId") int inprogressStatusId);

	/**
	 * Count bugs: status = closed, closed_on = yesterday
	 * 
	 * @param projectId
	 * @param bugTrackerId
	 * @param issueStatusIdClosed
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) FROM redmine_db.issues AS i\r\n" + 
			"WHERE i.project_id = :projectId\r\n" + 
			"AND i.tracker_id = :trackerId AND i.status_id = :statusId\r\n" +
			"AND DATE(i.closed_on) = SUBDATE(CURDATE(), 1)",
			nativeQuery = true)
	public int getYesterdayClosedBugsTotalByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("statusId") int statusId);

	@Query(value = "SELECT i.id, i.subject, u.login AS assignee, DATEDIFF(CURDATE(), i.due_date) AS overdue,\r\n" + 
			"IFNULL(i.estimated_hours, -1) AS estimation, s.name AS status\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.users AS u ON i.assigned_to_id = u.id\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS s ON i.status_id = s.id\r\n" + 
			"WHERE i.tracker_id = :trackerId AND (i.status_id=:newStatusId OR i.status_id=:inprogressStatusId)\r\n" + 
			"AND DATEDIFF(CURDATE(), i.due_date) >= 1 AND i.project_id = :projectId", nativeQuery = true)
	public List<Object> getOverdueTasksByProjectId(@Param("projectId") int projectId, @Param("trackerId") int trackerId,
			@Param("newStatusId") int newStatusId, @Param("inprogressStatusId") int inprogressStatusId);

	@Query(value = "SELECT i.id, i.subject, '-' AS assignee,\r\n" + 
			"IFNULL(i.estimated_hours, -1) AS estimation,\r\n" + 
			"IFNULL(SUM(t.hours), -1) AS spentTime, i.done_ratio AS doneRatio,\r\n" + 
			"i.status_id AS statusId, s.name AS status\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS s ON i.status_id = s.id\r\n" + 
			"LEFT JOIN redmine_db.time_entries AS t ON i.id = t.issue_id\r\n" + 
			"WHERE i.tracker_id = :taskTrackerId AND i.assigned_to_id IS NULL\r\n" + 
			"AND i.project_id = :projectId\r\n" + 
			"GROUP BY i.id\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT i.id, i.subject, u.login AS assignee,\r\n" + 
			"IFNULL(i.estimated_hours, -1) AS estimation,\r\n" + 
			"IFNULL(SUM(t.hours), -1) AS spentTime, i.done_ratio AS doneRatio,\r\n" + 
			"i.status_id AS statusId, s.name AS status\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.users AS u ON i.assigned_to_id = u.id\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS s ON i.status_id = s.id\r\n" + 
			"LEFT JOIN redmine_db.time_entries AS t ON i.id = t.issue_id\r\n" + 
			"WHERE i.tracker_id = :taskTrackerId AND i.assigned_to_id IS NOT NULL\r\n" + 
			"AND (i.estimated_hours = 0 OR i.estimated_hours IS NULL)\r\n" + 
			"AND i.project_id = :projectId\r\n" + 
			"GROUP BY i.id\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT i.id, i.subject, u.login AS assignee,\r\n" + 
			"IFNULL(i.estimated_hours, -1) AS estimation,\r\n" + 
			"IFNULL(SUM(t.hours), -1) AS spentTime, i.done_ratio AS doneRatio,\r\n" + 
			"i.status_id AS statusId, s.name AS status\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.users AS u ON i.assigned_to_id = u.id\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS s ON i.status_id = s.id\r\n" + 
			"LEFT JOIN redmine_db.time_entries AS t ON i.id = t.issue_id\r\n" + 
			"WHERE i.tracker_id = :taskTrackerId AND i.assigned_to_id IS NOT NULL\r\n" + 
			"AND !(i.estimated_hours = 0 OR i.estimated_hours IS NULL)\r\n" + 
			"AND i.status_id = :issueStatusIdResolved AND i.done_ratio < 90\r\n" + 
			"AND i.project_id = :projectId\r\n" + 
			"GROUP BY i.id", nativeQuery = true)
	public List<Object> getNoncomplianceTasksByProjectId(@Param("projectId") int projectId, @Param("taskTrackerId") int taskTrackerId,
			@Param("issueStatusIdResolved") int issueStatusIdResolved);

	/**
	 * Get Last Creation By UserId And ProjectId
	 * @param theUserId
	 * @param theProjectId
	 * @return Object 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT T2.name AS creationTracker, TIMESTAMPDIFF(MINUTE, T1.created_on, NOW()) AS created_on\r\n" + 
			"FROM redmine_db.issues AS T1\r\n" + 
			"INNER JOIN redmine_db.trackers AS T2 ON T2.id = T1.tracker_id\r\n" + 
			"WHERE author_id = :theUserId AND project_id = :theProjectId\r\n" + 
			"ORDER BY T1.created_on DESC LIMIT 1", nativeQuery = true)
	public Object getLastCreationByUserIdAndProjectId(@Param("theUserId") int theUserId, @Param("theProjectId") int theProjectId);

	/**
	 * Get Project Tasks Estimated Hours By ProjectId
	 * @param theProjectId
	 * @param taskTrackerId
	 * @return float 
	 * @author: GiangTM
	 */
	@Query(value="SELECT IFNULL(SUM(I1.estimated_hours), 0) AS project_estimated_hours\r\n" + 
			"FROM redmine_db.issues AS I1\r\n" + 
			"WHERE I1.tracker_id = :taskTrackerId AND I1.project_id = :theProjectId", nativeQuery = true)
	public float getProjectTasksEstimatedHoursByProjectId(@Param("theProjectId") int theProjectId, @Param("taskTrackerId") int taskTrackerId);

	/**
	 * Get Remain EstimatedHours By Days
	 * @param theProjectId
	 * @param theProjectStartDate
	 * @param theEndDate
	 * @param projectTasksEstimatedHours
	 * @param taskTrackerId
	 * @param issueStatusIdClosed
	 * @return List<Object> 
	 * @author: GiangTM
	 */
	@Query(value="-- Lay remain_estimated_hours theo ngay\r\n" + 
			"SELECT Y1.selected_date, IFNULL(Y2.remain_estimated_hours, :projectTasksEstimatedHours) AS remain_estimated_hours FROM (\r\n" + 
			"	-- Lay list ngay giua 2 ngay\r\n" + 
			"	SELECT T.selected_date FROM (\r\n" + 
			"	-- Create calender 9999 days from theProjectStartDate\r\n" + 
			"		SELECT DATE_FORMAT(ADDDATE(:theProjectStartDate, t3.i*1000 + t2.i*100 + t1.i*10 + t0.i),\"%Y-%m-%d\") AS selected_date\r\n" + 
			"		FROM (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3\r\n" + 
			"	) T\r\n" + 
			"	WHERE selected_date BETWEEN :theProjectStartDate AND :theEndDate\r\n" + 
			") AS Y1\r\n" + 
			"LEFT JOIN (\r\n" + 
			"-- Lay estimated_time con lai theo moi ngay\r\n" + 
			"SELECT T1.selected_date, :projectTasksEstimatedHours - SUM(T2.estimated_hours) AS remain_estimated_hours\r\n" + 
			"FROM (\r\n" + 
			"	SELECT * FROM (\r\n" + 
			"		SELECT DATE_FORMAT(ADDDATE(:theProjectStartDate, t3.i*1000 + t2.i*100 + t1.i*10 + t0.i),\"%Y-%m-%d\") AS selected_date\r\n" + 
			"		FROM (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3\r\n" + 
			"	) T\r\n" + 
			"	WHERE selected_date BETWEEN :theProjectStartDate AND :theEndDate\r\n" + 
			") AS T1\r\n" + 
			"LEFT JOIN redmine_db.issues AS T2 ON DATE_FORMAT(T2.closed_on, \"%Y-%m-%d\") < T1.selected_date\r\n" + 
			"WHERE T2.tracker_id = :taskTrackerId AND T2.status_id = :issueStatusIdClosed AND T2.project_id = :theProjectId\r\n" + 
			"GROUP BY T1.selected_date ) AS Y2\r\n" + 
			"ON Y1.selected_date = Y2.selected_date", nativeQuery = true)
	public List<Object> getRemainEstimatedHoursByDays(@Param("theProjectId") int theProjectId, @Param("theProjectStartDate") String theProjectStartDate,
			@Param("theEndDate") String theEndDate, @Param("projectTasksEstimatedHours") float projectTasksEstimatedHours,
			@Param("taskTrackerId") int taskTrackerId, @Param("issueStatusIdClosed") int issueStatusIdClosed);
}
