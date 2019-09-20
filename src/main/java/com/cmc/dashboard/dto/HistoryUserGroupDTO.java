package com.cmc.dashboard.dto;

public class HistoryUserGroupDTO {
private int duId;
private String duName;
private String startDate;
private String endDate;
private int idHistory;
private String updateDate;

public int getIdHistory() {
	return idHistory;
}
public void setIdHistory(int idHistory) {
	this.idHistory = idHistory;
}

public String getUpdateDate() {
	return updateDate;
}
public void setUpdateDate(String updateDate) {
	this.updateDate = updateDate;
}
public int getDuId() {
	return duId;
}
public void setDuId(int duId) {
	this.duId = duId;
}
public String getDuName() {
	return duName;
}
public HistoryUserGroupDTO() {}

public HistoryUserGroupDTO(int duId, String duName, String startDate, String endDate, int idHistory, String updateAt) {
	super();
	this.duId = duId;
	this.duName = duName;
	this.startDate = startDate;
	this.endDate = endDate;
	this.idHistory = idHistory;
	this.updateDate = updateAt;
}
public void setDuName(String duName) {
	this.duName = duName;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}

}
