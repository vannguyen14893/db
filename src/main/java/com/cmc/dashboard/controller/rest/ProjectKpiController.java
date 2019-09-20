package com.cmc.dashboard.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.ProjectKpiDTO;
import com.cmc.dashboard.service.ProjectKpiService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectKpiController {
	@Autowired
	ProjectKpiService projectKpiService;

	/**
	 * Get ProjectKpi
	 * 
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/kpi", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectKpiDTO>> getProjectKpi(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectKpiService.getProjectKpiByProjectId(projectId),
				HttpStatus.OK);
	}
}
