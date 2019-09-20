/**
 * dashboard-phase2-backend- - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.ProjectPcvRate;

/**
 * @author: GiangTM
 * @Date: Feb 27, 2018
 */
@Repository
public interface ProjectPcvRateRepository extends JpaRepository<ProjectPcvRate, Integer>{
	/**
	 * Get Project PcvRates By ProjectId
	 * @param projectId
	 * @return List<ProjectPcvRate> 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT p FROM ProjectPcvRate AS p WHERE projectId = :projectId")
	public List<ProjectPcvRate> getProjectPcvRatesByProjectId(@Param("projectId") int projectId);
	
	/**
	 * Get PcvRate By ProjectId
	 * @param projectId
	 * @return Float 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT AVG(score) FROM project_pcv_rate AS p WHERE p.project_id = :projectId", nativeQuery = true)
	public Float getPcvRateByProjectId(@Param("projectId") int projectId);
	
	/**
	 * Get accumulated has Max(end date) 
	 * @param projectId
	 * @return Float 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT accumulated FROM project_pcv_rate AS p"
			+ " WHERE p.project_id = :projectId"
			+ " AND p.end_time = (SELECT max(end_time) FROM project_pcv_rate p1 WHERE p1.project_id = :projectId);", nativeQuery = true)
	public Float getPcvRateMaxEndDateByProjectId(@Param("projectId") int projectId);

}
