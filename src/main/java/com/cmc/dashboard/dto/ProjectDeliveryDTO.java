package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.cmc.dashboard.model.ProjectDelivery;

/**
 * 
 * @author: LXLinh
 * @Date: Jun 18, 2018
 */
public class ProjectDeliveryDTO implements Serializable{

	
	private static final long serialVersionUID = 2364370431482987682L;

	private Integer projectDeliveryId;
	
	private Integer projectId;
	
	
	@NotEmpty
	@Size(max = 1000)
	private String name;
	
	@NotEmpty
	private Date scheduleDate;
	
	
	private Date realDate;

	
	private Boolean ontime;
	
	@Size(max = 5000)
	private String comment;

	public Integer getProjectDeliveryId() {
		return projectDeliveryId;
	}

	public void setProjectDeliveryId(Integer projectDeliveryId) {
		this.projectDeliveryId = projectDeliveryId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public Boolean isOntime() {
		return ontime;
	}

	public void setOntime(Boolean ontime) {
		this.ontime = ontime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	public ProjectDeliveryDTO() {
		super();
	}
	
	
	
	public ProjectDeliveryDTO(Integer projectDeliveryId, Integer projectId, String name, Date scheduleDate, Date realDate,
			Boolean ontime, String comment) {
		super();
		this.projectDeliveryId = projectDeliveryId;
		this.projectId = projectId;
		this.name = name;
		this.scheduleDate = scheduleDate;
		this.realDate = realDate;
		this.ontime = ontime;
		this.comment = comment;
	}

	public ProjectDeliveryDTO(ProjectDelivery projectDelivery) {
		
		this.projectDeliveryId = projectDelivery.getProjectDeliveryId();
		this.projectId = projectDelivery.getProjectId();
		this.name = projectDelivery.getName();
		this.scheduleDate = projectDelivery.getScheduleDate();
		this.realDate = projectDelivery.getRealDate();
		this.ontime = projectDelivery.isOntime();
		this.comment = projectDelivery.getComment();
	}
	
	public ProjectDelivery convertToModel() {
		
		ProjectDelivery projectDeliveryModel=new ProjectDelivery();
		projectDeliveryModel.setOntime(this.ontime);
		projectDeliveryModel.setProjectId(this.projectId);
		projectDeliveryModel.setProjectDeliveryId(this.projectDeliveryId);
		projectDeliveryModel.setComment(this.comment);
		projectDeliveryModel.setName(this.name);
		projectDeliveryModel.setRealDate(this.realDate);
		projectDeliveryModel.setScheduleDate( this.scheduleDate ); 
		return projectDeliveryModel;
	}

	@Override
	public String toString() {
		return "ProjectDeliveryDTO [projectDeliveryId=" + projectDeliveryId + ", projectId=" + projectId + ", name="
				+ name + ", scheduleDate=" + scheduleDate + ", realDate=" + realDate + ", ontime=" + ontime
				+ ", comment=" + comment + "]";
	}

    
	
	
}
