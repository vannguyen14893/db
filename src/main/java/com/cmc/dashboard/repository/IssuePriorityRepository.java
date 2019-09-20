package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.IssuePriority;
import com.cmc.dashboard.util.Constants;

@Repository
public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {

	@Query("FROM IssuePriority A WHERE A.deleted IS NULL OR A.deleted = " + Constants.Issue.ISSUE_PRIORITY_NOT_DELETED)
	public List<IssuePriority> getIssuePriority();

}
