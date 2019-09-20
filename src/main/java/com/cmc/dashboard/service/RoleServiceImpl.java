package com.cmc.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.RoleListDTO;
import com.cmc.dashboard.dto.RolePermissionDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.repository.RoleRepository;
import com.cmc.dashboard.util.MessageUtil;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleListDTO> getListRole() {
		List<Role> roles = roleRepository.findAll();
		List<RoleListDTO> roleDTOs = new ArrayList<>();
		if(roles == null) {
			return roleDTOs;
		}
		for (Role role : roles) {
			roleDTOs.add(new RoleListDTO(role));
		}
		return roleDTOs;
	}

	@Override
	public boolean save(Role role) {	
		 roleRepository.save(role);
		 return true;
	}

	@Override
	public Role updateRole(RoleListDTO role) {
		Role roleUpdate = roleRepository.findOne(role.getRoleId());
		if(null == roleUpdate) 
			throw new BusinessException(MessageUtil.NOT_EXIST);
		roleUpdate.setRoleName(role.getRoleName());
		roleUpdate.setDescription(role.getDescription());
		return roleRepository.save(roleUpdate);
	}

	@Override
	public List<Role> deleteRole(int roleId) {
		Role roleDelete = roleRepository.findOne(roleId);
		if(null == roleDelete)
			throw new BusinessException(MessageUtil.NOT_EXIST);
		roleRepository.delete(roleId);
		return roleRepository.findAll();
	}

	@Override
	public List<Role> getAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Transactional
	@Override
	public Role findByName(String name) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(name);
	}

	@Override
	public Role findById(int id) {
		// TODO Auto-generated method stub
		return roleRepository.findOne(id);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		roleRepository.delete(id);
		return true;
	}

	@Override
	public List<RolePermissionDTO> getAllRolePermission() {
		// TODO Auto-generated method stub
		List<Role> listRole = roleRepository.findAll();
		List<RolePermissionDTO> listDto=new ArrayList<RolePermissionDTO>();
		for(Role role : listRole) {
			RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
			Set<Permission> setPermission=role.getPermissions();
			rolePermissionDTO.setRole(role);
			rolePermissionDTO.setListPermission( setPermission);
			listDto.add(rolePermissionDTO);
		}	
		return listDto;
	}

	@Override
	public List<Role> listRoleOfUser(String userName) {
		// TODO Auto-generated method stub
		try {
			return roleRepository.listRoleUser(userName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public boolean edit(Role role) {
		roleRepository.save(role);
		return true;
		
	}

}
