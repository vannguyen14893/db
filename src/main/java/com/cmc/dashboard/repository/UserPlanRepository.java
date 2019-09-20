package com.cmc.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.ResourcePlanDTO;
import com.cmc.dashboard.model.UserPlan;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Integer> {

	/**
	 * get userplan by projectid.
	 * 
	 * @param projectId
	 * @return List<PlanResourceDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value = "FROM UserPlan dp" + " WHERE dp.projectId = :projectId")
	public List<UserPlan> getAllUserEachProject(@Param("projectId") int projectId);

	/**
	 * Get Resource by search's name and delivery Unit
	 * 
	 * @param name
	 * @param duName
	 * @param pageable
	 * @return List<ResourceUtilizationDTO>
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT TB1.id\r\n" + "FROM\r\n" + "  (SELECT DISTINCT u.id,\r\n"
			+ "                   concat(u.lastname,' ',u.firstname) AS full_name,\r\n"
			+ "                   u.login\r\n" + "   FROM dashboard.user_plan AS up\r\n"
			+ "   INNER JOIN dashboard.user_plan_detail upd ON upd.user_plan_id = up.user_plan_id\r\n"
			+ "   INNER JOIN redmine_db.users AS u ON u.id = up.user_id\r\n"
			+ "   WHERE upd.res_delivery_unit LIKE %:duName% ) TB1\r\n" + "WHERE login LIKE %:name%\r\n"
			+ "  OR TB1.full_name LIKE %:name% \n#pageable\n", countQuery = "SELECT count(*)\r\n" + "FROM\r\n"
					+ "  (SELECT DISTINCT u.id,\r\n"
					+ "                   concat(u.lastname,' ',u.firstname) AS full_name,\r\n"
					+ "                   u.login\r\n" + "   FROM dashboard.user_plan AS up\r\n"
					+ "   INNER JOIN dashboard.user_plan_detail upd ON upd.user_plan_id = up.user_plan_id\r\n"
					+ "   INNER JOIN redmine_db.users AS u ON u.id = up.user_id\r\n"
					+ "   WHERE upd.res_delivery_unit LIKE %:duName% ) TB1\r\n" + "WHERE login LIKE %:name%\r\n"
					+ "  OR TB1.full_name LIKE %:name%", nativeQuery = true)
	public Page<Integer> getResourceIdByNameAndDU(@Param("name") String name, @Param("duName") String duName,
			Pageable pageable);

	/**
	 * Get Resource plan by id of user
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException List<ResourceDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourceDTO(USER_PLAN.userPlanId," + "USER_PLAN.userId,"
			+ "USER_PLAN.projectId," + "USER_PLAN.fromDate," + "USER_PLAN.toDate," + "USER_PLAN.manDay, "
			+ "USER_PLAN.effortPerDay," + "USER_PLAN.updatedOn) " + " FROM UserPlan USER_PLAN"
			+ " WHERE USER_PLAN.userId = :userId ORDER BY USER_PLAN.userPlanId ASC")
	public List<ResourceDTO> getResourcePlanByUserId(@Param("userId") int userId);

	/**
	 * get list resource detail.
	 * 
	 * @param userId
	 * @param month
	 * @param year
	 * @return List<ResourceDetailDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourcePlanDTO(up.projectId,ROUND(SUM(upd.manDay),1)) FROM UserPlan up"
			+ " INNER JOIN up.userPlanDetails upd" + " WHERE up.userId = :userId AND upd.planMonth = :planMonth"
			+ " AND upd.deliveryUnit LIKE %:deliveryUnit%" + " GROUP BY up.projectId")
	public List<ResourcePlanDTO> getResourceDetailWithDeleveryUnit(@Param("userId") int userId,
			@Param("planMonth") String planMonth, @Param("deliveryUnit") String deliveryUnit);

	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourcePlanDTO(up.projectId,ROUND(SUM(upd.manDay),1)) FROM UserPlan up"
			+ " INNER JOIN up.userPlanDetails upd" + " WHERE up.userId = :userId AND upd.planMonth = :planMonth"
			+ " GROUP BY up.projectId")
	public List<ResourcePlanDTO> getResourceDetail(@Param("userId") int userId, @Param("planMonth") String planMonth);

	/**
	 * Count number user in userplan table.
	 * 
	 * @param userId
	 * @return Integer
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT count(*)\r\n" + "FROM dashboard.user_plan du\r\n"
			+ "WHERE du.user_id =:userId", nativeQuery = true)
	public Integer countAllUserPlaned(@Param("userId") int userId);

	/**
	 * get list user plan of user
	 * 
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @return List<UserPlan>
	 * @author: dung
	 */
//	@Query(value = "SELECT UP FROM UserPlan UP WHERE" + 
//			"(UP.fromDate between :fromDate AND  :toDate)" +
//			"OR (UP.toDate between :fromDate AND  :toDate)" +
//			"AND UP.userId = :userId")
//	public List<UserPlan> getUserPlans(@Param("userId") int userId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	@Query(value = "select up from UserPlan as up where\r\n" + 
			"((month(up.fromDate)=:month and year(up.fromDate)=:year) or (month(up.toDate)=:month and year(up.toDate)=:year)) and up.userId = :userId")
	public List<UserPlan> getUserPlans(@Param("userId") int userId, @Param("month") int month, @Param("year") int year);

	

	/**
	 * Get total allocation by DU
	 *
	 * @param time
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-03-24
	 */
	@Query(value = "SELECT USP.delivery_unit, SUM(USP.man_day) AS totalAllocation "
			+ "FROM dashboard.user_plan_detail USP " + "WHERE USP.plan_month = :time "
			+ "GROUP BY delivery_unit ;", nativeQuery = true)
	public List<Object> getTotalAllocationByDeliveryUnit(@Param("time") String time);

	/**
	 * Get effort efficiency by DU (EE = allocation / billable * 100)
	 *
	 * @param time
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-03-24
	 */
	@Query(value = "SELECT A.group_id, ((B.totalBillable/A.totalAllocation)*100) AS effort_efficiency FROM ( \r\n" + 
			"SELECT G.group_id, SUM(UP.man_day) AS totalAllocation  FROM user_plan UP\r\n" + 
			"INNER JOIN users u on u.user_id = UP.user_id\r\n" + 
			"INNER JOIN (select g.group_id from groups g \r\n" + 
			"where g.development_unit = 1 or g.internal_du = 1) as G on u.group_id = G.group_id\r\n" + 
			"WHERE Month(UP.from_date) =:month and (Year(UP.to_date) =:year or UP.to_date = null) GROUP BY G.group_id  \r\n" + 
			") as A \r\n" + 
			"INNER JOIN ( \r\n" + 
			"SELECT SUM(billable_value) AS totalBillable, PB.group_id FROM project_billable PB \r\n" + 
			"WHERE Month(PB.start_date) =:month and (Year(PB.end_date) =:year or PB.end_date = null ) GROUP BY group_id \r\n" + 
			") as B\r\n" + 
			" ON A.group_id = B.group_id;", nativeQuery = true)
	public List<Object> getEffortEfficiencyByDeliveryUnit(@Param("month") int month, @Param("year") int year);
	
	/**
	 * Get allocation to other DU
	 * 
	 *
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-04-02
	 */
	@Query(value = "SELECT USP.delivery_unit, SUM(USP.man_day) AS allocationOther "
			+ "FROM dashboard.user_plan_detail USP " + "WHERE USP.delivery_unit != USP.res_delivery_unit "
			+ "AND USP.plan_month = :time " + "GROUP BY USP.delivery_unit ", nativeQuery = true)
	public List<Object> getAllocationToOtherDeliveryUnit(@Param("time") String time);

	/**
	 * @param month
	 * @param year
	 * @return List Object.
	 */
	@Query(value = "SELECT u.user_id, Sum(upd.man_day) AS manday FROM dashboard.user_plan u\r\n"
			+ "INNER JOIN dashboard.user_plan_detail upd ON u.user_plan_id = upd.user_plan_id\r\n"
			+ "WHERE upd.plan_month =:monthYear GROUP BY u.user_id", nativeQuery = true)
	public List<Object> getManDayByMonthYear(@Param("monthYear") String monthYear);

	/**
	 * get plan time by user
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return List<Object>
	 * @author: NNDuy
	 */
	@Query(value = "SELECT up.user_id, up.user_plan_id, up.project_id, DATE(up.from_date) AS from_date , DATE(up.to_date) AS to_date, up.effort_per_day,pr.name,pr.project_role_id,sk.skill_name,sk.skill_id \r\n"
			+ "FROM      user_plan up,skill sk,project_role pr\r\n" + "WHERE     \r\n"
			+ "      !(up.to_date < :fromDate OR up.from_date > :toDate)\r\n"
			+ "          AND up.project_id IN (:projectIds)\r\n" + "          AND up.user_id IN (:userIds)\r\n"
			+ "	AND (FIND_IN_SET(sk.skill_id,up.skill) > 0)" + "	AND  pr.project_role_id = up.role "
			+ "ORDER BY  up.user_id ASC, up.project_id ASC, up.from_date ASC", nativeQuery = true)
	public List<Object> getPlanTimes(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("projectIds") List<Integer> projectIds, @Param("userIds") List<Integer> userIds);

	/**
	 * get man day by projectId
	 * 
	 * @param month
	 * @return Float.
	 */
	@Query(value = "SELECT Sum(u.man_day) AS manday FROM user_plan u"
			+ " WHERE u.project_id =:projectId", nativeQuery = true)
	public Float getManDayByProjectId(@Param("projectId") int projectId);

	@Query(value = "select p.project_name,pt.project_task_id,pt.subject,pt.estimate_time,pt.status from\r\n"
			+ " project_task pt inner join project p on p.project_id=pt.project_id\r\n"
			+ "where pt.assignee_id=:resourceId and "
			+ "(month(pt.start_date)=:month and year(pt.start_date)=:year) or "
			+ "(month(pt.end_date)=:month and year(pt.end_date)=:year) and (p.project_name REGEXP :projectName)"
			+ "\n#pageable\n",
			countQuery = "select p.project_name,pt.project_task_id,pt.subject,pt.estimate_time,pt.status from\r\n"
					+ " project_task pt inner join project p on p.project_id=pt.project_id\r\n"
					+ "where pt.assignee_id=:resourceId and "
					+ "(month(pt.start_date)=:month and year(pt.start_date)=:year) or "
					+ "(month(pt.end_date)=:month and year(pt.end_date)=:year) and (p.project_name REGEXP :projectName)"
			,
			nativeQuery = true)
	Page<Object> getListTimesheet(@Param("resourceId") int resourceId, @Param("month") int month,
			@Param("year") int year,@Param("projectName") String projectName,Pageable pageable);
	@Query(value = " select p.project_name from\r\n"
			+ " project_task pt inner join project p on p.project_id=pt.project_id\r\n"
			+ "where pt.assignee_id=:resourceId and (month(pt.start_date)=:month and year(pt.start_date)=:year) or (month(pt.end_date)=:month and year(pt.end_date)=:year) group by p.project_name", nativeQuery = true)
	List<String> getListNameProjectTimesheet(@Param("resourceId") int resourceId, @Param("month") int month,
			@Param("year") int year);
	@Query(value = "select sum(logWorkTime) from worklog where project_task_id=:projectTaskId", nativeQuery = true)
	String sumSpentTime(@Param("projectTaskId") String projectTaskId);
	
	
	@Query(value="SELECT effort_per_day from user_plan \r\n" + 
			" where DATE(from_date) <= :date  AND DATE(to_date) >= :date AND user_id =:userId \r\n",nativeQuery=true)
	List<Integer> getSumEffortPerDayByDate(@Param("date") String date,@Param("userId") int userId);
	
	//nvtiep2	  
	  @Query(value="SELECT p.project_id, p.project_name,g.group_name from project p left outer join groups g on g.group_id=p.group_id \r\n"
	  		+ "where month(p.start_date)<=:month and year(p.start_date)<=:year and (p.end_date is null or (month(p.end_date)>=:month and year(p.end_date)>=:year)) \r\n"
	  		+ "group by p.project_id, p.project_name,g.group_name"
		  		,nativeQuery=true)
	  public List<Object> getListProject(@Param("month") int month,@Param("year") int year);
	  
	  @Query(value="SELECT g.group_id from user_plan up inner join users u on u.user_id = up.user_id inner join groups g on g.group_id = u.group_id\r\n"
		  		+ "where month(up.from_date)=:month and year(up.from_date)=:year and up.project_id=:projectId\r\n"
		  		+ "group by g.group_id"
			  		,nativeQuery=true)
		  public List<Integer> getListDUInvited(@Param("projectId") int projectId,@Param("month") int month,@Param("year") int year);
	  @Query(value="SELECT sum(up.effort_per_day*up.man_day) from user_plan up inner join users u on u.user_id = up.user_id inner join groups g on g.group_id = u.group_id\r\n"
		  		+ "where month(up.from_date)=:month and year(up.from_date)=:year and up.project_id=:projectId and u.group_id=:groupId"
			  		,nativeQuery=true)
		  public String allocation(@Param("projectId") int projectId,@Param("month") int month,@Param("year") int year,@Param("groupId") int groupId);
	  @Query(value="SELECT count(Distinct up.user_id) from user_plan up inner join users u on u.user_id = up.user_id inner join groups g on g.group_id = u.group_id\r\n"
		  		+ "where month(up.from_date)=:month and year(up.from_date)=:year and up.project_id=:projectId and u.group_id=:groupId"
			  		,nativeQuery=true)
		  public Integer allocate(@Param("projectId") int projectId,@Param("month") int month,@Param("year") int year,@Param("groupId") int groupId);
	  @Query(value="SELECT sum(billable_value) from project_billable \r\n"
		  		+ "where( month(start_date)=:month or month(end_date)=:month) and year(start_date)=:year and project_id=:projectId and group_id=:groupId"
			  		,nativeQuery=true)
		  public String sumBillableByProject(@Param("projectId") int projectId,@Param("month") int month,@Param("year") int year,@Param("groupId") int groupId);
	    
	  @Query(value="SELECT *"
				+ "FROM "
				+ "user_plan \r\n" + 
				"where ( (year(from_date)=:year and month(from_date)=:month) or (year(to_date)=:year and month(to_date)=:month)) and user_id=:userId and project_id=:projectId and role=:role\r\n"
				,nativeQuery=true)
		public List<UserPlan> getListUserPlanByMonth(@Param("userId") int userId,@Param("projectId") int projectId,@Param("month") int month, @Param("year") int year,@Param("role") String role);
		
		@Query(value="SELECT sum(effort_per_day*(date(to_date)-date(from_date)+1)/8) as sumAlo  "
				+ "FROM "
				+ "user_plan \r\n" + 
				"where  ( (year(from_date)=:year and month(from_date)=:month) or (year(to_date)=:year and month(to_date)=:month)) and user_id=:userId and project_id=:projectId and role IS NULL\r\n"
				,nativeQuery=true)
		public List<UserPlan> getListUserPlanByMonth(@Param("userId") int userId,@Param("projectId") int projectId,@Param("month") int month, @Param("year") int year);
}
