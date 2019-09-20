package com.cmc.dashboard.dto;

public class ProjectResourceAllocationDTO {
	private int userId;
	private String fullName;
	private String deliveryUnit;
	private String role;
	private ResourceProjectDTO resourceProjectDTO;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public ResourceProjectDTO getProjectDTO() {
		return resourceProjectDTO;
	}

	public void setProjectDTO(ResourceProjectDTO resourceProjectDTO) {
		this.resourceProjectDTO = resourceProjectDTO;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ProjectResourceAllocationDTO() {
		super();
	}

	/**
	 * 
	 * @param userId
	 * @param fullName
	 * @param deliveryUnit
	 * @param projectDTO
	 * @author duyhieu
	 */
	public ProjectResourceAllocationDTO(int userId, String fullName, String deliveryUnit, String role,
			ResourceProjectDTO resourceProjectDTO) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.deliveryUnit = deliveryUnit;
		this.role = role;
		this.resourceProjectDTO = resourceProjectDTO;
	}

}
