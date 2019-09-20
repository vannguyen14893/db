/**
 * dashboard-phase2-backend - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.dto.RiskEditDTO;
import com.cmc.dashboard.dto.RiskHistoryDTO;
import com.cmc.dashboard.model.HistoryData;
import com.cmc.dashboard.model.PriorityRank;
import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.model.RiskCategory;
import com.cmc.dashboard.model.RiskHandlingOptions;
import com.cmc.dashboard.model.RiskImpact;
import com.cmc.dashboard.model.RiskLikelihood;
import com.cmc.dashboard.model.RiskStatus;
import com.cmc.dashboard.model.RiskSubCategory;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.HistoryDataRepository;
import com.cmc.dashboard.repository.PriorityRankRepository;
import com.cmc.dashboard.repository.ProjectRiskRepository;
import com.cmc.dashboard.repository.RiskCategoryRepository;
import com.cmc.dashboard.repository.RiskHandlingOptionsRepository;
import com.cmc.dashboard.repository.RiskImpactRepository;
import com.cmc.dashboard.repository.RiskLikelihoodRepository;
import com.cmc.dashboard.repository.RiskStatusRepository;
import com.cmc.dashboard.repository.RiskSubCategoryRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.HistoryDataUtil;

/**
 * @author: nvangoc
 * @Date: 19 Apr 2018
 */
@Service
public class ProjectRiskServiceImpl implements ProjectRiskService {
	@Autowired
	private ProjectRiskRepository projectRiskRepository;
	@Autowired
	private PriorityRankRepository priorityRankRepository;
	@Autowired
	private RiskCategoryRepository riskCategoryRepository;
	@Autowired
	private RiskHandlingOptionsRepository riskHandlingOptionsRepository;
	@Autowired
	private RiskImpactRepository riskImpactRepository;
	@Autowired
	private RiskLikelihoodRepository riskLikelihoodRepository;
	@Autowired
	private RiskStatusRepository riskStatusRepository;
	@Autowired
	private RiskSubCategoryRepository riskSubCategoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HistoryDataRepository historyDataRepository;
	@Autowired
	private UserQmsRepository userQmsRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#getAllRiskOfProject(int)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskDTO> getAllRiskOfProject(int projectId) {
		List<RiskDTO> riskDTOs = new ArrayList<>();
		for (Risk risk : projectRiskRepository.getAllRiskOfProject(projectId)) {
			riskDTOs.add(new RiskDTO(risk));
		}
		return riskDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectRiskService#save(com.cmc.dashboard.model.
	 * Risk)
	 */
	@Transactional
	@Override
	public Risk save(Risk risk) {
		risk.setIsDeleted(false);
		User userByID = userRepository.getOne(risk.getRegisteredBy().getUserId());
//		User userRegisteredBy =this.getUserByUserName(risk.getRegisteredBy().getUserId());

		risk.setRegisteredBy(userByID!=null?userByID:risk.getRegisteredBy());
		System.out.println("userByID : " + userByID.getFullName() + " , userWithRisk : " + risk.getRegisteredBy().getFullName());

		return projectRiskRepository.save(risk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllPriorityRank()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<PriorityRank> findAllPriorityRank() {
		return priorityRankRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllRiskCategory()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskCategory> findAllRiskCategory() {
		return riskCategoryRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectRiskService#findAllRiskHandlingOptions()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskHandlingOptions> findAllRiskHandlingOptions() {
		return riskHandlingOptionsRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllRiskImpact()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskImpact> findAllRiskImpact() {
		return riskImpactRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllRiskLikelihood()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskLikelihood> findAllRiskLikelihood() {
		return riskLikelihoodRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllRiskStatus()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskStatus> findAllRiskStatus() {
		return riskStatusRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#findAllRiskSubCategory()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<RiskSubCategory> findAllRiskSubCategory() {
		return riskSubCategoryRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectRiskService#getById(int)
	 */
	@Transactional(readOnly = true)
	@Override
	public RiskDTO getById(int riskId) {
		Risk risk = projectRiskRepository.findOne(riskId);
		return new RiskDTO(risk, riskSubCategoryRepository.findSubCategory(risk.getRiskCategoryId().getRiskCategoryId()));	}
	
	@Override
	public RiskDTO save(RiskEditDTO riskEdit) {
		Risk risk = projectRiskRepository.findOne(riskEdit.getRiskId());
		System.out.println("riskImpl : " + risk.getRiskDescription());
		
		Risk riskSave = new Risk();
		
		    if (riskEdit.getEarliestImpactDate() != null) {
		    	riskSave.setEarliestImpactDate(riskEdit.getEarliestImpactDate());
		    }else {
		    	riskSave.setEarliestImpactDate(risk.getEarliestImpactDate());
			}
		    
	        if (riskEdit.getLatestImpactDate() != null) {
	        	riskSave.setLatestImpactDate(riskEdit.getLatestImpactDate());
	        }else {
				riskSave.setLatestImpactDate(risk.getLatestImpactDate());
			}
	        
	        if (riskEdit.getPriorityRankId() != null) {
	        	riskSave.setPriorityRankId(priorityRankRepository.findOne(riskEdit.getPriorityRankId()));
	        }else {
				riskSave.setPriorityRankId(risk.getPriorityRankId());
			}
	        
	        
	        if (riskEdit.getReasons() != null) {
	        	riskSave.setReasons(riskEdit.getReasons());
	        }else {
				riskSave.setReasons(risk.getReasons());
			}
	        
	        if (riskEdit.getRiskCategoryId() != null) {
	        	riskSave.setRiskCategoryId(riskCategoryRepository.findOne(riskEdit.getRiskCategoryId()));
	        }else {
				riskSave.setRiskCategoryId(risk.getRiskCategoryId());
			}
	        
	        
	        if (riskEdit.getRiskDescription() != null) {
	        	riskSave.setRiskDescription(riskEdit.getRiskDescription());
	        }else {
	        	riskSave.setRiskDescription(risk.getRiskDescription());
	        }
	        
	        if (riskEdit.getRiskHandlingOptionsId() != null) {
	        	riskSave.setRiskHandlingOptionsId(riskHandlingOptionsRepository.findOne(riskEdit.getRiskHandlingOptionsId()));
	        }else {
				riskSave.setRiskHandlingOptionsId(risk.getRiskHandlingOptionsId());
			}
	        
	        if (riskEdit.getRiskLikelihoodId() != null) {
	        	riskSave.setRiskLikelihoodId(riskLikelihoodRepository.findOne(riskEdit.getRiskLikelihoodId()));
	        }else {
	        	riskSave.setRiskLikelihoodId(risk.getRiskLikelihoodId());
			}
	        
	        
	        if (riskEdit.getRiskImpactId() != null) {
	        	riskSave.setRiskImpactId(riskImpactRepository.findOne(riskEdit.getRiskImpactId()));
	        }else {
	        	riskSave.setRiskImpactId(risk.getRiskImpactId());
			}
	        
	        if (riskEdit.getRiskIndicator() != null) {
	        	riskSave.setRiskIndicator(riskEdit.getRiskIndicator());
	        }else {
	        	riskSave.setRiskIndicator(risk.getRiskIndicator());
			}
	        
	        if (riskEdit.getRiskStatusId() != null) {
	        	riskSave.setRiskStatusId(riskStatusRepository.findOne(riskEdit.getRiskStatusId()));
	        }else {
	        	riskSave.setRiskStatusId(risk.getRiskStatusId());
			}
	        
	        
	        if (riskEdit.getRiskSubCategoryId() != null) {
	        	riskSave.setRiskSubCategoryId(riskSubCategoryRepository.findOne(riskEdit.getRiskSubCategoryId()));
	        }else {
	        	riskSave.setRiskSubCategoryId(risk.getRiskSubCategoryId());
			}
	        
	        riskSave.setRiskId(risk.getRiskId());
	        riskSave.setRiskTitle(risk.getRiskTitle());
	        riskSave.setProjectId(risk.getProjectId());
	        riskSave.setRegisteredDate(risk.getRegisteredDate());
	      
	        riskSave.setSolution(risk.getSolution());
	        riskSave.setIsDeleted(risk.getIsDeleted());
	        if (riskEdit.getOwner() != 0) {
		    	riskSave.setOwner(riskEdit.getOwner());
		    }else {
		    	riskSave.setOwner(risk.getOwner());
			};
			if (riskEdit.getRisk_color() != 0) {
		    	riskSave.setRisk_color(riskEdit.getRisk_color());
		    }else {
		    	riskSave.setRisk_color(risk.getRisk_color());
			};
	        User userRegisteredBy =this.getUserByUserName(risk.getRegisteredBy().getUserId());	    	 
	        riskSave.setRegisteredBy(userRegisteredBy!=null?userRegisteredBy:risk.getRegisteredBy());
	        saveHistoryRiskActionUpdate(HistoryDataUtil.UPDATE, riskEdit.getUpdateBy(), riskEdit, risk, riskSave);
	        
	        return new RiskDTO(projectRiskRepository.save(riskSave), riskSubCategoryRepository.findSubCategory(risk.getRiskCategoryId().getRiskCategoryId()));
	}
	
	private void saveHistoryRiskActionUpdate(String action, int updateBy, RiskEditDTO riskEditDTO, Risk riskOld, Risk riskNew) {
		
		RiskHistoryDTO riskHistoryDTOOld = riskOld != null ? new RiskHistoryDTO(riskOld):null;
		
		RiskHistoryDTO riskHistoryDTONew = riskNew != null ? new RiskHistoryDTO(riskNew): new RiskHistoryDTO();
		
		Optional<User> user = Optional.ofNullable(this.getUserByUserName(updateBy));
		
		List<HistoryData> historyDatas = HistoryDataUtil.getChangeHistorys(Risk.class.getSimpleName(),
				Long.valueOf(riskHistoryDTONew.getRiskId()),
				user != null ? riskEditDTO.getUpdateBy(): null,
				user != null ? user.get().getUserName() : null, action, riskHistoryDTOOld, riskHistoryDTONew);
		if (!historyDatas.isEmpty()) {
			historyDataRepository.save(historyDatas);
		}
	}

	@Override
	public void deleteRisk(int riskId) {
		Optional<Risk> risk = Optional.ofNullable(projectRiskRepository.findOne(riskId));
		if (risk.isPresent()){
			risk.get().setIsDeleted(true);
			projectRiskRepository.save(risk.get());
		}		
	}


	/**
	 *
	 * get user name dashboard by id of readmine
	 * @author: LXLinh
	 */
	private User getUserByUserName(int id) {
		Optional<User> user=Optional.ofNullable(userRepository.getUserByUserId(id));
		return user.isPresent()? user.get():null;
	}

}
