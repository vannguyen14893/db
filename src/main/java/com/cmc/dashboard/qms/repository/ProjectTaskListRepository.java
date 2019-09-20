package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.qms.model.QmsProject;


/**
 * @author: tvdung
 * @Date: Feb 28, 2018
 */
@Repository
public interface ProjectTaskListRepository extends JpaRepository<QmsProject, Integer>{

	/**
	 * get list task of project
	 * 
	 * @param projectId
	 * @return List<Object> 
	 * @author: tvdung
	 */
	@Query(value = "SELECT i.id, i_status.name, subject, CONCAT(users.lastname ,\" \" , users.firstname) AS username,\r\n" + 
			"start_date, due_date, done_ratio, estimated_hours  FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS i_status\r\n" + 
			"ON i.status_id = i_status.id\r\n" + 
			"INNER JOIN redmine_db.users AS users\r\n" + 
			"ON i.assigned_to_id = users.id\r\n" + 
			"WHERE project_id = :projectId AND tracker_id = 6 " +
			"AND i.subject LIKE %:search% AND CONCAT(users.lastname ,\" \" , users.firstname) IN :assignee "
			+ "AND i_status.name IN :status AND start_date >= :minstartDate AND start_date <= :maxstartDate "+
			" AND due_date >= :mindueDate AND due_date <= :maxdueDate" 
			+ "\n#pageable\n",
			countQuery = "SELECT COUNT(*)  FROM redmine_db.issues AS i\r\n" + 
					"INNER JOIN redmine_db.issue_statuses AS i_status\r\n" + 
					"ON i.status_id = i_status.id\r\n" + 
					"INNER JOIN redmine_db.users AS users\r\n" + 
					"ON i.assigned_to_id = users.id\r\n" + 
					"WHERE project_id = :projectId AND tracker_id = 6 " +
					"AND i.subject LIKE %:search% AND CONCAT(users.lastname ,\" \" , users.firstname) IN :assignee "
					+ "AND i_status.name IN :status AND start_date >= :minstartDate AND start_date <= :maxstartDate " + 
					" AND due_date >= :mindueDate AND due_date <= :maxdueDate ",
			nativeQuery = true)
	public Page<Object> getListProjectTask(@Param("projectId") int projectId, @Param("search") String search, @Param("assignee") List<String> assignee,
			@Param("status") List<String> status,@Param("minstartDate") String minstartDate,
			@Param("maxstartDate") String maxstartDate, @Param("mindueDate") String mindueDate, @Param("maxdueDate") String maxdueDate, Pageable pageable);
	
	/**
	 * 
	 *get count of project task
	 * @param projectId
	 * @param search
	 * @return int 
	 * @author: tvdung
	 */
	@Query(value="SELECT COUNT(*)  FROM redmine_db.issues AS i\r\n" + 
					"INNER JOIN redmine_db.issue_statuses AS i_status\r\n" + 
					"ON i.status_id = i_status.id\r\n" + 
					"INNER JOIN redmine_db.users AS users\r\n" + 
					"ON i.assigned_to_id = users.id\r\n" + 
					"WHERE project_id = :projectId AND tracker_id = 6 " +
					"AND i.subject LIKE %:search% AND CONCAT(users.lastname ,\" \" , users.firstname) IN :assignee " + 
					"AND i_status.name IN :status AND start_date >= :minstartDate AND start_date <= :maxstartDate \r\n" + 
					" AND due_date >= :mindueDate AND due_date <= :maxdueDate", nativeQuery = true)
	public int getCountProjectTask(@Param("projectId") int projectId, @Param("search") String search, @Param("assignee") List<String> assignee,
			@Param("status") List<String> status,@Param("minstartDate") String minstartDate,
			@Param("maxstartDate") String maxstartDate, @Param("mindueDate") String mindueDate, @Param("maxdueDate") String maxdueDate);
	
	/**
	 * 
	 * get list asssingee
	 * @param projectId
	 * @return List<String> 
	 * @author: tvdung
	 */
	@Query(value="SELECT DISTINCT CONCAT(users.lastname ,\\\" \\\" , users.firstname) AS username  FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS i_status\r\n" + 
			"ON i.status_id = i_status.id\r\n" + 
			"INNER JOIN redmine_db.users AS users\r\n" + 
			"ON i.assigned_to_id = users.id\r\n" + 
			"WHERE project_id = :projectId AND tracker_id = 6 ", nativeQuery = true)
	public List<String> getListName(@Param("projectId") int projectId);
	
	/**
	 * 
	 * GET list status
	 * @param projectId
	 * @return List<String> 
	 * @author: tvdung
	 */
	@Query(value="SELECT DISTINCT i_status.name FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS i_status\r\n" + 
			"ON i.status_id = i_status.id\r\n" + 
			"INNER JOIN redmine_db.users AS users\r\n" + 
			"ON i.assigned_to_id = users.id\r\n" + 
			"WHERE project_id = :projectId AND tracker_id = 6 ", nativeQuery = true)
	public List<String> getListStatus(@Param("projectId") int projectId);
	
}
