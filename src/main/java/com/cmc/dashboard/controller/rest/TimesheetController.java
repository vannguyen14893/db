package com.cmc.dashboard.controller.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.TimesheetDTO;
import com.cmc.dashboard.service.UserPlanService;
import com.cmc.dashboard.service.qms.TimesheetService;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TimesheetController {
	
	@Autowired
	TimesheetService timesheetService;
	@Autowired
	UserPlanService userPlanService;
	
	/**
	 * TODO description
	 * @param http
	 * @param resourceId
	 * @param fromDate
	 * @param toDate
	 * @return ResponseEntity<?> 
	 * @author: HuanTV
	 */
	@GetMapping("/timesheetResource")
	public ResponseEntity<Map<String, Object>> getTimesheet(HttpServletRequest http, @RequestParam("resourceId") int resourceId,
				@RequestParam("month") int month, @RequestParam("year") int year,@RequestParam("projectName") String projectName ,@RequestParam("activePage") int activePage ,@RequestParam("size") int size  ){
//			if(fromDate.equals("")) {
//				return new ResponseEntity<String>(MessageUtil.TIMESHEET_ERROR_FROMDATE,HttpStatus.CONFLICT);
//			}else if(toDate.equals("")) {
//				return new ResponseEntity<String>(MessageUtil.TIMESHEET_ERROR_TODATE,HttpStatus.CONFLICT);
//			}
//			Date fromDates = MethodUtil.convertStringToDate(fromDate);
//			Date toDates = MethodUtil.convertStringToDate(toDate);
//			if( fromDates.compareTo(toDates) > 0) {
//				return new ResponseEntity<String>(MessageUtil.TIMESHEET_ERROR_DATE,HttpStatus.CONFLICT);
//			}
		  Map<String, Object> map= userPlanService.getListTimeSheetByUserId(resourceId, month, year,projectName,activePage,size);
			return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	/**
	 * TODO description
	 * @param http
	 * @param resourceId
	 * @return ResponseEntity<?> 
	 * @author: HuanTV
	 */
//	@GetMapping("/timesheetResourceList")
//	public ResponseEntity<?> getTimesheet(HttpServletRequest http, @RequestParam("resourceId") int resourceId){
//			List<TimesheetDTO> listTimesheet = timesheetService.getTimesheet(resourceId);
//			return new ResponseEntity<List<TimesheetDTO>>(listTimesheet,HttpStatus.OK);
//	}
}
