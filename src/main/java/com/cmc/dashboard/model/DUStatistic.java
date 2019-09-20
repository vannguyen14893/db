package com.cmc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "du_statistic")
public class DUStatistic implements Serializable {
	private static final long serialVersionUID = 1180166962575246146L;

	@TableGenerator(name = "DU_Statistic_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "du_statistic_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "DU_Statistic_Gen")
	@Column(name = "du_statistic_id")
	private int duStatisticId;

	@Column(name = "month")
	private String month;
	
	@Column(name = "group_id")
	private int groupId;

	@Column(name = "statistic_details", columnDefinition = "TEXT")
	private String statisticDetails;
	
	public String getStatisticDetails() {
		return statisticDetails;
	}

	public void setStatisticDetails(String statisticDetails) {
		this.statisticDetails = statisticDetails;
	}

	public DUStatistic() {
	}

	public int getDuStatisticId() {
		return duStatisticId;
	}

	public void setDuStatisticId(int duStatisticId) {
		this.duStatisticId = duStatisticId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
