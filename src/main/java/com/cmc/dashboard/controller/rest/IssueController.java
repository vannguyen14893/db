package com.cmc.dashboard.controller.rest;

import java.time.LocalDate;
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

import com.cmc.dashboard.dto.IssueDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.service.qms.IssueService;
import com.cmc.dashboard.util.MethodUtil;
import com.google.gson.JsonObject;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class IssueController {

	@Autowired
	IssueService issueService;

	@RequestMapping(value = "/project/auth/tasks-assigned-resource", method = RequestMethod.GET)
	public ResponseEntity<List<IssueDTO>> getTasksAssignedToResource(@RequestParam("resourceId") int resourceId) {
	    List<IssueDTO>	listTasksAssigned = issueService.getListTasksAssignedToResource(resourceId);
		if (!listTasksAssigned.isEmpty()) {
			return new ResponseEntity<>(listTasksAssigned, HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
	}

	@RequestMapping(value = "/project/auth/tasks-assigned-resource-by-time", method = RequestMethod.GET)
	public ResponseEntity<List<IssueDTO>> getListTasksAssignedToResourceByTime(@RequestParam("resourceId") int resourceId,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
		List<IssueDTO> listTasksAssignedByTime;
		// Validate fromDate < toDate
		LocalDate from = MethodUtil.converStringToLocalDate(fromDate);
		LocalDate to = MethodUtil.converStringToLocalDate(toDate);
		if (from.compareTo(to) > 0) {
			JsonObject json = new JsonObject();
			json.addProperty("toDate", "to date must be greater than from date");
			throw new BusinessException(json.toString()) ;
		} else {
			listTasksAssignedByTime = issueService.getListTasksAssignedToResourceByTime(resourceId,
					fromDate, toDate);
			if (listTasksAssignedByTime.isEmpty()) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
			}
			return new ResponseEntity<>(listTasksAssignedByTime, HttpStatus.OK);
		}
	}

}
