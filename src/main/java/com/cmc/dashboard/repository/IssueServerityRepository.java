package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.IssueServerity;
import com.cmc.dashboard.util.Constants;

@Repository
public interface IssueServerityRepository extends JpaRepository<IssueServerity, Integer> {
	
	@Query("FROM IssueServerity A WHERE A.deleted IS NULL OR A.deleted = " + Constants.Issue.ISSUE_SERVERITY_NOT_DELETED)
	public List<IssueServerity> getIssueServerity();
	
}
