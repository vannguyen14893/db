package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.Issue;

@Repository
public interface IssueDashboardRepository extends JpaRepository<Issue, Integer> {
	
	@Query(value = "SELECT * FROM issue i WHERE i.project_id=:projectId AND i.deleted = 0 ORDER BY i.open_date DESC", nativeQuery = true)
	public List<Issue> getIssueByProject(@Param("projectId") int projectId);
	@Query(value = "SELECT * FROM issue i WHERE i.project_id REGEXP :projectId AND i.deleted = 0  and month(i.open_date) REGEXP :month and year(i.open_date) REGEXP :year and"
			+ " i.project_id in (select project_id from project where group_id REGEXP :groupId)"
			+ " ORDER BY i.open_date DESC", nativeQuery = true)
	public List<Issue> getAllIssue(@Param("projectId") String projectId,@Param("month") String month,@Param("year") String year,@Param("groupId") String groupId);
}
