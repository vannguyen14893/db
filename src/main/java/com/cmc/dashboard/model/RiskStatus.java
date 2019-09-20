/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
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
@Table(name = "risk_status")
public class RiskStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_Status_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_status_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_Status_Gen")
	@Column(name = "risk_status_id", nullable = false)
	private Integer riskStatusId;

	@Column(name = "risk_status_desciption", nullable = false, length = 50)
	private String riskStatusDesciption;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "riskStatusId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskStatus() {
	}

	public RiskStatus(Integer riskStatusId) {
		this.riskStatusId = riskStatusId;
	}

	public RiskStatus(Integer riskStatusId, String riskStatusDesciption) {
		this.riskStatusId = riskStatusId;
		this.riskStatusDesciption = riskStatusDesciption;
	}

	public Integer getRiskStatusId() {
		return riskStatusId;
	}

	public void setRiskStatusId(Integer riskStatusId) {
		this.riskStatusId = riskStatusId;
	}

	public String getRiskStatusDesciption() {
		return riskStatusDesciption;
	}

	public void setRiskStatusDesciption(String riskStatusDesciption) {
		this.riskStatusDesciption = riskStatusDesciption;
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
		return "com.cmc.dashboard.model.RiskStatus[ riskStatusId=" + riskStatusId + " ]";
	}

}
