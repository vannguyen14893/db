/**
 * 
 */
package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.ProjectKpiDTO;

/**
 * @author GiangTM
 *
 */
public interface ProjectKpiService {
	
	/**
	 * Get project kpi by project id.
	 * @param projectId
	 * @return ProjectKpiDTO
	 * @author: TMGiang
	 */
	public List<ProjectKpiDTO> getProjectKpiByProjectId(int projectId);
}
