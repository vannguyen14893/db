package com.cmc.dashboard.dto;

public class IssueDTO {
	private String issueName;
	private String startDate;
	private String dueDate;
	private float estimatedHours;
	private String status;
	private String projectName;

	public IssueDTO() {
		super();
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public float getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(float estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	

}
