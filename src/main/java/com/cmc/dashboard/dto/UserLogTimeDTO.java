package com.cmc.dashboard.dto;

public class UserLogTimeDTO {
	
	private int id;
	private String name;
	private float hours;
	private String spentOn;

	public UserLogTimeDTO() {
		super();
	}
	
	public UserLogTimeDTO(int id, float hours, String spentOn) {
		super();
		this.id = id;
		this.hours = hours;
		this.spentOn = spentOn;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public String getSpentOn() {
		return spentOn;
	}

	public void setSpentOn(String spentOn) {
		this.spentOn = spentOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
