package com.cmc.dashboard.dto;

public class ResourcePlanUtilizationDTO {
	private int userId;
	private float manday;
	
	public ResourcePlanUtilizationDTO() {
		super();
	}
	public ResourcePlanUtilizationDTO(int userId, float manday) {
		super();
		this.userId = userId;
		this.manday = manday;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public float getManday() {
		return manday;
	}
	public void setManday(float manday) {
		this.manday = manday;
	}

}
