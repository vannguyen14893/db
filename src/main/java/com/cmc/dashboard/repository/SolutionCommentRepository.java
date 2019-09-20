package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.SolutionComment;

@Repository
public interface SolutionCommentRepository extends JpaRepository<SolutionComment, Integer> {
	
	@Query("FROM SolutionComment i WHERE i.solutionId.id = :solutionId AND i.isDeleted = false ORDER BY i.createDate ASC")
	public List<SolutionComment> getCommentBySolutionId(@Param("solutionId") int riskId);

}
