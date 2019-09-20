package com.cmc.dashboard.dto;

import java.util.Set;

import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.Role;

public class RolePermissionDTO {
	private Role role;
	private Set<Permission> listPermission;
	
	
	public RolePermissionDTO() {
		super();
	}


	public RolePermissionDTO(Role role, Set<Permission> listPermission) {
		super();
		this.role = role;
		this.listPermission = listPermission;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Set<Permission> getListPermission() {
		return listPermission;
	}


	public void setListPermission(Set<Permission> listPermission) {
		this.listPermission = listPermission;
	}



}
