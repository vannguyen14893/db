package com.cmc.dashboard.dto;

public class UserManPowerDTO {
	private int userId;
	private String deliveryUnit;
	private float manPower;
	private long workingDay;

	public UserManPowerDTO(int userId, float manPower, long workingDay) {
		super();
		this.userId = userId;
		this.manPower = manPower;
		this.workingDay = workingDay;
	}

	public UserManPowerDTO(int userId, float manPower) {
		super();
		this.userId = userId;
		this.manPower = manPower;
	}

	public UserManPowerDTO() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getManPower() {
		return manPower;
	}

	public void setManPower(float manPower) {
		this.manPower = manPower;
	}

	public long getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(long workingDay) {
		this.workingDay = workingDay;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	@Override
	public String toString() {
		return "UserManPowerDTO [userId=" + userId + ", deliveryUnit=" + deliveryUnit + ", manPower=" + manPower
				+ ", workingDay=" + workingDay + "]";
	}

}
