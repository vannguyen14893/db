package com.cmc.dashboard.dto;

import java.text.SimpleDateFormat;

import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.util.Constants;

public class RiskHistoryDTO {
	private int riskId;
	private String registeredBy;
	private String registeredDate;
	private String riskStatus;
	private String riskPriority;
	private String riskCategory;
	private String riskSubCategory;
	private String riskImpact;
	private String riskLikelihood;
	private String handlingOption;
	private String earliestImpactDate;
	private String latestImpactDate;
	private String riskDescription;
	private String riskIndicator;
	private String reasons;

	public RiskHistoryDTO(Risk risk) {
		this.riskId = risk.getRiskId();
		this.registeredBy = risk.getRegisteredBy()!=null?risk.getRegisteredBy().getUserName():"";
		this.registeredDate = risk.getRegisteredDate()!=null?risk.getRegisteredDate().toString():"";
		this.riskStatus = risk.getRiskStatusId()!=null?risk.getRiskStatusId().getRiskStatusDesciption():"";
		this.riskPriority = risk.getPriorityRankId()!=null?risk.getPriorityRankId().getPriorityRankDesciption():"";
		this.riskCategory = risk.getRiskCategoryId()!=null?risk.getRiskCategoryId().getRiskCategoryDesciption():"";
		this.riskSubCategory = risk.getRiskSubCategoryId()!=null?risk.getRiskSubCategoryId().getRiskSubCategoryDesciption():"";
		this.riskImpact = risk.getRiskImpactId()!=null?risk.getRiskImpactId().getRiskImpactDescription():"";
		this.riskLikelihood = risk.getRiskLikelihoodId()!=null?risk.getRiskLikelihoodId().getRiskLikelihoodDesciption():"";
		this.handlingOption = risk.getRiskHandlingOptionsId()!=null?risk.getRiskHandlingOptionsId().getRiskHandlingOptionsDesciption():"";
		this.earliestImpactDate = risk.getEarliestImpactDate()!=null?new SimpleDateFormat(Constants.DATE_FORMAT_PARAMS).format(risk.getEarliestImpactDate()):"";
		this.latestImpactDate = risk.getLatestImpactDate()!=null? new SimpleDateFormat(Constants.DATE_FORMAT_PARAMS).format(risk.getLatestImpactDate()):"";
		
		
		
		this.riskDescription = risk.getRiskDescription()!=null?risk.getRiskDescription():"";
		this.riskIndicator = risk.getRiskIndicator()!=null?risk.getRiskIndicator():"";
		this.reasons = risk.getReasons()!=null?risk.getReasons():"";
	}

	public int getRiskId() {
		return riskId;
	}

	public void setRiskId(int riskId) {
		this.riskId = riskId;
	}

	public String getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(String registeredBy) {
		this.registeredBy = registeredBy;
	}

	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getRiskPriority() {
		return riskPriority;
	}

	public void setRiskPriority(String riskPriority) {
		this.riskPriority = riskPriority;
	}

	public String getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getRiskCategory() {
		return riskCategory;
	}

	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}

	public String getRiskSubCategory() {
		return riskSubCategory;
	}

	public void setRiskSubCategory(String riskSubCategory) {
		this.riskSubCategory = riskSubCategory;
	}

	public String getRiskImpact() {
		return riskImpact;
	}

	public void setRiskImpact(String riskImpact) {
		this.riskImpact = riskImpact;
	}

	public String getRiskLikelihood() {
		return riskLikelihood;
	}

	public void setRiskLikelihood(String riskLikelihood) {
		this.riskLikelihood = riskLikelihood;
	}

	public String getHandlingOption() {
		return handlingOption;
	}

	public void setHandlingOption(String handlingOption) {
		this.handlingOption = handlingOption;
	}
	
	

	public String getEarliestImpactDate() {
		return earliestImpactDate;
	}

	public void setEarliestImpactDate(String earliestImpactDate) {
		this.earliestImpactDate = earliestImpactDate;
	}

	public String getLatestImpactDate() {
		return latestImpactDate;
	}

	public void setLatestImpactDate(String latestImpactDate) {
		this.latestImpactDate = latestImpactDate;
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

	public RiskHistoryDTO() {
		super();
	}

}
