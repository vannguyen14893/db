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
@Table(name = "risk_likelihood")
public class RiskLikelihood implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_Likelihood_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_likelihood_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_Likelihood_Gen")
	@Column(name = "risk_likelihood_id", nullable = false)
	private Integer riskLikelihoodId;

	@Column(name = "risk_likelihood_desciption", nullable = false, length = 50)
	private String riskLikelihoodDesciption;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "riskLikelihoodId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskLikelihood() {
	}

	public RiskLikelihood(Integer riskLikelihoodId) {
		this.riskLikelihoodId = riskLikelihoodId;
	}

	public RiskLikelihood(Integer riskLikelihoodId, String riskLikelihoodDesciption) {
		this.riskLikelihoodId = riskLikelihoodId;
		this.riskLikelihoodDesciption = riskLikelihoodDesciption;
	}

	public Integer getRiskLikelihoodId() {
		return riskLikelihoodId;
	}

	public void setRiskLikelihoodId(Integer riskLikelihoodId) {
		this.riskLikelihoodId = riskLikelihoodId;
	}

	public String getRiskLikelihoodDesciption() {
		return riskLikelihoodDesciption;
	}

	public void setRiskLikelihoodDesciption(String riskLikelihoodDesciption) {
		this.riskLikelihoodDesciption = riskLikelihoodDesciption;
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
		return "com.cmc.dashboard.model.RiskLikelihood[ riskLikelihoodId=" + riskLikelihoodId + " ]";
	}

}
