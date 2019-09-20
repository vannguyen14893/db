package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity
@Subselect(value = "")
public class ProjectListQms implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private String name;
	private String projectManager;
	private String projectType;
	private String deliveryUnit;
	private int projectSize;
	private int status;
	private String startDate;
	private String endDate;
	private String projectCode;
		
	public ProjectListQms(int id, String name, String projectManager, String projectType, String deliveryUnit,
			int projectSize, int status, String startDate, String endDate, String projectCode) {
		super();
		this.id = id;
		this.name = name;
		this.projectManager = projectManager;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.projectSize = projectSize;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectCode = projectCode;
	}

	public ProjectListQms() {
		super();
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public int getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(int projectSize) {
		this.projectSize = projectSize;
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

	
	
}
