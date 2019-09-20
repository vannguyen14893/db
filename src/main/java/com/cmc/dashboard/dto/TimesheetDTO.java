package com.cmc.dashboard.dto;

public class TimesheetDTO {
	private String issueNumber;
	private String issueName;
	private String logTimeDate;
	private Float estimateTime;
	private Float spentHours;
	private String status;
	private String projectName;
	
	public TimesheetDTO() {
		super();
	}

	public TimesheetDTO(String projectName,String issueNumeber, String issueName, String logTimeDate, Float estimateTime, Float spentHours,
			String status) {
		super();
		this.issueNumber = issueNumeber;
		this.issueName = issueName;
		this.logTimeDate = logTimeDate;
		this.estimateTime = estimateTime;
		this.spentHours = spentHours;
		this.status = status;
		this.projectName=projectName;
	}

	public String getIssueNumeber() {
		return issueNumber;
	}

	public void setIssueNumeber(String issueNumeber) {
		this.issueNumber = issueNumeber;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getLogTimeDate() {
		return logTimeDate;
	}

	public void setLogTimeDate(String logTimeDate) {
		this.logTimeDate = logTimeDate;
	}

	public Float getEstimateTime() {
		return estimateTime;
	}

	public void setEstimateTime(Float estimateTime) {
		this.estimateTime = estimateTime;
	}

	public Float getSpentHours() {
		return spentHours;
	}

	public void setSpentHours(Float spentHours) {
		this.spentHours = spentHours;
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
