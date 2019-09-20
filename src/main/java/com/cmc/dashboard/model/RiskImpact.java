/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Anh Ngoc
 */
@Entity
@Table(name = "risk_impact")
public class RiskImpact implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_Impact_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_impact_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_Impact_Gen")
	@Column(name = "risk_impact_id", nullable = false)
	private Integer riskImpactId;

	@Column(name = "risk_impact_description", nullable = false, length = 50)
	private String riskImpactDescription;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "riskImpactId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskImpact() {
	}

	public RiskImpact(Integer riskImpactId) {
		this.riskImpactId = riskImpactId;
	}

	public RiskImpact(Integer riskImpactId, String riskImpactDescription) {
		this.riskImpactId = riskImpactId;
		this.riskImpactDescription = riskImpactDescription;
	}

	public Integer getRiskImpactId() {
		return riskImpactId;
	}

	public void setRiskImpactId(Integer riskImpactId) {
		this.riskImpactId = riskImpactId;
	}

	public String getRiskImpactDescription() {
		return riskImpactDescription;
	}

	public void setRiskImpactDescription(String riskImpactDescription) {
		this.riskImpactDescription = riskImpactDescription;
	}

	public Collection<Risk> getRiskCollection() {
		return riskCollection;
	}

	public void setRiskCollection(Collection<Risk> riskCollection) {
		this.riskCollection = riskCollection;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "com.cmc.dashboard.model.RiskImpact[ riskImpactId=" + riskImpactId + " ]";
	}

}
