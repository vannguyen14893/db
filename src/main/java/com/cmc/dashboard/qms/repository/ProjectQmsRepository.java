package com.cmc.dashboard.qms.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsProject;
import com.cmc.dashboard.util.CustomValueUtil;


public interface ProjectQmsRepository extends JpaRepository<QmsProject, Integer> {

	/**
	 * Get project's manager name
	 * 
	 * @param id:
	 *            id of project
	 * @return List<String>
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT concat(u.lastname,\" \",u.firstname) AS projectManager FROM projects p\r\n"
			+ "INNER JOIN members m ON m.project_id = p.id INNER JOIN users u ON m.user_id = u.id\r\n"
			+ "INNER JOIN member_roles mr ON mr.member_id = m.id WHERE mr.role_id = " + CustomValueUtil.ROLE_PM + "\r\n"
			+ "  AND p.id = :id", nativeQuery = true)
	public List<String> getProjectManagerById(@Param("id") int id);

	/**
	 * Get project's start date
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.START_DATE_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectStartDateById(@Param("id") int id);

	/**
	 * Get project's end date
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.END_DATE_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectEndDateById(@Param("id") int id);

	/**
	 * Get project's delivery Unit
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.DELIVERY_UNIT_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectDeliveryUnitById(@Param("id") int id);

	/**
	 * Count project by type for each delivery unit
	 * Note: Count project for whole company
	 * @param startDate
	 * @param Date
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT TB1.delivery_unit, TB1.p_type ,count(*) as total FROM "
			+ "(SELECT P.id, P.name,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
			+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.delivery_unit IN (:units) AND TB1.p_type IS NOT NULL) AND\r"
			+ " 	((TB1.start_date < :startDate\r\n" 
			+ "   AND TB1.end_date>:endDate)\r\n"
			+ "   OR (TB1.start_date >= :startDate\r\n" 
			+ "   AND TB1.end_date<=:endDate)\r\n"
			+ "   OR (TB1.start_date >= :startDate\r\n" 
			+ "   AND TB1.start_date <= :endDate\r\n"
			+ "   AND TB1.end_date>=:endDate)\r\n" 
			+ "   OR (TB1.start_date <= :startDate\r\n"
			+ "   AND TB1.end_date<=:endDate\r\n"
			+ "   AND TB1.end_date>=:startDate))\r\n"
			+ "	GROUP BY TB1.delivery_unit, TB1.p_type", nativeQuery = true)
	public List<Object> getProjectByType(@Param("units") Set<String> units ,
													@Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * List of project by type for delivery unit
	 * 
	 * @param du
	 * @param projectType
	 * @param startDate
	 * @param Date
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT TB1.id, TB1.name, TB1.manager , TB1.project_size, TB1.status, TB1.start_date, TB1.end_date  FROM\r\n" + 
			"	(SELECT P.id, P.name, P.status,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.START_DATE_ID + " AND CV.customized_id = P.id) AS start_date,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.END_DATE_ID + " AND CV.customized_id = P.id) AS end_date,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.MAN_DAY_ID + " AND CV.customized_id = P.id) AS project_size,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.DELIVERY_UNIT_ID + " AND CV.customized_id = P.id) AS delivery_unit,\r\n" + 
			"	(SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.PROJECT_TYPE_ID + " AND CV.customized_id = P.id) AS p_type,\r\n" + 
			"	(SELECT group_concat(concat(U.lastname, ' ', U.firstname)) as manager FROM redmine_db.users U\r\n" + 
			"		INNER JOIN redmine_db.members M ON M.user_id = U.id\r\n" + 
			"		INNER JOIN redmine_db.member_roles MR ON MR.member_id = M.id \r\n" + 
			"		INNER JOIN redmine_db.roles R ON R.id = MR.role_id \r\n" + 
			"		WHERE R.name LIKE '"+ CustomValueUtil.PROJECT_MANAGER +"' AND M.project_id = P.id) as manager\r\n" + 
			"	FROM redmine_db.projects P \r\n" + 
			"	WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n" + 
			"	) TB1 \r\n" + 
			"    WHERE (TB1.delivery_unit LIKE :unit AND TB1.p_type LIKE :projectType)\r\n" + 
			"		AND ((TB1.start_date < :startDate\r\n" + 
			"		AND TB1.end_date> :endDate)\r\n" + 
			"		OR (TB1.start_date >= :startDate\r\n" + 
			"		AND TB1.end_date<= :endDate)\r\n" + 
			"		OR (TB1.start_date >= :startDate \r\n" + 
			"		AND TB1.start_date <= :endDate\r\n" + 
			"		AND TB1.end_date>= :endDate)\r\n" + 
			"		OR (TB1.start_date <= :startDate\r\n" + 
			"		AND TB1.end_date<= :endDate\r\n" + 
			"		AND TB1.end_date>= :startDate))", nativeQuery = true)
	public List<Object> getListProjectByType(@Param("unit") String unit, @Param("projectType") String projectType,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "SELECT p.id, p.name, p.status FROM projects p WHERE p.id =:projectId", nativeQuery = true)
	public QmsProject findOne(@Param("projectId") int projectId);
	
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=38 GROUP BY CV.value", nativeQuery = true)
	public List<String> getListDeliveryUnit();
	

	/**
	 * return duname of project .
	 * @param projectId
	 * @return String 
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="
			+ CustomValueUtil.DELIVERY_UNIT_ID + " AND CV.customized_id = :projectId", nativeQuery = true)
	public String getDuNameByProjectId(@Param("projectId") int projectId);

	/**
	 * Get Project Basic Info By ProjectId
	 * This method returns general project info: name, status, platform, start-date, end-date, type, DU, code, size.
	 * @param projectId
	 * @return Object
	 * @author: GiangTM
	 */
	@Query(value = "SELECT p.name, p.status, GROUP_CONCAT(c.value SEPARATOR ';') AS platform, c1.value AS startDate," + 
			" c2.value AS endDate, c3.value AS projectType, c4.value AS deliveryUnit, c5.value AS projectCode, c6.value AS projectSize" + 
			" FROM redmine_db.projects AS p" + 
			" JOIN redmine_db.custom_values AS c" + 
			" ON p.id = c.customized_id AND c.custom_field_id = :platformId" + 
			" JOIN redmine_db.custom_values AS c1" + 
			" ON p.id = c1.customized_id AND c1.custom_field_id = :startDateId" + 
			" JOIN redmine_db.custom_values AS c2" + 
			" ON p.id = c2.customized_id AND c2.custom_field_id = :endDateId" + 
			" JOIN redmine_db.custom_values AS c3" + 
			" ON p.id = c3.customized_id AND c3.custom_field_id = :projectTypeId" + 
			" JOIN redmine_db.custom_values AS c4" + 
			" ON p.id = c4.customized_id AND c4.custom_field_id = :deliveryUnitId" + 
			" JOIN redmine_db.custom_values AS c5" + 
			" ON p.id = c5.customized_id AND c5.custom_field_id = :projectCodeId" + 
			" JOIN redmine_db.custom_values AS c6" + 
			" ON p.id = c6.customized_id AND c6.custom_field_id = :projectManDayId" + 
			" WHERE p.id = :projectId", nativeQuery = true)
	public Object getProjectBasicInfoByProjectId(@Param("projectId") int projectId, @Param("platformId") int platformId, @Param("startDateId") int startDateId,
			@Param("endDateId") int endDateId, @Param("projectTypeId") int projectTypeId,
			@Param("deliveryUnitId") int deliveryUnitId, @Param("projectCodeId") int projectCodeId, @Param("projectManDayId") int projectManDayId);
	
	/**
	 * get list project from list id
	 * @param lstId
	 * @return List<Object> 
	 * @author: dung
	 */
	@Query(value="SELECT project.id, project.name FROM redmine_db.projects AS project WHERE project.id IN :lstId", nativeQuery = true)
	public List<Object> getProjectNames(@Param("lstId") List<Integer> lstId);
	

	/**
	 * Get all project by delivery unit
	 * 
	 * @param duName
	 * @param startDate
	 * @param endDate
	 * @param pageable
	 * @return Page<Object> 
	 * @author: LXLinh
	 */
	@Query(value = "SELECT TB.id,TB.name,TB.status,TB.startDate,TB.endDate,TB.projectType,TB.deliveryUnit,TB.projectCode FROM "
			+ "(SELECT P.id ,P.name ,P.status,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS startDate,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS endDate,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS deliveryUnit,\r\n"
	        + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS projectCode,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS projectType\r\n"
			+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB WHERE "
			+ " (TB.deliveryUnit =:duName AND TB.projectType IS NOT NULL) AND\r"
			+ " 			((TB.startDate < :startDate\r\n" 
			+ "               AND TB.endDate>:endDate)\r\n"
			+ "          OR (TB.startDate >= :startDate\r\n" 
			+ "              AND TB.endDate<=:endDate)\r\n"
			+ "          OR (TB.startDate >= :startDate\r\n" 
			+ "              AND TB.startDate <= :endDate\r\n"
			+ "              AND TB.endDate>=:endDate)\r\n" 
			+ "          OR (TB.startDate <= :startDate\r\n"
			+ "              AND TB.endDate<=:endDate\r\n" 
			+ "              AND TB.endDate>=:startDate))\r\n"
			+ "order by TB.id \n#pageable\n ",countQuery = "SELECT count(*)  FROM "
					+ "(SELECT P.id ,P.name ,P.status,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS startDate,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS endDate,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS deliveryUnit,\r\n"
			        + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS projectCode,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS projectType\r\n"
					+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
					+ ") TB WHERE "
					+ " (TB.deliveryUnit =:duName AND TB.projectType IS NOT NULL) AND\r"
					+ " 			((TB.startDate < :startDate\r\n" 
					+ "               AND TB.endDate>:endDate)\r\n"
					+ "          OR (TB.startDate >= :startDate\r\n" 
					+ "              AND TB.endDate<=:endDate)\r\n"
					+ "          OR (TB.startDate >= :startDate\r\n" 
					+ "              AND TB.startDate <= :endDate\r\n"
					+ "              AND TB.endDate>=:endDate)\r\n" 
					+ "          OR (TB.startDate <= :startDate\r\n"
					+ "              AND TB.endDate<=:endDate\r\n" 
					+ "              AND TB.endDate>=:startDate))\r\n",nativeQuery=true)
	public Page<Object> getProjectsByDeliveryUnit(@Param("duName") String duName,@Param("startDate") String startDate, @Param("endDate") String endDate,Pageable pageable);


	  /**
	   * Description method
	   * 
	   * @param userId
	   * @param startDate
	   * @param endDate
	   * @return List<QmsProject>
	   * @author: ntquy
	   */
		@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
	      + "    (SELECT P.id, P.name, P.status,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
	      + "FROM redmine_db.projects P \r\n" + "INNER JOIN members M ON M.project_id = P.id\r\n"
	      + "INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n" + "INNER JOIN redmine_db.member_roles MR ON MR.member_id = M.id\r\n"
	      + "WHERE U.id = :userId  "
	      + " AND P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
	      + ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n", 
	       nativeQuery = true)		
	  public List<QmsProject> getProjectListByUserWithoutPMPage(@Param("userId") int userId);
	
		 /**
		   * Description method
		   * 
		   * @param startDate
		   * @param endDate
		   * @return List<QmsProject>
		   * @author: ntquy 
		   */
			@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
		      + "(SELECT P.id, P.name, P.status,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
		      + "FROM redmine_db.projects P \r\n"
		      + " WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n" 
		      + ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"		    
		    , 
		    nativeQuery = true)
		  public List<QmsProject> getAllProjectWithoutPMPage();
			
			  /**
			   * Description method
			   * 
			   * @param duName
			   * @param startDate
			   * @param endDate
			   * @return List<QmsProject>
			   * @author: ntquy
			   */
			  @Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			      + "(SELECT P.id, P.name, P.status,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
			      + "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			      + ") TB1 WHERE "
			      + " (TB1.delivery_unit IN (:duName) AND TB1.p_type IS NOT NULL)\r"
			      , nativeQuery = true)
			  public List<QmsProject> getProjectByDeliveryUnitWithoutPMPage(@Param("duName") List<String> duName);
			  	/**
				 * 
				 * @param month
				 * @return
				 */
				@Query(value = "select p.id, p.name,TB3.PROJECT_TYPE, TB1.CODE, TB2.DU as DU_Project, p.status, TB2.endDate as endDate, TB1.startDate as startDate from \r\n"
						+ "redmine_db.projects p\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id , tb2.CODE, tb1.startDate from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as CODE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 41) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as startDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 35) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id,  tb2.CODE, tb1.startDate from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as CODE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 41) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as startDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 35) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ ") TB1\r\n" + "on p.id = TB1.customized_id\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.endDate from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as endDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 36) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.endDate from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as endDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 36) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ ") TB2\r\n" + "on p.id = TB2.customized_id\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id , tb2.DU, tb1.PROJECT_TYPE from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as PROJECT_TYPE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 37) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.PROJECT_TYPE from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as PROJECT_TYPE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 37) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "    ) TB3\r\n" + "on p.id = TB3.customized_id\r\n"
						+ "where (year(startDate) <= year(:month) and month(startDate) <= month(:month))\r\n"
						+ "AND   (year(endDate) >= year(:month) and month(endDate) >= month(:month)) and TB2.DU like %:duPic%", nativeQuery = true)
				public List<Object> getProjectByMonth(@Param("month") Date month, @Param("duPic") String duPic);
				
				/**
				 * 
				 * @param month
				 * @return
				 */
				@Query(value = "select p.id, p.name,TB3.PROJECT_TYPE, TB1.CODE, TB2.DU as DU_Project, p.status, TB2.endDate as endDate, TB1.startDate as startDate from \r\n"
						+ "redmine_db.projects p\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id , tb2.CODE, tb1.startDate from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as CODE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 41) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as startDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 35) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id,  tb2.CODE, tb1.startDate from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as CODE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 41) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as startDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 35) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ ") TB1\r\n" + "on p.id = TB1.customized_id\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.endDate from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as endDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 36) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.endDate from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as endDate from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 36) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ ") TB2\r\n" + "on p.id = TB2.customized_id\r\n" + "left join \r\n"
						+ "(select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id , tb2.DU, tb1.PROJECT_TYPE from\r\n"
						+ "	(select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	right join\r\n"
						+ "    (select cv1.customized_id as customized_id, cv1.custom_field_id as custom_field_id, cv1.value as PROJECT_TYPE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 37) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "union  \r\n"
						+ "select If(tb1.customized_id is null, tb2.customized_id, tb1.customized_id) as customized_id, tb2.DU, tb1.PROJECT_TYPE from\r\n"
						+ "	(select cv1.customized_id, cv1.custom_field_id, cv1.value as DU from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 38) tb2\r\n" + "	left join\r\n"
						+ "    (select cv1.customized_id, cv1.custom_field_id, cv1.value as PROJECT_TYPE from redmine_db.custom_values cv1\r\n"
						+ "	where cv1.custom_field_id = 37) tb1\r\n" + "	on tb1.customized_id = tb2.customized_id\r\n"
						+ "    ) TB3\r\n" + "on p.id = TB3.customized_id\r\n"
						+ "where (year(startDate) <= year(:month) and month(startDate) <= month(:month))\r\n"
						+ "AND   (year(endDate) >= year(:month) and month(endDate) >= month(:month)) and "
						+ "TB2.DU like %:duPic% and p.id = :projectId", nativeQuery = true)
				public List<Object> filterProjectByMonth(@Param("month") Date month, @Param("duPic") String duPic,@Param("projectId") int projectId);

				/**
				 * Get Project: projectId, status, startDate, endDate, projectSize, loggedTimeTotal
				 * @param projectId
				 * @param startDateId
				 * @param endDateId
				 * @param projectSizeId
				 * @return Object 
				 * @author: GiangTM
				 */
				@Query(value = "SELECT p.id AS projectId, p.status, c.value AS startDate, c1.value AS endDate,\r\n" + 
						"IFNULL(c2.value, 0) AS projectSize, IFNULL(SUM(t.hours), 0) AS loggedTimeTotal\r\n" + 
						"FROM redmine_db.projects AS p\r\n" + 
						"INNER JOIN redmine_db.custom_values AS c ON p.id = c.customized_id\r\n" + 
						"AND c.custom_field_id = :startDateId\r\n" + 
						"INNER JOIN redmine_db.custom_values AS c1 ON p.id = c1.customized_id\r\n" + 
						"AND c1.custom_field_id = :endDateId\r\n" + 
						"INNER JOIN redmine_db.custom_values AS c2 ON p.id = c2.customized_id\r\n" + 
						"AND c2.custom_field_id = :projectSizeId\r\n" + 
						"LEFT JOIN redmine_db.time_entries AS t ON p.id = t.project_id\r\n" + 
						"WHERE p.id = :projectId", nativeQuery = true)
				public Object getProjectForProjectProgress(@Param("projectId") int projectId, @Param("startDateId") int startDateId,
						@Param("endDateId") int endDateId, @Param("projectSizeId") int projectSizeId);

			  /**
			   * get all project to filter
			   * 
			   * @return List<Object>
			   * @author: NNDuy
			   */
			  @Query(value = "SELECT p.id AS id, p.name as project_name\r\n" + 
			      "FROM  redmine_db.projects p", nativeQuery = true)
			  public List<Object> getAllProjectToFilter();
			  
	       /**
         * get all project by User
         * 
         * @return List<Object>
         * @author: NNDuy
         */
        @Query(value = "SELECT    temp.project_id, p.name as project_name \r\n" + 
            "FROM\r\n" + 
            "  -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
            "    (SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" + 
            "  FROM    redmine_db.users u1\r\n" + 
            "  LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
            "  WHERE     u1.id = :userId\r\n" + 
            "  GROUP BY  u1.id, m1.project_id\r\n" + 
            "  UNION\r\n" + 
            "  SELECT    u2.id as user_id, te2.project_id AS project_id\r\n" + 
            "  FROM    redmine_db.users u2\r\n" + 
            "  LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
            "  WHERE     u2.id = :userId\r\n" + 
            "  GROUP BY  u2.id, te2.project_id) temp\r\n" + 
            "LEFT JOIN   redmine_db.projects p ON p.id = temp.project_id   \r\n" + 
            "ORDER BY  temp.user_id ASC, temp.project_id ASC", nativeQuery = true)
        public List<Object> getAllProjectByUser(@Param("userId") int userId);
        
        /**
        * get all project by User
        * https://codingexplained.com/coding/java/spring-framework/return-boolean-value-from-spring-data-jpa-query
        * 
        * @return boolean
        * @author: NNDuy
        */
       @Query(value = "SELECT IF(COUNT(1) > 0, 'TRUE', 'FALSE') AS 'EXISTS'\r\n" + 
           "FROM\r\n" + 
           "  -- select all project and user đã từng logtime hoặc tham gia vào project\r\n" + 
           "  (SELECT   u1.id as user_id, m1.project_id AS project_id\r\n" + 
           "  FROM    redmine_db.users u1\r\n" + 
           "  LEFT JOIN   redmine_db.members m1 ON u1.id = m1.user_id\r\n" + 
           "  WHERE     u1.id  = :userId AND m1.project_id = :projectId\r\n" + 
           "  GROUP BY  u1.id, m1.project_id\r\n" + 
           "  UNION\r\n" + 
           "  SELECT    u2.id as user_id, te2.project_id AS project_id\r\n" + 
           "  FROM    redmine_db.users u2\r\n" + 
           "  LEFT JOIN   redmine_db.time_entries te2 ON u2.id = te2.user_id \r\n" + 
           "  WHERE     u2.id = :userId AND te2.project_id  = :projectId\r\n" + 
           "  GROUP BY  u2.id, te2.project_id) temp \r\n" + 
           "LIMIT 1;", nativeQuery = true)
       public boolean isExistProjectOfUser(@Param("userId") int userId, @Param("projectId") int projectId);
             
      /**
      * get startDate and endDate of List project
      * 
      * @param projectIds
      * @return List<Object> 
      * @author: NNDuy
      */
     @Query(value = "SELECT  cv.custom_field_id, cv.`value` as date, cv.customized_id as project_id, p.name as project_name\r\n" + 
         "FROM  redmine_db.custom_values cv\r\n" + 
         "JOIN  redmine_db.projects p ON p.id = cv.customized_id\r\n" + 
         "WHERE   (cv.custom_field_id = 35 OR cv.custom_field_id = 36) AND cv.customized_id IN (:projectIds) \r\n" + 
         "ORDER BY cv.customized_id ASC, cv.custom_field_id ASC", nativeQuery = true)
      public List<Object> getTimeOfListProjectActveByUser(@Param("projectIds") List<Integer> projectIds);

	/**
	 * Get Dates From CurrentDate To Project EndDate
	 * @param sProjectEndDate
	 * @return List<String> 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT T.selected_date FROM (\r\n" + 
			"	-- Create calender 9999 days from ProjectStartDate\r\n" + 
			"		SELECT DATE_FORMAT(ADDDATE(:sProjectStartDate, t3.i*1000 + t2.i*100 + t1.i*10 + t0.i),\"%Y-%m-%d\") AS selected_date\r\n" + 
			"		FROM (SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t0,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,\r\n" + 
			"			(SELECT 0 i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4\r\n" + 
			"				UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3\r\n" + 
			"	) T\r\n" + 
			"WHERE selected_date BETWEEN :sProjectStartDate AND :sProjectEndDate", nativeQuery = true)
	public List<String> getDatesFromStartDateToProjectEndDate(@Param("sProjectStartDate") String sProjectStartDate, @Param("sProjectEndDate") String sProjectEndDate);

}
