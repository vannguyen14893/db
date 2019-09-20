/**
 * dashboard-phase2-backend- - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.BugDTO;
import com.cmc.dashboard.dto.CssTimeDto;
import com.cmc.dashboard.dto.KpiDTO;
import com.cmc.dashboard.dto.ProjectKpiDTO;
import com.cmc.dashboard.dto.ProjectKpiDetailDTO;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectDelivery;
import com.cmc.dashboard.model.ProjectPcvRate;
import com.cmc.dashboard.qms.repository.IssuesRepository;
import com.cmc.dashboard.qms.repository.TimeEntriesRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.ProjectDeliveryRepository;
import com.cmc.dashboard.repository.ProjectPcvRateRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectTaskRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.WorkLogRepository;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author: GiangTM
 * @Date: Feb 23, 2018
 */
@Service
public class ProjectKpiServiceImpl implements ProjectKpiService {
	@Autowired
	TimeEntriesRepository timeEntriesRepository;

	@Autowired
	IssuesRepository issuesRepository;

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	@Autowired
	ProjectDeliveryRepository projectDeliveryRepository;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	ProjectPcvRateRepository projectPcvRateRepository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	WorkLogRepository workLogRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectKpiService#getProjectKpi(int)
	 */
	@Override
	public List<ProjectKpiDTO> getProjectKpiByProjectId(int projectId) {
		Project project=projectRepository.findById(projectId);
		List<ProjectKpiDTO> projectKpiDTOs = new ArrayList<>(10);
		// 0. get actualEffort
		Float totalLogWorked = this.getTotalLogWorkedByProjectId(projectId);
		if (totalLogWorked == null || totalLogWorked == 0)
			totalLogWorked = 0f;
		Float actualEffort = totalLogWorked / 3600;
		// 1. add bug_rate
		try {
		List<BugDTO> bugDTOs = this.getIssueTypeByProjectId(projectId, CustomValueUtil.BUG);
		KpiDTO dto = this.getIssueTypeRate(actualEffort, bugDTOs);
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.BUG_RATE_CODE, CustomValueUtil.BUG_RATE,
				CustomValueUtil.UNIT_WDF_MAN_DAY,dto.getValue(), bugDTOs,project.getBugRate(),dto.getNumerator(),dto.getDenominator()));
		// 2. add leakage_rate
		List<BugDTO> leakageDTOs = this.getIssueTypeByProjectId(projectId, CustomValueUtil.LEAKAGE);
		dto= this.getIssueTypeRate(actualEffort, leakageDTOs);
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.LEAKAGE_RATE_CODE, CustomValueUtil.LEAKAGE_RATE,
				CustomValueUtil.UNIT_WDF_MAN_DAY,dto.getValue(), leakageDTOs,project.getLeakageRate(),dto.getNumerator(),dto.getDenominator()));
		// 3. add comment_rate
//		List<BugDTO> commentDTOs = this.getCommentsByProjectId(projectId);
//		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.COMMENT_RATE_CODE, CustomValueUtil.COMMENT_RATE, CustomValueUtil.UNIT_NO_OF_COMMENTS_MAN_DAY,
//				this.getCommentRate(actualEffort, commentDTOs), commentDTOs));
		// 4. add rework_rate
//		ProjectKpiDetailDTO reworkEffortDetail = new ProjectKpiDetailDTO(
//				this.getReworkEffortByProjectId(projectId).toString(), CustomValueUtil.UNIT_HOUR);
//		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.REWORK_RATE_CODE, CustomValueUtil.REWORK_RATE, CustomValueUtil.UNIT_PERCENT,
//				this.getReworkRate(actualEffort, Float.parseFloat(reworkEffortDetail.getValue())), reworkEffortDetail));
		// 5. add effort deviation
		
		ProjectKpiDetailDTO effortDeviationDetail = new ProjectKpiDetailDTO(
				this.getEstimatedTotalByProjectId(projectId).toString(), CustomValueUtil.UNIT_HOUR);
		dto=this.getEffortDeviation(actualEffort, Float.parseFloat(effortDeviationDetail.getValue()));
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.EFFORT_DEVIATION_CODE, CustomValueUtil.EFFORT_DEVIATION,
				CustomValueUtil.UNIT_PERCENT,
				dto.getValue(),
				effortDeviationDetail, project.getEffortDeviation(),dto.getNumerator(),dto.getDenominator()));
		// 6. add productivity
		int lineCode =this.getLineOfCodeByProjectId(projectId);
		int sumWorkLog=workLogRepository.countWorkLogProject(projectId);
		String result=null;
		if(sumWorkLog==0) {
			result=null;
			dto= new KpiDTO((float) lineCode, 0f, null);
		}
		else {
			float productivity = (float) Math.round((float)lineCode/sumWorkLog * 100) / 100;
			dto= new KpiDTO((float) lineCode,(float) sumWorkLog, productivity);
			result=String.valueOf(productivity);
		}
		ProjectKpiDetailDTO productivityDetail = new ProjectKpiDetailDTO(
				result, CustomValueUtil.UNIT_LINE_OF_CODE);

		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.PRODUCTIVITY_CODE, CustomValueUtil.PRODUCTIVITY,
				CustomValueUtil.UNIT_PRODUCTIVITY,
				productivityDetail.getValue()!=null? Float.parseFloat(productivityDetail.getValue()):null,
				productivityDetail,project.getProductivity(),dto.getNumerator(),dto.getDenominator()));

		// 7. add billable_rate
		Float totalManDay = userPlanRepository.getManDayByProjectId(projectId);
		ProjectKpiDetailDTO billableEffortDetail = new ProjectKpiDetailDTO(
				this.getBillableEffortByProjectId(projectId).toString(), CustomValueUtil.UNIT_MAN_DAY);
		dto=this.getBillableRate(totalManDay, Float.parseFloat(billableEffortDetail.getValue()));
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.BILLABLE_RATE_CODE, CustomValueUtil.BILLABLE_RATE,
				CustomValueUtil.UNIT_PERCENT
				,dto.getValue(),
				billableEffortDetail,project.getBillableRate(),dto.getNumerator(),dto.getDenominator()));
		// 8. add delivery timeliness
		List<ProjectKpiDetailDTO> deliverySummary = this.getDeliverySummaryByProjectId(projectId);
		dto = this.getDeliveryTimeliness(deliverySummary);
		projectKpiDTOs
				.add(new ProjectKpiDTO(CustomValueUtil.DELIVERY_TIMELINESS_CODE, CustomValueUtil.DELIVERY_TIMELINESS,
						CustomValueUtil.UNIT_PERCENT, dto.getValue(), deliverySummary,project.getTimeliness(),dto.getNumerator(),dto.getDenominator()));
		// 9. add css
		List<CssTimeDto> projectCsss = this.getCssByProjectId(projectId);
		dto =this.getCssAvg(projectCsss);
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.CSS_CODE, CustomValueUtil.CSS, CustomValueUtil.UNIT_POINT,
				dto.getValue(), projectCsss,project.getCss(),dto.getNumerator(),dto.getDenominator()));
		// 10. add pcv rate
		List<ProjectPcvRate> projectPcvRates = this.getProjectPcvRatesByProjectId(projectId);
		projectKpiDTOs.add(new ProjectKpiDTO(CustomValueUtil.PCV_RATE_CODE, CustomValueUtil.PCV_RATE,
				CustomValueUtil.UNIT_PERCENT, this.getPcvRateMaxEndDateByProjectId(projectId), projectPcvRates,project.getPcvRate(),null,null));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return projectKpiDTOs;
	}

	/**
	 * Get ActuallEffort By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 */
	private Float getTotalLogWorkedByProjectId(int projectId) {
		return projectTaskRepository.getTotalLogWorkedByProjectId(projectId);
	}

	/**
	 * Get BugRate
	 * 
	 * @param Float actualEffort
	 * @param       List<BugDTO> bugDTOs
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getBugRate(Float actualEffort, List<BugDTO> bugDTOs) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		int weightedBugTotal = 0;
		for (BugDTO bugDTO : bugDTOs) {
			switch (bugDTO.getBugSeverity()) {
			case CustomValueUtil.COSMETIC:
				weightedBugTotal += bugDTO.getBugTotal() * CustomValueUtil.WEIGHTED_ONE;
				break;
			case CustomValueUtil.MEDIUM:
				weightedBugTotal += bugDTO.getBugTotal() * CustomValueUtil.WEIGHTED_TWO;
				break;
			case CustomValueUtil.SERIOUS:
				weightedBugTotal += bugDTO.getBugTotal() * CustomValueUtil.WEIGHTED_THREE;
				break;
			case CustomValueUtil.FATAL:
				weightedBugTotal += bugDTO.getBugTotal() * CustomValueUtil.WEIGHTED_FOUR;
				break;
			default:
				break;
			}
		}
		return weightedBugTotal / (actualEffort / CustomValueUtil.WORKING_DAY);
	}

	/**
	 * Get BugRate
	 * 
	 * @param Float actualEffort
	 * @param       List<BugDTO> bugDTOs
	 * @return Float
	 */
	private Float getBugsRate(Float actualEffort, int projectId) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		Float totalBugs = projectTaskRepository.getTotalTaskByTypeAndProjectId(projectId, CustomValueUtil.BUG);
		return totalBugs / actualEffort;
	}

	/**
	 * Get Bugs By ProjectId
	 * 
	 * @param        int projectId
	 * @param String issueType
	 * @return List<BugDTO>
	 */
	private List<BugDTO> getIssueTypeByProjectId(int projectId, String issueType) {
		List<BugDTO> bugDTOs = new ArrayList<>();
		List<Object> objects = projectTaskRepository.getTaskPriorityByProjectId(projectId, issueType);
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			if (null != objs[0] && null != objs[1]) {
				BugDTO bugDTO = new BugDTO(objs[0].toString(), Integer.parseInt(objs[1].toString()));
				bugDTOs.add(bugDTO);
			}
		}
		return bugDTOs;
	}

	/**
	 * Get Issue type rate By ProjectId
	 * 
	 * @param        int projectId
	 * @param String issueType
	 * @return List<BugDTO>
	 */
	private KpiDTO getIssueTypeRate(Float actualEffort, List<BugDTO> issueDTOs) {
		int weightedTotal = 0;
		for (BugDTO issueDTO : issueDTOs) {
			weightedTotal += issueDTO.getBugTotal();
		}
		if (null == actualEffort || 0 == actualEffort)
		   	return new KpiDTO((float) weightedTotal, actualEffort, null);
		return new KpiDTO((float) weightedTotal, actualEffort, weightedTotal / actualEffort);
	}

	/**
	 * Get CommentRate
	 * 
	 * @param Float actualEffort
	 * @param       List<BugDTO> ommentTotal
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getCommentRate(Float actualEffort, List<BugDTO> commentDTOs) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		int commentTotal = 0;
		for (BugDTO commentDTO : commentDTOs) {
			commentTotal += commentDTO.getBugTotal();
		}
		return commentTotal / (actualEffort / CustomValueUtil.WORKING_DAY);
	}

	/**
	 * Get Comments By ProjectId
	 * 
	 * @param int projectId
	 * @return List<BugDTO>
	 * @author: GiangTM
	 */
	private List<BugDTO> getCommentsByProjectId(int projectId) {
		List<BugDTO> bugDTOs = new ArrayList<>();
		List<Object> objects = issuesRepository.getCommentsByProjectId(projectId, CustomValueUtil.PREFIX_REVIEW,
				CustomValueUtil.BUG_TRACKER_ID);
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			if (null != objs[0] && null != objs[1]) {
				BugDTO bugDTO = new BugDTO(objs[0].toString(), Integer.parseInt(objs[1].toString()));
				bugDTOs.add(bugDTO);
			}
		}
		return bugDTOs;
	}

	/**
	 * Get LeakageRate
	 * 
	 * @param Float actualEffort
	 * @param       List<BugDTO> leakageDTOs
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getLeakageRate(Float actualEffort, List<BugDTO> leakageDTOs) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		int weightedLeakageTotal = 0;
		for (BugDTO leakageDTO : leakageDTOs) {
			switch (leakageDTO.getBugSeverity()) {
			case CustomValueUtil.COSMETIC:
				weightedLeakageTotal += leakageDTO.getBugTotal() * CustomValueUtil.WEIGHTED_ONE;
				break;
			case CustomValueUtil.MEDIUM:
				weightedLeakageTotal += leakageDTO.getBugTotal() * CustomValueUtil.WEIGHTED_TWO;
				break;
			case CustomValueUtil.SERIOUS:
				weightedLeakageTotal += leakageDTO.getBugTotal() * CustomValueUtil.WEIGHTED_THREE;
				break;
			case CustomValueUtil.FATAL:
				weightedLeakageTotal += leakageDTO.getBugTotal() * CustomValueUtil.WEIGHTED_FOUR;
				break;
			default:
				break;
			}
		}
		return weightedLeakageTotal / (actualEffort / CustomValueUtil.WORKING_DAY);
	}

	/**
	 * Get Leakages By ProjectId
	 * 
	 * @param int projectId
	 * @return List<BugDTO>
	 * @author: GiangTM
	 */
	private List<BugDTO> getLeakagesByProjectId(int projectId) {
		List<BugDTO> leakageDTOs = new ArrayList<>();
		List<Object> objects = issuesRepository.getLeakagesByProjectId(projectId, CustomValueUtil.PREFIX_LEAKAGE,
				CustomValueUtil.BUG_TRACKER_ID);
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			if (null != objs[0] && null != objs[1]) {
				BugDTO bugDTO = new BugDTO(objs[0].toString(), Integer.parseInt(objs[1].toString()));
				leakageDTOs.add(bugDTO);
			}
		}
		return leakageDTOs;
	}

	/**
	 * Get ReworkRate
	 * 
	 * @param Float actualEffort
	 * @param Float reworkEffort
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getReworkRate(Float actualEffort, Float reworkEffort) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		return reworkEffort / actualEffort;
	}

	/**
	 * Get ReworkEffort By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getReworkEffortByProjectId(int projectId) {
		Float reworkEffort = timeEntriesRepository.getReworkEffortByProjectId(projectId,
				CustomValueUtil.BUG_TRACKER_ID);
		if (null == reworkEffort)
			return 0f;
		return reworkEffort;
	}

	/**
	 * Get BillableRate
	 * 
	 * @param Float actualEffort
	 * @param Float billableEffort
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getBillableRateOld(Float actualEffort, Float billableEffort) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		return billableEffort / (actualEffort / CustomValueUtil.WORKING_DAY);
	}

	/**
	 * Get BillableRate
	 * 
	 * @param Float totalManDay
	 * @param Float billableEffort
	 * @return Float
	 * @author: GiangTM
	 */
	private KpiDTO getBillableRate(Float totalManDay, Float billableEffort) {
		if (null == totalManDay || 0 == totalManDay)
			return new KpiDTO(billableEffort, totalManDay, null);
		return  new KpiDTO(billableEffort, totalManDay, billableEffort / totalManDay*100);
	}

	/**
	 * Get BillableEffort By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getBillableEffortByProjectId(int projectId) {
		Float billableEffort = projectBillableRepository.getBillableEffortsByProjectId(projectId);
		if (null == billableEffort)
			return 0f;
		return billableEffort;
	}

	/**
	 * Get Productivity
	 * 
	 * @param Float actualEffort
	 * @param Float storyPointTotal
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getProductivity(Float actualEffort, int lineOfCode) {
		if (null == actualEffort || 0 == actualEffort)
			return null;
		return lineOfCode / actualEffort;
	}

	/**
	 * Get StoryPointTotal By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getStoryPointTotalByProjectId(int projectId) {
		Float storyPointTotal = issuesRepository
				.getStoryPointTotalByProjectId(CustomValueUtil.STORY_POINT_CUSTOM_FIELDS_ID, projectId);
		if (null == storyPointTotal)
			return 0f;
		return storyPointTotal;
	}

	private Integer getLineOfCodeByProjectId(int projectId) {
		Integer storyPointTotal = projectRepository.getLineOfCodeByProjectId(projectId);
		if (null == storyPointTotal)
			return 0;
		return storyPointTotal;
	}

	/**
	 * Get DeliveryTimeliness
	 * 
	 * @param List<ProjectKpiDetailDTO>
	 * @return Float
	 * @author: GiangTM
	 */
	private KpiDTO getDeliveryTimeliness(List<ProjectKpiDetailDTO> deliverySummary) {
		if (Integer.parseInt(deliverySummary.get(1).getValue()) == 0)
			return new KpiDTO(Float.parseFloat(deliverySummary.get(0).getValue()),Float.parseFloat(deliverySummary.get(1).getValue()),null);
		return  new KpiDTO(Float.parseFloat(deliverySummary.get(0).getValue()),Float.parseFloat(deliverySummary.get(1).getValue()),
				Float.parseFloat(deliverySummary.get(0).getValue())
				/ Float.parseFloat(deliverySummary.get(1).getValue()));			
	}

	/**
	 * Get DeliverySummary By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private List<ProjectKpiDetailDTO> getDeliverySummaryByProjectId(int projectId) {
		List<ProjectKpiDetailDTO> projectDeliveryDetails = new ArrayList<>();
		Object obj = projectDeliveryRepository.getDeliveryTimelinessByProjectId(projectId);
		ProjectDelivery projectDelivery = this.getRecordLastedProjectDeliveryByProjectId(projectId);

		Object[] objs = (Object[]) obj;
		projectDeliveryDetails.add(new ProjectKpiDetailDTO(objs[0].toString(), CustomValueUtil.UNIT_MILESTONES));
		projectDeliveryDetails.add(new ProjectKpiDetailDTO(objs[1].toString(), CustomValueUtil.UNIT_MILESTONES));
		projectDeliveryDetails.add(
				new ProjectKpiDetailDTO(projectDelivery.isOntime() == null ? "" : projectDelivery.isOntime().toString(),
						CustomValueUtil.UNIT_ONTIME));
		return projectDeliveryDetails;
	}

	/**
	 * Get EffortDeviation
	 * 
	 * @param Float actualEffort
	 * @param Float estimatedTotal
	 * @return Float
	 * @author: GiangTM
	 */
	private KpiDTO getEffortDeviation(Float actualEffort, Float estimatedTotal) {
		if (null == actualEffort || 0 == actualEffort || 0 == estimatedTotal)
			return new KpiDTO(actualEffort, estimatedTotal, null);
		return new KpiDTO(Math.abs(actualEffort - estimatedTotal) ,estimatedTotal,(Math.abs(actualEffort - estimatedTotal) / estimatedTotal) * 100);
	}

	/**
	 * Get EstimatedTotal By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getEstimatedTotalByProjectId(int projectId) {
		Float estimatedTotal = projectTaskRepository.getEstimatedTotalByProjectId(projectId);
		if (null == estimatedTotal)
			return 0f;
		return estimatedTotal / 3600;
	}

	/**
	 * Get PcvRate By ProjectId
	 * 
	 * @param int projectId
	 * @return Float
	 * @author: GiangTM
	 */
	private Float getPcvRateMaxEndDateByProjectId(int projectId) {
		return projectPcvRateRepository.getPcvRateMaxEndDateByProjectId(projectId);
	}

	/**
	 * Get CssAvg
	 * 
	 * @param List<ProjectKpiDetailDTO> projectCsss
	 * @return Float
	 * @author: GiangTM
	 */
	private KpiDTO getCssAvg(List<CssTimeDto> projectCsss) {
		if (projectCsss.isEmpty())
			return new KpiDTO(0f, (float)projectCsss.size(), null);
		float sum = 0;
		for (CssTimeDto projectCss : projectCsss) {
			sum += Float.parseFloat(projectCss.getValues());
		}
		return new KpiDTO(sum, (float)projectCsss.size(), sum / projectCsss.size());
	}

	/**
	 * Get Css By ProjectId
	 * 
	 * @param int projectId
	 * @return List<ProjectKpiDetailDTO>
	 * @author: GiangTM
	 */
	private List<CssTimeDto> getCssByProjectId(int projectId) {
		List<CssTimeDto> projectCsss = new ArrayList<>();
		List<Object> objects = projectCssRepository.getCssByProjectId(projectId);
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			if (null != objs[0] && null != objs[1]) {
				CssTimeDto cssTimeDto = new CssTimeDto(Integer.parseInt(objs[0].toString()), objs[1].toString());
				projectCsss.add(cssTimeDto);
			}
		}
		return projectCsss;
	}

	private List<ProjectPcvRate> getProjectPcvRatesByProjectId(int projectId) {
		return projectPcvRateRepository.getProjectPcvRatesByProjectId(projectId);
	}

	/**
	 * 
	 * @return
	 */
	private ProjectDelivery getRecordLastedProjectDeliveryByProjectId(int projectId) {
		List<ProjectDelivery> projectDeliveries = projectDeliveryRepository.getDeliveryByProjectId(projectId);
		return projectDeliveries.size() > 0 ? projectDeliveries.get(projectDeliveries.size() - 1)
				: new ProjectDelivery();
	}
}
