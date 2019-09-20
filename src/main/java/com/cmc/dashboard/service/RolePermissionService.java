package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.model.RolePermission;

public interface RolePermissionService {

	/**
	 * Get All RolePermission
	 * @return
	 * @author: ngocd
	 */
	public List<RolePermission> getAllRolePermission();

	/**
	 * Save all RolePermission of List
	 * @param listRolePermission
	 * @return
	 * @author: ngocd
	 */
	public String saveAllRolePermission(List<RolePermission> listRolePermission);

}
