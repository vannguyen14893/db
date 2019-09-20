package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.IssueSource;
import com.cmc.dashboard.util.Constants;

@Repository
public interface IssueSourceRepository extends JpaRepository<IssueSource, Integer> {
	
	@Query("FROM IssueSource A WHERE A.deleted IS NULL OR A.deleted = " + Constants.Issue.ISSUE_SOURCE_NOT_DELETED)
	public List<IssueSource> getIssueSource();
	
}
