package com.cmc.dashboard.qms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.qms.model.QmsJournals;

/**
 * @author: GiangTM
 * @Date: May 23, 2018
 */
@Repository
public interface JournalsRepository extends JpaRepository<QmsJournals, Integer> {
	/**
	 * Get Last Update By UserId And ProjectId
	 * @param theUserId
	 * @param theProjectId
	 * @return Object 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT T4.name AS updateTracker, T5.name AS old_status,\r\n" + 
			"T6.name AS new_status, TIMESTAMPDIFF(MINUTE, T1.created_on, NOW()) AS updated_on\r\n" + 
			"FROM redmine_db.journals AS T1\r\n" + 
			"INNER JOIN redmine_db.journal_details AS T2 ON T2.journal_id = T1.id\r\n" + 
			"INNER JOIN redmine_db.issues AS T3 ON T3.id = T1.journalized_id\r\n" + 
			"INNER JOIN redmine_db.trackers AS T4 ON T4.id = T3.tracker_id\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS T5 ON T5.id = T2.old_value\r\n" + 
			"INNER JOIN redmine_db.issue_statuses AS T6 ON T6.id = T2.value\r\n" + 
			"WHERE T1.user_id = :theUserId AND T3.project_id = :theProjectId AND T2.prop_key = 'status_id'\r\n" + 
			"ORDER BY T1.created_on DESC LIMIT 1", nativeQuery = true)
	public Object getLastUpdateByUserIdAndProjectId(@Param("theUserId") int theUserId, @Param("theProjectId") int theProjectId);
}
