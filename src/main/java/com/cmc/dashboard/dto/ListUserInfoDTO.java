package com.cmc.dashboard.dto;

public class ListUserInfoDTO {
	private String userId;
	private String userName;
	private int numberDayOff;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getNumberDayOff() {
		return numberDayOff;
	}
	public void setNumberDayOff(int numberDayOff) {
		this.numberDayOff = numberDayOff;
	}
}
