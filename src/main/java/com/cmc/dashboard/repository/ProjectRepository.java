/**
 *
 */
package com.cmc.dashboard.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author nahung
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	/**
	 * get list project billable.
	 *
	 * @return List<ProjectBillable>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT DPB.project_billable_id, DPB.project_id,DPB.project_name,DPB.pm_name,DPB.billable_value,DPB.start_date,DPB.end_date FROM project_billable DPB", nativeQuery = true)
	public List<ProjectBillable> getAllProjectBillable();

	@Query(value = "select  p.project_id ,p.project_name, p.project_manager, pt.name,g.group_name,p.status, p.start_date, p.end_date, p.project_code,p.type  from"
			+ " project p left outer join groups g on p.group_id=g.group_id left outer join project_type pt on p.project_type_id=pt.project_type_id where \r\n"
			+ " p.project_name LIKE %:projectName%\r\n"
			+ "and p.status REGEXP :status\r\n"
			+ "and (p.project_manager REGEXP :PM or p.project_manager is null)\r\n"
			+  "and (pt.name REGEXP :typeProject or pt.name is null )\r\n"
			+  "and (p.type REGEXP :type)\r\n"
			+  "and (g.group_name REGEXP :DU or g.group_name is null)\r\n"
			+ "and ((p.start_date BETWEEN :startDateFrom and :startDateTo) or p.start_date is null) \r\n"
			+ "and ((p.end_date BETWEEN :endDateFrom and :endDateTo) or p.end_date is null) \r\n"
			+ "\n#pageable\n"
			,countQuery= "select count(*) from"
			+ " project p left outer join groups g on p.group_id=g.group_id left outer join project_type pt on p.project_type_id=pt.project_type_id where \r\n"
			+ " p.project_name LIKE %:projectName%\r\n"
			+ "and p.status REGEXP :status\r\n"
			+ "and (p.project_manager REGEXP :PM or p.project_manager is null)\r\n"
			+ "and (pt.name REGEXP :typeProject or pt.name is null)\r\n"
			+  "and (p.type REGEXP :type)\r\n"
			+ "and (g.group_name REGEXP :DU or g.group_name is null)\r\n"
			+ "and ((p.start_date BETWEEN :startDateFrom and :startDateTo) or p.start_date is null) \r\n"
			+ "and ((p.end_date BETWEEN :endDateFrom and :endDateTo) or p.end_date is null) \r\n"
			, nativeQuery=true)
	Page<Object> getAllProject( @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM , @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject,@Param("type") String type,Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query(value="delete from project_skill where (project_id=:projectId and skill_id=:skillId)", nativeQuery = true)
	void deleteSkill(@Param("projectId") int projectId,@Param("skillId") int skillId);
//	@Query(value = "select  p.project_id ,p.project_name, p.project_manager, pt.name,g.group_name,p.status, p.start_date, p.end_date, p.project_code  from"
//			+ " dashboard.project p inner join dashboard.groups g on p.group_id=g.group_id inner join dashboard.project_type pt on p.project_type_id=pt.project_type_id where \r\n"
//			+ " p.project_name LIKE %:projectName%\r\n"
//			 + "and p.status REGEXP :status\r\n"
//		     + "and p.project_manager REGEXP :PM\r\n"
//		     + "and pt.name REGEXP :typeProject\r\n"
//		      + "and g.group_name REGEXP :DU\r\n"	
//			   + "and p.start_date BETWEEN :startDateFrom and :startDateTo \r\n"
//			      + "and p.end_date BETWEEN :endDateFrom and :endDateTo \r\n"
//			      + "\n#pageable\n"
//			, nativeQuery=true)

	@Query(value ="SELECT GR.group_name,PT.name,count(*) as total FROM \r\n"
			+ "( ( project PR inner join groups GR on PR.group_id = GR.group_id) inner join project_type PT on PR.project_type_id = PT.project_type_id) \r\n"
			+ "WHERE GR.group_name IN (:units) AND PR.project_type_id IS NOT NULL AND PR.status = 1 AND \r\n"
			+ "     (( PR.start_date < :startDate \r\n"
			+ "     AND PR.end_date > :endDate) \r\n"
			+ " 	OR (PR.start_date >= :startDate \r\n"
			+ " 	AND PR.end_date <= :endDate) \r\n"
			+ " 	OR (PR.start_date >= :startDate \r\n"
			+ " 	AND PR.start_date <= :endDate \r\n"
			+ " 	AND PR.end_date >= :endDate) \r\n"
			+ " 	OR (PR.start_date <= :startDate \r\n"
			+ " 	AND PR.end_date <= :endDate \r\n"
			+ " 	AND PR.end_date >= :startDate)) \r\n "
			+ " GROUP BY GR.group_name, PT.name \r\n", nativeQuery = true)
	public List<Object> getProjectByType(@Param("units") Set<String> units , @Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value="SELECT PR.project_id,PR.project_name,PR.project_manager,PT.name,GR.group_name,PR.status,PR.start_date,PR.end_date,PR.project_code\r\n"
			+ "	FROM  ((project PR LEFT OUTER JOIN project_type PT ON PR.project_type_id = PT.project_type_id) LEFT OUTER JOIN groups GR ON PR.group_id = GR.group_id)\r\n"
			+"  WHERE GR.group_id IN (SELECT group_id FROM users  WHERE user_id = :userId) "
			+ " AND PR.project_name LIKE %:projectName%\r\n"
			+ "	AND PR.status REGEXP :status\r\n"
			+ "	AND (PR.project_manager REGEXP :PM OR PR.project_manager IS NULL)\r\n"
			+ "	AND (PT.name REGEXP :typeProject OR PT.name IS NULL)\r\n"
			+ "	AND (GR.group_name REGEXP :DU OR GR.group_name IS NULL)\r\n"
			+ " AND ((PR.start_date BETWEEN :startDateFrom AND :startDateTo) OR PR.start_date IS NULL) \r\n"
			+ " AND ((PR.end_date BETWEEN :endDateFrom AND :endDateTo) OR PR.end_date IS NULL)"
			+ "\n#pageable\n",countQuery="SELECT count(*)\r\n"
			+ "	 FROM  ((project PR LEFT JOIN project_type PT ON PR.project_type_id = PT.project_type_id) LEFT JOIN groups GR ON PR.group_id = GR.group_id)\r\n"
			+"  WHERE GR.group_id IN (SELECT group_id FROM users WHERE user_id = :userId)\r\n"
			+ " AND PR.project_name LIKE %:projectName%\r\n"
			+ "	AND PR.status REGEXP :status\r\n"
			+ "	AND (PR.project_manager REGEXP :PM OR PR.project_manager IS NULL)\r\n"
			+ "	AND (PT.name REGEXP :typeProject  OR PT.name IS NULL)\r\n"
			+ "	AND (GR.group_name REGEXP :DU  or GR.group_name is null)\r\n"
			+ " AND ((PR.start_date BETWEEN :startDateFrom AND :startDateTo) OR PR.start_date IS NULL) \r\n"
			+ " AND ((PR.end_date BETWEEN :endDateFrom AND :endDateTo) OR PR.end_date IS NULL)",nativeQuery=true)
	public Page<Object> getProjectListByUser(@Param("userId") int userId, Pageable pageable, @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM , @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject);
	
	@Query(value = "select  p.project_id ,p.project_name, p.project_manager, pt.name,g.group_name,p.status, p.start_date, p.end_date, p.project_code  from"
			+ " project p left outer join groups g on p.group_id=g.group_id left outer join project_type pt on p.project_type_id=pt.project_type_id where \r\n"
			+ " p.project_name LIKE %:projectName%\r\n"
			+ "and p.status REGEXP :status\r\n"
			+ "and (p.project_manager REGEXP :PM or p.project_manager is null)\r\n"
			+  "and (pt.name REGEXP :typeProject or pt.name is null )\r\n"
			+  "and (g.group_name REGEXP :DU or g.group_name is null)\r\n"
			+ "and ((p.start_date BETWEEN :startDateFrom and :startDateTo) or p.start_date is null) \r\n"
			+ "and ((p.end_date BETWEEN :endDateFrom and :endDateTo) or p.end_date is null) \r\n"
			+ "\n#pageable\n"
			,countQuery= "select count(*) from"
			+ " project p left outer join groups g on p.group_id=g.group_id left outer join project_type pt on p.project_type_id=pt.project_type_id where \r\n"
			+ " p.project_name LIKE %:projectName%\r\n"
			+ "and p.status REGEXP :status\r\n"
			+ "and (p.project_manager REGEXP :PM or p.project_manager is null)\r\n"
			+ "and (pt.name REGEXP :typeProject or pt.name is null)\r\n"
			+ "and (g.group_name REGEXP :DU or g.group_name is null)\r\n"
			+ "and ((p.start_date BETWEEN :startDateFrom and :startDateTo) or p.start_date is null) \r\n"
			+ "and ((p.end_date BETWEEN :endDateFrom and :endDateTo) or p.end_date is null) \r\n"
			, nativeQuery=true)
	public Page<Object> getProjectListByUserAdminOrPmoOrQa(Pageable pageable, @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM , @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject);
	 //PNTHANH
	 @Query(value="SELECT p.start_date FROM project p WhERE p.project_id=:projectId", nativeQuery= true)
	 public String getProjectStartDateById(@Param("projectId") int projectId);
	 
	 @Query(value="SELECT p.end_date FROM project p WhERE p.project_id=:projectId", nativeQuery= true)
	 public String getProjectEndDateById(@Param("projectId") int projectId);
	 
	 @Query(value="SELECT p.group_id FROM project p WhERE p.project_id=:projectId ", nativeQuery= true)
	 public String getProjectDeliveryUnitById(@Param("projectId") int projectId);
	
	 
	 @Query(value="SELECT p.project_manager FROM project p WHERE p.project_id=:projectId", nativeQuery = true)
	 public List<String> getProjectManagerById(@Param("projectId") int projectId);
	 
	 @Query(value = "SELECT p.project_id AS id, p.project_name as project_name, p.start_date as start_date, p.end_date as end_date, p.status as status\r\n" + 
		      "FROM  project p", nativeQuery = true)
		  public List<Object> getAllProjectToFilter();
	 
	 @Query(value = "SELECT project_id AS project_id,project_name AS project_name,start_date AS start_date,end_date AS end_date \r\n"
	 		+ " FROM project \r\n"
	 		+ " WHERE project_id IN (:projectIds)", nativeQuery = true)
	 public List<Object> getTimeOfListProjectActveByUser(@Param("projectIds") List<Integer> projectIds);
	 ///////// nvcong
	 @Query(value = "SELECT  p.project_id,p.project_name, pt.name as p_type_name, g.group_name, p.status,p.project_code,s.skill_name,e.quality_id ,e.process_id, e.delivery_id,e.startDate,e.endDate,e.comment,p.start_date,p.end_date\r\n" + 
				"FROM evaluate AS e\r\n" + 
				"right outer JOIN project AS p\r\n" + 
				"ON p.project_id = e.project_id \r\n" + 
				"left outer join project_type AS pt\r\n" + 
				"ON p.project_type_id = pt.project_type_id \r\n" + 
				"left outer join evaluate_level el \r\n" + 
				"ON e.process_id = el.project_evaluate_level_id\r\n" + 
				"left join project_skill pk ON p.project_id = pk.project_id\r\n" + 
				"left outer join skill s ON s.skill_id = pk.skill_id \r\n" + 
				"left join groups as g ON p.group_id = g.group_id\r\n" + 
				"WHERE p.project_id=:projectId ", nativeQuery = true)
	  public List<Object> getProjectBasicInfoByProjectId(@Param("projectId") int projectId);
	 
	  @Query(value = "SELECT p.line_code FROM project p WhERE p.project_id=:projectId ", nativeQuery = true)
	  public Integer getLineOfCodeByProjectId(@Param("projectId") int projectId);
	
	  /**
		 * Add to_date
		 * 
		 * @return int 
		 * @author: ntquy
		 * @created: 2018-04-16
		 */
	  @Transactional
	  @Modifying(clearAutomatically=true)
	  @Query(value = "UPDATE project SET project.line_code = :lineCode WHERE project_id=:projectId = :projectId", nativeQuery = true)
	  public int updateLineOfCode(@Param("projectId") int projectId, @Param("lineCode") int lineCode);
	  
      @Query(value="select a.done, b.total from\r\n" + 
              "(select count(*) as done from project_task pt where pt.issue_type in ('Task','SubTask','Story') \r\n" + 
              "and pt.status in ('Done','Closed') and pt.project_id = :projectId) a\r\n" + 
              "join\r\n" + 
              "(select count(*) as total from project_task pt where pt.issue_type in ('Task','SubTask','Story') \r\n" + 
              "and pt.project_id = :projectId) b\r\n" + 
              ";", nativeQuery = true)
      public Object getProjectWorkingProgress(@Param("projectId") int projectId);
      @Query(value="select a.logged, b.effort from\r\n" + 
              "(select ifnull(sum(w.logworktime),0)/3600 as logged from worklog w inner join project_task pt on w.project_task_id = pt.project_task_id \r\n" + 
              "where pt.project_id = :projectId) a\r\n" + 
              "join\r\n" + 
              "(select ifnull(sum(up.effort_per_day),0) as effort from user_plan up inner join project_user pu on up.user_id = pu.user_id\r\n" + 
              "where pu.project_id = :projectId) b;", nativeQuery = true)
      public Object getProjectTimeLog(@Param("projectId") int projectId);
      
      @Query(value = "SELECT dw.user_id, u.user_name, sum(dw.logWorkTime), dw.create_at FROM worklog dw \r\n" + 
      		"inner join project_task pt on pt.project_task_id = dw.project_task_id\r\n" + 
      		"inner join users u on u.user_id = dw.user_id\r\n" + 
      		"where pt.project_id = :projectId and week(dw.create_at) = week(now()) group by dw.user_id, dw.create_at;", nativeQuery = true)
      public List<Object> getThisWeekLogTime(@Param("projectId")int projectId);
      
      @Query(value = "SELECT dw.user_id, u.user_name, sum(dw.logWorkTime), dw.create_at FROM worklog dw \r\n" + 
      		"inner join project_task pt on pt.project_task_id = dw.project_task_id\r\n" + 
      		"inner join users u on u.user_id = dw.user_id\r\n" + 
      		"where pt.project_id = :projectId and week(dw.create_at) = week(now()) - 1 group by dw.user_id, dw.create_at;", nativeQuery = true)
      public List<Object> getLastWeekLogTime(@Param("projectId")int projectId);
      
      
      @Query(value = "select ifnull(u.user_id,0), ifnull(u.user_name,''), ifnull(u.img,''), ifnull(pr.name,'') role, ifnull(u.full_name,'') from project_user pu\r\n" + 
      		"left join users u on pu.user_id = u.user_id\r\n" + 
      		"left join project_role pr on pr.project_role_id = pu.project_role_id\r\n" + 
      		"where pu.project_id = :projectId", nativeQuery = true)
      public List<Object> getMembersWithRoleByProjectId(@Param("projectId")int projectId);
      
      
      @Query(value = "select count(*) from project_task where project_id = :projectId and issue_type in ('Bug','Leakage') and status != 'Done'; ", nativeQuery = true)
      public int getProjectOpenBugs(@Param("projectId")int projectId);
      
      @Query(value = "SELECT COUNT(*) from risk WHERE project_id = :projectId and risk_status_id != 5; ", nativeQuery = true)
      public int getProjectOpenRisks(@Param("projectId")int projectId);
      
      @Query(value = "select coalesce(sum(estimate_time),0) from project_task where project_id = :projectId \r\n" + 
      		"and date(start_date) <= date(now()) and date(end_date) >= date(now()); ", nativeQuery = true)
      public float getProjectEstimatedTime(@Param("projectId")int projectId);
      
      public Project findById(int id);
      
      @Query(value="select * from project where project_id=:projectId",nativeQuery=true)
      public Project getProjectByProjectId(@Param("projectId") int projectId);
      
      @Query(value = "SELECT p.project_id AS id, p.project_name as project_name\r\n" + 
		      "FROM  project p WHERE p.project_id IN (SELECT PRU.user_id FROM project_user PRU WHERE PRU.project_id IN \r\n"
		      +"( SELECT project_id FROM project_user WHERE project_role_id =" + Constants.Role.PM + " AND user_id =:userId))", nativeQuery = true)
	 public List<Object> getAllProjectOfPmToFilter(@Param("userId") int userId);
      
      @Query(value = "SELECT p.project_id AS id, p.project_name as project_name\r\n"
  	 		+ " FROM  project p WHERE p.project_id IN\r\n"
  	 		+ " (SELECT project_id FROM project_user WHERE user_id IN\r\n"
  	 		+ " (SELECT user_id FROM users WHERE group_id =(SELECT group_id FROM users where user_id =:userId)))", nativeQuery = true)
  	 public List<Object> getAllProjectOfDulToFilter(@Param("userId") int userId);

  	@Query(value = "select  p.project_id ,p.project_name, p.project_manager, pt.name,g.group_name,p.status, p.start_date, p.end_date, p.project_code,p.type  from"
			+ " project p left outer join groups g on p.group_id=g.group_id left outer join project_type pt on p.project_type_id=pt.project_type_id where \r\n"
			+ " p.group_id REGEXP :groupId\r\n"
		
			, nativeQuery=true)
	List<Object> getProjectByDu( @Param("groupId") String groupId);
  	@Query(value = "select p.project_id ,p.project_name, p.project_code from project p  where p.type != 0", nativeQuery = true)
  	List<Object> getListProject();
}
