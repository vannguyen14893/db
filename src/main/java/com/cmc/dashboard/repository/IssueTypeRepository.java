package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.IssueType;
import com.cmc.dashboard.util.Constants;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
	
	@Query("FROM IssueType A WHERE A.deleted IS NULL OR A.deleted = " + Constants.Issue.ISSUE_TYPE_NOT_DELETED)
	public List<IssueType> getIssueType();
	
}
