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
@Table(name = "risk_handling_options")
public class RiskHandlingOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_HandlingOption_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_handling_options_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_HandlingOption_Gen")
	@Column(name = "risk_handling_options_id", nullable = false)
	private Integer riskHandlingOptionsId;

	@Column(name = "risk_handling_options_desciption", nullable = false, length = 50)
	private String riskHandlingOptionsDesciption;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "riskHandlingOptionsId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskHandlingOptions() {
	}

	public RiskHandlingOptions(Integer riskHandlingOptionsId) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
	}

	public RiskHandlingOptions(Integer riskHandlingOptionsId, String riskHandlingOptionsDesciption) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
		this.riskHandlingOptionsDesciption = riskHandlingOptionsDesciption;
	}

	public Integer getRiskHandlingOptionsId() {
		return riskHandlingOptionsId;
	}

	public void setRiskHandlingOptionsId(Integer riskHandlingOptionsId) {
		this.riskHandlingOptionsId = riskHandlingOptionsId;
	}

	public String getRiskHandlingOptionsDesciption() {
		return riskHandlingOptionsDesciption;
	}

	public void setRiskHandlingOptionsDesciption(String riskHandlingOptionsDesciption) {
		this.riskHandlingOptionsDesciption = riskHandlingOptionsDesciption;
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
		return "com.cmc.dashboard.model.RiskHandlingOptions[ riskHandlingOptionsId=" + riskHandlingOptionsId + " ]";
	}

}
