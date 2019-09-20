/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cmc.dashboard.model.ProjectEvaluate;

/**
 * 
 * @author: LXLinh
 * @Date: May 2, 2018
 */
public class ProjectEvaluateDTO {
	
	private int projectEvaluateId;
	@NotNull
	private Integer projectId;
	@NotNull
	private Integer userId;
	@NotNull
	private String userName;
	@NotNull
	private Integer quality;
	@NotNull
//	private Integer cost;
//	@NotNull
	private Integer delivery;
	@NotNull
	private Integer process;
	private Date startDate;
	private Date endDate;
	@Size(max = 500)
	private String subject;
	@Size(max = 1000)
	private String comment;

	public ProjectEvaluateDTO() {
		super();
	}
	public ProjectEvaluateDTO(ProjectEvaluate projectEvaluate) {
		this.userName="";
        this.projectEvaluateId=projectEvaluate.getId();
		this.projectId=projectEvaluate.getProjectId();
		this.comment=projectEvaluate.getComment();
		this.userName=projectEvaluate.getUserName();
		this.startDate=projectEvaluate.getStartDate();
		this.endDate=projectEvaluate.getEndDate();
		this.subject=projectEvaluate.getSubject();
		this.quality=projectEvaluate.getQualityId();
//		this.cost=projectEvaluate.getCostId();
		this.delivery=projectEvaluate.getDeliveryId();
		this.process=projectEvaluate.getProcessId();
	}

	public int getProjectEvaluateId() {
		return projectEvaluateId;
	}

	public void setProjectEvaluateId(int projectEvaluateId) {
		this.projectEvaluateId = projectEvaluateId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

//	public Integer getCost() {
//		return cost;
//	}
//
//	public void setCost(Integer cost) {
//		this.cost = cost;
//	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		this.startDate = date;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public ProjectEvaluate toEntity() {
		ProjectEvaluate projectEvaluate=new ProjectEvaluate();
		projectEvaluate.setId(this.projectEvaluateId);
		projectEvaluate.setProjectId(this.projectId);
		projectEvaluate.setUserName(this.userName);
		projectEvaluate.setStartDate(this.startDate);
		projectEvaluate.setEndDate(this.endDate);
		projectEvaluate.setComment(this.comment);
		projectEvaluate.setSubject(this.subject);
		projectEvaluate.setQualityId(this.quality);
		projectEvaluate.setProcessId(this.process);
		projectEvaluate.setDeliveryId(this.delivery);
//		projectEvaluate.setCostId(this.cost);
		return projectEvaluate;
	}
	@Override
	public String toString() {
		return "ProjectEvaluateDTO [projectEvaluateId=" + projectEvaluateId + ", projectId=" + projectId + ", userId="
				+ userId + ", userName=" + userName + ", quality=" + quality + ", delivery="
				+ delivery + ", process=" + process + ", startDate=" + startDate + ", endDate=" + endDate + ", subject="
				+ subject + ", comment=" + comment + "]";
	}
	
}
