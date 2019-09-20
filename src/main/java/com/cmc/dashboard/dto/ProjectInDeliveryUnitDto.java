package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProjectInDeliveryUnitDto implements Serializable{


	/**
	 * TODO Description
	 */
	private static final long serialVersionUID = -7846938383914269134L;
	/**
	 * TODO Description
	 */
	private Integer projectId;
	private String projectName;
	private List<String> member;
	private String projectType;
	private String deliveryUnit;
	private Integer status;
	private String startDate;
	private String endDate;;
	private String projectCode;
	
	
	
	public ProjectInDeliveryUnitDto() {
		super();
	}
	
	
	public ProjectInDeliveryUnitDto(Integer projectId, String projectName,Integer status, String startDate, String endDate, String projectType) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.status=status;
		this.projectType = projectType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
//	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<String> getMember() {
		return member;
	}
	public void setMember(List<String> member) {
		this.member = member;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(timezone = "GMT+7", pattern = "yyyy-MM-dd")
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
