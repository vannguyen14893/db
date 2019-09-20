package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ProjectEvaluateComment;

public interface ProjectEvaluateCommentRepository extends JpaRepository<ProjectEvaluateComment, Integer> {
	 @Query(value="select *\r\n" + 
		  		"from evaluate_comment e\r\n" + 
		  		"where e.project_evaluate_id=:id", nativeQuery=true)
	 public List<ProjectEvaluateComment>findByEvalutedId(@Param("id") int id);
	 @Query(value="select * from evaluate_comment where project_evaluate_id=:evaluateId and type=:type order by created_date DESC limit 1",nativeQuery = true)
	 public ProjectEvaluateComment getLastCommentPmOrQa(@Param("evaluateId")int evaluateId, @Param("type") int type);
}
