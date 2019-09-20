package com.cmc.dashboard.model;

import java.util.Date;

public class ProjectDTOTemp {
	private int id;
    private String name;
    private String projectManager;
    private String projectType;
    private String deliverUnitName;
    private int status;
    private Date start_date;
    private Date end_date;
    private String projectCode;
    private float project_billable;
	public float getProject_billable() {
		return project_billable;
	}
	public void setProject_billable(float project_billable) {
		this.project_billable = project_billable;
	}
	public ProjectDTOTemp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectDTOTemp(int id, String name, String projectManager, String projectType, String deliverUnitName,
			int status, Date start_date, Date end_date, String projectCode, float project_billable) {
		super();
		this.id = id;
		this.name = name;
		this.projectManager = projectManager;
		this.projectType = projectType;
		this.deliverUnitName = deliverUnitName;
		this.status = status;
		this.start_date = start_date;
		this.end_date = end_date;
		this.projectCode = projectCode;
		this.project_billable = project_billable;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getDeliverUnitName() {
		return deliverUnitName;
	}
	public void setDeliverUnitName(String deliverUnitName) {
		this.deliverUnitName = deliverUnitName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
    

}
