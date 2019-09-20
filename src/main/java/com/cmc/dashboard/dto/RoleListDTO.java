package com.cmc.dashboard.dto;

import com.cmc.dashboard.model.Role;

public class RoleListDTO {
	private int roleId;
	private String roleName;
	private String description;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RoleListDTO(Role role) {
		this.roleId = role.getRoleId();
		this.roleName = role.getRoleName();
		this.description = role.getDescription();
	}
	public RoleListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
