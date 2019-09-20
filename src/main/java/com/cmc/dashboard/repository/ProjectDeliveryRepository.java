/**
 * dashboard-phase2-backend- - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.ProjectDelivery;

/**
 * @author: GiangTM
 * @Date: Feb 27, 2018
 */
public interface ProjectDeliveryRepository extends JpaRepository<ProjectDelivery, Integer> {
	/**
	 * Get DeliveryTimeliness By ProjectId
	 * @param projectId
	 * @return Float
	 * @author: GiangTM
	 */
	@Query(value = "SELECT COUNT(CASE WHEN p.ontime = 1 THEN 1 END) AS OntimeDelivery, COUNT(*) AS DeliveryTotal"
			+ " FROM project_delivery p WHERE p.project_id = :projectId", nativeQuery = true)
	public Object getDeliveryTimelinessByProjectId(@Param("projectId") int projectId);
	
	@Query(value = "select * from project_delivery pd where pd.project_id=:projectId", nativeQuery = true)
	public List<ProjectDelivery> getDeliveryByProjectId(@Param("projectId") int projectId);
	

}
