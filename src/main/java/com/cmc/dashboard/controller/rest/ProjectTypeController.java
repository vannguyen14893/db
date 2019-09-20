package com.cmc.dashboard.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.model.ProjectType;
import com.cmc.dashboard.service.ProjectTypeService;

@CrossOrigin("*")
@RestController
///@RequestMapping("/api")
public class ProjectTypeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProjectTypeService projectTypeService;

	@RequestMapping(value = "/project-type/list", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectType>> getAllUser() {
		logger.info("Get all project type");
		return new ResponseEntity<>(projectTypeService.getAll(), HttpStatus.OK);
	}
}
