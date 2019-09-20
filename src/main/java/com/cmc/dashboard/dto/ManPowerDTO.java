package com.cmc.dashboard.dto;

public class ManPowerDTO {
	private String UserName;
	private int NumberDayOff;
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getNumberDayOff() {
		return NumberDayOff;
	}
	public void setNumberDayOff(int numberDayOff) {
		NumberDayOff = numberDayOff;
	}
	public ManPowerDTO(String userName, int numberDayOff) {
		super();
		UserName = userName;
		NumberDayOff = numberDayOff;
	}
	public ManPowerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
