/**
 * DashboardSystem - com.cmc.dashboard.service
 */
package com.cmc.dashboard.service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cmc.dashboard.dto.DuDTO;
import com.cmc.dashboard.dto.ProjectOfDuDto;
import com.cmc.dashboard.dto.ProjectResourceAllocationDTO;
import com.cmc.dashboard.dto.ReAllocationDTO;
import com.cmc.dashboard.dto.ReAllocationTableDTO;
//import com.cmc.dashboard.dto.ReAllocationTableDTO;
import com.cmc.dashboard.dto.ResourceFilterDto;
import com.cmc.dashboard.dto.ResourcePlanUtilizationDTO;
import com.cmc.dashboard.dto.ResourcePlansDTO;
import com.cmc.dashboard.dto.ResourceProjectDTO;
import com.cmc.dashboard.dto.ResourceProjectRestDto;
import com.cmc.dashboard.dto.ResourceRestDTO;
import com.cmc.dashboard.dto.ResourcesAllocationRestDTO;
import com.cmc.dashboard.dto.ResourcesAllocationUpdateRestDTO;
import com.cmc.dashboard.dto.UnallocationDTO;
import com.cmc.dashboard.dto.UnallocationListDTO;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserResourceDTO;
import com.cmc.dashboard.model.ManPower;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ResourceAllocationDb;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.qms.model.QmsProject;
import com.cmc.dashboard.qms.model.QmsUserUnallocation;
import com.cmc.dashboard.qms.model.ResourceAllocationQms;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.IssuesRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.ResourceAllocationQmsRepository;
import com.cmc.dashboard.qms.repository.TimeEntriesRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.qms.repository.UserUnallocationRepository;
import com.cmc.dashboard.repository.DeliveryUnitRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.ManPowerRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectTaskRepository;
import com.cmc.dashboard.repository.ProjectUserRepository;
import com.cmc.dashboard.repository.ResourceAllocationRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.Constants.TypeResourceConvert;
import com.cmc.dashboard.util.ConvertDto;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.filter.FilterResource;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceAllocationQmsRepository resourceAllocationQmsRepository;
 
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPlanRepository userPlanRepository;

	@Autowired
	private UserPlanDetailRepository userPlanDetailRepository;

	@Autowired
	private UserQmsRepository userQmsRepository;

	@Autowired
	private TimeEntriesRepository timeEntriesRepository;
    
	@Autowired
    private  ProjectTaskRepository projectTaskRepository;
	
	
	@Autowired
	private IssuesRepository issuesRepository;

	@Autowired
	private ProjectQmsRepository projectRepository;
    
	@Autowired
	private ProjectRepository projectDashBoardReposioty;
	
	@Autowired
	private ManPowerRepository manPowerRepository;

	@Autowired
	private ProjectQmsRepository projectQmsRepository;

	@Autowired
	private ResourceAllocationRepository resourceAllocationRepository;

	@Autowired
	private CustomValueRepository customValueRepository;

	@Autowired
	private UserUnallocationRepository userUnallocationRepository;

	@Autowired
	private UserPlanServiceImpl userPlanService;
	
	@Autowired
	private DeliveryUnitRepository deliverUnitReposioty;
    
	@Autowired
	private UserRepository userDashBoardRepository;
	
	@Autowired
	private ProjectUserRepository projectUserRepostitory;
	
	@Autowired
	private GroupRepository groupRepostitory;
	
	
	@Autowired
	private UserRepository userRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getAllUserDeliveryUnit()
	 */
	@Override
	public List<String> getAllUserDeliveryUnit() {
		List<String> listDeliveryUnit = new ArrayList<>();
		String userDeliveryUnit = userQmsRepository.getAllUserDeliveryUnit();
		String[] listDU = userDeliveryUnit.split("-");
		for (String dU : listDU) {
			if (!dU.trim().isEmpty()) {
				listDeliveryUnit.add(dU.trim());
			}
		}
		return listDeliveryUnit;
	}

	/**
	 * get list resource plan
	 */
	@Override
	public List<ResourcePlansDTO> getResourcePlans(int userId, int month,int year) {
		   Calendar cal = Calendar.getInstance();
			List<ResourcePlansDTO> listResult= new ArrayList<ResourcePlansDTO>();
			List<UserPlan> lstUsrPlan = userPlanRepository.getUserPlans(userId, month,
					year);
//			List<Integer> lstId = this.retrieveIdPlan(lstUsrPlan);
//			List<QmsProject> lstProject = new ArrayList<>();

				for (UserPlan userPlan : lstUsrPlan) {
					 String projectName="";
					 cal.setTime(userPlan.getToDate());
					int endDay=cal.get(Calendar.DAY_OF_MONTH);
					 cal.setTime(userPlan.getFromDate());
					 int startDay=cal.get(Calendar.DAY_OF_MONTH);
					 float planHours= ((endDay-startDay+1)*userPlan.getEffortPerDay());
					ResourcePlansDTO resourcePlansDTO = new ResourcePlansDTO();
					try {
					projectName=projectDashBoardReposioty.findOne(userPlan.getProjectId()).getName();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					resourcePlansDTO.setProjectName(projectName);
					resourcePlansDTO.setStartDate(userPlan.getFromDate());
					resourcePlansDTO.setDueDate(userPlan.getToDate());
					resourcePlansDTO.setPlanHours(planHours);
					listResult.add(resourcePlansDTO);
				}
			//}
			

		//	return this.getResourcePlansDTO(lstUsrPlan, lstProject);
			return listResult;
		}


	/**
	 * get list resourceplanDTO
	 * 
	 * @param lstPlan
	 * @param lstProject
	 * @return List<ResourcePlansDTO>
	 * @author: dung
	 */
	private List<ResourcePlansDTO> getResourcePlansDTO(List<UserPlan> lstPlan, List<QmsProject> lstProject) {
		List<ResourcePlansDTO> lstRePlan = new ArrayList<>();
		for (int i = 0; i < lstPlan.size(); i++) {
			ResourcePlansDTO resourcePlansDTO = this.getResourcePlans(lstPlan.get(i), lstProject);
			lstRePlan.add(resourcePlansDTO);
		}
		return lstRePlan;
	}

	/**
	 * get list resourceplandto from two list
	 * 
	 * @param lstPlan
	 * @param lstProject
	 * @return List<ResourcePlansDTO>
	 * @author: dung
	 */
	private ResourcePlansDTO getResourcePlans(UserPlan plan, List<QmsProject> lstProject) {
		ResourcePlansDTO resourcePlan = new ResourcePlansDTO();
		resourcePlan.setId(plan.getUserPlanId());
		resourcePlan.setStartDate(plan.getFromDate());
		resourcePlan.setDueDate(plan.getToDate());
		resourcePlan.setPlanHours(Constants.Numbers.HOURS_PER_DAY * plan.getManDay());
		for (int i = 0; i < lstProject.size(); i++) {
			if (plan.getProjectId() == lstProject.get(i).getId()) {
				resourcePlan.setProjectName(lstProject.get(i).getProjectName());
			}
		}
		return resourcePlan;
	}

	/**
	 * get list id project form list user plan
	 * 
	 * @param plans
	 * @return List<Integer>
	 * @author: dung
	 */
	private List<Integer> retrieveIdPlan(List<UserPlan> plans) {
		List<Integer> lstId = new ArrayList<>();
		for (int i = 0; i < plans.size(); i++) {
			lstId.add(plans.get(i).getProjectId());
		}
		return lstId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getAllManPowers()
	 */
	@Override
	public List<ManPower> getAllManPowers(int userId) {
		return manPowerRepository.getManPowersByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#createManPower(com.cmc.dashboard.
	 * model.ManPower)
	 */
	@Override
	public void createManPower(ManPower manPower) {
		ManPower mPower = new ManPower();
		mPower.setAllocationValue(manPower.getAllocationValue());
		mPower.setFromDate(manPower.getFromDate());
		mPower.setToDate(manPower.getToDate());
		mPower.setUserId(manPower.getUserId());
		mPower.setCreatedOn(new Date());
		mPower.setUpdatedOn(new Date());
		manPowerRepository.save(mPower);
	}

	@Override
	public void updateManPowers(List<ManPower> listManPower) {
		manPowerRepository.save(listManPower);
	}

	@Override
	public void deleteManPower(int id) {
		manPowerRepository.deleteMan(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#updateManPower(com.cmc.dashboard.
	 * model.ManPower)
	 */
	@Override
	public void updateManPower(ManPower manPower, ManPower entity) {		
		entity.setAllocationValue(manPower.getAllocationValue());
		entity.setFromDate(manPower.getFromDate());
		entity.setToDate(manPower.getToDate());
		entity.setUpdatedOn(new Date());

		manPowerRepository.save(entity);
	}

	@Override
	public ManPower getManPowerById(int id) {
		ManPower entity = manPowerRepository.getManPowerById(id);
	    return entity;
	}
	
	@Override
	public boolean checkExistManPowerByMonth(ManPower manPower) {	
		SimpleDateFormat f = new SimpleDateFormat(Constants.DATE_FORMAT_MONTH_YEAR);
		String monthYear = f.format(manPower.getFromDate());
		List<ManPower> manPowers = manPowerRepository.getManPowersByMonthUser(monthYear, manPower.getUserId());
		if(!manPowers.isEmpty()) {
			return true;
		}
	    return false;
	}
	
	@Override
	public boolean checkValidateManPower(ManPower manPower, ManPower manPowerOld) {
			
		// Không cho sửa manpower của các tháng trong quá khứ
		Calendar fromDateOld = Calendar.getInstance();
		fromDateOld.setTime(manPowerOld.getFromDate());				
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTime( new Date());
		
		if(fromDateOld.get(Calendar.YEAR) < dateNow.get(Calendar.YEAR) || (fromDateOld.get(Calendar.YEAR) == dateNow.get(Calendar.YEAR) && (fromDateOld.get(Calendar.MONTH) < dateNow.get(Calendar.MONTH)))) {
			return false;
		}
	    return true;
	}
	
	@Override
	public boolean checkValidateSameMonthManPower(ManPower manPower) {			
		Calendar fromDateNew = Calendar.getInstance();
		fromDateNew.setTime(manPower.getFromDate());
		Calendar toDateNew = Calendar.getInstance();
		toDateNew.setTime(manPower.getToDate());

		if(fromDateNew.get(Calendar.YEAR) != toDateNew.get(Calendar.YEAR) || fromDateNew.get(Calendar.MONTH) != toDateNew.get(Calendar.MONTH)) {
			return false;
		}
	    return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#checkManPower(java.lang.String)
	 */
	@Override
	public boolean checkValidManPower(ManPower manPower) {
		List<ManPower> manPowers = this.getAllManPowers(manPower.getUserId());
		if (manPowers.isEmpty()) {
			return !manPower.getFromDate().before(Date
					.from(CustomValueUtil.THE_FOUNDING_DATE_COMPANY.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		} else {
			ManPower lastManPower = manPowers.stream().reduce((first, second) -> second).get();
			return (lastManPower.getToDate() == null) ? manPower.getFromDate().after(lastManPower.getFromDate())
					: manPower.getFromDate().after(lastManPower.getToDate());
		}
	}

	private UserResourceDTO getUserPlanByUserIdAndMonth(int userId, int projectId, String month2) {
		float planDay = 0;
		Float manDay = userPlanDetailRepository.getManDay(userId, projectId, month2);
		if (manDay != null) {
			planDay = userPlanDetailRepository.getManDay(userId, projectId, month2);
		}
		return new UserResourceDTO(planDay);
	}

	private List<UserDTO> getUserByProjectId(int projectId) {
		List<UserDTO> userDTOs = new ArrayList<>();
		List<Object> objects = userQmsRepository.getUserByProjectId(projectId);
		for (Object object : objects) {
			Object[] oobj = (Object[]) object;
			int param_0 = Integer.parseInt(oobj[0].toString());
			String param_1 = oobj[1] == null ? null : oobj[1].toString();
			String param_2 = oobj[2] == null ? null : oobj[2].toString();
			String param_3 = oobj[3] == null ? null : oobj[3].toString();
			UserDTO userDTO = new UserDTO(param_0, param_1, param_2, param_3);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	private List<ResourceProjectDTO> getProjectByMonth(Date month, String duPic, int projectId) {
		List<ResourceProjectDTO> projectDTOs = new ArrayList<>();
		List<Object> objects;
		if (projectId == 0) {
			objects = projectQmsRepository.getProjectByMonth(month, duPic);
		} else {
			objects = projectQmsRepository.filterProjectByMonth(month, duPic, projectId);
		}

		for (Object object : objects) {
			Object[] oobj = (Object[]) object;
			int param_0 = Integer.parseInt(oobj[0].toString());
			String param_1 = oobj[1] == null ? null : oobj[1].toString();
			String param_2 = oobj[2] == null ? null : oobj[2].toString();
			String param_3 = oobj[3] == null ? null : oobj[3].toString();
			String param_4 = oobj[4] == null ? null : oobj[4].toString();
			int param_5 = oobj[5] == null ? null : Integer.parseInt(oobj[5].toString());
			String param_6 = oobj[6] == null ? null : oobj[6].toString();
			String param_7 = oobj[7] == null ? null : oobj[7].toString();

			ResourceProjectDTO projectDTO = new ResourceProjectDTO(param_0, param_1, param_2, param_3, param_4, param_5,
					param_6, param_7);
			projectDTOs.add(projectDTO);
		}
		return projectDTOs;
	}

	/**
	 * Get resource allocation
	 * 
	 * @author duyhieu
	 */
	@Override
	public List<ProjectResourceAllocationDTO> getResourceAllocation(String date, String monthAndYear, String duPic,
			int projectId) {

		Date dDate = MethodUtil.convertStringToDate(date);
		List<ProjectResourceAllocationDTO> projectResourceAllocationDTOs = new ArrayList<>();
		int totalWorkingDay = MethodUtil.getTotalWorkingDay(monthAndYear);

		// lay tat ca cac project co thoi gian nam trong startDate va endDate
		List<ResourceProjectDTO> resourceProjectDTOs = this.getProjectByMonth(dDate, duPic, 0);
		// voi moi project ta lay tat ca cac resource
		for (ResourceProjectDTO projectDTO : resourceProjectDTOs) {
			// lay ca resource cùng info
			List<UserDTO> userDTOs = getUserByProjectId(projectDTO.getProjectId());
			// moi resource ta lay plan tuong ung voi thang duoc chon
			for (UserDTO userDTO : userDTOs) {
				// get plan allocation cua resource
				UserResourceDTO userPlanDetails = this.getUserPlanByUserIdAndMonth(userDTO.getId(),
						projectDTO.getProjectId(), monthAndYear);
				ResourceProjectDTO projectDTOadd = new ResourceProjectDTO(projectDTO.getProjectId(),
						projectDTO.getProjectName(), projectDTO.getProjectType(), projectDTO.getProjectCode(),
						projectDTO.getDeliveryUnit(), projectDTO.getStatus(), projectDTO.getEndDate(),
						projectDTO.getStartDate(), this.planAllocation(totalWorkingDay, userPlanDetails.getManDay()));
				ProjectResourceAllocationDTO resourceAllocationDTO = new ProjectResourceAllocationDTO(userDTO.getId(),
						userDTO.getFullName(), userDTO.getDeliveryUnit(), userDTO.getRole(), projectDTOadd);

				projectResourceAllocationDTOs.add(resourceAllocationDTO);
			}
		}
		return projectResourceAllocationDTOs;
	}

	private float planAllocation(int totalWorkingDay, float planning) {
		DecimalFormat df = new DecimalFormat(CustomValueUtil.DEFAULT_FORMAT_FLOAT);
		return Float.parseFloat(df.format(planning / totalWorkingDay)) * 100;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashb
	 * 
	 * oard.service.ResourceService#getUnallocations(int, int) xuan linh
	 * 
	 * @modify ddhieu
	 */
	@Override
	public UnallocationListDTO getUnallocations(int month, int year, int page, int size, String column, String sort,
			String resourceName, String status, String deliveryUnit) {

//		List<UnallocationDTO> pageUnallocations = new ArrayList<>();
//		List<UnallocationDTO> qmsUserUnallocations = new ArrayList<>();
//
//		YearMonth yearMonth = YearMonth.of(year, month);
//		String monthYear = yearMonth.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_MONTH_YEAR));
//
//		// get all delivery unit
//		List<String> listDu = customValueRepository.getAllDu();
//		List<QmsUserUnallocation> getAllUserAndDuNames = userUnallocationRepository.getAllUserAndDuNames(deliveryUnit,
//				resourceName.trim());
//		if (MethodUtil.checkList(getAllUserAndDuNames)) {
//			return new UnallocationListDTO(pageUnallocations, listDu, getAllUserAndDuNames.size());
//		}
//		// get manday and userId
//		List<Object> mandays = userPlanRepository.getManDayByMonthYear(monthYear);
//		// get allocation value
//		List<ManPower> manPowers = manPowerRepository.getManPowerByMonth(monthYear);
//
//		List<ResourcePlanUtilizationDTO> handleManDay = this.getManDayPlans(mandays);
//		Map<Integer, List<ManPower>> getManPowerOfUser = this.getManPowerOfUser(manPowers);
//		List<ResourcePlanUtilizationDTO> manDayPowers = getTotalManpowerOfUser(getManPowerOfUser, yearMonth);
//		qmsUserUnallocations = addUserToUnallocationDTO(getAllUserAndDuNames, qmsUserUnallocations);
//
//		List<UnallocationDTO> unallocationDTOafter = getUnlocationUsers(handleManDay, manDayPowers,
//				qmsUserUnallocations).stream().filter(user -> user.getAllocation() > Constants.Numbers.MANDAY_ZERO)
//						.collect(Collectors.toList());
//		pageUnallocations = filterUnallocation(status, column, sort, unallocationDTOafter, pageUnallocations,
//				handleManDay, manDayPowers).getUnallocationDTO();
//
//		int fromIndex = (page - 1) * size;
//		if(fromIndex > pageUnallocations.size()) {	
//			fromIndex = (pageUnallocations.size() / size) * size;
//		}
//		pageUnallocations = pageUnallocations.subList(fromIndex, Math.min(fromIndex + size, pageUnallocations.size()));
//		return new UnallocationListDTO(pageUnallocations, listDu, filterUnallocation(status, column, sort,
//				unallocationDTOafter, pageUnallocations, handleManDay, manDayPowers).getTotalElements());
		List<UnallocationDTO> listUn= new ArrayList<UnallocationDTO>();
		UnallocationListDTO unList= new UnallocationListDTO();
		Page<Object> pageUnAllo=null;
		CalculateClass cal= new CalculateClass();
		int workingDay=cal.calculateWorkingDay(month, year);	
	    Pageable pageable = new PageRequest(page-1,size);
	    try {   
	    	   if("".equals(status))
	    		pageUnAllo =resourceAllocationRepository.getAllResourceUnAllocation(pageable, resourceName,deliveryUnit);
	    	   else if("0".equals(status)) 
	    		   pageUnAllo =resourceAllocationRepository.getAllResourceUnAllocationNoAllo(pageable, resourceName, deliveryUnit,month,year);
	    	   else 
	    		   pageUnAllo =resourceAllocationRepository.getAllResourceUnAllocationNotFull(pageable, resourceName, deliveryUnit,month,year);
	    	   
	    } 
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    List<Object> listObject=pageUnAllo.getContent();
	    List<UserPlan> listUserPlan = null;
	    if(listObject.size()>0) {
	    for(Iterator<Object> it = listObject.iterator(); it.hasNext();) {
	    	Object[] object = (Object[]) it.next();
	    	int userId=(Integer)object[0];
	    	float allocation=0.00f;
	    	float sumAlo=0.00f;
	    	String statusAllocation = "";
	    	try {
	    	listUserPlan=userPlanRepository.getUserPlans(userId, month, year);
	    	for(UserPlan up: listUserPlan) 
	    		sumAlo+=cal.countWorkingDayInMonth(up.getFromDate(), up.getToDate(),month)*up.getEffortPerDay()/8;
	    	
	    	
	    		allocation=(float) (100f-round(sumAlo/workingDay*100,2));
	    		if(allocation == 100f) {
	    			int nextMonth=0;
	    			int yearPlan=year;
	    			if(month==12) {
	    				nextMonth=1;
	    				yearPlan++;
	    			}
	    			else nextMonth=month+1;
	    			listUserPlan=userPlanRepository.getUserPlans(userId, nextMonth, yearPlan);
	    			if(listUserPlan.size()>0) statusAllocation="Book";
	    			else statusAllocation="Available";
	    		       }
	 
	    		UnallocationDTO dto= new UnallocationDTO((Integer)object[0],(String)object[2],(String)object[1],allocation,"Không có gì",statusAllocation);
	    	    listUn.add(dto);
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	      }
	    if (sort.equals("ASC")) {
	       if(column.equals("allocation"))	Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorAlloAsc);
	       else if (column.equals("resourceName")) Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorProjectNameAsc);
	       else Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorDuAsc);
	     }
	    else {
	    	  if(column.equals("allocation"))	Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorAlloDesc);
		       else if (column.equals("resourceName")) Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorProjectNameDesc);
		       else Collections.sort(listUn,UnallocationDTO.UnAllocationComparatorDuDesc);
	    } 
	   
	 
	    unList.setTotalElements(pageUnAllo.getTotalElements());
	  
	    
	    }
	    List<String>listDu=groupRepostitory.getListName();
	    unList.setUnallocationDTO(listUn); 
	    unList.setDU(listDu);
       return unList;
	}

	/**
	 * @author duyhieu
	 * @param column
	 * @param sort
	 * @return
	 */
	private Comparator<UnallocationDTO> sortUnallocation(final String column, final String sort) {
		final String fieldSort = column.trim().toUpperCase();
		boolean typeSort = Constants.SortType.ASCENDING.equals(sort.trim().toUpperCase());
		Comparator<UnallocationDTO> comparing = Comparator.comparing(UnallocationDTO::getAllocation);

		switch (fieldSort) {
		case Constants.FieldResourceUnallocation.DELIVERY_UNIT:
			comparing = typeSort
					? Comparator.comparing(UnallocationDTO::getDuName).thenComparing(UnallocationDTO::getResourceName)
					: Comparator.comparing(UnallocationDTO::getDuName).thenComparing(UnallocationDTO::getResourceName)
							.reversed();
			break;
		case Constants.FieldResourceUnallocation.NAME:
			comparing = typeSort
					? Comparator.comparing(UnallocationDTO::getResourceName)
							.thenComparing(UnallocationDTO::getAllocation)
					: Comparator.comparing(UnallocationDTO::getResourceName)
							.thenComparing(UnallocationDTO::getAllocation).reversed();
			break;
		case Constants.FieldResourceUnallocation.STATUS:
			comparing = typeSort
					? Comparator.comparing(UnallocationDTO::getStatus).thenComparing(UnallocationDTO::getAllocation)
					: Comparator.comparing(UnallocationDTO::getStatus).thenComparing(UnallocationDTO::getAllocation)
							.reversed();
			break;
		default:
			comparing = typeSort
					? Comparator.comparing(UnallocationDTO::getAllocation)
							.thenComparing(UnallocationDTO::getResourceName)
					: Comparator.comparing(UnallocationDTO::getAllocation)
							.thenComparing(UnallocationDTO::getResourceName).reversed();
			break;
		}
		return comparing;
	}

	/**
	 * @author duyhieu
	 * @param status
	 * @param column
	 * @param sort
	 * @param unallocationDTOs1
	 * @param unallocationDTOs
	 * @param handleManDay
	 * @param manDayPowers
	 * @return
	 */
	private UnallocationListDTO filterUnallocation(String status, String column, String sort,
			List<UnallocationDTO> qmsUserUnallocations, List<UnallocationDTO> pageUnallocations,
			List<ResourcePlanUtilizationDTO> handleManDay, List<ResourcePlanUtilizationDTO> manDayPowers) {
		// get all delivery unit
		List<String> listDu = customValueRepository.getAllDu();
		List<UnallocationDTO> unallocationDTOafter = new ArrayList<>();
		if (Constants.FieldResourceUnallocation.NOT_FULL_ALLOCATION.equalsIgnoreCase(status)) {
			unallocationDTOafter = qmsUserUnallocations.stream().filter(
					p -> Constants.FieldResourceUnallocation.STATUS_NOT_FULL_ALLOCATION.equalsIgnoreCase(p.getStatus()))
					.sorted(sortUnallocation(column, sort)).collect(Collectors.toList());
			pageUnallocations = commpareUserId(pageUnallocations, unallocationDTOafter);
			return new UnallocationListDTO(unallocationDTOafter, listDu, unallocationDTOafter.size());
		} else if (Constants.FieldResourceUnallocation.NO_ALLOCATION.equalsIgnoreCase(status)) {
			unallocationDTOafter = qmsUserUnallocations.stream().filter(
					p -> Constants.FieldResourceUnallocation.STATUS_NO_ALLOCATION.equalsIgnoreCase(p.getStatus()))
					.sorted(sortUnallocation(column, sort)).collect(Collectors.toList());
			pageUnallocations = commpareUserId(pageUnallocations, unallocationDTOafter);
			return new UnallocationListDTO(unallocationDTOafter, listDu, unallocationDTOafter.size());
		}
		unallocationDTOafter = qmsUserUnallocations.stream().sorted(sortUnallocation(column, sort))
				.collect(Collectors.toList());
		pageUnallocations = commpareUserId(pageUnallocations, unallocationDTOafter);
		return new UnallocationListDTO(unallocationDTOafter, listDu, unallocationDTOafter.size());
	}

	/**
	 * @author duyhieu
	 * @param pageUnallocations
	 * @param unallocationDTOafter
	 * @return
	 */
	private List<UnallocationDTO> commpareUserId(List<UnallocationDTO> pageUnallocations,
			List<UnallocationDTO> unallocationDTOafter) {
		List<UnallocationDTO> common = new ArrayList<>();
		for (UnallocationDTO unallocationDTO1 : pageUnallocations) {
			for (UnallocationDTO unallocationDTO : unallocationDTOafter) {
				if (unallocationDTO1.getUserId() == unallocationDTO.getUserId()) {
					common.add(new UnallocationDTO(unallocationDTO1.getUserId(), unallocationDTO1.getDuName(),
							unallocationDTO1.getResourceName(), unallocationDTO.getAllocation(),
							unallocationDTO.getStatus(),unallocationDTO.getStatusAllocation()));
				}
			}
		}
		return common;
	}

	/**
	 * @author duyhieu
	 * @param getAllUserAndDuNames
	 * @param unallocationDTOs1
	 * @return
	 */
	private List<UnallocationDTO> addUserToUnallocationDTO(List<QmsUserUnallocation> getAllUserAndDuNames,
			List<UnallocationDTO> unallocationDTOs1) {
		for (QmsUserUnallocation userAndDU1 : getAllUserAndDuNames) {
			unallocationDTOs1
					.add(new UnallocationDTO(userAndDU1.getId(), userAndDU1.getResourceName(), userAndDU1.getDuName()));
		}
		return unallocationDTOs1;
	}

	/**
	 * 
	 * convert list type object to list type ResourcePlanUtilization
	 * 
	 * @param mandays
	 * @return List<ResourcePlanUtilizationDTO>
	 * @author: LXLinh
	 */
	private List<ResourcePlanUtilizationDTO> getManDayPlans(final List<Object> mandays) {
		List<ResourcePlanUtilizationDTO> resourcePlanUtilizationDTOs = new ArrayList<>();
		int userId;
		float manDay;
		for (Object plan : mandays) {
			Object[] plans = (Object[]) plan;
			userId = Integer.parseInt(plans[0].toString());
			manDay = Float.parseFloat((plans[1].toString()));
			resourcePlanUtilizationDTOs.add(new ResourcePlanUtilizationDTO(userId, manDay));
		}
		return resourcePlanUtilizationDTOs;
	}

	/**
	 * Compare date month year
	 * 
	 * @param yearMonth
	 * @param date
	 * @return boolean
	 * @author: LXLinh
	 */
	private boolean comparingYearMonthAndDate(YearMonth yearMonth, Date date) {
		return (yearMonth.compareTo(YearMonth.from(MethodUtil.convertDateTimeToLocalDate(date))) == 0) ? true : false;
	}

	/**
	 * 
	 * Get unallocation users
	 * 
	 * @param manDayAllocations
	 * @param manDayPowers
	 * @param userUnAllocation
	 * @return List<UnallocationDTO>
	 * @author: LXLinh
	 */
	private List<UnallocationDTO> getUnlocationUsers(final List<ResourcePlanUtilizationDTO> manDayAllocations,
			final List<ResourcePlanUtilizationDTO> manDayPowers, List<UnallocationDTO> userUnAllocation) {
		userUnAllocation.forEach(user -> {
			Optional<ResourcePlanUtilizationDTO> manDayAllocation = manDayAllocations.stream()
					.filter(manDay -> manDay.getUserId() == user.getUserId()).findFirst();
			Optional<ResourcePlanUtilizationDTO> manDayPower = manDayPowers.stream()
					.filter(manDay -> manDay.getUserId() == user.getUserId()).findFirst();
			float valueUnlocation = (manDayAllocation.isPresent() && manDayPower.isPresent())
					? (Constants.Numbers.PER_UNALLOCTION
							- ((manDayAllocation.get().getManday() / manDayPower.get().getManday()) * 100))
					: Constants.Numbers.PER_UNALLOCTION;
			user.setAllocation(valueUnlocation);
		});
		return userUnAllocation;
	}

	/**
	 * 
	 * convert map total manPower to type list
	 * 
	 * @param getManPowerOfUser
	 * @param yearMonth
	 * @return List<ResourcePlanUtilizationDTO>
	 * @author: LXLinh
	 */
	private List<ResourcePlanUtilizationDTO> getTotalManpowerOfUser(
			final Map<Integer, List<ManPower>> getManPowerOfUser, YearMonth yearMonth) {
		List<ResourcePlanUtilizationDTO> manDays = new ArrayList<>();
		getManPowerOfUser.entrySet().forEach(manPower -> {
			ResourcePlanUtilizationDTO manDay = new ResourcePlanUtilizationDTO();
			manDay.setManday(manPower.getValue().isEmpty() ? Constants.Numbers.MANDAY_ZERO
					: calculateManPowerOfUser(manPower.getValue(), yearMonth));
			manDay.setUserId(manPower.getKey());
			manDays.add(manDay);
		});
		return manDays;
	}

	/**
	 * 
	 * calculate total manPower in monthYear for each user
	 * 
	 * @param manPowers
	 * @param yearMonth
	 * @return float
	 * @author: LXLinh
	 */
	private float calculateManPowerOfUser(final List<ManPower> manPowers, YearMonth yearMonth) {
		float totalWorkingDays = 0L;
		for (ManPower manPower : manPowers) {
			// to date is not null
			if (!MethodUtil.isNull(manPower.getToDate())) {
				// fromDate->toDate
				if (comparingYearMonthAndDate(yearMonth, manPower.getFromDate())
						&& comparingYearMonthAndDate(yearMonth, manPower.getToDate())) {
					totalWorkingDays += (MethodUtil.getTotalWorkingDays(manPower.getFromDate(), manPower.getToDate())
							* manPower.getAllocationValue());

				} else
				// fromDate->lastDayOfMonth
				if (comparingYearMonthAndDate(yearMonth, manPower.getFromDate())
						&& !comparingYearMonthAndDate(yearMonth, manPower.getToDate())) {
					totalWorkingDays += (calculateDateOfMonthYear(
							MethodUtil.convertDateTimeToLocalDate(manPower.getFromDate()), yearMonth, false)
							* manPower.getAllocationValue());
				} else
				// firstDayOfMonth->toDate
				if (!comparingYearMonthAndDate(yearMonth, manPower.getFromDate())
						&& comparingYearMonthAndDate(yearMonth, manPower.getToDate()))
					totalWorkingDays += (calculateDateOfMonthYear(
							MethodUtil.convertDateTimeToLocalDate(manPower.getToDate()), yearMonth, true)
							* manPower.getAllocationValue());
				else
				// firstDayOfMonth->lastDayOfMonth
				if (!comparingYearMonthAndDate(yearMonth, manPower.getFromDate())
						&& !comparingYearMonthAndDate(yearMonth, manPower.getToDate()))
					totalWorkingDays += (MethodUtil.getBetweenWorkingDaysLocalDate(
							yearMonth.atDay(Constants.Numbers.FIRST_DAY), yearMonth.atEndOfMonth())
							* manPower.getAllocationValue());
			}
			// from date is null
			else {
				// fromDate->lastDayOfMonth
				if (comparingYearMonthAndDate(yearMonth, manPower.getFromDate())) {
					totalWorkingDays += (calculateDateOfMonthYear(
							MethodUtil.convertDateTimeToLocalDate(manPower.getFromDate()), yearMonth, false)
							* manPower.getAllocationValue());
					break;
				} else
				// fromdate before yearMonth
				// firstDayOfMonth->lastDayOfMonth
				if (MethodUtil.convertDateTimeToLocalDate(manPower.getFromDate())
						.isBefore(yearMonth.atDay(Constants.Numbers.FIRST_DAY))) {
					totalWorkingDays += (MethodUtil.getBetweenWorkingDaysLocalDate(
							yearMonth.atDay(Constants.Numbers.FIRST_DAY), yearMonth.atEndOfMonth())
							* manPower.getAllocationValue());
				} else
				// return totalWorkingDays=0
				if (MethodUtil.convertDateTimeToLocalDate(manPower.getFromDate()).isAfter(yearMonth.atEndOfMonth())) {
					break;
				}

			}
		}

		return totalWorkingDays;
	}

	/**
	 * get long start date to last day of year month or long first day of year month
	 * to date
	 * 
	 * @param date
	 * @param yearMonth
	 * @param firstOrlastDayOfMonth
	 * @return long
	 * @author: LXLinh
	 */
	private long calculateDateOfMonthYear(LocalDate localDate, YearMonth yearMonth, boolean firstOrlastDayOfMonth) {
		return firstOrlastDayOfMonth ? MethodUtil.getBetweenWorkingDaysLocalDate(yearMonth.atDay(1), localDate)
				: MethodUtil.getBetweenWorkingDaysLocalDate(localDate, yearMonth.atEndOfMonth());

	}

	/**
	 * 
	 * group list manPower of each user
	 * 
	 * @param manPowers
	 * @return Map<Integer,List<ManPower>>
	 * @author: LXLinh
	 */
	// group manpower of user to group
	private Map<Integer, List<ManPower>> getManPowerOfUser(final List<ManPower> manPowers) {
		return manPowers.stream().collect(Collectors.groupingBy((ManPower::getUserId)));
	}

	/**
	 * getUserByProjectId
	 * 
	 * @param resourceName
	 * 
	 * @param du
	 * 
	 * @param projectId
	 * 
	 * @author HuanTv
	 */
	private List<UserDTO> getUserByProjectId(int projectId, String du, String resourceName) {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		List<Object> objects = userQmsRepository.filterUserByProjectId(projectId, du, resourceName);
		for (Object object : objects) {
			Object[] oobj = (Object[]) object;
			int param_0 = Integer.parseInt(oobj[0].toString());
			String param_1 = oobj[1] == null ? null : oobj[1].toString();
			String param_2 = oobj[2] == null ? null : oobj[2].toString();
			String param_3 = oobj[3] == null ? null : oobj[3].toString();
			UserDTO userDTO = new UserDTO(param_0, param_1, param_2, param_3);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	/**
	 * getDuList
	 *
	 * @param null
	 * 
	 * @author HuanTv
	 */
	public DuDTO getDuList(String du) {
		DuDTO dtoDu = new DuDTO();
		dtoDu.setDu(this.getDU(1));
		dtoDu.setDuPic(this.getDU(0));
		dtoDu.setProjectOfDuDto(this.getListDuOfProject(du));
		return dtoDu;
	}

	/**
	 * getListDuOfProject
	 * 
	 * @param du
	 * 
	 * @author HuanTv
	 */
	private List<ProjectOfDuDto> getListDuOfProject(String du) {
		List<Object> arrObjs = userQmsRepository.getProjectlistByDu(du);
		List<ProjectOfDuDto> listProjectOfDu = new ArrayList<>();
		listProjectOfDu.add(new ProjectOfDuDto("", CustomValueUtil.DEFAULT_PROJECT_LABEL));
		for (Object arrObj : arrObjs) {
			Object[] arr = (Object[]) arrObj;
			ProjectOfDuDto proDto = new ProjectOfDuDto();
			proDto.setValue(arr[0] != null ? arr[0].toString() : "");
			proDto.setLabel(arr[1] != null ? arr[1].toString() : "");
			listProjectOfDu.add(proDto);
		}
		return listProjectOfDu;
	}

	private List<ProjectOfDuDto> getDU(int isDu) {
		List<Object> arrObjs = new ArrayList<>();
		List<ProjectOfDuDto> listProjectOfDu = new ArrayList<>();
		if (isDu == 1) {
			arrObjs = userQmsRepository.getlistDu();
			listProjectOfDu.add(new ProjectOfDuDto("", CustomValueUtil.DEFAULT_DU_LABEL));
		} else if (isDu == 0) {
			arrObjs = userQmsRepository.getlistDuPic();
			listProjectOfDu.add(new ProjectOfDuDto("", CustomValueUtil.DEFAULT_DUPIC_LABEL));
		}
		for (Object arrObj : arrObjs) {
			Object[] arr = (Object[]) arrObj;
			ProjectOfDuDto proDto = new ProjectOfDuDto();
			proDto.setValue(arr[0] != null ? arr[0].toString() : "");
			proDto.setLabel(arr[0] != null ? arr[0].toString() : "");
			listProjectOfDu.add(proDto);
		}
		return listProjectOfDu;
	}

	/**
	 * fiter-resource-allocation
	 * 
	 * @param date
	 * 
	 * @param monthAndYear
	 * 
	 * @param resourceName
	 * 
	 * @param du
	 * 
	 * @param duPic
	 * 
	 * @param projectId
	 * 
	 * @author HuanTv
	 */
	@Override
	public List<ProjectResourceAllocationDTO> fiterResourceAllocation(String date, String monthAndYear,
			String resourceName, String du, String duPic, int projectId) {

		Date dDate = MethodUtil.convertStringToDate(date);
		List<ProjectResourceAllocationDTO> projectResourceAllocationDTOs = new ArrayList<>();

		int totalWorkingDay = MethodUtil.getTotalWorkingDay(monthAndYear);

		// lay tat ca cac project co thoi gian nam trong startDate va endDate
		List<ResourceProjectDTO> resourceProjectDTOs = this.getProjectByMonth(dDate, duPic, projectId);

		// voi moi project ta lay tat ca cac resource
		for (ResourceProjectDTO projectDTO : resourceProjectDTOs) {

			// lay ca resource cùng info
			List<UserDTO> userDTOs = this.getUserByProjectId(projectDTO.getProjectId(), du, resourceName);

			// moi resource ta lay plan tuong ung voi thang duoc chon
			for (UserDTO userDTO : userDTOs) {

				// get plan allocation cua resource
				UserResourceDTO userPlanDetails = this.getUserPlanByUserIdAndMonth(userDTO.getId(),
						projectDTO.getProjectId(), monthAndYear);
				ResourceProjectDTO projectDTOadd = new ResourceProjectDTO(projectDTO.getProjectId(),
						projectDTO.getProjectName(), projectDTO.getProjectType(), projectDTO.getProjectCode(),
						projectDTO.getDeliveryUnit(), projectDTO.getStatus(), projectDTO.getEndDate(),
						projectDTO.getStartDate(), this.planAllocation(totalWorkingDay, userPlanDetails.getManDay()));
				ProjectResourceAllocationDTO resourceAllocationDTO = new ProjectResourceAllocationDTO(userDTO.getId(),
						userDTO.getFullName(), userDTO.getDeliveryUnit(), userDTO.getRole(), projectDTOadd);

				projectResourceAllocationDTOs.add(resourceAllocationDTO);
			}
		}
		return projectResourceAllocationDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getAllProjects()
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<ResourceFilterDto> getAllProjects(int userId) {
		List<Integer> checkRole = new ArrayList<Integer>();
		checkRole = userService.checkRoleViewListProject(userId); 
		if( checkRole.contains(BigInteger.valueOf(1))) {
	    	return ConvertDto.convertResourceFilterDtos(projectDashBoardReposioty.getAllProjectToFilter());
		} else if(!checkRole.contains(BigInteger.valueOf(3)) && checkRole.contains(BigInteger.valueOf(2))) {
			return ConvertDto.convertResourceFilterDtos(projectDashBoardReposioty.getAllProjectOfPmToFilter(userId));
		} else {	
			return ConvertDto.convertResourceFilterDtos(projectDashBoardReposioty.getAllProjectOfDulToFilter(userId));
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getAllMembers()
	 */
	@Override
	public List<ResourceFilterDto> getAllMembers() {
		return ConvertDto.convertResourceFilterDtos(userQmsRepository.getAllUserToFilter());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#getAllDus()
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<String> getAllDus(int userId) {
		List<Integer> checkRole = userService.checkRoleViewListProject(userId); 
		if( checkRole.contains(BigInteger.valueOf(1))) {
		   return deliverUnitReposioty.getAllDeliveruUnitName();
		} else if(checkRole.contains(BigInteger.valueOf(3)) && !checkRole.contains(BigInteger.valueOf(2))) {
            List<String> dus = new ArrayList<>();
            dus.add(deliverUnitReposioty.getGroupNameByUserId(userId));
            return dus;
		} else {
			 Set<String> temp = new HashSet<>();
			 temp = userService.getListGroupByPm(userId);
			 List<String> dus = new ArrayList<>(temp);
			return dus;
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#getResources(com.cmc.dashboard.util
	 * .filter.FilterResource, java.lang.String, int, int)
	 */
	@Override
	public Page<ResourceRestDTO> getResources(final FilterResource filter, final String typeTime, final int page,
			final int numberPerPage,final int userId) {
		// get all user
		final List<ResourceRestDTO> resources = getAllUsers(filter, page, numberPerPage,userId);

		// if resource != empty then get infor project and manpower
		if (!MethodUtil.checkList(resources)) {
			// get list userId of list resources
			List<Integer> userIds = resources.stream().map(ResourceRestDTO::getUserId).collect(Collectors.toList());

			// get all projects of list userId
			getAllProjectOfUser(resources, filter, userIds);
			
			//get all skill of list user
			getAllSkillOfUser(resources,filter,userIds);

			// get list projectId of list resources
			List<Integer> projectIds = getAllProjectIdOfResource(resources);
            
			// if projectIds != empty then get all time of project
			if (!MethodUtil.checkList(projectIds)) {
				// get spent time
				//getSpentTimeByProjects(resources, filter, typeTime, userIds, projectIds);
				
				// get estimate time
				//getEstimateTimeByProjects(resources, filter, typeTime, userIds, projectIds);

				// get plan time
				getPlanTimeByProjects(resources, filter, typeTime, userIds, projectIds);
			}

			// get man power
			//getManPowerByUsers(resources, filter, typeTime, userIds);
		}
		// return result
		return new PageImpl<>(resources, new PageRequest(page - 1, numberPerPage), getCountResources(filter));
	}

	/**
	 * get All ProjectId Of Resource
	 * 
	 * @param resources
	 * @return List<Integer>
	 * @author: NNDuy
	 */
	private List<Integer> getAllProjectIdOfResource(List<ResourceRestDTO> resources) {
		List<Integer> projectIds = new ArrayList<>();
		for (ResourceRestDTO resource : resources) {
			for (ResourceProjectRestDto project : resource.getProjects()) {
				projectIds.add(project.getProjectId());
			}
		}
		// convert set to list
		return new ArrayList<>(projectIds);
	}

	/**
	 * set filter all user
	 * 
	 * @param filter
	 *            void
	 * @author: NNDuy
	 */
	@SuppressWarnings("unlikely-arg-type")
	private void setFilterAllUser(final FilterResource filter,int userId) {
		List<Integer> checkRole = userService.checkRoleViewListProject(userId);
		if( checkRole.contains(BigInteger.valueOf(1))) {
			filter.setUserIds(userDashBoardRepository.getAllUserIds(filter.getFromDate(),filter.getToDate()));
		} else if(checkRole.contains(BigInteger.valueOf(3)) && !checkRole.contains(BigInteger.valueOf(2))) {
			 filter.setUserIds(userDashBoardRepository.getListUserOfDu(userId,filter.getFromDate(),filter.getToDate()));
		} else {
			filter.setUserIds(userDashBoardRepository.getUserInProjectOfPm(userId,filter.getFromDate(),filter.getToDate()));
		}
	}

	/**
	 * set search user by userName
	 * 
	 * @param filter
	 *            void
	 * @author: NNDuy
	 */
	private void setSearchUserByUserName(final FilterResource filter) {
		try {
		filter.setUserIds(userDashBoardRepository.getUserIdByUserName(filter.getUserIds(), filter.getUserNameSearch().trim()));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * set filter all user
	 * 
	 * @param filter
	 *            void
	 * @author: NNDuy
	 */
	private void setFilterAllDU(final FilterResource filter) {
		filter.setUserIds(userDashBoardRepository.getAllUserOfDU(filter.getDuNames(), filter.getUserIds()));
	}

	/**
	 * set filter all project
	 * 
	 * @param filter
	 *            void
	 * @author: NNDuy
	 */
	private void setFilterAllProject(final FilterResource filter, final List<Integer> userIds) {
		try {
		filter.setProjectIds(userDashBoardRepository.getAllProjectIdOfUser(userIds));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * get count of resource
	 * 
	 * void
	 * 
	 * @author: NNDuy
	 */
	private int getCountResources(final FilterResource filter) {
		// nếu không có user nào
		if (MethodUtil.checkList(filter.getUserIds())) {
			return 0;
		}

		// nếu có user thì query data
		try {
		return userDashBoardRepository.getCountAllUsers(filter.getUserIds());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * get all user not admin
	 * 
	 * @param page
	 * @param numberPerPage
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getAllUsers(final FilterResource filter, final int page, final int numberPerPage,final int userId) {
		// if filter user == empty then get all user
		if (MethodUtil.checkList(filter.getUserIds())) {
			setFilterAllUser(filter,userId);
		}

		// if search userName != empty then get
		if (!StringUtils.isEmpty(filter.getUserNameSearch())) {
			setSearchUserByUserName(filter);
		}

		// if filter DU != empty then get all user
		if (!MethodUtil.checkList(filter.getUserIds()) && !MethodUtil.checkList(filter.getDuNames())) {
			setFilterAllDU(filter);
		}

		// filter user thuộc project
		if (!MethodUtil.checkList(filter.getUserIds()) && !MethodUtil.checkList(filter.getProjectIds())) {
			setFilterUserOfProject(filter);
		}

		// nếu không có user nào
		if (MethodUtil.checkList(filter.getUserIds())) {
			return new ArrayList<>();
		}
		// nếu có user thì query data
		try {
		return ConvertDto.initResourceDtos(userDashBoardRepository.getAllUsers(numberPerPage,
				MethodUtil.getOffsetForPaging(page, numberPerPage), filter.getUserIds()));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * filter user of project
	 * 
	 * @param filter
	 *            void
	 * @author: NNDuy
	 */
	private void setFilterUserOfProject(FilterResource filter) {
		Set<Integer> temp = new HashSet<>(userDashBoardRepository.getAllUserOfProject(filter.getUserIds(), filter.getProjectIds(),filter.getFromDate()));
		List<Integer> userids = new ArrayList<>(temp);
		filter.setUserIds(userids);
	}

	/**
	 * get all project of user not admin
	 * 
	 * @param page
	 * @param numberPerPage
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getAllProjectOfUser(final List<ResourceRestDTO> resources,
			final FilterResource filter, final List<Integer> userIds) {
		// if filter projects == empty then get all project
		if (MethodUtil.checkList(filter.getProjectIds())) {
			setFilterAllProject(filter, userIds);
		}
		if((filter.getProjectIds().size() !=0 && filter.getProjectIds().get(0) == null) || MethodUtil.checkList(filter.getProjectIds())){
			try {
			return ConvertDto.convertResourceDtos(resources,
					userDashBoardRepository.getAllProjectOfUser(null, userIds),
					TypeResourceConvert.PROJECT,
					null, null);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
//			List<Object> projectByUser =
			try {
		return ConvertDto.convertResourceDtos(resources,
				userDashBoardRepository.getAllProjectOfUser(filter.getProjectIds(), userIds),
				TypeResourceConvert.PROJECT,
				null, null);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private List<ResourceRestDTO> getAllSkillOfUser(final List<ResourceRestDTO> resources,final FilterResource filter,final List<Integer> userIds){
		List<Object> data = new ArrayList<>();
		try {
		data = userDashBoardRepository.listSkillUser();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();;
		}
		 return ConvertDto.convertResourceDtos(resources,data,TypeResourceConvert.SKILL, null, null);
	}

	/**
	 * get spent time user by project
	 * 
	 * @param resources
	 * @param filter
	 * @param typeTime
	 * @param page
	 * @param numberPerPage
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getSpentTimeByProjects(final List<ResourceRestDTO> resources,
			final FilterResource filter, final String typeTime, final List<Integer> userIds,
			final List<Integer> projectIds) {
		try {
		return ConvertDto.convertResourceDtos(resources,
				projectUserRepostitory.getSpentTimes(filter.getFromDate(), filter.getToDate(), projectIds, userIds),
				TypeResourceConvert.SPENT_TIMES, typeTime, filter.getToDate());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get estimate time user by project
	 * 
	 * @param resources
	 * @param filter
	 * @param typeTime
	 * @param page
	 * @param numberPerPage
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getEstimateTimeByProjects(final List<ResourceRestDTO> resources,
			final FilterResource filter, final String typeTime, final List<Integer> userIds,
			final List<Integer> projectIds) {
		try {
		return ConvertDto.convertResourceDtos(resources,
				projectTaskRepository.getEstimateTimes(filter.getFromDate(), filter.getToDate(), projectIds, userIds),
				TypeResourceConvert.ESTIMATE_TIMES, typeTime, filter.getToDate());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get plan time user by project
	 * 
	 * @param resources
	 * @param filter
	 * @param typeTime
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getPlanTimeByProjects(final List<ResourceRestDTO> resources,
			final FilterResource filter, final String typeTime, final List<Integer> userIds,
			final List<Integer> projectIds) {
		//List<Object> test = userPlanRepository.getPlanTimes(filter.getFromDate(), filter.getToDate(), projectIds, userIds);
		try {
		return ConvertDto.convertResourceDtos(resources,
				userPlanRepository.getPlanTimes(filter.getFromDate(), filter.getToDate(), projectIds, userIds),
				TypeResourceConvert.PLAN_TIMES, typeTime, filter.getToDate());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get manpower user by project
	 * 
	 * @param resources
	 * @param filter
	 * @param typeTime
	 * @return List<ResourceRestDTO>
	 * @author: NNDuy
	 */
	private List<ResourceRestDTO> getManPowerByUsers(final List<ResourceRestDTO> resources, final FilterResource filter,
			final String typeTime, final List<Integer> userIds) {
		try {
		return ConvertDto.convertResourceDtos(resources,
				manPowerRepository.getManPowerByUsers(filter.getFromDate(), filter.getToDate(), userIds),
				TypeResourceConvert.MAN_POWER, typeTime, filter.getToDate());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author tvhuan
	 */

	@Override
	public Page<ResourceAllocationQms> getRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId) {
		Pageable pageable;
		if (CustomValueUtil.ASC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, CustomValueUtil.PAGE_SIZE,
					new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_QMS + column));
		} else {
			pageable = new PageRequest(page - 1, CustomValueUtil.PAGE_SIZE,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_QMS + column));
		}
		return resourceAllocationQmsRepository.getRessourceAllocation(pageable, dateym, resName, du, duPic, projectId);
	}

	@Override
	public Page<ResourceAllocationQms> getAllRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId) {
		Pageable pageable;
		if (CustomValueUtil.ASC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, CustomValueUtil.MAX_PAGE_SIZE,
					new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_QMS + column));
		} else {
			pageable = new PageRequest(page - 1, CustomValueUtil.MAX_PAGE_SIZE,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_QMS + column));
		}
		return resourceAllocationQmsRepository.getRessourceAllocation(pageable, dateym, resName, du, duPic, projectId);
	}

	/**
	 * 
	 * @author tvhuan
	 */
	@Override
	public Page<ResourceAllocationDb> getAllallocation(int page, String column, String sort, String dateym,
			String datemy) {
		Pageable pageable;
		if (CustomValueUtil.ASC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, CustomValueUtil.MAX_PAGE_SIZE,
					new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_DASBOARD + column));
		} else {
			pageable = new PageRequest(page - 1, CustomValueUtil.MAX_PAGE_SIZE,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_DASBOARD + column));
		}
		return null;
	}

	/**
	 * set data dashboard vao page Qms
	 * 
	 * @author tvhuan
	 */
	@Override
	public Page<ResourceAllocationQms> getListResourceQms(Page<ResourceAllocationDb> pageDbs,
			Page<ResourceAllocationQms> pageQmss) {
		for (ResourceAllocationQms pageQms : pageQmss.getContent()) {
			for (ResourceAllocationDb pageDb : pageDbs.getContent()) {
				if (pageDb.getUserId() == pageQms.getUser_id() && pageDb.getProjectId() == pageQms.getProject_id()) {
					pageQms.setAllocation(pageDb.getAllocation());
					pageQms.setPlanAllocation(pageDb.getPlanAllocation());
				} else if (pageDb.getUserId() == pageQms.getUser_id() && pageDb.getProjectId() == 0) {
					pageQms.setPlanAllocation(pageDb.getPlanAllocation());
				}
			}
		}
		return pageQmss;
	}

	/**
	 * set data dashboard vao page Qms trong th sort theo allocation và
	 * planAllocation
	 * 
	 * @author tvhuan
	 */
	@Override
	public Page<ResourceAllocationQms> getListResourceDashboard(Page<ResourceAllocationQms> pageQmss,
			List<ResourceAllocationDb> listAllDbs, List<ResourceAllocationQms> listAllQmss, String sort, String column,
			int page) {

		for (ResourceAllocationQms listAllQms : listAllQmss) {
			for (ResourceAllocationDb listAllDb : listAllDbs) {
				if (listAllDb.getUserId() == listAllQms.getUser_id()
						&& listAllDb.getProjectId() == listAllQms.getProject_id()) {
					listAllQms.setAllocation(listAllDb.getAllocation());
					listAllQms.setPlanAllocation(listAllDb.getPlanAllocation());
				} else if (listAllDb.getUserId() == listAllQms.getUser_id() && listAllDb.getProjectId() == 0) {
					listAllQms.setPlanAllocation(listAllDb.getPlanAllocation());
				}
			}
		}
		listAllQmss = new ArrayList<>(listAllQmss);
		this.sort(listAllQmss, sort, column);
		int i = page * 20;
		for (ResourceAllocationQms pageQms : pageQmss.getContent()) {
			pageQms.setUser_id(listAllQmss.get(i).getUser_id());
			pageQms.setProject_id(listAllQmss.get(i).getProject_id());
			pageQms.setSdateP(listAllQmss.get(i).getSdateP());
			pageQms.setEdateP(listAllQmss.get(i).getEdateP());
			pageQms.setDu(listAllQmss.get(i).getDu());
			pageQms.setUsername(listAllQmss.get(i).getUsername());
			pageQms.setRole(listAllQmss.get(i).getRole());
			pageQms.setDuPic(listAllQmss.get(i).getDuPic());
			pageQms.setName(listAllQmss.get(i).getName());
			pageQms.setProjectCode(listAllQmss.get(i).getProjectCode());
			pageQms.setProjectType(listAllQmss.get(i).getProjectType());
			pageQms.setStatus(listAllQmss.get(i).getStatus());
			pageQms.setPlanAllocation(listAllQmss.get(i).getPlanAllocation());
			pageQms.setAllocation(listAllQmss.get(i).getAllocation());
			i++;
		}
		return pageQmss;
	}

	/**
	 * @param listContent
	 * @param sort
	 * @param column
	 */
	private void sort(List<ResourceAllocationQms> listContent, String sort, String column) {
		try {
			Collections.sort(listContent, new Comparator<ResourceAllocationQms>() {
				@Override
				public int compare(ResourceAllocationQms fruit2, ResourceAllocationQms fruit1) {
					if (CustomValueUtil.ASC.equals(sort) && CustomValueUtil.RESOURCE_COLUMN_ALLOC.equals(column)) {
						return (int) (fruit2.getAllocation() - fruit1.getAllocation());
					} else if (CustomValueUtil.ASC.equals(sort)
							&& CustomValueUtil.RESOURCE_COLUMN_PLANALLOC.equals(column)) {
						return (int) (fruit2.getPlanAllocation() - fruit1.getPlanAllocation());
					} else if (CustomValueUtil.DESC.equals(sort)
							&& CustomValueUtil.RESOURCE_COLUMN_ALLOC.equals(column)) {
						return (int) (fruit1.getAllocation() - fruit2.getAllocation());
					}
					return (int) (fruit1.getPlanAllocation() - fruit2.getPlanAllocation());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ResourceService#exportUnallocations(int, int,
	 * int, int, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public UnallocationListDTO exportUnallocations(int month, int year, int page, int size, String column, String sort,
			String resourceName, String status, String deliveryUnit) {
		List<UnallocationDTO> pageUnallocations = new ArrayList<>();
		List<UnallocationDTO> qmsUserUnallocations = new ArrayList<>();
		List<UnallocationDTO> unallocationDTOafter;
		UnallocationListDTO unallocationListDTO;

		YearMonth yearMonth = YearMonth.of(year, month);
		String monthYear = yearMonth.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_MONTH_YEAR));

		// get all delivery unit
		List<String> listDu = customValueRepository.getAllDu();
		List<QmsUserUnallocation> getAllUserAndDuNames = userUnallocationRepository.getAllUserAndDuNames(deliveryUnit,
				resourceName.trim());
		if (MethodUtil.checkList(getAllUserAndDuNames)) {
			return new UnallocationListDTO(pageUnallocations, listDu, getAllUserAndDuNames.size());
		}
		// get manday and userId
		List<Object> mandays = userPlanRepository.getManDayByMonthYear(monthYear);
		// get allocation value
		List<ManPower> manPowers = manPowerRepository.getManPowerByMonth(monthYear);

		List<ResourcePlanUtilizationDTO> handleManDay = this.getManDayPlans(mandays);
		Map<Integer, List<ManPower>> getManPowerOfUser = this.getManPowerOfUser(manPowers);
		List<ResourcePlanUtilizationDTO> manDayPowers = getTotalManpowerOfUser(getManPowerOfUser, yearMonth);
		qmsUserUnallocations = addUserToUnallocationDTO(getAllUserAndDuNames, qmsUserUnallocations);

		unallocationDTOafter = getUnlocationUsers(handleManDay, manDayPowers, qmsUserUnallocations).stream()
				.filter(user -> user.getAllocation() > Constants.Numbers.MANDAY_ZERO).collect(Collectors.toList());
		pageUnallocations = filterUnallocation(status, column, sort, unallocationDTOafter, pageUnallocations,
				handleManDay, manDayPowers).getUnallocationDTO();

		unallocationListDTO = new UnallocationListDTO(pageUnallocations, listDu, filterUnallocation(status, column,
				sort, unallocationDTOafter, pageUnallocations, handleManDay, manDayPowers).getTotalElements());
		return unallocationListDTO;
	}

	@Override
	public Page<ResourceAllocationDb> exportAllallocation(int page, String column, String sort, String dateym,
			String datemy) {
		Pageable pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
				new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_DASBOARD + column));
		if (CustomValueUtil.DESC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_DASBOARD + column));
		}
//		return resourceAllocationRepository.getResourceAllocation(pageable, dateym, datemy);
		return null;
	}

	/*
	 * @see com.cmc.dashboard.service.ResourceService#exportRessourceAllocation(int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<ResourceAllocationQms> exportRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId) {
		Pageable pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
				new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_QMS + column));
		if (CustomValueUtil.DESC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_QMS + column));
		}
		return resourceAllocationQmsRepository.getRessourceAllocation(pageable, dateym, resName, du, duPic, projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#exportAllRessourceAllocation(int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Page<ResourceAllocationQms> exportAllRessourceAllocation(int page, String column, String sort, String dateym,
			String resName, String du, String duPic, String projectId) {
		Pageable pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
				new Sort(Sort.Direction.ASC, CustomValueUtil.DB_SORT_QMS + column));
		if (CustomValueUtil.DESC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, Constants.Numbers.MAX_SIZE_EXPORT,
					new Sort(Sort.Direction.DESC, CustomValueUtil.DB_SORT_QMS + column));
		}
		return resourceAllocationQmsRepository.getRessourceAllocation(pageable, dateym, resName, du, duPic, projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#saveAllocation(com.cmc.dashboard.
	 * dto.ResourcesAllocationRestDTO)
	 */
	@Override
	@Transactional
	public UserPlan saveAllocation(ResourcesAllocationRestDTO allocation) throws ParseException {
		UserPlan plan = allocation.toEntity(allocation.getCheckWeekend());
	    int projectId = plan.getProjectId();
	    Project project = projectDashBoardReposioty.getProjectByProjectId(projectId);
	    int roleId = Integer.parseInt(plan.getRole());
//	    String namePm = 
		//plan.setRole(userQmsRepository.getRoleByUserId(plan.getUserId()));
		if (allocation.getCheckWeekend()) {

			if (plan.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.getDayOfWeek() == DayOfWeek.SATURDAY) {
				plan.setFromDate(Date.from(plan.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.plusDays(Constants.Numbers._2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
			if (plan.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.getDayOfWeek() == DayOfWeek.SUNDAY) {
				plan.setFromDate(Date.from(plan.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.plusDays(Constants.Numbers._1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
			if (plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.getDayOfWeek() == DayOfWeek.SATURDAY) {
				plan.setToDate(Date.from(plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.minusDays(Constants.Numbers._1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
			if (plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
					.getDayOfWeek() == DayOfWeek.SUNDAY) {
				plan.setToDate(Date.from(plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.minusDays(Constants.Numbers._2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
			long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDateWeekend(plan.getFromDate(),
					plan.getToDate(), allocation.getCheckWeekend());
			int numberDayOfFirstRecord = Constants.Numbers._6 - plan.getFromDate().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().getValue();

			if (numberDayOfFirstRecord >= totalWorkingDay) {
				plan = userPlanRepository.save(plan);
				plan.setUserPlanDetails(userPlanService.insertUserPlanDetail(plan));
			} else {
				int numberDayOfLastRecord = plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.getDayOfWeek().getValue();
				int numberWeekFull = (int) (totalWorkingDay - numberDayOfFirstRecord - numberDayOfLastRecord)
						/ Constants.Numbers._5;

				UserPlan planFirst = new UserPlan(plan.getUserId(), plan.getProjectId(), plan.getEffortPerDay(),
						plan.getFromDate(),
						Date.from(plan.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
								.plusDays(numberDayOfFirstRecord - Constants.Numbers._1)
								.atStartOfDay(ZoneId.systemDefault()).toInstant()),
						allocation.getCheckWeekend(),allocation.getSkill(),allocation.getRole());
				planFirst.setRole(plan.getRole());
				userPlanRepository.save(planFirst);
				planFirst.setUserPlanDetails(userPlanService.insertUserPlanDetail(planFirst));
				UserPlan planLast = new UserPlan(plan.getUserId(), plan.getProjectId(), plan.getEffortPerDay(),
						Date.from(plan.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
								.minusDays(numberDayOfLastRecord - Constants.Numbers._1)
								.atStartOfDay(ZoneId.systemDefault()).toInstant()),
						plan.getToDate(), allocation.getCheckWeekend(),allocation.getSkill(),allocation.getRole());
				planLast.setRole(plan.getRole());
				userPlanRepository.save(planLast);
				planLast.setUserPlanDetails(userPlanService.insertUserPlanDetail(planLast));
				Date startingPointDate = plan.getFromDate();
				for (int i = 0; i < numberWeekFull; i++) {
					LocalDate fromDate = startingPointDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
							.with(DayOfWeek.MONDAY).plusDays((i + Constants.Numbers._1) * Constants.Numbers._7);
					LocalDate toDate = fromDate.plusDays(Constants.Numbers._4);
					UserPlan planX = new UserPlan(plan.getUserId(), plan.getProjectId(), plan.getEffortPerDay(),
							Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
							Date.from(toDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
							allocation.getCheckWeekend(),allocation.getSkill(),allocation.getRole());
					planX.setRole(plan.getRole());
					userPlanRepository.save(planX);
					planX.setUserPlanDetails(userPlanService.insertUserPlanDetail(planX));
				}
			}

		} else {
			plan = userPlanRepository.save(plan);
			plan.setUserPlanDetails(userPlanService.insertUserPlanDetail(plan));
		}
		return plan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ResourceService#updateAllocation(com.cmc.dashboard.
	 * dto.ResourcesAllocationUpdateRestDTO)
	 */
	@Override
	public UserPlan updateAllocation(ResourcesAllocationUpdateRestDTO allocation) throws ParseException {
		// get userPlan by id
		UserPlan current = userPlanRepository.findOne(allocation.getPlanId());
		// update infor userPlan
		current.setInforUpdateUserPlan(allocation);

		// update table userPlan
		current = userPlanRepository.save(current);
		// update table userPlanDetail
		deleteUserPlanDetails(current.getUserPlanId());
		current.setUserPlanDetails(userPlanService.insertUserPlanDetail(current));
		return current;
	}

	/**
	 * delete userPlanDetail from userPlanId
	 * 
	 * @param userPlanId
	 *            void
	 * @author: NNDuy
	 */
	private void deleteUserPlanDetails(int userPlanId) {
		userPlanDetailRepository.deleteListUserPlanDetail(Arrays.asList(userPlanId));
	}

	@Override
	public void deleteResourcesAllocation(int id) {
		userPlanDetailRepository.deleteUserPlan(id);
		userPlanDetailRepository.deleteUserPlanDetail(id);
	}

	@Transactional
	@Override
	public ReAllocationTableDTO getRessourceAllAllocation(int page, String column, String sort, int month,int year ,String fullName,
			String du, String duPic, String projectName) {
		List<ReAllocationDTO> listRe= new ArrayList<ReAllocationDTO>();
		ReAllocationTableDTO reTable= new ReAllocationTableDTO();
		CalculateClass cal= new CalculateClass();
		int workingDay=cal.calculateWorkingDay(month, year);
		Page<Object> pageAllo=null;
		String sortName="u.full_name";
		if("full_name".equals(column))sortName="u.full_name";
		else if("project_name".equals(column)) sortName="p.project_name";
		 Sort sortable = null;
		    if (sort.equals("ASC")) {
		      sortable = new Sort(Sort.Direction.ASC, sortName);
		    } else {
		      sortable = new Sort(Sort.Direction.DESC, sortName);
		    }
	    Pageable pageable = new PageRequest(page-1,25,sortable);
	    try {
	    pageAllo =resourceAllocationRepository.getAllResourceAllocation(pageable,month,year,du,fullName,duPic,projectName);
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    List<Object> listObject=pageAllo.getContent();
	    List<UserPlan> listUserPlan = null;
	    if(listObject.size()>0) {
	    for(Iterator<Object> it = listObject.iterator(); it.hasNext();) {
	    	Object[] object = (Object[]) it.next();
	    	int userId=(int) object[1];
	    	int projectId=(int) object[0];
	    	String role=(String)object[10];
	    	double allocation=0.00d;
	    	float sumAlo =0;
	    	try {
	     
	        if(role!=null)
	        	listUserPlan=userPlanRepository.getListUserPlanByMonth(userId, projectId, month, year,role);
	        else listUserPlan =userPlanRepository.getListUserPlanByMonth(userId, projectId, month, year);
	    	}catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
			}
	    	for(UserPlan up : listUserPlan) 
	    		sumAlo+=cal.countWorkingDayInMonth(up.getFromDate(), up.getToDate(),month)*up.getEffortPerDay()/8;
	    	allocation=round((double)sumAlo/workingDay*100,2);
	    	try {
	    	ReAllocationDTO dto= new ReAllocationDTO((String)object[2],(String)object[3],(String)object[4],(String)object[5],(String)object[6],(String)object[7],(String)object[8],(byte)object[9],allocation);
	    	listRe.add(dto);
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	      }
	      if("allocation".equals(column)) {
	    	  if (sort.equals("ASC"))Collections.sort(listRe,ReAllocationDTO.ComparatorAlloAsc );
	    	  else Collections.sort(listRe,ReAllocationDTO.ComparatorAlloDesc);
	      }
	      reTable.setListAllo(listRe);
	    }
	    
	    try {
	      List<String> listDu= resourceAllocationRepository.getListDUMonth(month, year);
	      List<String> listDuPic=resourceAllocationRepository.getListDUPicMonth(month, year);
	      List<String> listProjectName=resourceAllocationRepository.getListProjectNameMonth(month, year);
	      reTable.setListDu(listDu);
	      reTable.setListDuPic(listDuPic);
	      reTable.setListProject(listProjectName);
	      reTable.setTotalElements(pageAllo.getTotalElements());
	    }  catch(Exception e) {
	      e.printStackTrace();
	    }
		return reTable;
	    }
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	/**
	 * format date sang dang MM-yyyy
	 *
	 * @param fromDate
	 */
	private Date formatDateMonthYear(Date fromDate) {
		SimpleDateFormat f = new SimpleDateFormat(Constants.DATE_FORMAT_MONTH_YEAR);
	    return MethodUtil.convertStringToDate(f.format(fromDate));
	}
	/**
	 * Kiem tra ngay <= ngay nao do
	 *
	 * @param date
	 * @param dateCompare
	 * @return boolean \n true : date <= dateCompare
	 * @author: HungNC
	 * @created: 2018-04-07
	 */
	private boolean isBeforeOrEqual(Date date, Date dateCompare) {
		if (date.before(dateCompare) || date.equals(dateCompare)) {
			return true;
		}
		return false;
	}
}
