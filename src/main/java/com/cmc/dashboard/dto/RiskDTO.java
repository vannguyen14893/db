/**
 * dashboard-phase2-backend - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cmc.dashboard.model.PriorityRank;
import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.model.RiskCategory;
import com.cmc.dashboard.model.RiskHandlingOptions;
import com.cmc.dashboard.model.RiskImpact;
import com.cmc.dashboard.model.RiskLikelihood;
import com.cmc.dashboard.model.RiskStatus;
import com.cmc.dashboard.model.RiskSubCategory;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: nvangoc
 * @Date: 19 Apr 2018
 */
public class RiskDTO implements Serializable {
	/**
	 * Description
	 */
	private static final long serialVersionUID = 1L;
	private Integer riskId;
	private Date earliestImpactDate;
	private Date latestImpactDate;
	@Size(max = 1000)
	private String reasons;
	@NotNull
	private Date registeredDate;
	@Size(min = 50, max = 5000)
	@NotNull
	private String riskDescription;
	@Size(max = 5000)
	private String riskIndicator;
	@NotNull
	@Size(max = 100)
	private String riskTitle;
	private String priorityRankDesciption;
	private String fullName;
	private String riskCategoryDesciption;
	private String riskHandlingOptionsDesciption;
	private String riskImpactDescription;
	private String riskLikelihoodDesciption;
	private String riskStatusDesciption;
	private String riskSubCategoryDesciption;
	private int project_id;
	private String projectName;
	private int owner;
	private int risk_color;
	private String ownerName;
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getRisk_color() {
		return risk_color;
	}

	public void setRisk_color(int risk_color) {
		this.risk_color = risk_color;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	@NotNull
	private int projectId;
	@NotNull
	private int registeredBy;
	@NotNull
	private int riskStatusId;
	@NotNull
	private int riskCategoryId;
	@NotNull
	private int riskSubCategoryId;
	private Integer riskImpactId;
	private Integer riskLikelihoodId;
	private Integer priorityRankId;
	private Integer riskHandlingOptionsId;
	private List<RiskSubCategory> listRiskSubCategory;	
	
	public List<RiskSubCategory> getListRiskSubCategory() {
		return listRiskSubCategory;
	}

	public void setListRiskSubCategory(List<RiskSubCategory> listRiskSubCategory) {
		this.listRiskSubCategory = listRiskSubCategory;
	}
	public Integer getRiskId() {
		return riskId;
	}

	public void setRiskId(Integer riskId) {
		this.riskId = riskId;
	}

	public Date getEarliestImpactDate() {
		return earliestImpactDate;
	}

	public void setEarliestImpactDate(Date earliestImpactDate) {
		this.earliestImpactDate = earliestImpactDate;
	}

	public Date getLatestImpactDate() {
		return latestImpactDate;
	}

	public void setLatestImpactDate(Date latestImpactDate) {
		this.latestImpactDate = latestImpactDate;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}

	public String getRiskIndicator() {
		return riskIndicator;
	}

	public void setRiskIndicator(String riskIndicator) {
		this.riskIndicator = riskIndicator;
	}

	public String getRiskTitle() {
		return riskTitle;
	}

	public void setRiskTitle(String riskTitle) {
		this.riskTitle = riskTitle;
	}

	public String getPriorityRankDesciption() {
		return priorityRankDesciption;
	}

	public void setPriorityRankDesciption(String priorityRankDesciption) {
		this.priorityRankDesciption = priorityRankDesciption;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRiskCategoryDesciption() {
		return riskCategoryDesciption;
	}

	public void setRiskCategoryDesciption(String riskCategoryDesciption) {
		this.riskCategoryDesciption = riskCategoryDesciption;
	}

	public String getRiskHandlingOptionsDesciption() {
		return riskHandlingOptionsDesciption;
	}

	public void setRiskHandlingOptionsDesciption(String riskHandlingOptionsDesciption) {
		this.riskHandlingOptionsDesciption = riskHandlingOptionsDesciption;
	}

	public String getRiskImpactDescription() {
		return riskImpactDescription;
	}

	public void setRiskImpactDescription(String riskImpactDescription) {
		this.riskImpactDescription = riskImpactDescription;
	}

	public String getRiskLikelihoodDesciption() {
		return riskLikelihoodDesciption;
	}

	public void setRiskLikelihoodDesciption(String riskLikelihoodDesciption) {
		this.riskLikelihoodDesciption = riskLikelihoodDesciption;
	}

	public String getRiskStatusDesciption() {
		return riskStatusDesciption;
	}

	public void setRiskStatusDesciption(String riskStatusDesciption) {
		this.riskStatusDesciption = riskStatusDesciption;
	}

	public String getRiskSubCategoryDesciption() {
		return riskSubCategoryDesciption;
	}

	public void setRiskSubCategoryDesciption(String riskSubCategoryDesciption) {
		this.riskSubCategoryDesciption = riskSubCategoryDesciption;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(int registeredBy) {
		this.registeredBy = registeredBy;
	}

	public int getRiskStatusId() {
		return riskStatusId;
	}

	public void setRiskStatusId(int riskStatusId) {
		this.riskStatusId = riskStatusId;
	}

	public int getRiskCategoryId() {
		return riskCategoryId;
	}

	public void setRiskCategoryId(int riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
	}

	public int getRiskSubCategoryId() {
		return riskSubCategoryId;
	}

	public void setRiskSubCategoryId(int riskSubCategoryId) {
		this.riskSubCategoryId = riskSubCategoryId;
	}

	public Integer getRiskImpactId() {
		return riskImpactId;
	}

	public void setRiskImpactId(Integer riskImpactId) {
		this.riskImpactId = riskImpactId;
	}

	public Integer getRiskLikelihoodId() {
		return riskLikelihoodId;
	}

	public void setRiskLikelihoodId(Integer riskLikelihoodId) {
		this.riskLikelihoodId = riskLikelihoodId;
	}

	public Integer getPriorityRankId() {
		return priorityRankId;
	}

	public void setPriorityRankId(Integer priorityRankId) {
		this.priorityRankId = priorityRankId;
	}

	public Integer getRiskHandlingOptionsId() {
		return riskHandlingOptionsId;
	}

	public void setRiskHandlingOptionsId(Integer riskHandlingOptionsId) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
	}

	/**
	 * Constructure
	 */
	public RiskDTO(Integer riskId, Date registeredDate, String riskDescription, String riskTitle, int projectId,
			int registeredBy, int riskStatusId, int riskCategoryId, int riskSubCategoryId, String projectName, int owner, int risk_color, String ownerName) {
		super();
		this.riskId = riskId;
		this.registeredDate = registeredDate;
		this.riskDescription = riskDescription;
		this.riskTitle = riskTitle;
		this.projectId = projectId;
		this.registeredBy = registeredBy;
		this.riskStatusId = riskStatusId;
		this.riskCategoryId = riskCategoryId;
		this.riskSubCategoryId = riskSubCategoryId;
		this.projectName = projectName;
		this.owner = owner;
		this.risk_color = risk_color;
		this.ownerName = ownerName;
	}

	/**
	 * Constructure
	 */
	public RiskDTO() {
		super();
	}

	/**
	 * Constructure
	 */
	public RiskDTO(Risk risk) {
		this.riskId = risk.getRiskId();
		if (!MethodUtil.isNull(risk.getEarliestImpactDate())) {
			this.earliestImpactDate = risk.getEarliestImpactDate();
		}
		if (!MethodUtil.isNull(risk.getLatestImpactDate())) {
			this.latestImpactDate = risk.getLatestImpactDate();
		}
		if (!MethodUtil.isNull(risk.getReasons())) {
			this.reasons = risk.getReasons();
		}
		this.registeredDate = risk.getRegisteredDate();
		this.riskDescription = risk.getRiskDescription();
		if (!MethodUtil.isNull(risk.getRiskIndicator())) {
			this.riskIndicator = risk.getRiskIndicator();
		}
		this.riskTitle = risk.getRiskTitle();
		if (!MethodUtil.isNull(risk.getPriorityRankId())) {
			this.priorityRankDesciption = risk.getPriorityRankId().getPriorityRankDesciption();
		}
		this.fullName = risk.getRegisteredBy().getFullName();
		this.riskCategoryDesciption = risk.getRiskCategoryId().getRiskCategoryDesciption();
		if (!MethodUtil.isNull(risk.getRiskHandlingOptionsId())) {
			this.riskHandlingOptionsDesciption = risk.getRiskHandlingOptionsId().getRiskHandlingOptionsDesciption();
		}
		if (!MethodUtil.isNull(risk.getRiskImpactId())) {
			this.riskImpactDescription = risk.getRiskImpactId().getRiskImpactDescription();
			this.riskImpactId = risk.getRiskImpactId().getRiskImpactId();
		}
		if (!MethodUtil.isNull(risk.getRiskLikelihoodId())) {
			this.riskLikelihoodDesciption = risk.getRiskLikelihoodId().getRiskLikelihoodDesciption();
		}
		this.riskStatusDesciption = risk.getRiskStatusId().getRiskStatusDesciption();
		this.riskSubCategoryDesciption = risk.getRiskSubCategoryId().getRiskSubCategoryDesciption();
		if (!MethodUtil.isNull(risk.getOwner())) {
			this.owner = risk.getOwner();
		}
		this.risk_color = risk.getRisk_color();
	}

	public RiskDTO(Risk risk, List<RiskSubCategory> listSub) {
		this.riskId = risk.getRiskId();
		if (risk.getEarliestImpactDate() != null) {
			this.earliestImpactDate = risk.getEarliestImpactDate();
		}
		if (risk.getLatestImpactDate() != null) {
			this.latestImpactDate = risk.getLatestImpactDate();
		}
		if (risk.getReasons() != null) {
			this.reasons = risk.getReasons();
		}
		this.registeredDate = risk.getRegisteredDate();
		this.riskDescription = risk.getRiskDescription();
		if (risk.getRiskIndicator() != null) {
			this.riskIndicator = risk.getRiskIndicator();
		}
		this.riskTitle = risk.getRiskTitle();
		if (risk.getPriorityRankId() != null) {
			this.priorityRankDesciption = risk.getPriorityRankId().getPriorityRankDesciption();
			this.priorityRankId = risk.getPriorityRankId().getPriorityRankId();
		}
		this.fullName = risk.getRegisteredBy().getFullName();
		this.riskCategoryDesciption = risk.getRiskCategoryId().getRiskCategoryDesciption();
		this.riskCategoryId = risk.getRiskCategoryId().getRiskCategoryId();
		if (risk.getRiskHandlingOptionsId() != null) {
			this.riskHandlingOptionsDesciption = risk.getRiskHandlingOptionsId().getRiskHandlingOptionsDesciption();
		    this.riskHandlingOptionsId = risk.getRiskHandlingOptionsId().getRiskHandlingOptionsId();
		}
		if (risk.getRiskImpactId() != null) {
			this.riskImpactDescription = risk.getRiskImpactId().getRiskImpactDescription();
			this.riskImpactId = risk.getRiskImpactId().getRiskImpactId();
		}
		if (risk.getRiskLikelihoodId() != null) {
			this.riskLikelihoodDesciption = risk.getRiskLikelihoodId().getRiskLikelihoodDesciption();
			this.riskLikelihoodId = risk.getRiskLikelihoodId().getRiskLikelihoodId();
		}
		this.riskStatusDesciption = risk.getRiskStatusId().getRiskStatusDesciption();
		this.riskStatusId = risk.getRiskStatusId().getRiskStatusId();
		this.riskSubCategoryDesciption = risk.getRiskSubCategoryId().getRiskSubCategoryDesciption();
		this.riskSubCategoryId =risk.getRiskSubCategoryId().getRiskSubCategoryId();
		this.listRiskSubCategory = listSub;
		this.owner = risk.getOwner();
		this.risk_color = risk.getRisk_color();

	}
	
	public Risk toEntity() {
		Risk risk = new Risk();
		risk.setRiskTitle(this.riskTitle);
		risk.setProjectId(this.projectId);
		risk.setRiskDescription(this.riskDescription);
		risk.setRiskIndicator(this.riskIndicator);
		risk.setRegisteredDate(this.registeredDate);
		risk.setReasons(this.reasons);
		risk.setOwner(this.owner);
		risk.setRisk_color(this.risk_color);
		risk.setEarliestImpactDate(this.earliestImpactDate);
		risk.setLatestImpactDate(this.latestImpactDate);
		risk.setRiskCategoryId(new RiskCategory(this.riskCategoryId));
		risk.setRiskSubCategoryId(new RiskSubCategory(this.riskSubCategoryId));
		risk.setRegisteredBy(new User(this.registeredBy));
		if (!MethodUtil.isNull(this.riskImpactId)) {
			risk.setRiskImpactId(new RiskImpact(this.riskImpactId));
		}
		if (!MethodUtil.isNull(this.riskLikelihoodId)) {
			risk.setRiskLikelihoodId(new RiskLikelihood(this.riskLikelihoodId));
		}
		if (!MethodUtil.isNull(this.priorityRankId)) {
			risk.setPriorityRankId(new PriorityRank(this.priorityRankId));
		}
		if (!MethodUtil.isNull(this.riskHandlingOptionsId)) {
			risk.setRiskHandlingOptionsId(new RiskHandlingOptions(this.riskHandlingOptionsId));
		}
		risk.setRiskStatusId(new RiskStatus(this.riskStatusId));
		return risk;
	}
}
