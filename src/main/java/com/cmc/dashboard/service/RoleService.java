package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.RoleListDTO;
import com.cmc.dashboard.dto.RolePermissionDTO;
import com.cmc.dashboard.model.Role;

public interface RoleService {
	public List<Role> getAll();

	public List<RoleListDTO> getListRole();

	public boolean save(Role role);

	public Role updateRole(RoleListDTO role);

	public List<Role> deleteRole(int roleId);

	public Role findByName(String name);

	public Role findById(int id);

	public boolean delete(int id);

	public List<RolePermissionDTO> getAllRolePermission();
	
	public List<Role> listRoleOfUser(String userName);

	public boolean edit(Role role);
}
