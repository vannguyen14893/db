package com.cmc.dashboard.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.ProjectDeliveryDTO;
import com.cmc.dashboard.model.ProjectDelivery;
import com.cmc.dashboard.service.ProjectDeliveryService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectDeliveryController {

	@Autowired
	ProjectDeliveryService projectDeliveryService;
	
	@RequestMapping(value = "/project/project-delivery/get", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectDelivery>> getProjectDelivery(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectDeliveryService.getDeliveryByProjectId(projectId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/project-delivery/create", method = RequestMethod.POST)
	public ResponseEntity<ProjectDelivery> saveProjectDelivery(@RequestBody ProjectDeliveryDTO projectDeliveryDTO ) {
		return new ResponseEntity<>(projectDeliveryService.saveProjectDelivery(projectDeliveryDTO), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/project-delivery/update", method = RequestMethod.PUT)
	public ResponseEntity<List<ProjectDelivery>> updateProjectDelivery(@RequestBody List<ProjectDeliveryDTO> projectDeliveryDTOs ) {
		return new ResponseEntity<>(projectDeliveryService.updateProjectDelivery(projectDeliveryDTOs), HttpStatus.OK);
	}
}
