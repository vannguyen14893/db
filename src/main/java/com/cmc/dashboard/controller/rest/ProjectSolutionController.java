package com.cmc.dashboard.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.service.ProjectSolutionService;
import com.cmc.dashboard.util.MessageUtil;
import com.google.gson.JsonObject;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api")
public class ProjectSolutionController {

	@Autowired
	private ProjectSolutionService projectSolutionService;
	
	/**
	 * 
	 * add solution
	 * @param solution
	 * @return ResponseEntity<?> 
	 * @author: tvdung
	 */
	@PostMapping(value="/solutions")
	public ResponseEntity<Boolean> addSolution(@RequestBody Solution solution) {
        if (solution.getStartDate().after(solution.getEndDate())) {
        	JsonObject jsonObject = new JsonObject();
        	jsonObject.addProperty(MessageUtil.GET_ERROR, MessageUtil.COMPARE_DATE_TIME);
			throw new BusinessException(jsonObject.toString());
        }
        
		projectSolutionService.addSolution(solution);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
