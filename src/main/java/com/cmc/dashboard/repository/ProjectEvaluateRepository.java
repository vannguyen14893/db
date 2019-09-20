package com.cmc.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ProjectEvaluate;


public interface ProjectEvaluateRepository extends JpaRepository<ProjectEvaluate, Integer> {
	

  public List<ProjectEvaluate> findByProjectIdAndStartDate (int projectId,Date startDate);
    
  public List<ProjectEvaluate> findById(int id);
  
  public List<ProjectEvaluate> findByProjectId(int projectId);
  
  @Query(value="select *\r\n" + 
	  		"from evaluate e\r\n" + 
	  		"where e.project_id=:projectId and (year(e.startDate)=:year or year(e.endDate)=:year) and (month(e.startDate)=:month or month(e.endDate)=:month)", nativeQuery=true)
  public List<ProjectEvaluate> findByProjectId(@Param("projectId") int projectId,
		  @Param("month") int month,
		  @Param("year") int year);
  
  @Query(value="select *\r\n" + 
  		"from evaluate e\r\n" + 
  		"where e.project_id=:projectId and e.startDate =:startDate and e.endDate=:endDate", nativeQuery=true)
  public ProjectEvaluate findProjectId(@Param("projectId") int projectId,
		  @Param("startDate") String startDate,
		  @Param("endDate") String endDate );
  
  @Query(value="select *\r\n" + 
	  		"from evaluate e\r\n" + 
	  		"where e.project_evaluate_id=:projectEvaluateId", nativeQuery=true)
	  public ProjectEvaluate findProjectEvaluateId(@Param("projectEvaluateId") int projectEvaluateId);
  
  
  @Query(value="select *\r\n" + 
	  		"from evaluate e\r\n" + 
	  		"where e.project_id=:projectId",nativeQuery=true)
	  public List<ProjectEvaluate> findProjectId(@Param("projectId") int projectId);
  
  @Query(value = "SELECT p.project_name, g.group_name, e.startDate, e.endDate, e.quality_id, e.delivery_id, e.process_id, e.subject,e.project_evaluate_id\r\n" + 
  		"FROM evaluate as e left join project as p on e.project_id = p.project_id\r\n" + 
  		"				   left join groups as g on p.group_id = g.group_id\r\n" + 
  		"WHERE e.endDate>=:startDate and e.startDate<=:endDate and g.group_id in (:groupId)", nativeQuery = true)
  public List<Object> getListMonitoring(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("groupId") List<String> groupId);
  
  @Query(value = "SELECT p.project_name, g.group_name, e.startDate, e.endDate, e.quality_id, e.delivery_id, e.process_id, e.subject,e.project_evaluate_id\r\n" + 
  		"	FROM evaluate as e left join project as p on e.project_id = p.project_id\r\n" + 
  		"  						   left join groups as g on p.group_id = g.group_id\r\n" + 
  		"  		WHERE e.endDate>=:startDate and e.startDate<=:endDate", nativeQuery = true)
  public List<Object> getAllMonitoring(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
  @Query(value = "SELECT el.value FROM evaluate_level as el where el.project_evaluate_level_id=:id", nativeQuery = true)
  public String getEvaluateLevel(@Param("id") int id);
}

