package com.cmc.dashboard.controller.rest;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import org.springframework.web.client.RestTemplate;

import com.cmc.dashboard.dto.AmsUserDTO;
import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.GroupManagerDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.service.GroupService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class GroupController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/allgroup", method = RequestMethod.GET)
	public ResponseEntity<List<Group>> getAllUser() {
		logger.info("Get all group");
		return new ResponseEntity<>(groupService.getListGroups(), HttpStatus.OK);
	}

	@PostMapping(value = "/group/insert")
	public ResponseEntity<Object> insert(@RequestBody String group) {
		JSONObject obj = new JSONObject(group);
		String groupName = obj.getString("groupName");
		String description = obj.getString("description");
		return new ResponseEntity<Object>(groupService.insert(groupName, description), HttpStatus.OK);
	}

	@PostMapping(value = "/group/update")
	public ResponseEntity<Object> updateGroup(@RequestParam String group) {
		JSONObject obj = new JSONObject(group);
		int id = obj.getInt("groupId");
		String groupName = obj.getString("groupName");
		String description = obj.getString("description");
		boolean status = obj.getBoolean("status");
		boolean developmentUnit = obj.getBoolean("developmentUnit");
		boolean internalDu = obj.getBoolean("internalDu");
		return new ResponseEntity<Object>(groupService.edit(id, groupName, description, status, developmentUnit, internalDu), HttpStatus.OK);
	}

	@DeleteMapping(value = "/group/delete")
	public ResponseEntity<Object> delete(@RequestParam int id) {
		groupService.delete(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	//@Scheduled(cron = "0 0 0 * * ?")
	@GetMapping(value = "/api")
	public void updateGroupAndManager() {

		final String uri =
				 "http://192.168.35.21:8001/api/synchronize/listDepartment";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<GroupManagerDTO[]> result = null;
		try {
			result = ((RestTemplate) restTemplate).getForEntity(uri,GroupManagerDTO[].class);
			GroupManagerDTO[] array = result.getBody();
			groupService.updateGroupManager(0,array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
