package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.List;

import com.cmc.dashboard.model.ProjectEvaluate;
import com.cmc.dashboard.model.ProjectEvaluateLevel;
/**
 * 
 * @author: LXLinh
 * @Date: May 6, 2018
 */
public class ProjectEvaluateWeekDTO {

	private Date startDate;
	private Date endDate;
	private String subject;
	private List<ProjectEvaluate> projectEvaluates;
	private List<UserInfoDTO> userInfoDTOs;
    private ProjectEvaluateLevel quality;
    private ProjectEvaluateLevel cost;
    private ProjectEvaluateLevel delivery;
    private ProjectEvaluateLevel  process;
    private String comment;
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public List<ProjectEvaluate> getProjectEvaluates() {
		return projectEvaluates;
	}

	public void setProjectEvaluates(List<ProjectEvaluate> projectEvaluates) {
		this.projectEvaluates = projectEvaluates;
	}

	public List<UserInfoDTO> getUserInfoDTOs() {
		return userInfoDTOs;
	}

	public void setUserInfoDTOs(List<UserInfoDTO> userInfoDTOs) {
		this.userInfoDTOs = userInfoDTOs;
	}

	public ProjectEvaluateLevel getQuality() {
		return quality;
	}

	public void setQuality(ProjectEvaluateLevel quality) {
		this.quality = quality;
	}

	public ProjectEvaluateLevel getCost() {
		return cost;
	}

	public void setCost(ProjectEvaluateLevel cost) {
		this.cost = cost;
	}

	public ProjectEvaluateLevel getDelivery() {
		return delivery;
	}

	public void setDelivery(ProjectEvaluateLevel delivery) {
		this.delivery = delivery;
	}

	public ProjectEvaluateLevel getProcess() {
		return process;
	}

	public void setProcess(ProjectEvaluateLevel process) {
		this.process = process;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	

}
