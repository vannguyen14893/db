/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.util.MessageUtil;

/**
 * @author: ngocd
 * @Date: Apr 3, 2018
 */
public class ProjectCssRestResponse {
	private int projectId;
	private String projectName;
	private String startDate;
	private String endDate;
	private List<ProjectCss> projectCsses;
	private String message;

	public ProjectCssRestResponse(int projectId, String projectName, String startDate, String endDate,
			List<ProjectCss> projectCsses, String message) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectCsses = projectCsses;
		this.message = message;
	}

	public ProjectCssRestResponse(String message) {
		super();
		this.message = message;
	}

	public ProjectCssRestResponse() {
		super();
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

	public List<ProjectCss> getProjectCsses() {
		return projectCsses;
	}

	public void setProjectCsses(List<ProjectCss> projectCsses) {
		this.projectCsses = projectCsses;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static ProjectCssRestResponse error(String message) {
		return new ProjectCssRestResponse(message);
	}
	
	public static ProjectCssRestResponse success() {
		return new ProjectCssRestResponse(MessageUtil.SUCCESS);
	}
}
