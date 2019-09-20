package com.cmc.dashboard.dto;

public class ProjectInfoDTO {
	private Integer projectId;
	private String projectName;
	private String projectCode;
	public Integer getProjectId() {
		return projectId;
	}
	public ProjectInfoDTO(Integer projectId, String projectName, String projectCode) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectCode = projectCode;
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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
