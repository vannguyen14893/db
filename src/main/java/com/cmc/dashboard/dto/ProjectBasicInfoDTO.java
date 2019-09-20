package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.Skill;

public class ProjectBasicInfoDTO {
	private int projectId;
	private String projectName;
	private String projectType;
	private String deliveryUnit;
	private int status;
	private List<Skill> skillName;
	private int qualityId;
	private int processId;
	private int deliveryId;
	private String projectCode;
	private String startDate;
	private String endDate;
	private String comment;
	private String pStartDate;
	private String pEndDate;
	
	
	public ProjectBasicInfoDTO() {
		super();
	}

	

	public ProjectBasicInfoDTO(int projectId, String projectName, String projectType, String deliveryUnit, int status,
			List<Skill> skillName, int qualityId, int processId, int deliveryId, String projectCode, String startDate,
			String endDate, String comment, String pStartDate, String pEndDate) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.status = status;
		this.skillName = skillName;
		this.qualityId = qualityId;
		this.processId = processId;
		this.deliveryId = deliveryId;
		this.projectCode = projectCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment;
		this.pStartDate = pStartDate;
		this.pEndDate = pEndDate;
	}



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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Skill> getSkillName() {
		return skillName;
	}

	public void setSkillName(List<Skill> skillName) {
		this.skillName = skillName;
	}

	public int getQualityId() {
		return qualityId;
	}

	public void setQualityId(int qualityId) {
		this.qualityId = qualityId;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getpStartDate() {
		return pStartDate;
	}



	public void setpStartDate(String pStartDate) {
		this.pStartDate = pStartDate;
	}



	public String getpEndDate() {
		return pEndDate;
	}



	public void setpEndDate(String pEndDate) {
		this.pEndDate = pEndDate;
	}
	

}
