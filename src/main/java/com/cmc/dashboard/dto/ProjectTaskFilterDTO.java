package com.cmc.dashboard.dto;

public class ProjectTaskFilterDTO {
	private String assignee;
	private String status;
	private String minstartDate;
	private String maxstartDate;
	private String mindueDate;
	private String maxdueDate;
	
	public ProjectTaskFilterDTO() {
		super();
	}
	
	public ProjectTaskFilterDTO(String assignee, String status, String minstartDate, String maxstartDate,
			String mindueDate, String maxdueDate) {
		super();
		this.assignee = assignee;
		this.status = status;
		this.minstartDate = minstartDate;
		this.maxstartDate = maxstartDate;
		this.mindueDate = mindueDate;
		this.maxdueDate = maxdueDate;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMinstartDate() {
		return minstartDate;
	}
	public void setMinstartDate(String minstartDate) {
		this.minstartDate = minstartDate;
	}
	public String getMaxstartDate() {
		return maxstartDate;
	}
	public void setMaxstartDate(String maxstartDate) {
		this.maxstartDate = maxstartDate;
	}
	public String getMindueDate() {
		return mindueDate;
	}
	public void setMindueDate(String mindueDate) {
		this.mindueDate = mindueDate;
	}
	public String getMaxdueDate() {
		return maxdueDate;
	}
	public void setMaxdueDate(String maxdueDate) {
		this.maxdueDate = maxdueDate;
	}
	
	
}
