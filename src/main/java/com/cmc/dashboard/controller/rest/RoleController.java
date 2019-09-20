package com.cmc.dashboard.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.RoleListDTO;
import com.cmc.dashboard.dto.RolePermissionDTO;
import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RoleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permisisonService;
	
	@RequestMapping(value = "/allrole", method = RequestMethod.GET)
	public ResponseEntity<List<RoleListDTO>> getAllRole() {	
		logger.info("Get all role");
		return new ResponseEntity<>(roleService.getListRole(), HttpStatus.OK);
	}

	@DeleteMapping(value="/role/delete/{roleId}")
	public List<Role> deleteRole(@PathVariable int roleId) {
		return roleService.deleteRole(roleId);
	}
	
	@PostMapping(value="/role/save")
	public ResponseEntity<Object> insertRole(@RequestBody String role) {
		JSONObject jsonObject = new JSONObject(role);
		String roleName = jsonObject.getString("roleName");
		String description  = jsonObject.getString("description");
		Role findByName = roleService.findByName(roleName);
		if(findByName != null) {
			return new ResponseEntity<Object>("roleexist", HttpStatus.OK);
		}else {
		Role roleInsert = new Role();
		roleInsert.setRoleName(roleName);
		roleInsert.setDescription(description);
		return new ResponseEntity<>(roleService.save(roleInsert), HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/role/update")
	public ResponseEntity<Object> updateRole( @RequestParam String role) {
		JSONObject obj = new JSONObject(role);
		int id = obj.getInt("roleId");
		String roleName = obj.getString("roleName");
		String description  = obj.getString("description");
		Role findByName = roleService.findByName(roleName);
		if(findByName != null) {
			if(findByName.getRoleId() != id) {
				return new ResponseEntity<Object>("roleexist", HttpStatus.BAD_REQUEST);
			}
		}
			Role fineById = roleService.findById(id);
			fineById.setRoleName(roleName);
			fineById.setDescription(description);					
		return new ResponseEntity<Object>(roleService.save(fineById), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/role/delete")
	public ResponseEntity<Object> delete(@RequestParam int id) {
		roleService.delete(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	//////// Role Permission

	@PostMapping(value = "/add/role/permission")
	public ResponseEntity<Object> addRolePermission(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		String namePermission = obj.getString("permissionName");
		String roleName = obj.getString("roleName");
		HttpStatus httpStatus = null;
		boolean isSuccess = false;
		try {
			Role role = roleService.findByName(roleName);
			com.cmc.dashboard.model.Permission permission = permisisonService.findByName(namePermission);
			role.addPermission(permission);
			 isSuccess = roleService.edit(role);
			httpStatus = HttpStatus.OK;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(isSuccess, httpStatus);
	}

	@GetMapping(value = "role/getAllRolePermission")
	public ResponseEntity<Object> getAllRolePermission() {
		return new ResponseEntity<Object>(roleService.getAllRolePermission(), HttpStatus.OK);
	}

	@PostMapping(value = "role/removeRolePermission", produces = "application/json")
	public ResponseEntity<Object> removeRolePermission(@RequestParam String json) {
		JSONObject obj = new JSONObject(json);
		String namePermission = obj.getString("permissionName");
		String roleName = obj.getString("roleName");
		HttpStatus httpStatus = null;
		boolean isSuccess = false;
		try {
			Role role = roleService.findByName(roleName);
			Permission permission = permisisonService.findByName(namePermission);
			role.removePermission(permission);
			isSuccess = roleService.save(role);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(isSuccess, httpStatus);

	}
}
