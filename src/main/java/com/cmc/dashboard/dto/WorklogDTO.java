package com.cmc.dashboard.dto;

public class WorklogDTO {
	private Long worklogId;
	private String created;
	private String updated;
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	private float worklogTime;
	private String userName;
	public WorklogDTO() {
		super();
	}
	public Long getWorklogId() {
		return worklogId;
	}
	public void setWorklogId(Long worklogId) {
		this.worklogId = worklogId;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public float getWorklogTime() {
		return worklogTime;
	}
	public void setWorklogTime(float worklogTime) {
		this.worklogTime = worklogTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public WorklogDTO(Long worklogId, String created, String updated, float worklogTime, String userName) {
		super();
		this.worklogId = worklogId;
		this.created = created;
		this.updated = updated;
		this.worklogTime = worklogTime;
		this.userName = userName;
	}




}