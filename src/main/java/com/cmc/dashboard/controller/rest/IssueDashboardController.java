package com.cmc.dashboard.controller.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.HistoryDTO;
import com.cmc.dashboard.dto.IssueAssign;
import com.cmc.dashboard.dto.IssueDashboardDTO;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.model.Issue;
import com.cmc.dashboard.service.HistoryDataService;
import com.cmc.dashboard.service.IssueDashboardService;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class IssueDashboardController {

	@Autowired
	private IssueDashboardService issueDashboardService;
	
	@Autowired
	private HistoryDataService historyDataService;
	
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/project/auth/issue-dashboard", method = RequestMethod.GET)
	public ResponseEntity<List<IssueDashboardDTO>> getIssueByProject(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(issueDashboardService.getIssueByProject(projectId),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/auth/issue-create", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> createIssue() {
		return new ResponseEntity<>(issueDashboardService.getOptional(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/auth/issue-create", method = RequestMethod.POST)
	public ResponseEntity<Issue> createIssue(@Valid @RequestBody IssueDashboardDTO issue){
		return new ResponseEntity<>(issueDashboardService.save(issue), HttpStatus.OK); 
	}
	
	@RequestMapping(value = "/project/auth/issue-update", method = RequestMethod.PUT)
	public ResponseEntity<Issue> updateIssue(@Valid @RequestBody IssueDashboardDTO issue){
		return new ResponseEntity<>(issueDashboardService.updateIssue(issue), HttpStatus.OK); 
	}
	
	@RequestMapping(value = "/project/auth/issue-detail", method = RequestMethod.GET)
	public ResponseEntity<IssueDashboardDTO> getIssueById(@RequestParam("issueId") int issueId) {
		return new ResponseEntity<>(issueDashboardService.getIssueById(issueId), HttpStatus.OK);
	}
	@RequestMapping(value= "/project/user" , method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getUserByProject (@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getUserByProject(projectId), HttpStatus.OK);
	}
//	@RequestMapping(value= "/project/user" , method = RequestMethod.GET)
//	public ResponseEntity<List<ProjectUserDTO>> getUserByProject (@RequestParam("projectId") int projectId) {
//		return new ResponseEntity<List<ProjectUserDTO>>(userService.getListUserByProjectId(projectId), HttpStatus.OK);
//	}
	@RequestMapping(value="/project/issue/assign", method =RequestMethod.PUT)
	public ResponseEntity<IssueDashboardDTO> assignDashboardIssue(@Valid @RequestBody IssueAssign issue) {
		return new ResponseEntity<>(issueDashboardService.assign(issue.getIssueId(), issue.getUserId(),issue.getUserAssign()), HttpStatus.OK);
	}
	
	@RequestMapping(value="/project/issue-delete", method = RequestMethod.DELETE)
	public ResponseEntity<IssueDashboardDTO> deleteIssue(@RequestParam("issueId") int issueId) {
		return new ResponseEntity<>(issueDashboardService.deleteIssue(issueId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/project/issue-history", method = RequestMethod.GET)
	public ResponseEntity<List<HistoryDTO>> getIssueHistory(@RequestParam("issueId") int issueId) {
		return new ResponseEntity<>(historyDataService.getHistoryByIssueId(issueId), HttpStatus.OK);
	}
	@RequestMapping(value="/issue/all", method = RequestMethod.GET)
	public ResponseEntity<List<IssueDashboardDTO>> listAllIssue(@RequestParam("groupId") int groupId,@RequestParam("month") int month,@RequestParam("year") int year,@RequestParam("projectId") int projectId) {
		List<IssueDashboardDTO> issueDashboardDTOs=issueDashboardService.getAllIssue(projectId, month, year, groupId);
		return new ResponseEntity<>(issueDashboardDTOs, HttpStatus.OK);
	}
 }
