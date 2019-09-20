package com.cmc.dashboard.dto;

import java.util.Date;

public class ManpowerTime {
private Date startDate;
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public ManpowerTime() {
	
}
public ManpowerTime(Date startDate, Date endDate, float manPower, int workingDay) {
	super();
	this.startDate = startDate;
	this.endDate = endDate;
	this.manPower = manPower;
	this.workingDay = workingDay;
}
public float getManPower() {
	return manPower;
}
public void setManPower(float manPower) {
	this.manPower = manPower;
}
private Date endDate;
private float manPower;
private int workingDay;
private int projectType;
public ManpowerTime(Date startDate, Date endDate, float manPower, int workingDay, int projectType) {
	super();
	this.startDate = startDate;
	this.endDate = endDate;
	this.manPower = manPower;
	this.workingDay = workingDay;
	this.projectType = projectType;
}
public int getProjectType() {
	return projectType;
}
public void setProjectType(int projectType) {
	this.projectType = projectType;
}
public int getWorkingDay() {
	return workingDay;
}
public void setWorkingDay(int workingDay) {
	this.workingDay = workingDay;
}
}

