package com.cmc.dashboard.controller.rest;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.dashboard.dto.DuDTO;
import com.cmc.dashboard.dto.ProjectResourceAllocationDTO;
import com.cmc.dashboard.dto.ReAllocationTableDTO;
import com.cmc.dashboard.dto.ResourcePlansDTO;
import com.cmc.dashboard.dto.ResourcesAllocationRestDTO;
import com.cmc.dashboard.dto.ResourcesAllocationUpdateRestDTO;
import com.cmc.dashboard.dto.ResponeResourceAllocation;
import com.cmc.dashboard.dto.UnallocationListDTO;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.ManPower;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.service.ManPowerService;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.ResourceService;
import com.cmc.dashboard.service.UserPlanService;
import com.cmc.dashboard.service.qms.QmsUserService;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.filter.FilterResource;
import com.google.gson.JsonObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/api")
public class ResourceController {
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private QmsUserService qmsUserService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserPlanService userPlanService;
	
	@Autowired
	ManPowerService manPowerService;

	/**
	 * 
	 * get resource plan from user
	 * 
	 * @param userId
	 * @param fromDate
	 * @param endDate
	 * @return ResponseEntity<?>
	 * @author: tvdung
	 */
	@RequestMapping(value = "/resource/plan", method = RequestMethod.GET)
	public ResponseEntity<List<ResourcePlansDTO>> getListPlanResource(@RequestParam("resourceId") int userId,
			@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) {
		List<ResourcePlansDTO> lstPlan = new ArrayList<>();
//
//		if ( !MethodUtil.validateDate(fromDate)) {
//			JsonObject json = new JsonObject();
//			json.addProperty("invalid", "invalid fromdate");
//			throw new BusinessException(json.toString()) ;
//		}
//		if (!MethodUtil.validateDate(toDate)) {
//			JsonObject json = new JsonObject();
//			json.addProperty("invalid", "invalid todate time");
//			throw new BusinessException(json.toString()) ;
//		}
		lstPlan = resourceService.getResourcePlans(userId, month, year);
		return new ResponseEntity<List<ResourcePlansDTO>>(lstPlan, HttpStatus.OK);
	}

	/**
	 * Get all Man Power of user that have userId
	 * 
	 * @param userId
	 * @return ResponseEntity<?>
	 * @author: ngocdv
	 */
	@RequestMapping(value = "/resource/man-power", method = RequestMethod.GET)
	public ResponseEntity<?> getManPowers(@RequestParam("userId") int userId) {
		return new ResponseEntity<List<ManPower>>(resourceService.getAllManPowers(userId), HttpStatus.OK);
	}
	/**
	 * Get all Man Power of user that have userId
	 * 
	 * @param userId
	 * @return ResponseEntity<?>
	 * @author: ngocdv
	 */
	@RequestMapping(value = "/resource/man-power", method = RequestMethod.POST)
	public ResponseEntity<?> createManPower(@RequestBody ManPower manPower) {
		//ManPower manPower = MethodUtil.convertJsonToManPower(json);		
		
		if(resourceService.checkExistManPowerByMonth(manPower)) {
			return new ResponseEntity<String>(MessageUtil.MessageManPower.CREATE_A_MANPOWER, HttpStatus.BAD_REQUEST);
		}

		if (manPower.getAllocationValue() > Constants.Numbers.MAN_POWER_VALUE_MAX
				|| manPower.getAllocationValue() < Constants.Numbers.MAN_POWER_VALUE_MIN)
			return new ResponseEntity<String>(Constants.StringPool.MESSAGE_ERROR_MANPOWER, HttpStatus.BAD_REQUEST);
		
		if (!resourceService.checkValidManPower(manPower))
			return new ResponseEntity<String>(MessageUtil.CANNOT_EDIT, HttpStatus.BAD_REQUEST);
		
		if(!resourceService.checkValidateSameMonthManPower(manPower)) {
			return new ResponseEntity<String>(MessageUtil.SAME_MONTH_TIME, HttpStatus.BAD_REQUEST);
		}
		
		if (manPower.getToDate().before(manPower.getFromDate())) {
			return new ResponseEntity<String>(MessageUtil.EVALUATE_ERROR_START_END_DATE, HttpStatus.BAD_REQUEST);
		}
		resourceService.createManPower(manPower);
		return new ResponseEntity<List<ManPower>>(resourceService.getAllManPowers(manPower.getUserId()), HttpStatus.OK);
	}
	/**
	 * Delete Man Power of user
	 * 
	 * @param 
	 * @return ResponseEntity<?>
	 * @author: ntquy
	 */
	@RequestMapping(value = "/resource/man-power", method = RequestMethod.DELETE)
	public ResponseEntity<?> listManPowers(@RequestParam("id") int id, @RequestParam("userId") int userId) {
	    resourceService.deleteManPower(id);
		return new ResponseEntity<List<ManPower>>(resourceService.getAllManPowers(userId), HttpStatus.OK);

	}
	/**
	 * Update List Man Power of user
	 * 
	 * @param userId
	 * @return ResponseEntity<?>
	 * @author: ntquy
	 */
	@RequestMapping(value = "/resource/man-powers", method = RequestMethod.PUT)
	public ResponseEntity<?> updateListManPower(@RequestBody List<ManPower> manPowers) {	
		JsonObject validDate = MethodUtil.validateListManPowers(manPowers);
		if (validDate != null) {
			throw new BusinessException(validDate.toString());
		}
		resourceService.updateManPowers(manPowers);
			return new ResponseEntity<List<ManPower>>(resourceService.getAllManPowers(manPowers.get(0).getUserId()), HttpStatus.OK);		
	}
	/**
	 * Update Man Power of user
	 * 
	 * @param userId
	 * @return ResponseEntity<?>
	 */
	@RequestMapping(value = "/resource/man-power", method = RequestMethod.PUT)
	public ResponseEntity<?> updateManPower(@RequestBody ManPower manPower) {	
		JsonObject validDate = MethodUtil.validateManPower(manPower);
		if (validDate != null) {
		   throw new BusinessException(validDate.toString());
		}
		
		ManPower manPowerOld = resourceService.getManPowerById(manPower.getManPowerId());
		
		if(manPowerOld == null) {
			return new ResponseEntity<String>(MessageUtil.DATA_NOT_EXIST, HttpStatus.NOT_FOUND);
		}
		
		if (manPower.getAllocationValue() > Constants.Numbers.MAN_POWER_VALUE_MAX
				|| manPower.getAllocationValue() < Constants.Numbers.MAN_POWER_VALUE_MIN)
			return new ResponseEntity<String>(Constants.StringPool.MESSAGE_ERROR_MANPOWER, HttpStatus.BAD_REQUEST);
		
		if(!resourceService.checkValidateManPower(manPower, manPowerOld)) {
			return new ResponseEntity<String>(MessageUtil.CANNOT_EDIT, HttpStatus.BAD_REQUEST);
		}
		
//		if(!resourceService.checkValidateSameMonthManPower(manPower)) {
//			return new ResponseEntity<String>(MessageUtil.SAME_MONTH_TIME, HttpStatus.BAD_REQUEST);
//		}
		
		if (manPower.getToDate().before(manPower.getFromDate())) {
			return new ResponseEntity<String>(MessageUtil.EVALUATE_ERROR_START_END_DATE, HttpStatus.BAD_REQUEST);
		}
		  resourceService.updateManPower(manPower, manPowerOld);
			return new ResponseEntity<List<ManPower>>(resourceService.getAllManPowers(manPower.getUserId()), HttpStatus.OK);		
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @param resourceName
	 * @param du
	 * @param duPic
	 * @param projectId
	 * @return
	 * @author tvhuan
	 */
	@RequestMapping(value = "/filter-resource-allocation", method = RequestMethod.GET)
	public ResponseEntity<?> filterResourceAllocation(@RequestParam("month") int month, @RequestParam("year") int year,
			String resourceName, String du, String duPic, int projectId) {
		List<ProjectResourceAllocationDTO> resourceAllocations = new ArrayList<>();
		String date = MethodUtil.convertMonthAndYear(month, year);
		LocalDate loDate = LocalDate.of(year, month, 1);
		DuDTO listDuParam = new DuDTO();
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DateFormart.DATE_D_M_Y_FORMAT2);

		Date dMonthAndYear = (Date) MethodUtil.convertStringToDate(date);
		String parsedDate = formatter.format(dMonthAndYear);

		String monthAndYear = parsedDate.substring(3);
		// validation isNull, isValid
		if (!MethodUtil.isNull(month) && !MethodUtil.isNull(year) && MethodUtil.isValid(date)) {
			// month and year chỉ đc nhap sau 4/2017
			if (loDate.isAfter(CustomValueUtil.MIN_DATE) || loDate.isEqual(CustomValueUtil.MIN_DATE)) {
				// tinh toan thoi gian nam trong khoang startDate, endDate cua projectId
				resourceAllocations = (List<ProjectResourceAllocationDTO>) resourceService.fiterResourceAllocation(date,
						monthAndYear, resourceName, du, duPic, projectId);
				listDuParam = resourceService.getDuList(duPic);
				return new ResponseEntity<ResponeResourceAllocation>(new ResponeResourceAllocation(resourceAllocations,listDuParam),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<ResponeResourceAllocation>(new ResponeResourceAllocation(resourceAllocations,listDuParam),
				HttpStatus.OK);
	}
	

	/**
	 * 
	 * get api infomation of user as du, name user
	 * @param userId
	 * @return ResponseEntity<UserDTO> 
	 * @author: tvdung
	 */
	@RequestMapping(value="/getDeliverUnit", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserDeliverUnit(@RequestParam("userId") int userId) {
		UserDTO userDTO = qmsUserService.getDeliverUnit(userId);
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	/** Get Unallocation.
	 * 
	 * @param month, year
	 * @return ResponseEntity<?>
	 * @author: thnam
	 */
	@RequestMapping(value = "/resource/unallocation", method = RequestMethod.GET)
	public ResponseEntity<UnallocationListDTO> getUnallocations(@RequestParam("month") int month, @RequestParam("year") int year,
			@RequestParam("activePage")final int activePage, @RequestParam("size")final int size,
			@RequestParam("column")final String column, @RequestParam("sort")final String sort,
			@RequestParam("resourceName")final String resourceName, @RequestParam("status")final String status,
			@RequestParam("deliveryUnit")final String deliveryUnit) {
		UnallocationListDTO listDTO=resourceService.getUnallocations(month, year, activePage, size,
				column, sort, resourceName.trim(), status, deliveryUnit);
		return new ResponseEntity<UnallocationListDTO>(listDTO, HttpStatus.OK);
	//	return new ResponseEntity<>( null, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param du
	 * @return
	 * @author tvhuan
	 */
	@GetMapping(value="paramFilter-resource-allocation")
	public ResponseEntity<?> getParamFilterResourceAllocation(@RequestParam("du") String du) {
		DuDTO listDuParam = resourceService.getDuList(du);
		return new ResponseEntity<DuDTO>(listDuParam, HttpStatus.OK);
	}
	
  /**
   * get all project to filter
   * 
   * @return ResponseEntity<Object> 
   * @author: NNDuy
   */
  @RequestMapping(value = "/resources/projects", method = RequestMethod.GET)
  public ResponseEntity<Object> getAllProjects(@RequestParam int userId) {

    // return result
	  return new ResponseEntity<>(resourceService.getAllProjects(userId),HttpStatus.OK);
  }
  
  /**
   * get all member to filter
   * 
   * @return ResponseEntity<Object> 
   * @author: NNDuy
   */
  @RequestMapping(value = "/resources/members", method = RequestMethod.GET)
  public ResponseEntity<Object> getAllMembers() {

    // return result
    return new ResponseEntity<>(resourceService.getAllMembers(),HttpStatus.OK);
  }
  
  /**
   * get all DU to filter
   * 
   * @return ResponseEntity<Object> 
   * @author: NNDuy
   */
  @RequestMapping(value = "/resources/dus", method = RequestMethod.GET)
  public ResponseEntity<Object> getAllDus(@RequestParam int userId) {

    // return result
    return new ResponseEntity<>(resourceService.getAllDus(userId),HttpStatus.OK);
  }
	
  /**
   * Get Resource Info for Resource Monitor
   * 
   * @param page
   *          - paging
   * @param fromDate
   *          - filter from date
   * @param toDate
   *          - filter to date
   * @param userId
   *          - id of user
   * @param typeTime
   *          - type: date, week, month
   * @return ResponseEntity<ResourceRestDTO>
   * @author: NNDuy
   */
  @RequestMapping(value = "/resources", method = RequestMethod.GET)
  public ResponseEntity<Object> getResources(
      @RequestParam(name = "page", defaultValue = "1") String page,
      @RequestParam(name = "numberPerPage", defaultValue = "25") String numberPerPage,
      @RequestParam(name = "type", defaultValue = "date") final String typeTime,
      @RequestParam(value="toke",required = false) String token,
      @RequestParam(value="userId") int userId,
      FilterResource filter) {

    // validate page
    if (!MethodUtil.isValidatePageNumber(page)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.PAGE_RESOURCE_INVALID,
          HttpStatus.BAD_REQUEST);
    }
    
    // validate numberPerPage
    if (!MethodUtil.isValidatePageNumber(page)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.NUMBER_PER_PAGE_RESOURCE_INVALID,
          HttpStatus.BAD_REQUEST);
    }
    
    // validate type
    if (!MethodUtil.isValidateTypeResource(typeTime)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.TYPE_RESOURCE_INVALID,
          HttpStatus.BAD_REQUEST); 
    }
    
    // validate filter
    ResponseEntity<Object> responeFilter = filter.validate();
    if (null != responeFilter) {
      return responeFilter;
    }
    // return result
    return new ResponseEntity<>(resourceService.getResources(filter, typeTime, 
        Integer.parseInt(page), Integer.parseInt(numberPerPage),userId),
        HttpStatus.OK);
  }
  
  /**
   * add Resource Allocation
   * 
   * @param allocation
   * @return ResponseEntity<Object> 
   * @author: NNDuy
   */
  @RequestMapping(value = "/resources/allocations", method = RequestMethod.POST)
  public ResponseEntity<Object> addResourcesAllocation(@Valid @RequestBody ResourcesAllocationRestDTO allocation) throws ParseException{
    ResponseEntity<Object> responeValidateDate;
    
    //validate effort
    Date fromDate = (Date) MethodUtil.convertStringToDate(allocation.getFrom());
    Date toDate = (Date) MethodUtil.convertStringToDate(allocation.getTo());
    LocalDate calStart;
	LocalDate calEnd;
	calStart = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	calEnd = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	calEnd = calEnd.plusDays(1L);
	long count = 0;
	count = Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS)).
	filter(item -> {
		List<Integer> efforts = userPlanService.getSumEffortPerDayByDay(item.toString(),allocation.getUserId());
		int effort = efforts.stream().reduce(0, (a , b) ->  (a + (b == null ? 0 : b)));
		if(8 - effort >= allocation.getHourPerDay()) {
			return false;
		} else {
			return true;
		}
	}).count();
	if(count > 0) {
		  return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.EXCEED_EFFOT_OF_DAY, HttpStatus.BAD_REQUEST);
	}
    // validate userId exist
    if (!qmsUserService.isExistUserId(allocation.getUserId())) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.USER_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
    }
    
    // validate projectId of user exist
//    if (!projectService.isExistProjectOfUser(allocation.getUserId(), allocation.getProjectId())) {
//      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.PROJECT_OF_USER_NOT_EXIST, HttpStatus.BAD_REQUEST);
//    }
    
    // validate format from and to
    responeValidateDate = ResourcesAllocationRestDTO.validateFormatDate(allocation.getFrom(),allocation.getTo());
    if (null != responeValidateDate) {
      return responeValidateDate;
    }
    
    // validate DateFrom < DateStartProject and DateEnd < DateEndProject  
    responeValidateDate = ResourcesAllocationRestDTO.validateDateCompareProject(
        projectService.getTimeOfProject(allocation.getProjectId()),
        allocation.getFrom(),allocation.getTo());
    if (null != responeValidateDate) {
      return responeValidateDate;
    }
    //Validate plan by group user
    
//    responeValidateDate = userPlanService.checkUserPlan(allocation.getUserId(), allocation.getFrom(),allocation.getTo());
//    if (null != responeValidateDate) {
//        return responeValidateDate;
//      }
    // validate có time != holiday
    if(allocation.getCheckWeekend()) {
    	if(MethodUtil.getTotalWorkingDaysBetweenDate(MethodUtil.convertStringToDate(allocation.getFrom()), 
    	        MethodUtil.convertStringToDate(allocation.getTo())) == 0) {
    	      // not add
    	      return new ResponseEntity<>(new UserPlan(), HttpStatus.OK); 
    	    }
    }        
		if (allocation.getPlanId() != null) {
			if (!userPlanService.isExistPlanId(allocation.getPlanId())) {
				return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.PLAN_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
			} else {
				resourceService.deleteResourcesAllocation(allocation.getPlanId());
			}
		}
    // return result
	try {
    return new ResponseEntity<>(resourceService.saveAllocation(allocation), HttpStatus.OK); 
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
  }
  
 @RequestMapping(value = "/resources/allocations", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteResourcesAllocation(@RequestParam("planId") Integer planId) {
		 if (!userPlanService.isExistPlanId(planId)) {
		      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.PLAN_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
		    }
		resourceService.deleteResourcesAllocation(planId);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
 	
  /**
   * update Resource Allocation
   * 
   * @param allocation
   * @return ResponseEntity<Object> 
   * @author: NNDuy
 * @throws java.text.ParseException 
   */
  @RequestMapping(value = "/resources/allocations", method = RequestMethod.PUT)
  public ResponseEntity<Object> updateResourcesAllocation(@Valid @RequestBody ResourcesAllocationUpdateRestDTO allocation) throws ParseException, java.text.ParseException{
    ResponseEntity<Object> responeValidateDate;
   // validate effort
    UserPlan plan = userPlanService.getPlanById(allocation.getPlanId());
    Date fromDate = (Date) MethodUtil.convertStringToDate(allocation.getFrom());
    Date toDate = (Date) MethodUtil.convertStringToDate(allocation.getTo());
    LocalDate calStart;
	LocalDate calEnd;
	calStart = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	calEnd = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	calEnd = calEnd.plusDays(1L);
	long count = 0;
	count = Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS)).
	filter(item -> {
		List<Integer> efforts = userPlanService.getSumEffortPerDayByDay(item.toString(),plan.getUserId());
		int effort = efforts.stream().reduce(0, (a , b) ->  (a + (b == null ? 0 : b)));
		if( 8 - effort >= allocation.getHourPerDay() - plan.getEffortPerDay()) {
			return false;
		} else {
			return true;
		}
	}).count();
	if(count > 0) {
		  return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.EXCEED_EFFOT_OF_DAY, HttpStatus.BAD_REQUEST);
	}
    // validate planId exist
    if (!userPlanService.isExistPlanId(allocation.getPlanId())) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.PLAN_ID_NOT_EXIST, HttpStatus.BAD_REQUEST);
    }
        
    // validate format from and to
    responeValidateDate = allocation.validateFormatDate();
    if (null != responeValidateDate) {
      return responeValidateDate;
    }
    
    // validate DateFrom < DateStartProject and DateEnd < DateEndProject  
    responeValidateDate = allocation.validateDateCompareProject(
        projectService.getTimeOfProject(userPlanService.getProjectIdFromUserPlan(allocation.getPlanId())));
    if (null != responeValidateDate) {
      return responeValidateDate;
    }
    
    // return result
    return new ResponseEntity<>(resourceService.updateAllocation(allocation), HttpStatus.OK); 
  }
  
	/**
	 * @param page
	 * @param column
	 * @param sort
	 * @param month
	 * @param year
	 * @param resName
	 * @param du
	 * @param duPic
	 * @param projectId
	 * @return
	 * @author tvhuan
	 */
	@GetMapping(value = "resource-allocation-list")
	public ResponseEntity<ReAllocationTableDTO> getListResourceallo(@RequestParam("page") int page, @RequestParam("column") String column,
			@RequestParam("sort") String sort, @RequestParam("month") int month, @RequestParam("year") int year,
			@RequestParam("fullName") String fullName, @RequestParam("du") String du, @RequestParam("duPic") String duPic,
			@RequestParam("projectName") String projectName) {
//		String dateym = MethodUtil.renMonthFromYm(month, year, CustomValueUtil.YEAR_MONTH);
//		String datemy = MethodUtil.renMonthFromYm(month, year, CustomValueUtil.MONTH_YEAR);
//		Page<> listResource = resourceService.getAllallocation(1,
//				CustomValueUtil.RESOURCE_COLUMN_ALLOC, sort, dateym, datemy);
//		
//		if (!CustomValueUtil.RESOURCE_COLUMN_ALLOC.equals(column)
//				&& !CustomValueUtil.RESOURCE_COLUMN_PLANALLOC.equals(column)) {
//			Page<ResourceAllocationQms> pageResource = resourceService.getRessourceAllocation(page, column, sort,
//					dateym, resName, du, duPic, projectId);
//			return new ResponseEntity<Page<ResourceAllocationQms>>(
//					resourceService.getListResourceQms(listResource, pageResource), HttpStatus.OK);
//		} else {
//			Page<ResourceAllocationQms> listResource2 = resourceService.getAllRessourceAllocation(1,
//					CustomValueUtil.RESOURCE_COLUMN_DU, sort, dateym, resName, du, duPic, projectId);
//			Page<ResourceAllocationQms> pageResource = resourceService.getRessourceAllocation(page,
//					CustomValueUtil.RESOURCE_COLUMN_USERNAME, sort, dateym, resName, du, duPic, projectId);
		ReAllocationTableDTO list= resourceService.getRessourceAllAllocation(page,column,sort,month,year,fullName,du,duPic,projectName);
			return new ResponseEntity<>(
					list,
					HttpStatus.OK);
		}
	
	//PNTHANH
//	@Scheduled(cron = "00 00 00 1 * ?")
//	public void getManPowerFromAms() {
//		LocalDateTime now = LocalDateTime.now();
//		int year = now.getYear();
//		int month = now.getMonthValue() == 1 ? 12 : (now.getMonthValue() - 1);
//		int day = now.getDayOfMonth();
//		String date =  year + "-" + month + "-" + day;
//		String manthMonth = month + "-" + year;
//		final String uri =
//				"https://tms.cmcglobal.com.vn:8000/api/manpower/getmanpower?month="+ date;
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("client_id", "6ce4874c-4cf9-49d3-b5f0-9fddc3b5aa0f");
//	    RestTemplate restTemplate = new RestTemplate();
//		HttpEntity<String> entity = new HttpEntity<>(headers);
//		ResponseEntity<ManPowerDTO[]> result = restTemplate.exchange(uri, HttpMethod.GET, entity, ManPowerDTO[].class);
//		ManPowerDTO[] manPowerDTOs = result.getBody();
//	manPowerService.getManPowerFromAms(manPowerDTOs, manthMonth);
//	}
//	@Scheduled(cron = "00 09 11 * * ?")
//	public void generateManpowerAuto(){
//		manPowerService.generateManpowerAuto();
//		
//	}
	
//	@Scheduled(cron = "0 37 16 * * ?")
//	public void getManpowerOlderFromAms() {
//		final String uri = "https://tms.cmcglobal.com.vn:8000/api/manpower/getmanpower";
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("client_id", "6ce4874c-4cf9-49d3-b5f0-9fddc3b5aa0f");
//		 RestTemplate restTemplate = new RestTemplate();
//		HttpEntity<String> entity = new HttpEntity<>(headers);
//		ResponseEntity<ManpowerOlderDTO[]> result = restTemplate.exchange(uri, HttpMethod.GET, entity, ManpowerOlderDTO[].class);
//		ManpowerOlderDTO[] manpowerOlderDTOs = result.getBody();
//		manPowerService.getManpowerOlderFromAms(manpowerOlderDTOs);
//	}

	@GetMapping(value="/man-powers/ams")
	public ResponseEntity<?> getManPower() {
		return new ResponseEntity<>(manPowerService.getManpower(), HttpStatus.OK);
	}
	@GetMapping(value="/man-powers/updatebytime")
	public ResponseEntity<?> getManPower(@RequestParam("startMonth") int startMonth,@RequestParam("startYear") int startYear,
			@RequestParam("endMonth") int endMonth,@RequestParam("endYear") int endYear) {
		  CalculateClass cal = new CalculateClass();
		  LocalDate stDate= LocalDate.of(startYear, startMonth,1);
		  LocalDate edDate= LocalDate.of(endYear, endMonth,1);
		  boolean updated = true;
		  try {
			 manPowerService.updateManPowerCustomDate(0,cal.convertToDateViaInstant(stDate), cal.convertToDateViaInstant(edDate));
			 return new ResponseEntity<>(updated,HttpStatus.OK);
			 
		  } catch (Exception e) {
			  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
