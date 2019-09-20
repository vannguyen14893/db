package com.cmc.dashboard.dto;

public class UserDTO {
	private int id;
	private String userName;
	private String fullName;
	private String deliveryUnit;
	private String role;
	private String img;
	private LastActivityDto lastActivityDto;

	public UserDTO() {

	}

	public UserDTO(int id, String fullName, String deliveryUnit, String role) {
		this.id = id;
		this.fullName = fullName;
		this.deliveryUnit = deliveryUnit;
		this.role = role;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LastActivityDto getLastActivityDto() {
		return lastActivityDto;
	}

	public void setLastActivityDto(LastActivityDto lastActivityDto) {
		this.lastActivityDto = lastActivityDto;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
