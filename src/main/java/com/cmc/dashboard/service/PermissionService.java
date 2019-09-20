package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.Role;

public interface PermissionService {

	/**
	 * TODO description
	 * 
	 * @return
	 * @throws SQLException
	 *             List<Role>
	 * @author: ngocd
	 */
	public List<Role> getAllRole();

	/**
	 * TODO description
	 * 
	 * @return
	 * @throws SQLException
	 *             List<AuthorizationUtilzationDTO>
	 * @author: ngocd
	 */
	public List<AuthorizationUtilzationDTO> getRolePermission();

	List<String> getAllPermission();
	
	public Permission findByName(String name);
	
	public List<Permission> listPermission();
	
	public boolean createPermisison(String permission_name,String permission_desc, int permission_parent,int permisison_depth,String permission_url);
	
	public boolean editPermisison(int permission_id,String permission_name,String permission_desc, int permission_parent,int permisison_depth,String permission_url);
	
	public boolean deletePermisison(int id);
	
	List<Integer> getAllSubId(int parentId);
	
	public Permission findById(int id);
}