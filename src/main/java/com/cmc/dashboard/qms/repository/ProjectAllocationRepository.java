package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;

public interface ProjectAllocationRepository extends JpaRepository<QmsUser, Integer>{
	
	  /**
	   * Description method
	   * 
	   * @param userId
	   * @param startDate
	   * @param endDate
	   * @return List<QmsProject>
	   * @author: tvhuan
	   */
		@Query(value = "SELECT TB1.id, TB1.name, TB1.delivery_unit FROM "
	      + "    (SELECT P.id, P.name, P.status,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
	      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
	      + "FROM redmine_db.projects P \r\n" + "INNER JOIN members M ON M.project_id = P.id\r\n"
	      + "INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n" + "INNER JOIN redmine_db.member_roles MR ON MR.member_id = M.id\r\n"
	      + "WHERE U.id = :userId AND MR.role_id =" + CustomValueUtil.ROLE_PM + "\r\n"
	      + " AND P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
	      + ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"
	      + "AND (\r\n" + 
	      "		concat(year(TB1.start_date),\"-\",LPAD(month(TB1.start_date), 2, '0')) <= :dateMonth AND\r\n" + 
	      "        concat(year(TB1.end_date),\"-\",LPAD(month(TB1.end_date), 2, '0')) >= :dateMonth\r\n" + 
	      "     )ORDER BY TB1.name ASC;"
	     , 
	       nativeQuery = true)
	  public List<Object> getProjectListByUserWithoutPMPage(@Param("userId") int userId, @Param("dateMonth") String dateMonth);
	
		 /**
		   * Description method
		   * 
		   * @param startDate
		   * @param endDate
		   * @return List<QmsProject>
		   * @author: tvhuan 
		   */
			@Query(value = "SELECT TB1.id, TB1.name, TB1.delivery_unit FROM "
		      + "(SELECT P.id, P.name, P.status,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
		      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
		      + "FROM redmine_db.projects P \r\n"
		      + " WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n" 
		      + ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"
		      + "  AND (\r\n" + 
		      "		concat(year(TB1.start_date),\"-\",LPAD(month(TB1.start_date), 2, '0')) <= :dateMonth AND\r\n" + 
		      "        concat(year(TB1.end_date),\"-\",LPAD(month(TB1.end_date), 2, '0')) >= :dateMonth \r\n" + 
		      "     )ORDER BY TB1.name ASC;"
		    , 
		    nativeQuery = true)
		  public List<Object> getAllProjectWithoutPMPage(@Param("dateMonth") String dateMonth);
			
			  /**
			   * Description method
			   * 
			   * @param duName
			   * @param startDate
			   * @param endDate
			   * @return List<QmsProject>
			   * @author: tvhuan
			   */
			  @Query(value = "SELECT TB1.id, TB1.name, TB1.delivery_unit FROM "
			      + "(SELECT P.id, P.name, P.status,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_CODE+"\r AND CV.customized_id = P.id) AS project_code,\r\n"
			      + "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
			      + "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			      + ") TB1 WHERE "
			      + " (TB1.delivery_unit = (:duName) AND TB1.p_type IS NOT NULL) AND\r"
			      + "       (\r\n" + 
			      "		concat(year(TB1.start_date),\"-\",LPAD(month(TB1.start_date), 2, '0')) <= :dateMonth AND\r\n" + 
			      "        concat(year(TB1.end_date),\"-\",LPAD(month(TB1.end_date), 2, '0')) >= :dateMonth\r\n" + 
			      "     )ORDER BY TB1.name ASC"
			      , nativeQuery = true)
			  public List<Object> getProjectByDeliveryUnitWithoutPMPage(@Param("duName") List<String> duName, @Param("dateMonth") String dateMonth);
			  
			  
			  @Query(value="SELECT distinct cs.value as DU FROM redmine_db.custom_values cs\r\n" + 
			  		"where cs.custom_field_id = 6 and cs.customized_id in (SELECT distinct a.user_id FROM redmine_db.members a where a.project_id = :projectId)",nativeQuery=true)
			  public List<String> getDuByProjectId(@Param("projectId") int projectId);
}
