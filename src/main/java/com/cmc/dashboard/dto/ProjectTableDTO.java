package com.cmc.dashboard.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectTableDTO {
	private int projectId;
	private String projectName;
	private String  projectManager;
	private String projectType;
	private String deliveryUnit;
	private String projectSize;
	private int status;
	private String startDate;
	private String endDate;;
	private String projectCode;
	private int projectTypeId;
	private int type;
	private float project_billable;
	
	
	public float getProject_billable() {
		return project_billable;
	}
	public void setProject_billable(float project_billable) {
		this.project_billable = project_billable;
	}
	public int getProjectTypeId() {
		return projectTypeId;
	}
	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ProjectTableDTO(Integer projectId, String projectName, String projectManager, String projectType,
			String deliveryUnit, int status, Date startDate, Date endDate, String projectCode,int type, float project_billable) {
		super();
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectManager = projectManager;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.status = status;
		if(startDate!=null)
		this.startDate = df.format(startDate);
		if(endDate!=null)
		this.endDate = df.format(endDate);
		this.projectCode = projectCode;
		this.type=type;
		this.project_billable = project_billable;
	}
//	public ProjectTableDTO(Integer projectId, String projectName, String projectManager, Integer projectTypeId,
//			String deliveryUnit, int status, Date startDate, Date endDate, String projectCode,int type) {
//		super();
//		String pattern = "dd/MM/yyyy";
//		DateFormat df = new SimpleDateFormat(pattern);
//		this.projectId = projectId;
//		this.projectName = projectName;
//		this.projectManager = projectManager;
//		this.projectTypeId = projectTypeId;
//		this.deliveryUnit = deliveryUnit;
//		this.status = status;
//		if(startDate!=null)
//		this.startDate = df.format(startDate);
//		if(endDate!=null)
//		this.endDate = df.format(endDate);
//		this.projectCode = projectCode;
//		this.type=type;
//	}
	public ProjectTableDTO() {}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getDeliveryUnit() {
		return deliveryUnit;
	}
	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}
	public String getProjectSize() {
		return projectSize;
	}
	public void setProjectSize(String projectSize) {
		this.projectSize = projectSize;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
}
