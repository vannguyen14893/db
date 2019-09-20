package com.cmc.dashboard.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.ListResourceDetailDTO;
import com.cmc.dashboard.dto.RemoveResourcePlanDTO;
import com.cmc.dashboard.dto.ResourceAllocationDTO;
import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.dto.UpdateResourceDTO;
import com.cmc.dashboard.service.UserPlanService;
import com.cmc.dashboard.util.MethodUtil;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class UserPlanController {

	@Autowired
	protected UserPlanService userPlanService;

	/**
	 * Method get list resource allocate.
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<UserPlanUtilizationDTO>>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "/project/auth/plan", method = RequestMethod.GET)
	public ResponseEntity<?> getAllResourceAllocateByProjectId(@RequestParam("projectId") int projectId) {
		// if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.RESOURCE_ACCESS)) {
		List<ResourceAllocationDTO> ltsUserPlanUtilization = new ArrayList<ResourceAllocationDTO>();
		ltsUserPlanUtilization = userPlanService.getListResourcesByProjectId(projectId);
		return new ResponseEntity<List<ResourceAllocationDTO>>(ltsUserPlanUtilization, HttpStatus.OK);
		// } else {
		// return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
		// }
	}

	/**
	 * Method create new plan for user.
	 * 
	 * @param plans
	 * @return
	 * @throws ResponseEntity<UserPlanMessageDto>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "project/auth/plan", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveNewPlan(@RequestBody String plans) {
		RestResponse savePlan = userPlanService.saveNewPlan(plans);
		if (savePlan.getStatus().equals(HttpStatus.BAD_REQUEST)) {
			return new ResponseEntity<String>(savePlan.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(savePlan, HttpStatus.OK);
	}

	/**
	 * Method update plan of resource
	 * 
	 * @param plan
	 * @return ResponseEntity<?>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "/project/auth/plan", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updatePlan(@RequestBody String plan) {
		return new ResponseEntity<UpdateResourceDTO>(userPlanService.updatePlan(plan), HttpStatus.OK);
	}

	/**
	 * Method remove user palnded by id.
	 * 
	 * @param userPlanId
	 * @return ResponseEntity<UserPlanMessageDto>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "project/auth/plan", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> removeUserPlan(@RequestParam("userPlanId") int userPlanId,
			@RequestParam("userId") int userId, @RequestParam("projectId") int projectId) {
		if (MethodUtil.hasRole(com.cmc.dashboard.util.Role.RESOURCE_DELETE)) {
			return new ResponseEntity<RemoveResourcePlanDTO>(userPlanService.removePlan(userPlanId, userId, projectId),
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Access denied", HttpStatus.FORBIDDEN);
	}

	/**
	 * resource detail.
	 * 
	 * @param planMonth
	 * @param page
	 * @param size
	 * @return ResponseEntity<List<ResourceDetailDTO>>
	 * @author: Hoai-Nam
	 */
	@RequestMapping(value = "/resource/detail", method = RequestMethod.GET)
	public ResponseEntity<ListResourceDetailDTO> getResourceDetail1(@RequestParam("userLoginId") String userLoginId,
			@RequestParam("userId") String userId, @RequestParam("planMonth") String planMonth) {
		ListResourceDetailDTO resourceDetails = userPlanService.getResourceDetail(userLoginId, userId, planMonth);
		return new ResponseEntity<ListResourceDetailDTO>(resourceDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/plans", method = RequestMethod.GET)
	public ResponseEntity<List<ResourceDTO>> getListPlansByUser(@RequestParam("userId") int userId,
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<List<ResourceDTO>>(userPlanService.getResourcePlanByUser(userId, projectId),
				HttpStatus.OK);
	}

}
