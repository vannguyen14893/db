package com.cmc.dashboard.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResourcePlansDTO {
	
	private int id;
	private String projectName;
	private Date startDate;
	private Date dueDate;
	private float planHours;
	
	public ResourcePlansDTO() {
		super();
	}
	
	
	public ResourcePlansDTO(int id, String projectName, Date startDate, Date dueDate, float planHours) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.planHours = planHours;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public float getPlanHours() {
		return planHours;
	}


	public void setPlanHours(float planHours) {
		this.planHours = planHours;
	}

	

}
