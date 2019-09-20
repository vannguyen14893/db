package com.cmc.dashboard.qms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsTimeEntries;

public interface TimesheetReponsitory extends JpaRepository<QmsTimeEntries, Integer>{
	
	/**
	 * get list timesheet of resource
	 * @param resourceId
	 * @return
	 * @author tvhuan
	 */	
	@Query(value="SELECT i.id as 'index',i.subject as 'issues name',i.start_date as 'startdate', " +
			"i.due_date as 'duedate', " + 
			"i.estimated_hours as 'estimate_hour' ,rt.hours, i_status.name as 'status', pro.name as project\r\n" + 
			"FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS i_status ON i.status_id = i_status.id\r\n" + 
			"inner join redmine_db.time_entries rt ON i.id = rt.issue_id\r\n" + 
			"AND rt.user_id=:resourceId\r\n" + 
			"inner join redmine_db.projects pro ON pro.id = rt.project_id "+
			"order by rt.updated_on desc\r\n" + 
			"limit 20 ",nativeQuery=true)
	List<Object> getListTimesheet(@Param("resourceId") int resourceId);
	
	/**
	 * get list timesheet of resour
	 * @param resourceId
	 * @return
	 * @author tvhuan
	 */
	
	@Query(value="SELECT i.id as 'index',i.subject as 'issues name',rt.spent_on as 'spent on', i.estimated_hours as 'estimate_hour' ,rt.hours, i_status.name as 'status',\r\n" + 
			" (select p.name from redmine_db.projects p where p.id = rt.project_id) projectName FROM redmine_db.issues AS i\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS i_status ON i.status_id = i_status.id\r\n" + 
			"inner join redmine_db.time_entries rt ON i.id = rt.issue_id\r\n" + 
			"AND rt.user_id= :resourceId	 and rt.spent_on >= :fromDate and rt.spent_on <= :toDate \r\n" + 
			"order by rt.updated_on desc\r\n" + 
			"limit 20 ",nativeQuery=true)
	List<Object> getListTimesheet(@Param("resourceId") int resourceId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
