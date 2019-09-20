package com.cmc.dashboard.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.ProjectAllocationDTO;
import com.cmc.dashboard.service.qms.ProjectAllocationService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectAllocationController {
	@Autowired
	ProjectAllocationService projectAllocationService;
	
	@RequestMapping(value = "/projects_allocation", method = RequestMethod.GET)
	public ResponseEntity<?> getDeliveryUnitInfo(@RequestParam("userId") int userId, @RequestParam("dateMonth") String dateMonth){
		List<ProjectAllocationDTO> projectAllocationDTO = new ArrayList<>();
		String[] arrayTime=dateMonth.trim().split("-");
		int year=Integer.parseInt(arrayTime[0]);
		int month= Integer.parseInt(arrayTime[1]);
		projectAllocationDTO = projectAllocationService.getListProject(month, year);
		return new ResponseEntity<List<ProjectAllocationDTO>>(projectAllocationDTO, HttpStatus.OK);
	}
}
