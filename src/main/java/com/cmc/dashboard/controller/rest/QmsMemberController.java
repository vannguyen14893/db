package com.cmc.dashboard.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.ProjectMemberDTO;
import com.cmc.dashboard.service.qms.ProjectMemberService;
import com.cmc.dashboard.util.MethodUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class QmsMemberController {

	@Autowired
	ProjectMemberService projectMemberService;

	@GetMapping("/projectMember")
	public ResponseEntity<?> getlistMember(HttpServletRequest request, @RequestParam("projectId") int projectId) {
		if (!MethodUtil.hasRole(com.cmc.dashboard.util.Role.ADMIN)) {
			List<ProjectMemberDTO> listProjectMememberDTO = projectMemberService.getListProjectMember(projectId);
			return new ResponseEntity<List<ProjectMemberDTO>>(listProjectMememberDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		}
	}
}
