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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Anh Ngoc
 */
@Entity
@Table(name = "risk_sub_category")
public class RiskSubCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_SubCategory_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "risk_sub_category_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_SubCategory_Gen")
	@Column(name = "risk_sub_category_id", nullable = false)
	private Integer riskSubCategoryId;

	@Column(name = "risk_sub_category_desciption", nullable = false, length = 50)
	private String riskSubCategoryDesciption;

	@JoinColumn(name = "risk_category_id", referencedColumnName = "risk_category_id", nullable = false)
	@ManyToOne(optional = false)
	private RiskCategory riskCategoryId;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "riskSubCategoryId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public RiskSubCategory() {
	}

	public RiskSubCategory(Integer riskSubCategoryId) {
		this.riskSubCategoryId = riskSubCategoryId;
	}

	public RiskSubCategory(Integer riskSubCategoryId, String riskSubCategoryDesciption) {
		this.riskSubCategoryId = riskSubCategoryId;
		this.riskSubCategoryDesciption = riskSubCategoryDesciption;
	}

	public Integer getRiskSubCategoryId() {
		return riskSubCategoryId;
	}

	public void setRiskSubCategoryId(Integer riskSubCategoryId) {
		this.riskSubCategoryId = riskSubCategoryId;
	}

	public String getRiskSubCategoryDesciption() {
		return riskSubCategoryDesciption;
	}

	public void setRiskSubCategoryDesciption(String riskSubCategoryDesciption) {
		this.riskSubCategoryDesciption = riskSubCategoryDesciption;
	}

	public RiskCategory getRiskCategoryId() {
		return riskCategoryId;
	}

	public void setRiskCategoryId(RiskCategory riskCategoryId) {
		this.riskCategoryId = riskCategoryId;
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
		return "com.cmc.dashboard.model.RiskSubCategory[ riskSubCategoryId=" + riskSubCategoryId + " ]";
	}

}
