package com.cmc.dashboard.dto;

import java.util.Date;

public class RiskEditDTO {
	
	private Integer riskId;
	private Integer priorityRankId;
	private Integer riskStatusId;
	private Integer riskCategoryId;
	private Integer riskSubCategoryId;
	private Integer riskLikelihoodId;
	private Integer riskImpactId;
	private Integer riskHandlingOptionsId;
	private String riskDescription;
	private String riskIndicator;
	private String reasons;
	private Date earliestImpactDate;
	private Date latestImpactDate;
	private Integer updateBy;
	private int owner;
	private int risk_color;
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getRisk_color() {
		return risk_color;
	}

	public void setRisk_color(int risk_color) {
		this.risk_color = risk_color;
	}

	public RiskEditDTO(Integer riskId, Integer priorityRankId, Integer riskStatusId, Integer riskCategoryId,
			Integer riskSubCategoryId, Integer riskLikelihoodId, Integer riskImpactId, Integer riskHandlingOptionsId,
			String riskDescription, String riskIndicator, String reasons, Date earliestImpactDate,
			Date latestImpactDate,Integer updateBy, int owner, int risk_color) {
		super();
		this.riskId = riskId;
		this.priorityRankId = priorityRankId;
		this.riskStatusId = riskStatusId;
		this.riskCategoryId = riskCategoryId;
		this.riskSubCategoryId = riskSubCategoryId;
		this.riskLikelihoodId = riskLikelihoodId;
		this.riskImpactId = riskImpactId;
		this.riskHandlingOptionsId = riskHandlingOptionsId;
		this.riskDescription = riskDescription;
		this.riskIndicator = riskIndicator;
		this.reasons = reasons;
		this.earliestImpactDate = earliestImpactDate;
		this.latestImpactDate = latestImpactDate;
		this.updateBy = updateBy;
		this.owner = owner;
		this.risk_color = risk_color;
	}	
	
	public RiskEditDTO() {
		super();
	}


	public Integer getRiskId() {
		return riskId;
	}
	public void setRiskId(Integer riskId) {
		this.riskId = riskId;
	}
	public Integer getPriorityRankId() {
		return priorityRankId;
	}
	public void setPriorityRankId(Integer priorityRankId) {
		this.priorityRankId = priorityRankId;
	}
	public Integer getRiskStatusId() {
		return riskStatusId;
	}
	public void setRiskStatusId(Integer riskStatusId) {
		this.riskStatusId = riskStatusId;
	}
	public Integer getRiskCategoryId() {
		return riskCategoryId;
	}
	public void setRiskCategoryId(Integer riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
	}
	public Integer getRiskSubCategoryId() {
		return riskSubCategoryId;
	}
	public void setRiskSubCategoryId(Integer riskSubCategoryId) {
		this.riskSubCategoryId = riskSubCategoryId;
	}
	public Integer getRiskLikelihoodId() {
		return riskLikelihoodId;
	}
	public void setRiskLikelihoodId(Integer riskLikelihoodId) {
		this.riskLikelihoodId = riskLikelihoodId;
	}
	public Integer getRiskImpactId() {
		return riskImpactId;
	}
	public void setRiskImpactId(Integer riskImpactId) {
		this.riskImpactId = riskImpactId;
	}
	public Integer getRiskHandlingOptionsId() {
		return riskHandlingOptionsId;
	}
	public void setRiskHandlingOptionsId(Integer riskHandlingOptionsId) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
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
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
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

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
