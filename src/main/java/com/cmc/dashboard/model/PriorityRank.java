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
@Table(name = "priority_rank")
public class PriorityRank implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "Risk_PriorityRank_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "priority_rank_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Risk_PriorityRank_Gen")
	@Column(name = "priority_rank_id", nullable = false)
	private Integer priorityRankId;
	
	@Column(name = "priority_rank_desciption", nullable = false, length = 50)
	private String priorityRankDesciption;
	
	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;
	
	@OneToMany(mappedBy = "priorityRankId")
	@JsonIgnore
	private Collection<Risk> riskCollection;

	public PriorityRank() {
	}

	public PriorityRank(Integer priorityRankId) {
		this.priorityRankId = priorityRankId;
	}

	public PriorityRank(Integer priorityRankId, String priorityRankDesciption) {
		this.priorityRankId = priorityRankId;
		this.priorityRankDesciption = priorityRankDesciption;
	}

	public Integer getPriorityRankId() {
		return priorityRankId;
	}

	public void setPriorityRankId(Integer priorityRankId) {
		this.priorityRankId = priorityRankId;
	}

	public String getPriorityRankDesciption() {
		return priorityRankDesciption;
	}

	public void setPriorityRankDesciption(String priorityRankDesciption) {
		this.priorityRankDesciption = priorityRankDesciption;
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
		return "com.cmc.dashboard.model.PriorityRank[ priorityRankId=" + priorityRankId + " ]";
	}

}
