package com.cmc.dashboard.dto;

public class DUOverviewStatisticDTO {
	private int duStatisticId;
	private String month;
	private int groupId;
	private String statisticDetails;
	
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
	public String getStatisticDetails() {
		return statisticDetails;
	}
	public void setStatisticDetails(String statisticDetails) {
		this.statisticDetails = statisticDetails;
	}
	
	
}
