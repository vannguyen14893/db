/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Anh Ngoc
 */
@Entity
@Table(name = "risk")
public class Risk implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "risk_id")
	private Integer riskId;

	@Column(name = "risk_title", nullable = false, length = 100)
	private String riskTitle;

	@Column(name = "project_id", nullable = false)
	private int projectId;

	@Column(name = "risk_description", length = 5000, nullable = false)
	private String riskDescription;

	@Column(name = "risk_indicator", length = 5000)
	private String riskIndicator;

	@Column(name = "registered_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	private Date registeredDate;

	@Column(name = "reasons", length = 1000)
	private String reasons;

	@Column(name = "earliest_impact_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	private Date earliestImpactDate;

	@Column(name = "latest_impact_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	private Date latestImpactDate;

	@JoinColumn(name = "risk_category_id", referencedColumnName = "risk_category_id", nullable = false)
	@ManyToOne(optional = false)
	private RiskCategory riskCategoryId;

	@JoinColumn(name = "risk_sub_category_id", referencedColumnName = "risk_sub_category_id", nullable = false)
	@ManyToOne(optional = false)
	private RiskSubCategory riskSubCategoryId;

	@JoinColumn(name = "registered_by", referencedColumnName = "user_id", nullable = false)
	@ManyToOne(optional = false)
	private User registeredBy;
	
	

	@JoinColumn(name = "risk_impact_id", referencedColumnName = "risk_impact_id")
	@ManyToOne
	private RiskImpact riskImpactId;

	@JoinColumn(name = "risk_likelihood_id", referencedColumnName = "risk_likelihood_id")
	@ManyToOne
	private RiskLikelihood riskLikelihoodId;

	@JoinColumn(name = "priority_rank_id", referencedColumnName = "priority_rank_id")
	@ManyToOne
	private PriorityRank priorityRankId;

	@JoinColumn(name = "risk_handling_options_id", referencedColumnName = "risk_handling_options_id")
	@ManyToOne
	private RiskHandlingOptions riskHandlingOptionsId;

	@JoinColumn(name = "risk_status_id", referencedColumnName = "risk_status_id", nullable = false)
	@ManyToOne(optional = false)
	private RiskStatus riskStatusId;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;
	
	@Column(name = "risk_color", nullable = false, columnDefinition = "tinyint(5)")
	private Integer risk_color;
	
	@Column(name = "owner", nullable = false, columnDefinition = "int(10)")
	private int owner;
	
	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public Integer getRisk_color() {
		return risk_color;
	}

	public void setRisk_color(Integer risk_color) {
		this.risk_color = risk_color;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "riskId")
	@JsonIgnore
	private Collection<Solution> solution;

	public Risk() {
	}

	public Risk(Integer riskId) {
		this.riskId = riskId;
	}

	public Risk(Integer riskId, String riskTitle, int projectId, String riskDescription, Date registeredDate,
			Date earliestImpactDate, Date latestImpactDate, int owner, int risk_color) {
		this.riskId = riskId;
		this.riskTitle = riskTitle;
		this.projectId = projectId;
		this.riskDescription = riskDescription;
		this.registeredDate = registeredDate;
		this.earliestImpactDate = earliestImpactDate;
		this.latestImpactDate = latestImpactDate;
	}

	public Integer getRiskId() {
		return riskId;
	}

	public void setRiskId(Integer riskId) {
		this.riskId = riskId;
	}

	public String getRiskTitle() {
		return riskTitle;
	}

	public void setRiskTitle(String riskTitle) {
		this.riskTitle = riskTitle;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
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

	public RiskCategory getRiskCategoryId() {
		return riskCategoryId;
	}

	public void setRiskCategoryId(RiskCategory riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
	}

	public RiskSubCategory getRiskSubCategoryId() {
		return riskSubCategoryId;
	}

	public void setRiskSubCategoryId(RiskSubCategory riskSubCategoryId) {
		this.riskSubCategoryId = riskSubCategoryId;
	}

	public User getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(User registeredBy) {
		this.registeredBy = registeredBy;
	}

	public RiskImpact getRiskImpactId() {
		return riskImpactId;
	}

	public void setRiskImpactId(RiskImpact riskImpactId) {
		this.riskImpactId = riskImpactId;
	}

	public RiskLikelihood getRiskLikelihoodId() {
		return riskLikelihoodId;
	}

	public void setRiskLikelihoodId(RiskLikelihood riskLikelihoodId) {
		this.riskLikelihoodId = riskLikelihoodId;
	}

	public PriorityRank getPriorityRankId() {
		return priorityRankId;
	}

	public void setPriorityRankId(PriorityRank priorityRankId) {
		this.priorityRankId = priorityRankId;
	}

	public RiskHandlingOptions getRiskHandlingOptionsId() {
		return riskHandlingOptionsId;
	}

	public void setRiskHandlingOptionsId(RiskHandlingOptions riskHandlingOptionsId) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
	}

	public RiskStatus getRiskStatusId() {
		return riskStatusId;
	}

	public void setRiskStatusId(RiskStatus riskStatusId) {
		this.riskStatusId = riskStatusId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	

	public Collection<Solution> getSolution() {
		return solution;
	}

	public void setSolution(Collection<Solution> solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return "com.cmc.dashboard.model.Risk[ riskId=" + riskId + " ]";
	}

}
