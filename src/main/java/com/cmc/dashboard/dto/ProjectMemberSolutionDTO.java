package com.cmc.dashboard.dto;

public class ProjectMemberSolutionDTO {
	private int userId;
	private String name;

	public ProjectMemberSolutionDTO(int userId, String name) {
		super();
		this.userId = userId;
		this.name = name;
	}

	public ProjectMemberSolutionDTO() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
