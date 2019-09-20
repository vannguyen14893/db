/**
 * 
 */
package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.DashboardDTO;
import com.cmc.dashboard.dto.EfficiencyDTO;
import com.cmc.dashboard.dto.EffortEfficiencyDetails;
import com.cmc.dashboard.dto.ProjectCssDetailDTO;
import com.cmc.dashboard.dto.ProjectDTO;

/**
 * @author nahung
 *
 */
public interface ReportService {
	
	/**
	 * 
	 * Get list Project CSS
	 * @param month
	 * @param year
	 * @param userId
	 * @return
	 * @author: tvdung
	 */
	public List<CssChartDTO> getChartCSSService(int month, int year, int userId);
	
	/**
	 * Get list Project CSS
	 * @param month
	 * @param year
	 * @param userId
	 * @return List<CssChartDTO> 
	 * @author: DVNgoc
	 */
	public List<CssChartDTO> getProjectCSSService(int month, int year, int userId);

	/**
	 * Get list project details (css)
	 * @param deliveryUnit: delivery unit name
	 * @param month: month of the given time period
	 * @param year: year of the given time period
	 * @return List<ProjectCssDetailDto> 
	 * @author: DVNgoc
	 */
	public List<ProjectCssDetailDTO> getCssDetails(String deliveryUnit, int month, int year);
	
	/**
	 * Get total project by type
	 * @param month
	 * @param year
	 * @return List<Map<String,String>> 
	 * @author: NVKhoa
	 */
	public List<DashboardDTO<Integer>> getProjectByType(int month, int year, int userId);
	
	
	/**
	 * Get list of project by type, delivery unit
	 * @param duName
	 * @param projectType
	 * @param month
	 * @param year
	 * @return List<ProjectDTO> 
	 * @author: NVKhoa
	 */
	public List<ProjectDTO> getProjectListByType(String duName, String projectType, int month, int year);
	
	/**
	 * Get effort efficiency 
	 * @param month
	 * @param year
	 * @param userId
	 * @return
	 * @throws SQLException List<EfficiencyDTO> 
	 * @author: NVKhoa
	 */
	public List<EfficiencyDTO> getEffortEfficiency(int month, int year, int userId);
	
	public List<EffortEfficiencyDetails> getListProjectBillabeByDU(String duName, String billableMonth);
}