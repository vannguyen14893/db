package com.cmc.dashboard.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.SolutionCommentDTO;
import com.cmc.dashboard.dto.SolutionDTO;
import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.service.SolutionService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class SolutionController {
	@Autowired
	private SolutionService solutionService;

	@RequestMapping(value = "/project/auth/solution", method = RequestMethod.GET)
	public ResponseEntity<List<SolutionDTO>> getSolutionByRisk(@RequestParam(value = "riskId") Integer riskId) {
		return new ResponseEntity<>(solutionService.getSolutionByRisk(riskId), HttpStatus.OK);
	}

	@RequestMapping(value = "/project/auth/solution/viewDetail", method = RequestMethod.GET)
	public ResponseEntity<SolutionDTO> getSolutionById(@RequestParam(value= "solutionId", required = false) Integer solutionId) {
		return new ResponseEntity<>(solutionService.getSolutionById(solutionId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/auth/commentSolution", method = RequestMethod.GET)
	public ResponseEntity<List<SolutionCommentDTO>> getCommentBySolutionId(@RequestParam(value= "solutionId", required = false) Integer solutionId) {
		return new ResponseEntity<>(solutionService.getCommentBySolutionId(solutionId), HttpStatus.OK);
	}

	@RequestMapping(value = "/project/auth/comment-create", method = RequestMethod.POST)
	public ResponseEntity<SolutionCommentDTO> createComment(@Valid @RequestBody SolutionCommentDTO comment){
		return new ResponseEntity<>(solutionService.save(comment), HttpStatus.OK); 
	}
	
	@RequestMapping(value = "/project/auth/delete-solution", method = RequestMethod.DELETE)
	public ResponseEntity<Solution> deleteSolution(@RequestParam("solutionId") Integer solutionId) {
		return new ResponseEntity<>(solutionService.deleteSolution(solutionId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/project/auth/delete-comment", method = RequestMethod.DELETE)
	public ResponseEntity<SolutionCommentDTO> deleteComment(@RequestParam("solutionCommentId") Integer solutionCommentId) {
		return new ResponseEntity<>(solutionService.deleteComment(solutionCommentId), HttpStatus.OK);
	}
	
	@PutMapping("/project/auth/edit-comment")
	public ResponseEntity<SolutionCommentDTO> editComment(@RequestBody SolutionCommentDTO solutionCommentDTO) {
		return new ResponseEntity<>(solutionService.editComment(solutionCommentDTO), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/auth/assign-solution", method = RequestMethod.GET)
	public ResponseEntity<Solution> assignSolution(@RequestParam("solutionId") Integer solutionId, 
			@RequestParam("userId") Integer userId) {
		return new ResponseEntity<>(solutionService.assignSolution(solutionId, userId), HttpStatus.OK);
	}
	
	@PutMapping("/project/auth/solution/update-status")
	public ResponseEntity<SolutionDTO> updateStatusSolution(@RequestBody SolutionDTO solutionDTO) {
		return new ResponseEntity<>(solutionService.updateStatusSolution(solutionDTO), HttpStatus.OK);
	}
}
