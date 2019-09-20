package com.cmc.dashboard.dto;

import java.util.List;

public class AmsUserDTO {
private String fullName;
private String email;
private String avatar;
private String departmentName;
private boolean status;
private String userName;
private String createdDate;
private String updatedDate;
private String startDate;
private String endDate;
private List<HistoryUserGroupDTO> listHistoryUserGroup;

public List<HistoryUserGroupDTO> getListHistoryUserGroup() {
	return listHistoryUserGroup;
}
public void setListHistoryUserGroup(List<HistoryUserGroupDTO> listHistoryUserGroup) {
	this.listHistoryUserGroup = listHistoryUserGroup;
}
public AmsUserDTO(String fullName, String email, String avatar, String departmentName, boolean status, String userName,
		String createdDate, String updatedDate, String startDate, String endDate,
		List<HistoryUserGroupDTO> listHistoryUserGroup) {
	super();
	this.fullName = fullName;
	this.email = email;
	this.avatar = avatar;
	this.departmentName = departmentName;
	this.status = status;
	this.userName = userName;
	this.createdDate = createdDate;
	this.updatedDate = updatedDate;
	this.startDate = startDate;
	this.endDate = endDate;
	this.listHistoryUserGroup = listHistoryUserGroup;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}
public String getUpdatedDate() {
	return updatedDate;
}
public void setUpdatedDate(String updatedDate) {
	this.updatedDate = updatedDate;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public AmsUserDTO() {

}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAvatar() {
	return avatar;
}
public void setAvatar(String avatar) {
	this.avatar = avatar;
}


}
