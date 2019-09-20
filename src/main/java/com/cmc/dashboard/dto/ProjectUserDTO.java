package com.cmc.dashboard.dto;

public class ProjectUserDTO {
	private int userId;
	private String fullname;
	
	public ProjectUserDTO() {
		super();
	}
	public ProjectUserDTO(int userId, String fullname) {
		super();
		this.userId = userId;
		this.fullname = fullname;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
