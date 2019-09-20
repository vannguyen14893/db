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
@Table(name = "risk_category")
public class RiskCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_Category_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_category_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_Category_Gen")
	@Column(name = "risk_category_id", nullable = false)
	private Integer riskCategoryId;

	@Column(name = "risk_category_desciption", nullable = false, length = 50)
	private String riskCategoryDesciption;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "riskCategoryId")
	@JsonIgnore
	private Collection<RiskSubCategory> riskSubCategoryCollection;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "riskCategoryId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskCategory() {
	}

	public RiskCategory(Integer riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
	}

	public RiskCategory(Integer riskCategoryId, String riskCategoryDesciption) {
		this.riskCategoryId = riskCategoryId;
		this.riskCategoryDesciption = riskCategoryDesciption;
	}

	public Integer getRiskCategoryId() {
		return riskCategoryId;
	}

	public void setRiskCategoryId(Integer riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
	}

	public String getRiskCategoryDesciption() {
		return riskCategoryDesciption;
	}

	public void setRiskCategoryDesciption(String riskCategoryDesciption) {
		this.riskCategoryDesciption = riskCategoryDesciption;
	}

	public Collection<RiskSubCategory> getRiskSubCategoryCollection() {
		return riskSubCategoryCollection;
	}

	public void setRiskSubCategoryCollection(Collection<RiskSubCategory> riskSubCategoryCollection) {
		this.riskSubCategoryCollection = riskSubCategoryCollection;
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
		return "com.cmc.dashboard.model.RiskCategory[ riskCategoryId=" + riskCategoryId + " ]";
	}

}
