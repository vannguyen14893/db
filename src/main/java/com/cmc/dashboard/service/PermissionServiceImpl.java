package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cmc.dashboard.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.AuthorizationUtilzationDTO;
import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.repository.RoleRepository;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private RoleService roleService;
    /*
     * (non-Javadoc)
     *
     * @see com.cmc.dashboard.service.PermissionService#getAllRole()
     */
    @Override
    public List<Role> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext(); ) {
            Role role = iterator.next();
            if (role.getRoleName().trim().equals("ADMIN")) {
                iterator.remove();
            }
        }
        return roles;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.cmc.dashboard.service.PermissionService#getRolePermission()
     */
    @Override
    public List<AuthorizationUtilzationDTO> getRolePermission() {
        List<AuthorizationUtilzationDTO> result = new ArrayList<>();
        AuthorizationUtilzationDTO authorizationUtilzationDTO = null;
        List<String> listResourceName = new ArrayList<>();
        listResourceName.add("Resource Allocate");
        listResourceName.add("Billable");
        listResourceName.add("CSS");
        for (int i = 0; i < 3; i++) {
            authorizationUtilzationDTO = new AuthorizationUtilzationDTO();
            authorizationUtilzationDTO.setResourceName(listResourceName.get(i));
            authorizationUtilzationDTO.setRolePermission_DUL(getListRolePermission(1).get(i));
            authorizationUtilzationDTO.setRolePermission_PM(getListRolePermission(2).get(i));
            authorizationUtilzationDTO.setRolePermission_QA(getListRolePermission(3).get(i));
            authorizationUtilzationDTO.setRolePermission_BOD(getListRolePermission(4).get(i));
            result.add(authorizationUtilzationDTO);
        }
        return result;
    }

    /**
     * Get list role permission
     *
     * @param i
     * @return
     * @throws SQLException List<List<RolePermission>>
     * @author: ngocd
     */
    public List<List<RolePermission>> getListRolePermission(int i) {
        List<List<RolePermission>> listRolePermissions = new ArrayList<>();
        List<RolePermission> rolePermissions = roleRepository.getOne(i).getRolePermissions();
        List<RolePermission> rolePermissionsRole = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            rolePermissionsRole.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissionsRole);
        rolePermissionsRole = new ArrayList<>();
        for (int j = 4; j < 8; j++) {
            rolePermissionsRole.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissionsRole);
        rolePermissionsRole = new ArrayList<>();
        for (int j = 8; j < 12; j++) {
            rolePermissionsRole.add(rolePermissions.get(j));
        }
        listRolePermissions.add(rolePermissionsRole);
        return listRolePermissions;
    }

    @Override
    public List<String> getAllPermission() {
        return permissionRepository.getAllPermission();
    }

	@Override
	public Permission findByName(String name) {
		// TODO Auto-generated method stub
		try {
			return permissionRepository.findByName(name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Permission> listPermission() {
		// TODO Auto-generated method stub
		try {
			return permissionRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean createPermisison(String permission_name,String permission_desc, int permission_parent,int permisison_depth, String permission_url) {
		// TODO Auto-generated method stub
		Permission findByName = null;
		com.cmc.dashboard.model.Role roleDefault = null;
		try {
			findByName = permissionRepository.findByName(permission_name);
			if (findByName != null) {
				 new ResponseEntity<>("permission already exist", HttpStatus.OK);
				 return false;
			}
			Permission newPer = new Permission();
			newPer.setPermissionName(permission_name);
			newPer.setPermissionDesc(permission_desc);
			newPer.setPermisison_parent(permission_parent);
			newPer.setPermisison_depth(permisison_depth);
			newPer.setPermission_url(permission_url);
			permissionRepository.save(newPer);

			 return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean editPermisison(int permission_id,String permission_name,String permission_desc, int permission_parent,int permisison_depth,String permission_url) {
		// TODO Auto-generated method stub
		Permission findByName = null;
		try {
			findByName = permissionRepository.findByName(permission_name);
//			if (findByName != null) {
//				if(findByName.getPermissionId() != permission_id)
//					new ResponseEntity<>("ERROR!", HttpStatus.OK);
//					return false;
//			}else 
			Permission findById = permissionRepository.findById(permission_id);
			findById.setPermissionName(permission_name);
			findById.setPermissionDesc(permission_desc);
			findById.setPermisison_parent(permission_parent);
			findById.setPermisison_depth(permisison_depth);
			findById.setPermission_url(permission_url);
			permissionRepository.save(findById);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deletePermisison(int id) {
		// TODO Auto-generated method stub
		try {
			permissionRepository.delete(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Integer> getAllSubId(int parentId) {
		// TODO Auto-generated method stub
		try {
			return permissionRepository.getAllSubId(parentId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Permission findById(int id) {
		// TODO Auto-generated method stub
		try {
			return permissionRepository.findById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}