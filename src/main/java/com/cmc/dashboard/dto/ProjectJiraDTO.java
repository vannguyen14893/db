package com.cmc.dashboard.dto;

import java.util.List;

public class ProjectJiraDTO {
	private Long projectId;
	private String projectName;
	private String projectCode;
	private List<UserJiraDTO> usersDTOS;
	private List<IssueJiraDTO> issuesDTOS;
	public ProjectJiraDTO() {
		super();
	}
	public ProjectJiraDTO(Long projectId, String projectName, String projectCode, List<UserJiraDTO> usersDTOS,
			List<IssueJiraDTO> issues) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectCode = projectCode;
		this.usersDTOS = usersDTOS;
		this.issuesDTOS = issues;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
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
	public List<UserJiraDTO> getUsersDTOS() {
		return usersDTOS;
	}
	public void setUsersDTOS(List<UserJiraDTO> usersDTOS) {
		this.usersDTOS = usersDTOS;
	}
	public List<IssueJiraDTO> getIssuesDTOS() {
		return issuesDTOS;
	}
	public void setIssuesDTOS(List<IssueJiraDTO> issuesDTOS) {
		this.issuesDTOS = issuesDTOS;
	}

	
	
}
