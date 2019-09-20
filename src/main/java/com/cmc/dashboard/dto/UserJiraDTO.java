package com.cmc.dashboard.dto;

public class UserJiraDTO {
	public UserJiraDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Long userId;
	private String userName;
	public Long getUserId() {
		return userId;
	}
	public UserJiraDTO(Long userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
