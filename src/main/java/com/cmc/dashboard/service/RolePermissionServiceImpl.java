package com.cmc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.repository.RolePermissionRepository;
import com.cmc.dashboard.util.MessageUtil;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	RolePermissionRepository rolePermissionRepository;

	@Override
	public List<RolePermission> getAllRolePermission() {
		return rolePermissionRepository.findAll();
	}

	@Override
	public String saveAllRolePermission(List<RolePermission> listRolePermission) {
		List<RolePermission> result = rolePermissionRepository.save(listRolePermission);
		return result != null ? MessageUtil.SAVE_SUCCESS : MessageUtil.SAVE_ERROR;
	}

}
