package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.ProjectListQms;
import com.cmc.dashboard.util.CustomValueUtil;

public interface ProjectListQmsRepository extends JpaRepository<ProjectListQms, Integer> {

	/**
	 * Get all project by user who is incharge
	 * 
	 * @param roleName
	 * @param userId
	 * @return List<ProjectQms>
	 * @author: ntquy
	 */

	@Query(value = "SELECT DISTINCT TB1.id,TB1.name, TB1.projectManager, TB1.projectType,TB1.deliveryUnit, ROUND(TB1.projectSize, 0) as projectSize, TB1.status,TB1.startDate, TB1.endDate,  TB1.projectCode  FROM "
			+ "	   (SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
			 + "    (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager,\r\n" 
			 + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
			  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit\r\n"
			+ "FROM redmine_db.projects, redmine_db.members, redmine_db.users, redmine_db.member_roles WHERE redmine_db.members.project_id = redmine_db.projects.id\r\n"
			+ "AND redmine_db.members.user_id = redmine_db.users.id\r\n" + "AND redmine_db.member_roles.member_id = redmine_db.members.id\r\n"
			+ "AND redmine_db.users.id = :userId "
			+ " AND redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.deliveryUnit IS NOT NULL)\r\n"
			 + "AND TB1.name LIKE %:projectName%\r\n"
		     + "AND TB1.status REGEXP :status\r\n"
		     + "AND TB1.projectManager REGEXP :PM\r\n"
		     + "AND TB1.projectType REGEXP :typeProject\r\n"
		      + "AND TB1.deliveryUnit REGEXP :DU\r\n"		
			   + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
			      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n"
			+ "\n#pageable\n", 
			countQuery = "SELECT count(*) FROM "
					+ "	   (SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
					 + "   (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager,\r\n" 
					 + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
				     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
					  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
				     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
				     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
				     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit\r\n"
					+ "FROM redmine_db.projects, redmine_db.members, redmine_db.users, redmine_db.member_roles WHERE redmine_db.members.project_id = redmine_db.projects.id\r\n"
					+ "AND redmine_db.members.user_id = redmine_db.users.id\r\n" + "AND redmine_db.member_roles.member_id = redmine_db.members.id\r\n"
					+ "AND redmine_db.users.id = :userId "
					+ " AND redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"
					+ ") TB1 WHERE (TB1.deliveryUnit IS NOT NULL)\r\n"
					 + "AND TB1.name LIKE %:projectName%\r\n"
					 + "AND TB1.status REGEXP :status\r\n"
				     + "AND TB1.projectManager REGEXP :PM\r\n"
				     + "AND TB1.projectType REGEXP :typeProject\r\n"
				      + "AND TB1.deliveryUnit REGEXP :DU\r\n"					
					   + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
					      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n", nativeQuery = true)
		public Page<ProjectListQms> getProjectListByUser(@Param("userId") int userId, Pageable pageable, @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM , @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject);

	/**
	 * Get all project
	 * 
	 * @return List<ProjectQms>
	 * @author: ntquy
	 */

	@Query(value = "SELECT DISTINCT TB1.id,TB1.name, TB1.projectManager, TB1.projectType,TB1.deliveryUnit, ROUND(TB1.projectSize, 0) as projectSize, TB1.status,TB1.startDate, TB1.endDate,  TB1.projectCode  FROM "
				+ "(SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
			      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
			      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
				  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
		          + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
			      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
			      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit,\r\n"
				  + "    (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager\r\n" + 
				  "FROM redmine_db.projects \r\n"
			      + " WHERE redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"  
			      + ") AS TB1 WHERE (TB1.deliveryUnit IS NOT NULL)\r\n"
			      + "AND TB1.name LIKE %:projectName%\r\n"
			      + "AND TB1.status REGEXP :status\r\n"
				     + "AND TB1.projectManager REGEXP :PM\r\n"
				     + "AND TB1.projectType REGEXP :typeProject\r\n"
				      + "AND TB1.deliveryUnit REGEXP :DU\r\n"	
			      + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
			      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n"
			      + "   \n#pageable\n",

				    countQuery = "SELECT count(*) FROM \r\n"
				    		+ "(SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
						      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
						      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
							  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
					          + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
						      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
						      + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit,\r\n"
							  + "    (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager\r\n" + 
				   	 "FROM redmine_db.projects \r\n"
						      + " WHERE redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"  
						      + ") AS TB1 WHERE (TB1.deliveryUnit IS NOT NULL)\r\n"
						      + "AND TB1.name LIKE %:projectName%\r\n"
						      + "AND TB1.status REGEXP :status\r\n"
							     + "AND TB1.projectManager REGEXP :PM\r\n"
							     + "AND TB1.projectType REGEXP :typeProject\r\n"
							      + "AND TB1.deliveryUnit REGEXP :DU\r\n"	
						      + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
						      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n", nativeQuery = true)
	  public Page<ProjectListQms> getAllProject(Pageable pageable, @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM, @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject);

	/**
	 * Get project by delivery unit
	 * 
	 * @param duName
	 * @return List<ProjectQms>
	 * @author: ntquy
	 */
	@Query(value = "SELECT DISTINCT TB1.id,TB1.name, TB1.projectManager, TB1.projectType,TB1.deliveryUnit, ROUND(TB1.projectSize, 0) as projectSize, TB1.status,TB1.startDate, TB1.endDate,  TB1.projectCode  FROM "
	+ "(SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
	 + "    (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager,\r\n" 
	 + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
	  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit,\r\n"
	+ "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS p_type\r\n"
	+ "FROM redmine_db.projects WHERE redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"
	+ ") TB1 WHERE "
	+ " (TB1.deliveryUnit IN (:duName) AND TB1.p_type IS NOT NULL) AND\r"
	 + "TB1.name LIKE %:projectName%\r\n"
	 + "AND TB1.status REGEXP :status\r\n"
     + "AND TB1.projectManager REGEXP :PM\r\n"
     + "AND TB1.projectType REGEXP :typeProject\r\n"
      + "AND TB1.deliveryUnit REGEXP :DU\r\n"	
	   + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
	      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n"
	+ "\n#pageable\n",
	countQuery = "SELECT count(*) FROM \r\n"
			+ "(SELECT redmine_db.projects.id, redmine_db.projects.name, redmine_db.projects.status,\r\n"
			 + "    (SELECT IFNULL((SELECT GROUP_CONCAT(D.namePM SEPARATOR ', ') FROM (SELECT DISTINCT CONCAT(C.lastname,\" \", C.firstname) as namePM ,C.project_id FROM (SELECT A.id, A.login, A.hashed_password, A.firstname, A.lastname, A.admin, A.status, A.salt, B.project_id FROM (SELECT redmine_db.users.* FROM redmine_db.users, redmine_db.members, redmine_db.member_roles WHERE redmine_db.users.id = redmine_db.members.user_id AND member_roles.member_id = members.id AND member_roles.role_id =3) AS A JOIN (SELECT members.id, members.user_id, members.project_id FROM members, member_roles WHERE member_roles.member_id = members.id AND member_roles.role_id = 3) AS B ON A.id = B.user_id) AS C) AS D  WHERE D.project_id = redmine_db.projects.id  GROUP BY 'all'),'')) AS projectManager,\r\n" 
			 + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectSize,\r\n"		     
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectType,\r\n"
			  + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS startDate,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS endDate,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS projectCode,\r\n"
		     + "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS deliveryUnit,\r\n"
			+ "    (SELECT redmine_db.custom_values.value FROM redmine_db.custom_values WHERE redmine_db.custom_values.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND redmine_db.custom_values.customized_id = redmine_db.projects.id) AS p_type\r\n"
			+ "FROM redmine_db.projects WHERE redmine_db.projects.id NOT IN (SELECT redmine_db.projects.parent_id FROM redmine_db.projects WHERE redmine_db.projects.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE "
			+ " (TB1.deliveryUnit IN (:duName) AND TB1.p_type IS NOT NULL) AND\r"
			 + "TB1.name LIKE %:projectName%\r\n"
			 + "AND TB1.status REGEXP :status\r\n"
		     + "AND TB1.projectManager REGEXP :PM\r\n"
		     + "AND TB1.projectType REGEXP :typeProject\r\n"
		      + "AND TB1.deliveryUnit REGEXP :DU\r\n"	
			   + "AND TB1.startDate BETWEEN :startDateFrom AND :startDateTo \r\n"
			      + "AND TB1.endDate BETWEEN :endDateFrom AND :endDateTo \r\n", nativeQuery = true)
public Page<ProjectListQms> getProjectByDeliveryUnit(@Param("duName") List<String> duName, Pageable pageble, @Param("projectName") String projectName, @Param("status") String status, @Param("PM") String PM , @Param("startDateFrom") String startDateFrom, @Param("startDateTo") String startDateTo,@Param("endDateFrom") String endDateFrom, @Param("endDateTo") String endDateTo, @Param("DU") String DU, @Param("typeProject") String typeProject);
	
}
