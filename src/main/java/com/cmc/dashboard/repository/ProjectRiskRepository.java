/**
 * dashboard-phase2-backend - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.model.Risk;

/**
 * @author: nvangoc
 * @Date: 19 Apr 2018
 */
@Repository
public interface ProjectRiskRepository extends JpaRepository<Risk, Integer> {
	/**
	 * 
	 * description this function return list risk by project id
	 * 
	 * @param projectId
	 * @return List<Risk>
	 * @author: nvangoc
	 */
//	@Query(value = "from Risk r where r.projectId = :projectId and r.isDeleted = 0 order by r.registeredDate desc")
//	public List<Risk> getAllRiskOfProject(@Param("projectId") int projectId);

	/**
	 * @author: Overwrite by LinhGia
	 */
	@Query(value = "SELECT r FROM Risk AS r WHERE projectId = :projectId")
	public List<Risk> getAllRiskOfProject(@Param("projectId") int projectId);

	/**
	 * Get Open Risks Total By ProjectId
	 * @param projectId
	 * @param riskStatusIdOpen
	 * @return int 
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(1) AS open_risks_total FROM risk AS T1\r\n" + 
			"WHERE T1.project_id = :projectId AND T1.risk_status_id = :riskStatusIdOpen", nativeQuery = true)
	public int getOpenRisksTotalByProjectId(@Param("projectId") int projectId, @Param("riskStatusIdOpen") int riskStatusIdOpen);
}
