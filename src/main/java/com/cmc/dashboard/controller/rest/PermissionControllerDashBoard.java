package com.cmc.dashboard.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.model.Permission;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.util.Utils;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PermissionControllerDashBoard {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PermissionService permissionService;

	@GetMapping(value = "/allpermission")
	public ResponseEntity<List<Permission>> getAllPermission() {
		return new ResponseEntity<List<Permission>>(permissionService.listPermission(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/permission/delete")
	public ResponseEntity<Object> deletePermisison(@RequestParam int id) {
		List<Integer> listParent = permissionService.getAllSubId(id);
		if (listParent == null) {
			permissionService.deletePermisison(id);
		}else {
			for (int i = 0; i < listParent.size(); i++) {
				permissionService.deletePermisison(listParent.get(i));
			}
		} 
		return new ResponseEntity<Object>(id, HttpStatus.OK);
	}

	@PostMapping(value = "/permission/save")
	public ResponseEntity<Object> insertPermission(@RequestParam String json) {
		JSONObject obj = new JSONObject(json);
		String permission_name = obj.getString("permisisonName");
		String permission_desc = obj.getString("permisisonDesc");
		int permission_parent = obj.getInt("permissionParent");
		int permisison_depth = obj.getInt("permissionDepth");
		String permission_url = obj.getString("permissionUrl");
		return new ResponseEntity<Object>(permissionService.createPermisison(permission_name, permission_desc,
				permission_parent, permisison_depth, permission_url), HttpStatus.OK);
	}

	@PostMapping(value = "/permission/id")
	public ResponseEntity<Object> getOnePermission(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		int permissionId = obj.getInt("permissionId");
		return new ResponseEntity<Object>(permissionService.findById(permissionId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/permission/update")
	public ResponseEntity<Object> updatePermission(@RequestParam String json) {
		JSONObject obj = new JSONObject(json);
		int permission_id = obj.getInt("permissionId");
		String permission_name = obj.getString("permisisonName");
		String permission_desc = obj.getString("permisisonDesc");
		int permission_parent = obj.getInt("permissionParent");
		int permisison_depth = obj.getInt("permissionDepth");
		String permission_url = obj.getString("permissionUrl");
		return new ResponseEntity<Object>(permissionService.editPermisison(permission_id, permission_name,
				permission_desc, permission_parent, permisison_depth, permission_url), HttpStatus.OK);
	}

	@GetMapping(value = "list/method")
	public List<String> getListMethodByControllerName(@RequestParam("nameController") String nameController) {
		List<String> result = new ArrayList<>();
		Map<String, String> mapController = new HashMap<>();
		List<Class<?>> classes = Utils.find("com.cmc.dashboard.controller.rest");
		for (Class<?> class1 : classes) {
			String endIndex = class1.getName();
			String[] controllerName = endIndex.split("\\.");
			int length = controllerName[3].length();
			String oldName = controllerName[3].substring(0, length - 10);
			String newName = controllerName[3].substring(0, length - 10).toLowerCase();
			mapController.put(newName, oldName);
		}
		String oldName = mapController.get(nameController);
		result = Utils.getMethod(oldName);
		return result;
	}

}