package com.cmc.dashboard.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.GroupDTO;
import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.service.ProjectBillableSevice;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.validator.ProjectBillableValidator;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectBillableController {
	
	@Autowired
	private ProjectBillableSevice projectBillableSevice;
	
	@Autowired
	private ProjectBillableValidator projectBillableValidator;
	
	@RequestMapping(value = "/projects/{projectId}/project-billable", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectBillableDTO>> listProjectBillable(@PathVariable("projectId") int projectId) {
		List<ProjectBillableDTO> list = projectBillableSevice.getProjectBillableByProjectId(projectId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value="/project-billable", method = RequestMethod.POST)
	public ResponseEntity<Object> create (@RequestBody String json) {
		ProjectBillable projectBillable = MethodUtil.convertJsonBillable(json);
//		projectBillableValidator.validate(projectBillable, result);
//		if(result.hasErrors()) {
//			return new ResponseEntity<Object>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
//		}
		return new ResponseEntity<Object>(projectBillableSevice.create(projectBillable),HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/project-billable", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProjectBillable(@Valid @RequestBody ProjectBillable projectBillable){
//		projectBillableValidator.validate(projectBillable, result);
//		if(result.hasErrors()) {
//			return new ResponseEntity<Object>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
//		}
		return new ResponseEntity<Object>(projectBillableSevice.update(projectBillable), HttpStatus.OK);
	}
	
	@RequestMapping(value="/project-billable", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProjectBillable(@RequestParam("projectBillableId") int projectBillableId) {
		if(projectBillableSevice.delete(projectBillableId)) {
			return new ResponseEntity<Object>(MessageUtil.SUCCESS,HttpStatus.OK);
		}
		return new ResponseEntity<Object>(MessageUtil.ERROR,HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/project/group", method = RequestMethod.POST)
	public ResponseEntity<List<GroupDTO>> listGroupByProjectUser(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		int projectId = obj.getInt("projectId");
		return new ResponseEntity<List<GroupDTO>>(projectBillableSevice.getGroupByProjectMember(projectId),HttpStatus.OK);
	}
}
