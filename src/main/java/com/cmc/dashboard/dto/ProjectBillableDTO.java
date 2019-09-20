package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.Date;

public class ProjectBillableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int projectBillableId;
	private String startDate;
	private String endDate;
	private float billableValue;
	private String issueCode;
	private String groupName;
	private int groupId;

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getProjectBillableId() {
		return projectBillableId;
	}
	public void setProjectBillableId(int projectBillableId) {
		this.projectBillableId = projectBillableId;
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
	public float getBillableValue() {
		return billableValue;
	}
	public void setBillableValue(float billableValue) {
		this.billableValue = billableValue;
	}
	public String getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public ProjectBillableDTO() {
		super();
	}
	
	
}
