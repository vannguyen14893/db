/**
 * dashboard-phase2-backend- - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.qms.model.QmsTimeEntries;

/**
 * @author: GiangTM
 * @Date: Feb 23, 2018
 */
@Repository
public interface TimeEntriesRepository extends JpaRepository<QmsTimeEntries, Integer> {
  /**
   * Get ActuallEffort By ProjectId
   * 
   * @param projectId
   * @return Float
   * @author: GiangTM
   */
  @Query(value = "SELECT SUM(t.hours) AS actuallEffort FROM redmine_db.time_entries AS t"
      + " WHERE t.project_id = :projectId", nativeQuery = true)
  public Float getActuallEffortByProjectId(@Param("projectId") int projectId);

  /**
   * Get ReworkEffort By ProjectId
   * 
   * @param projectId
   * @param trackerId
   * @return Float
   * @author: GiangTM
   */
  @Query(value = "SELECT SUM(t.hours) AS reworkEffort FROM redmine_db.time_entries AS t"
      + " INNER JOIN redmine_db.issues AS i ON t.issue_id = i.id"
      + " WHERE i.project_id = :projectId AND i.tracker_id = :trackerId", nativeQuery = true)
  public Float getReworkEffortByProjectId(@Param("projectId") int projectId,
      @Param("trackerId") int trackerId);
	
	/**
	 * Get man month of resource
	 * @param projectId
	 * @return List<Object> 
	 * @author: HungNC
	 */
	@Query(value = "SELECT C.sp, sum(C.man_month) as man_month FROM "
			+ "(SELECT A.project_id, A.user_id, DATE_FORMAT(A.spent_on, '%m-%Y') AS sp, "
			+ "SUM(A.hours/8/22) man_month "
			+ "FROM redmine_db.time_entries A "
			+ "WHERE A.project_id = :projectId "
			+ "GROUP BY sp, A.user_id "
			+ ") C GROUP BY C.sp ", nativeQuery = true)
	public List<Object> getManMonthOfResource(@Param("projectId") int projectId);

  /**
   * 
   * Get TimeEntries By ProjectId
   * @param projectId
   * @param userId
   * @return List<Object> 
   * @author: DuyHieu
   */
  @Query(value = "SELECT project_id, user_id, SUM(hours) sum_hours, tmonth, tyear FROM redmine_db.time_entries "
      + "where project_id = :projectId AND user_id = :userId group by tmonth, tyear", nativeQuery = true)
  public List<Object> getTimeEntriesByProjectId(@Param("projectId") int projectId,
      @Param("userId") int userId);
  
  /**
   * get all spent time
   * 
   * @param fromDate
   * @param toDate
   * @return List<Object>
   * @author: NNDuy
   */
  @Query(value = "SELECT    te.user_id AS user_id, te.id AS spent_id,  te.project_id AS project_id, te.spent_on AS spent_on, hours\r\n" + 
      "FROM     \r\n" + 
      "      -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
      "      (SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u1\r\n" + 
      "  LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
      "  WHERE     u1.id IN (:userIds) \r\n" + 
      "        -- Filter\r\n" + 
      "        AND m1.project_id IN (:projectIds)\r\n" + 
      "  GROUP BY  u1.id, m1.project_id\r\n" + 
      "  UNION\r\n" + 
      "  SELECT    u2.id as user_id, te2.project_id AS project_id\r\n" + 
      "  FROM    redmine_db.users u2\r\n" + 
      "  LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
      "  WHERE     u2.id IN (:userIds) \r\n" + 
      "        -- Filter\r\n" + 
      "        AND te2.project_id IN (:projectIds)\r\n" + 
      "  GROUP BY  u2.id, te2.project_id) temp \r\n" + 
      "JOIN    redmine_db.time_entries te ON (temp.user_id = te.user_id AND temp.project_id = te.project_id)\r\n" + 
      "-- filter từ fromDate --> toDate\r\n" + 
      "WHERE     te.spent_on >= :fromDate AND te.spent_on <= :toDate \r\n" + 
      "ORDER BY  te.user_id ASC, te.project_id ASC, te.spent_on ASC", nativeQuery = true)
  public List<Object> getSpentTimes(@Param("fromDate") String fromDate, @Param("toDate") String toDate, 
      @Param("projectIds") List<Integer> projectIds, @Param("userIds") List<Integer> userIds);
  
  /**
   * Get Project Timesheets By ProjectId
   * @param projectId
   * @return List<Object> 
   * @author: GiangTM
   */
  @Query(value = "-- Get users in project\r\n" + 
  		"SELECT T1.userID, T1.userName, IFNULL(T2.yesterdayLogtime, -1) AS yesterdayLogtime,\r\n" + 
  		"IFNULL(T3.thisWeekLogtime, -1) AS thisWeekLogtime FROM\r\n" + 
  		"(SELECT u.id AS userID, u.login AS userName FROM redmine_db.users AS u\r\n" + 
  		"INNER JOIN redmine_db.members AS m ON u.id = m.user_id\r\n" + 
  		"WHERE m.project_id = :projectId) AS T1\r\n" + 
  		"LEFT JOIN\r\n" + 
  		"-- Get yesterday logtime\r\n" + 
  		"(SELECT t.user_id AS userID, SUM(t.hours) AS yesterdayLogtime FROM redmine_db.time_entries AS t\r\n" + 
  		"INNER JOIN redmine_db.users AS u ON t.user_id = u.id\r\n" + 
  		"WHERE t.project_id = :projectId AND t.spent_on = SUBDATE(CURDATE(), 1)\r\n" + 
  		"GROUP BY t.user_id) AS T2 ON T1.userID = T2.userID\r\n" + 
  		"LEFT JOIN\r\n" + 
  		"-- Get this week logtime\r\n" + 
  		"(SELECT t.user_id AS userID, SUM(t.hours) AS thisWeekLogtime FROM redmine_db.time_entries AS t\r\n" + 
  		"INNER JOIN redmine_db.users AS u ON t.user_id = u.id\r\n" + 
  		"WHERE t.project_id = :projectId AND \r\n" + 
  		"(case \r\n" + 
  		"	when weekday(t.spent_on) = 6\r\n" + 
  		"		then week(t.spent_on) - 1\r\n" + 
  		"		else week(t.spent_on) end)\r\n" + 
  		"= (case when weekday(CURDATE()) = 6\r\n" + 
  		"		then week(CURDATE()) - 1\r\n" + 
  		"		else week(CURDATE()) end)\r\n" + 
  		"GROUP BY t.user_id) AS T3 ON T1.userID = T3.userID", nativeQuery = true)
  public List<Object> getProjectTimesheetsByProjectId(@Param("projectId")int projectId);

  @Query(value = "SELECT T1.activity_id, T1.activity_name, IFNULL(T2.team_hour, 0) AS team_hour FROM (\r\n" + 
  		"SELECT id AS activity_id, name AS activity_name\r\n" + 
  		"FROM redmine_db.enumerations\r\n" + 
  		"WHERE type = 'TimeEntryActivity' AND active = 1) AS T1\r\n" + 
  		"LEFT JOIN (\r\n" + 
  		"SELECT activity_id, SUM(hours) AS team_hour\r\n" + 
  		"FROM redmine_db.time_entries\r\n" + 
  		"WHERE project_id = :theProjectId GROUP BY activity_id) AS T2\r\n" + 
  		"ON T1.activity_id = T2.activity_id", nativeQuery = true)
  public List<Object> getTeamHourByActivitiesByProjectId(@Param("theProjectId")int theProjectId);
  
  
  
  /**
   * Get last week log time
   * @param projectId
   * @return
   */
  @Query(value="SELECT t.user_id AS userID, SUM(t.hours) AS hours, "
  		+ " (case when weekday(t.spent_on) = 0 then 'mon' "
  		+ " 	when weekday(t.spent_on) = 1 then 'tue' "
  		+ " 	when weekday(t.spent_on) = 2 then 'wed' "
  		+ " 	when weekday(t.spent_on) = 3 then 'thu' "
  		+ " 	when weekday(t.spent_on) = 4 then 'fri' "
  		+ " 	when weekday(t.spent_on) = 5 then 'sat' "
  		+ " 	when weekday(t.spent_on) = 6 then 'sun' end "
  		+ " ) AS spent_on FROM redmine_db.time_entries AS t "
  		+ " INNER JOIN redmine_db.users AS u ON t.user_id = u.id "
  		+ " WHERE t.project_id = :projectId AND YEARWEEK(t.spent_on) = YEARWEEK(NOW()- INTERVAL 1 WEEK) "
  		+ " GROUP BY t.spent_on, t.user_id ", nativeQuery = true)
  public List<Object> getLastWeekLogTime(@Param("projectId")int projectId);
  
  /**
   * Get this week log time
   * @param projectId
   * @return
   */
  @Query(value = "SELECT t.user_id AS userID, SUM(t.hours) AS hours, "
  		+ " (case when weekday(t.spent_on) = 0 then 'mon' "
  		+ " 	when weekday(t.spent_on) = 1 then 'tue' "
  		+ " 	when weekday(t.spent_on) = 2 then 'wed' "
  		+ " 	when weekday(t.spent_on) = 3 then 'thu' "
  		+ " 	when weekday(t.spent_on) = 4 then 'fri' "
  		+ " 	when weekday(t.spent_on) = 5 then 'sat' "
  		+ " 	when weekday(t.spent_on) = 6 then 'sun' end "
  		+ " ) AS spent_on FROM redmine_db.time_entries AS t "
  		+ " INNER JOIN redmine_db.users AS u ON t.user_id = u.id "
  		+ " WHERE t.project_id = :projectId AND "
  		+ " (case when weekday(t.spent_on) = 6 "
  		+ " 	then week(t.spent_on) - 1 "
  		+ " 	else week(t.spent_on) end) "
  		+ " = (case when weekday(CURDATE()) = 6 "
  		+ " 	then week(CURDATE()) - 1 "
  		+ " 	else week(CURDATE()) end) "
  		+ " GROUP BY t.spent_on, t.user_id ", nativeQuery = true)
  public List<Object> getThistWeekLogTime(@Param("projectId")int projectId);
  
}


