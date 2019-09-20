package com.cmc.dashboard.dto;

import java.util.Set;

public class UserRoleDTO {
	private int userId;
	private String userName;
	private String groupName;
	private String fullName;
	private Set<RoleListDTO> listRole;

	public UserRoleDTO() {
		super();
	}

	public UserRoleDTO(int userId, String userName, String groupName, String fullName, Set<RoleListDTO> listRole) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.groupName = groupName;
		this.listRole = listRole;
		this.fullName = fullName;
	}

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}


	
	public String getGroupName() {
		return groupName;
	}



	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<RoleListDTO> getListRole() {
		return listRole;
	}

	public void setListRole(Set<RoleListDTO> listRole) {
		this.listRole = listRole;
	}

}