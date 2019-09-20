/**
 * dashboard-phase2-backend - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.dto.RiskEditDTO;
import com.cmc.dashboard.model.PriorityRank;
import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.model.RiskCategory;
import com.cmc.dashboard.model.RiskHandlingOptions;
import com.cmc.dashboard.model.RiskImpact;
import com.cmc.dashboard.model.RiskLikelihood;
import com.cmc.dashboard.model.RiskStatus;
import com.cmc.dashboard.model.RiskSubCategory;

/**
 * @author: nvangoc
 * @Date: 19 Apr 2018
 */
public interface ProjectRiskService {
	/**
	 * 
	 * description this function will return all risk of project by projectId
	 * 
	 * @param projectId
	 * @return List<RiskDTO>
	 * @author: nvangoc
	 */
	public List<RiskDTO> getAllRiskOfProject(int projectId);

	/**
	 * 
	 * description this function return detail risk by risk id
	 * 
	 * @param riskId
	 * @return RiskDTO
	 * @author: nvangoc
	 */
	public RiskDTO getById(int riskId);

	/**
	 * 
	 * description this function will save new risk
	 * 
	 * @param risk
	 * @return Risk
	 * @author: nvangoc
	 */
	public Risk save(Risk risk);

	/**
	 * 
	 * description this function will return all priority rank
	 * 
	 * @return List<PriorityRank>
	 * @author: nvangoc
	 */
	public List<PriorityRank> findAllPriorityRank();

	/**
	 * 
	 * description this function will return all risk category
	 * 
	 * @return List<RiskCategory>
	 * @author: nvangoc
	 */
	public List<RiskCategory> findAllRiskCategory();

	/**
	 * 
	 * description this function will return all Risk Handling Options
	 * 
	 * @return List<RiskHandlingOptions>
	 * @author: nvangoc
	 */
	public List<RiskHandlingOptions> findAllRiskHandlingOptions();

	/**
	 * 
	 * description this function will return all Risk Impact
	 * 
	 * @return List<RiskImpact>
	 * @author: nvangoc
	 */
	public List<RiskImpact> findAllRiskImpact();

	/**
	 * 
	 * description this function will return all Risk Likelihood
	 * 
	 * @return List<RiskLikelihood>
	 * @author: nvangoc
	 */
	public List<RiskLikelihood> findAllRiskLikelihood();

	/**
	 * 
	 * description this function will return all Risk Status
	 * 
	 * @return List<RiskStatus>
	 * @author: nvangoc
	 */
	public List<RiskStatus> findAllRiskStatus();

	/**
	 * 
	 * description this function will return all Risk Sub-Category
	 * 
	 * @return List<RiskSubCategory>
	 * @author: nvangoc
	 */
	public List<RiskSubCategory> findAllRiskSubCategory();
	
	/**
	 * 
	 * description this function will return Risk
	 * 
	 * @return Risk
	 * @author: ntquy
	 */
	public RiskDTO save(RiskEditDTO risk);
	
	/** delete risk
	 * @author duyhieu
	 * @param solutionId
	 */
	public void deleteRisk(int riskId);
		

}
