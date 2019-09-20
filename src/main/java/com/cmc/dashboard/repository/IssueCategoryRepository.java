package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.IssueCategory;
import com.cmc.dashboard.util.Constants;

@Repository
public interface IssueCategoryRepository extends JpaRepository<IssueCategory, Integer> {

	@Query("FROM IssueCategory A WHERE A.deleted IS NULL OR A.deleted = " + Constants.Issue.ISSUE_CATEGORY_NOT_DELETED)
	List<IssueCategory> getIssueCategory();

}
