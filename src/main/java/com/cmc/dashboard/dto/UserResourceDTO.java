package com.cmc.dashboard.dto;

public class UserResourceDTO {
	private float manDay;

	public float getManDay() {
		return manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public UserResourceDTO() {
		super();
	}

	public UserResourceDTO(float manDay) {
		super();
		this.manDay = manDay;
	}

}
