package com.cmc.dashboard.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.model.ProjectPcvRate;
import com.cmc.dashboard.service.ProjectPcvRateService;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.validator.ProjectPcvRateValidator;

/**
 * @author GiangTM
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectPcvRateController {
	@Autowired
	private ProjectPcvRateService projectPcvRateService;
	
	@Autowired
	private ProjectPcvRateValidator projectPcvRateValidator;
	
	/**
	 * Create new Project PcvRate
	 * 
	 * @param ProjectPcvRate
	 * @return ProjectPcvRate
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project-pcv-rates", method = RequestMethod.POST)
	public ResponseEntity<Object> createProjectPcvRate(@RequestBody ProjectPcvRate theProjectPcvRate) {
//		projectPcvRateValidator.validate(theProjectPcvRate, result);
//		if(result.hasErrors()){
//			return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
//		} else {
			return new ResponseEntity<>(projectPcvRateService.createProjectPcvRate(theProjectPcvRate), HttpStatus.OK);
//		}
	}
	
	/**
	 * Update Project PcvRate
	 * 
	 * @param ProjectPcvRate
	 * @return ProjectPcvRate
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project-pcv-rates", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProjectPcvRate(@RequestBody ProjectPcvRate theProjectPcvRate) {
//		projectPcvRateValidator.validate(theProjectPcvRate, result);
//		if(result.hasErrors())
//			return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(projectPcvRateService.updateProjectPcvRate(theProjectPcvRate), HttpStatus.OK);
	}
	
	/**
	 * 
	 * delete project pcv rate
	 * @param projectPcvRateId
	 * @return ResponseEntity<Object> 
	 * @author: LXLinh
	 */
	@RequestMapping(value = "/project-pcv-rates", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProjectPcvRate(@RequestParam(value="projectPcvRateId") int projectPcvRateId) {
		if(projectPcvRateService.deletePrjectPcvRate(projectPcvRateId))
		return new ResponseEntity<>(MessageUtil.SUCCESS, HttpStatus.OK);
		return new ResponseEntity<>(MessageUtil.NOT_EXIST, HttpStatus.BAD_REQUEST);
	}
}
