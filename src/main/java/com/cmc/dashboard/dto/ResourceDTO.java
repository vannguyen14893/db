package com.cmc.dashboard.dto;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResourceDTO {
	@Id
	private int userPlanId;
	private int userId;
	private int projectId;
	private float effortPerDay;
	private Date fromDate;
	private Date toDate;
	private float manDay;
	private String overloadedMsg;
	private String messageError;
	private String messagefDate;
	private String messagetDate;
	@Transient
	private Date updatedOn;

	public ResourceDTO() {
	}

	public ResourceDTO(Date fromDate, float manDay) {
		super();
		this.fromDate = fromDate;
		this.manDay = manDay;
	}

	public ResourceDTO(int userPlanId, int userId, Date fromDate, Date toDate, float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
	}

	public ResourceDTO(int userPlanId, int userId, int projectId, Date fromDate, Date toDate, float manDay,
			float effortPerDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.projectId = projectId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
		this.effortPerDay = effortPerDay;
	}

	public ResourceDTO(int userPlanId, int userId, Date fromDate, Date toDate, float effortPerDay, float manDay) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.effortPerDay = effortPerDay;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
	}

	public ResourceDTO(int userPlanId, int userId, int projectId, Date fromDate, Date toDate, float manDay,
			float effortPerDay, Date updatedOn) {
		super();
		this.userPlanId = userPlanId;
		this.userId = userId;
		this.projectId = projectId;
		this.effortPerDay = effortPerDay;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.manDay = manDay;
		this.updatedOn = updatedOn;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserPlanId() {
		return userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public float getEffortPerDay() {
		return effortPerDay;
	}

	public void setEffortPerDay(float effortPerDay) {
		this.effortPerDay = effortPerDay;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public float getManDay() {
		return manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public String getOverloadedMsg() {
		return overloadedMsg;
	}

	public void setOverloadedMsg(String overloadedMsg) {
		this.overloadedMsg = overloadedMsg;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public String getMessagefDate() {
		return messagefDate;
	}

	public void setMessagefDate(String messagefDate) {
		this.messagefDate = messagefDate;
	}

	public String getMessagetDate() {
		return messagetDate;
	}

	public void setMessagetDate(String messagetDate) {
		this.messagetDate = messagetDate;
	}
}
