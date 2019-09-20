package com.cmc.dashboard.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.BookResourcesDTO;
import com.cmc.dashboard.dto.DUOverviewDTO;
import com.cmc.dashboard.dto.DeliveryUnitDTO;
import com.cmc.dashboard.dto.DuInternalDuDTO;
import com.cmc.dashboard.dto.DuStatisticDTO;
import com.cmc.dashboard.dto.ManpowerTime;
import com.cmc.dashboard.dto.ProjectBillableDTO;
import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.dto.ProjectInDeliveryUnitDto;
import com.cmc.dashboard.dto.ResourcesAvailableDTO;
import com.cmc.dashboard.dto.UserManPowerDTO;
import com.cmc.dashboard.model.DUStatistic;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.IssuesRepository;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.DUStatisticRepository;
import com.cmc.dashboard.repository.DeliveryUnitRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectTypeLogRepository;
import com.cmc.dashboard.repository.SkillRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.google.gson.Gson;

import io.swagger.models.auth.In;

@Service
public class DeliveryUnitServiceImpl implements DeliveryUnitService {

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	CustomValueRepository customValueRepository;

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	MemberQmsRepository reMemberQmsRepository;

	@Autowired
	IssuesRepository issuesRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	ManPowerService manPowerService;

	@Autowired
	DeliveryUnitRepository deliveryUnitRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectTypeLogRepository projectTypeLogRepository ;
    
	@Autowired
	private DUStatisticRepository duStatisticRepository;
	
	private List<BookResourcesDTO> listUserBooked;
	private List<Integer> listUserAvailable;
	private List<Integer> listUserProject;

	@Override
	public List<DuInternalDuDTO> GetDeliveryUnitById(int month, int year) {
		List<DuInternalDuDTO> duInternalDuDTOs = new ArrayList<>();
		List<Group> groups = groupRepository.getListDUAndInternalDu();
		for (Group group : groups) {
			System.out.println(group.getGroupName());
			List<Object> listUserOfDu = deliveryUnitRepository.getAllUserDU(group.getGroupId(), month, year);
			List<Object> listUserInProject = deliveryUnitRepository.getListUserInProject(group.getGroupId(), month, year);
			DuInternalDuDTO duInternalDuDTO = new DuInternalDuDTO();
			duInternalDuDTO.setId(group.getGroupId());
			duInternalDuDTO.setGroup_name(group.getGroupName());
			 float[] manpowerOfDu = new float[6];
	         int [] numberOfUser = new int[6];
	         int [] booked = new int[6];
	         int [] available = new int[6];
	         float [] utilized = new float[6];
	         float [] manday = new float [6];
	         float [] billRate = new float[6];
            //manpower
            CalculateClass calculateClass = new CalculateClass();
    		int workDay = calculateClass.calculateWorkingDay(month, year);
			List<Object> listUserAndManpowerOfDu = deliveryUnitRepository.getListUserAndManpower(month, year,group.getGroupId());
			List<Object> listUserAndMandayOfDu = deliveryUnitRepository.getListUserAndManDay(month, year, group.getGroupId());
			List<Integer> listuser = new ArrayList<>();
			for(int i = 0 ; i< listUserAndManpowerOfDu.size() ; i++) {
				int userId = Integer.parseInt(((Object[])listUserAndManpowerOfDu.get(i))[0].toString());
				float manpower = Float.parseFloat(((Object[])listUserAndManpowerOfDu.get(i))[1].toString());
				int indexStartUserInlistManday = 0;
				indexStartUserInlistManday = getIndexUserInList(listUserAndMandayOfDu,userId);
				if(indexStartUserInlistManday == -1) { continue;}
                listuser.add(userId);
				int indexEndUserInlistManday = getIndexEndInList(listUserAndMandayOfDu,userId,indexStartUserInlistManday);
				int indexProjectStart = indexStartUserInlistManday;
				while (indexProjectStart <= indexEndUserInlistManday) {
					int projectId = Integer.parseInt(((Object[])listUserAndMandayOfDu.get(indexProjectStart))[1].toString());
					Project project = null;
					project = projectRepository.getProjectByProjectId(projectId);
					if(project != null) {
					int indexProjectEnd = getIndexProjectEnd(listUserAndMandayOfDu,indexEndUserInlistManday,indexProjectStart,projectId);
					float sumManDay = 0;
					for(int j = indexProjectStart ; j <= indexProjectEnd ; j++) {
					    sumManDay +=  Float.parseFloat(((Object[])listUserAndMandayOfDu.get(j))[2].toString());
					    manday[project.getType()] += Float.parseFloat(((Object[])listUserAndMandayOfDu.get(j))[2].toString());
					}
					manpowerOfDu[project.getType()] += (float) (manpower * sumManDay)/workDay;
					indexProjectStart = indexProjectEnd + 1;
				  } else {
					  break;
				  }
				}
			}
			float manPowerUserNoWork = 0.0f;
			for(Object obj : listUserOfDu) {
				Integer userId = Integer.parseInt(obj.toString());
				if(!listuser.contains(userId)) {
                   for(Object obj1 : listUserAndManpowerOfDu) {
                	   if(userId == Integer.parseInt(((Object[])obj1)[0].toString())) {
                		  manPowerUserNoWork += Float.parseFloat(((Object[])obj1)[1].toString());
                	   }
                   }
				}
			}
			if(group.isDevelopmentUnit() && !group.isInternalDu()) {
			  manpowerOfDu[Constants.Numbers.PROJECT_TYPE_POOL] += (float) (manPowerUserNoWork); 
			} else if(group.isInternalDu()){
				manpowerOfDu[Constants.Numbers.PROJECT_TYPE_INVESTMENT] += (float) (manPowerUserNoWork);
			}
			//Utilized
			float sumManday = 0;
			float sumManPower = 0;
			for(int i = 1 ;i <= 4; i++ ) {
				sumManday += manday[i];
				sumManPower += manpowerOfDu[i];
				if(manpowerOfDu[i] !=0) {
			       utilized[i] = (float)(manday[i]/(manpowerOfDu[i] * workDay));
				}
			}
			//get summanday
			duInternalDuDTO.setManday(sumManday);
			duInternalDuDTO.setWorkDay(workDay);
			if(sumManPower != 0) duInternalDuDTO.setUtilized((float)sumManday /(sumManPower * workDay));
			else duInternalDuDTO.setUtilized(0.0f);
			//numberofuser
			for(int i = 0 ; i < listUserOfDu.size() ; i++) {
				int userId = Integer.parseInt((listUserOfDu.get(i).toString()));
				int indexUserStartInListUserInProject = getIndexUserInList(listUserInProject,userId);
				if(indexUserStartInListUserInProject == -1) {continue;}
				int indexUserEndInListUserInProject = getIndexEndInList(listUserInProject,userId,indexUserStartInListUserInProject);
				List<Integer> listType = new ArrayList<>();
				for(int j = indexUserStartInListUserInProject; j <= indexUserEndInListUserInProject ; j++) {
					int projectId = Integer.parseInt(((Object[])listUserInProject.get(j))[1].toString());
					Project project = projectRepository.getProjectByProjectId(projectId);
					if(project != null) {
					int type = project.getType();
					listType.add(type);
					if(type == 4) {
						booked[Constants.Numbers.PROJECT_TYPE_POOL] ++;
					  }
					} else {
						break;
					}
				}
				//Available book
			    //7day cuoi thang k co viec thang sau co viec => book
				//7day cuoi thang k co viec thang sau khong viec => available
				// available - du internal la nhu ng k co viec
				 Calendar calendar = Calendar.getInstance();
				 calendar.set(year, month - 1  , 1);
				 calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				 Date date = calendar.getTime();
				 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				 String endDate = format.format(date);
				 LocalDate temp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				 LocalDate before7days = temp.minusDays(7);
				 String fromDate = before7days.toString();
				 Integer checkLast7Day = deliveryUnitRepository.checkLast7Day(fromDate,endDate,group.getGroupId(),userId);
				 if(checkLast7Day == 0) {
					 if(month + 1 > 12) {
						 year ++;
					 }
					 Integer isWork = deliveryUnitRepository.checkNextMonthIsWork((month + 1) % 12,year,userId);
					if(isWork != 0) {
						booked[Constants.Numbers.PROJECT_TYPE_POOL] ++;
					} else if(!listType.contains(Constants.Numbers.PROJECT_TYPE_PROFIT) && !listType.contains(Constants.Numbers.PROJECT_TYPE_START) && group.isInternalDu()) {
						available[Constants.Numbers.PROJECT_TYPE_INVESTMENT] ++;
					}
				}
			    //
				if(listType.contains(Constants.Numbers.PROJECT_TYPE_PROFIT)) {
					numberOfUser[Constants.Numbers.PROJECT_TYPE_PROFIT] ++;
					
				} else if (listType.contains(Constants.Numbers.PROJECT_TYPE_START)) {
					numberOfUser[Constants.Numbers.PROJECT_TYPE_START] ++;
					
				} else if (listType.contains(Constants.Numbers.PROJECT_TYPE_POOL)) {
					numberOfUser[Constants.Numbers.PROJECT_TYPE_POOL] ++;
					
				} else {
					numberOfUser[Constants.Numbers.PROJECT_TYPE_INVESTMENT] ++;
					
				  }
			   }
				int numberUserInProject = 0;
				for(int i = 0 ; i < numberOfUser.length ; i++) {
					numberUserInProject += numberOfUser[i];
			    }
				if(group.isDevelopmentUnit() && !group.isInternalDu()) {
					numberOfUser[Constants.Numbers.PROJECT_TYPE_POOL] += listUserOfDu.size() - numberUserInProject;
				} else if(group.isInternalDu()){
					numberOfUser[Constants.Numbers.PROJECT_TYPE_INVESTMENT] += listUserOfDu.size() - numberUserInProject;
					available[Constants.Numbers.PROJECT_TYPE_INVESTMENT] += listUserOfDu.size() - numberUserInProject;
				}
				
				//billrate
				float sumBillable = 0.0f;
				for(int i = 0 ; i< listUserInProject.size(); i ++) {
					int projectId = Integer.parseInt(((Object[])listUserInProject.get(i))[1].toString());
					Project project = projectRepository.getProjectByProjectId(projectId);
					if(project != null) {
					float billable= 0.0f;
					 billable = deliveryUnitRepository.getDeliveryUnitProjectBillable(group.getGroupId(),projectId,month,year);
					 sumBillable += billable;
					if(manpowerOfDu[project.getType()] != 0.0) {
					   if(project != null) {
					   billRate[project.getType()] += (float) billable / manpowerOfDu[project.getType()];
					   }
					  }
					} else {
						break;
					}
				}
				duInternalDuDTO.setBillable(sumBillable);
				for(int i = 1;i <= 4;i ++) {
					if(i==1) {
						duInternalDuDTO.setProfit(new DuStatisticDTO(manpowerOfDu[1],numberOfUser[1],utilized[1],billRate[1],booked[1],available[1]));
					} else if (i == 2) {
						duInternalDuDTO.setStart(new DuStatisticDTO(manpowerOfDu[2],numberOfUser[2],utilized[2],billRate[2],booked[2],available[2]));	
					} else if ( i== 3) {
						duInternalDuDTO.setInvestment(new DuStatisticDTO(manpowerOfDu[3],numberOfUser[3],utilized[3],billRate[3],booked[3],available[3]));
					} else {
						duInternalDuDTO.setPool(new DuStatisticDTO(manpowerOfDu[4],numberOfUser[4],utilized[4],billRate[4],booked[4],available[4]));
					}
				}
				duInternalDuDTO.setTotalUser(listUserOfDu.size());
				duInternalDuDTOs.add(duInternalDuDTO);
		}
		return duInternalDuDTOs;
	}
	
	@Override
	public List<DuInternalDuDTO> GetDUStatistic(String startDateMonth, String endDateMonth, String monthDate) throws ParseException {
		Map<String, DUOverviewDTO> duOverviewDTOs = new HashMap<String, DUOverviewDTO>();
		List<Group> groups = groupRepository.getListDUAndInternalDu();	
			
		for (Group group : groups) {
			DUOverviewDTO duOverviewDTO = new DUOverviewDTO();
			
			duOverviewDTO.setGroupId(group.getGroupId());
			duOverviewDTO.setMonth(monthDate);
			
			duOverviewDTOs.put(String.valueOf(group.getGroupId()), duOverviewDTO);
		}
		// Define DU Overview all DU
		int allDUID = 0;
		DUOverviewDTO duOverviewDTO = new DUOverviewDTO();
		
		duOverviewDTO.setGroupId(allDUID);
		duOverviewDTO.setMonth(monthDate);
		
		duOverviewDTOs.put(String.valueOf(allDUID), duOverviewDTO);
		
		CalculateClass calculateClass = new CalculateClass();
		
		String[] parseMonth = monthDate.split("-");
		int month = Integer.parseInt(parseMonth[0]);
		int year = Integer.parseInt(parseMonth[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endateCheck = sdf.parse(endDateMonth);
		Date dateCheckAvailable = calculateClass.getLastNumberDate(endateCheck, 7);
		// Get woking day in current month
		int wokingDayInMonth =  calculateClass.countWorkingDay(sdf.parse(startDateMonth), sdf.parse(endDateMonth));
		// Get billable by DU
		List<Object> projectBillables = groupRepository.getBillableOfGroup(startDateMonth, endDateMonth);
		
		if (!projectBillables.isEmpty()) {
			for (int pb=0; pb<projectBillables.size(); pb++) {
				String group_bill_id = ((Object[])projectBillables.get(pb))[0].toString();
				Date pb_start_date = sdf.parse(((Object[])projectBillables.get(pb))[2].toString());
				Date pb_end_date = sdf.parse(((Object[])projectBillables.get(pb))[3].toString());
				
				String startDateBillableRange = null;
				String endDateBillableRange = null;
				float valueBillableRange = 0.0f;
				int workingDayBillRange = 0;
				int wokingDateBillable = calculateClass.countWorkingDay(pb_start_date, pb_end_date);
				List<String> dateRange = calculateClass.devideDateByMonth(pb_start_date, pb_end_date, month);
				if (!dateRange.isEmpty()) {
					String[] parseDateRange = dateRange.get(0).split("to");
					int wokingDateRange =  calculateClass.countWorkingDay(sdf.parse(parseDateRange[0]), sdf.parse(parseDateRange[1]));
					
					if (wokingDateBillable == wokingDateRange) {
						startDateBillableRange = ((Object[])projectBillables.get(pb))[2].toString();
						endDateBillableRange = ((Object[])projectBillables.get(pb))[3].toString();
						valueBillableRange = Float.parseFloat(((Object[])projectBillables.get(pb))[1].toString());;
					} else {
						startDateBillableRange = parseDateRange[0];
						endDateBillableRange = parseDateRange[1];
						
						valueBillableRange = (float) wokingDateRange*Float.parseFloat(((Object[])projectBillables.get(pb))[1].toString()) / wokingDateBillable;
					}
					workingDayBillRange = wokingDateRange;
					
					ManpowerTime mpt1 = new ManpowerTime();
					mpt1.setStartDate(sdf.parse(startDateBillableRange));
					mpt1.setEndDate(sdf.parse(endDateBillableRange));
					mpt1.setManPower(1);
					
					ManpowerTime upTime = new ManpowerTime();
					upTime.setStartDate(sdf.parse(startDateBillableRange));
					upTime.setEndDate(sdf.parse(endDateBillableRange));
					
					// Define list project type log range
					List<ManpowerTime> ptlTimes = new ArrayList<>();
					// Check by project type log
					List<Object> projectTypeLogByBillables = projectTypeLogRepository.getByProject(startDateMonth, endDateMonth, Integer.parseInt(((Object[])projectBillables.get(pb))[4].toString()));
					if (!projectTypeLogByBillables.isEmpty()) {
						for(int ptlBill=0; ptlBill<projectTypeLogByBillables.size(); ptlBill++) {
							Date start_ptl_bill = sdf.parse(((Object[])projectTypeLogByBillables.get(ptlBill))[2].toString());
							Date end_ptl_bill = sdf.parse(((Object[])projectTypeLogByBillables.get(ptlBill))[3].toString());
							// Define man_power time by project type log
							ManpowerTime ptl_mp_time = new ManpowerTime();
							ptl_mp_time.setStartDate(start_ptl_bill);
							ptl_mp_time.setEndDate(end_ptl_bill);
							ptl_mp_time.setProjectType(Integer.parseInt(((Object[])projectTypeLogByBillables.get(ptlBill))[1].toString()));
							
							ptlTimes.add(ptl_mp_time);
						}
					}
					
					if (!ptlTimes.isEmpty()) {
						List<ManpowerTime> timeRangeBillByProjectType = calculateClass.devideManPowerByTime(mpt1, upTime, ptlTimes);
						for(ManpowerTime mptBill : timeRangeBillByProjectType) {
							float billProjectType = mptBill.getWorkingDay()*valueBillableRange/workingDayBillRange;
							float new_bill = duOverviewDTOs.get(group_bill_id).getListDuElement().get(mptBill.getProjectType()).getBillRate() + billProjectType;
							
							duOverviewDTOs.get(group_bill_id).getListDuElement().get(mptBill.getProjectType()).setBillRate(new_bill);
							
							// ALL DU
							try {
								float all_new_bill = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(mptBill.getProjectType()).getBillRate() + billProjectType;
								duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(mptBill.getProjectType()).setBillRate(all_new_bill);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							
						}
					}
				}
			}
		}
		
		// Get list user in group DU
		List<Object> users = userRepository.getUsers(startDateMonth, endDateMonth);
		for (int ui=0; ui<users.size(); ui++) {
			Boolean isAvailable = true; Boolean isMemberOfDU = false;
			
			// Init list project type log by user
			List<Integer> listProjectOfUser = new ArrayList<>();
			// Get all manpower of user
			List<Object> manPowerOfUser = userRepository.getManPowerUser(monthDate, Integer.parseInt(((Object[])users.get(ui))[0].toString()));
			// Define total manpower, total manday of user
			float totalManpowerOfUser = 0; int totalManDayByUser = 0; float totalManPowerUserUse = 0;
			
			if (!manPowerOfUser.isEmpty()) {
				for (int mp=0; mp<manPowerOfUser.size(); mp++) {
					totalManpowerOfUser += Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString());
				}
				// Get user_plan user
				List<Object> planOfUser =  userRepository.getPlanUser(startDateMonth, endDateMonth, Integer.parseInt(((Object[])users.get(ui))[0].toString()));
				String group_mp_id = "0";
				if (!planOfUser.isEmpty()) {
					for (int pu=0; pu<planOfUser.size(); pu++) {
						// Define man_power time by user plan
						ManpowerTime up_mp_time = new ManpowerTime();
						// Define project type log time 
						List<ManpowerTime> projectTypeLogTimes = new ArrayList<>();
						// Set time user plan
						Date start_up = sdf.parse(((Object[])planOfUser.get(pu))[3].toString());
						Date end_up = sdf.parse(((Object[])planOfUser.get(pu))[4].toString());
						
						up_mp_time.setStartDate(start_up);
						up_mp_time.setEndDate(end_up);
						
						// check available
						if (end_up.after(dateCheckAvailable)) isAvailable = false;
						
						// Get history change project type project
						List<Object> projectTypeLog = projectTypeLogRepository.getByProject(startDateMonth, endDateMonth, Integer.parseInt(((Object[])planOfUser.get(pu))[0].toString()));
						if (!projectTypeLog.isEmpty()) {
							for(int ptl=0; ptl<projectTypeLog.size(); ptl++) {
								Date start_ptl = sdf.parse(((Object[])projectTypeLog.get(ptl))[2].toString());
								Date end_ptl = sdf.parse(((Object[])projectTypeLog.get(ptl))[3].toString());
								// Define man_power time by project type log
								ManpowerTime ptl_mp_time = new ManpowerTime();
								ptl_mp_time.setStartDate(start_ptl);
								ptl_mp_time.setEndDate(end_ptl);
								ptl_mp_time.setProjectType(Integer.parseInt(((Object[])projectTypeLog.get(ptl))[1].toString()));
								
								projectTypeLogTimes.add(ptl_mp_time);
							}
						}
						
						
						// Get manpower by manpower range
						for (int mp=0; mp<manPowerOfUser.size(); mp++) {
							// Check is member of DU
							if (!Boolean.parseBoolean(((Object[])manPowerOfUser.get(mp))[7].toString())) isMemberOfDU = true;
							group_mp_id = ((Object[])manPowerOfUser.get(mp))[1].toString();
							
							// Set total manpower in DU
							duOverviewDTOs.get(group_mp_id).setTotalManpower(
									duOverviewDTOs.get(group_mp_id).getTotalManpower() + Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString())
								);
							
							// Set total manpower in ALL
							duOverviewDTOs.get(String.valueOf(0)).setTotalManpower(
									duOverviewDTOs.get(String.valueOf(0)).getTotalManpower() + Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString())
								);
							
							Date start_mp = sdf.parse(((Object[])manPowerOfUser.get(mp))[4].toString());
							Date end_mp = sdf.parse(((Object[])manPowerOfUser.get(mp))[5].toString());
							// Define man_power time by manpower
							ManpowerTime mp_time = new ManpowerTime();
							mp_time.setStartDate(start_mp);
							mp_time.setEndDate(end_mp);
							mp_time.setManPower(Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString()));
							int workingDayInManPowerRange = CalculateClass.countWorkingDay(start_mp, end_mp);
							mp_time.setWorkingDay(workingDayInManPowerRange);
							
							// Operation manpower detail
							List<ManpowerTime> manPowerDetails = calculateClass.devideManPowerByTime(mp_time, up_mp_time, projectTypeLogTimes);
							
							if (!manPowerDetails.isEmpty()) {
								
								for(ManpowerTime mpTime : manPowerDetails) {
									int project_type_id = mpTime.getProjectType();
									// Sum manpower in DU
									float new_manpower = duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).getManPower() + (float) mpTime.getManPower();
									duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).setManPower(new_manpower);
									
									//Sum manpower AllDU
									float all_new_manpower = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).getManPower() + (float) mpTime.getManPower();
									duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).setManPower(all_new_manpower);
									
									// Sum man day in DU
									int new_manDay = duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).getManDay() + mpTime.getWorkingDay();
									duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).setManDay(new_manDay);
									
									// Sum man day AllDU
									int all_new_manDay = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).getManDay() + mpTime.getWorkingDay();
									duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).setManDay(all_new_manDay);
									
									if (!Boolean.parseBoolean(((Object[])manPowerOfUser.get(mp))[7].toString())) {
										// Sum man day in DU
										float new_manDay_bill = duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).getManPowerBillable() + (float) mpTime.getManPower();
										duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).setManPowerBillable(new_manDay_bill);
										
										// Sum man day AllDU
										float all_new_manDay_bill = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).getManPowerBillable() + (float) mpTime.getManPower();
										duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).setManPowerBillable(all_new_manDay_bill);
									}
									totalManPowerUserUse += (float) mpTime.getManPower(); totalManDayByUser += mpTime.getWorkingDay();
									if (!listProjectOfUser.contains(project_type_id)) {
										listProjectOfUser.add(project_type_id);
									}
								}
							}
						}
					}
					
					// manpower va manday con lai cua user se dc them vao manday, manpower cua pool neu la user cua DU va investment neu la user Internal
					int manDayConLai = wokingDayInMonth - totalManDayByUser; 
					float manPowerConLai = totalManpowerOfUser - totalManPowerUserUse;
					if (isMemberOfDU) {
//						duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setManDay(
//									duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getManDay() + manDayConLai);
						duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setManPower(
								duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getManPower() + manPowerConLai);
						// ALL DU
//						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setManDay(
//								duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getManDay() + manDayConLai);
						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setManPower(
								duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getManPower() + manPowerConLai);
					} else {
//						duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setManDay(
//								duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getManDay() + manDayConLai);
						duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setManPower(
								duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getManPower() + manPowerConLai);
						// ALL DU
//						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setManDay(
//								duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getManDay() + manDayConLai);
						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setManPower(
								duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getManPower() + manPowerConLai);
					}
				} else {
					for (int mp=0; mp<manPowerOfUser.size(); mp++) {
						group_mp_id = ((Object[])manPowerOfUser.get(mp))[1].toString();
						int project_type_id = 0;
						// Checkif group is internal + manpower&man day => investment
						if (!Boolean.parseBoolean(((Object[])manPowerOfUser.get(mp))[7].toString())) {
							project_type_id = 3;
							isMemberOfDU = true;
						} else {
							project_type_id = 4;
						}
					
						// get man_day from_date->to_date manpower ranger
						Date start_mp = sdf.parse(((Object[])manPowerOfUser.get(mp))[4].toString());
						Date end_mp = sdf.parse(((Object[])manPowerOfUser.get(mp))[5].toString());
						int workingDayInManPowerRange = CalculateClass.countWorkingDay(start_mp, end_mp);
						// Sum manpower in DU
						float new_manpower = duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).getManPower() + Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString());
						duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).setManPower(new_manpower);
						// Sum manpower All DU
						float all_new_manpower = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).getManPower() + Float.parseFloat(((Object[])manPowerOfUser.get(mp))[2].toString());
						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).setManPower(all_new_manpower);
//						// Sum man day
//						int new_manDay = duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).getManDay() + workingDayInManPowerRange;
//						duOverviewDTOs.get(group_mp_id).getListDuElement().get(project_type_id).setManDay(new_manDay);
//						// Sum man day All DU
//						int all_new_manDay = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).getManDay() + workingDayInManPowerRange;
//						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(project_type_id).setManDay(all_new_manDay);
						
						if (!listProjectOfUser.contains(project_type_id)) {
							listProjectOfUser.add(project_type_id);
						}
					}
				}
				
				// Get person user
				int new_person = 0; int all_new_person =0;
				if(listProjectOfUser.contains(Constants.Numbers.PROJECT_TYPE_PROFIT)) {
					// In DU
					new_person = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_PROFIT).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_PROFIT).setNumberOfUser(new_person);
					// ALL DU
					all_new_person = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_PROFIT).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_PROFIT).setNumberOfUser(all_new_person);
				} else if (listProjectOfUser.contains(Constants.Numbers.PROJECT_TYPE_START)) {
					new_person = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_START).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_START).setNumberOfUser(new_person);
					// ALL DU
					all_new_person = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_START).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_START).setNumberOfUser(all_new_person);
				} else if (listProjectOfUser.contains(Constants.Numbers.PROJECT_TYPE_POOL)) {
					new_person = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setNumberOfUser(new_person);
					// ALL DU
					all_new_person = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setNumberOfUser(all_new_person);
				} else {
					new_person = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setNumberOfUser(new_person);	
					// ALL DU
					all_new_person = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getNumberOfUser() + 1;
					
					duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setNumberOfUser(all_new_person);
				}
				
				// Check Book or Available
				if(isAvailable) {
					if (isMemberOfDU) {
						if(month + 1 > 12) year ++;

						Integer isWork = deliveryUnitRepository.checkNextMonthIsWork((month + 1) % 12,year,Integer.parseInt(((Object[])users.get(ui))[0].toString()));
						if(isWork != 0) {
							int new_book = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getBooked() + 1;
							
							duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setBooked(new_book);	
							// ALL DU
							int all_new_book = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).getBooked() + 1;
							
							duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_POOL).setBooked(all_new_book);
						}
					} else {
						int new_available = duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getAvailable() + 1;
						
						duOverviewDTOs.get(group_mp_id).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setAvailable(new_available);
						// All DU
						int all_new_available = duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).getAvailable() + 1;
						
						duOverviewDTOs.get(String.valueOf(0)).getListDuElement().get(Constants.Numbers.PROJECT_TYPE_INVESTMENT).setAvailable(all_new_available);
					}
				}
			}
		}
		
		// Process data final
		for (Map.Entry map : duOverviewDTOs.entrySet()) {
			String group_statistic = (String) map.getKey();
			DUOverviewDTO value = (DUOverviewDTO) map.getValue();
			float totalManpower = value.getTotalManpower();
			float total_man_power = 0.0f;
			int total_man_day = 0;
			int total_person = 0;
			float total_billable = 0.0f;
			int total_booked = 0;
			int total_available = 0;
			float total_manpower_bill = 0;
			
			for(int vDU=1; vDU<value.getListDuElement().size(); vDU++) {
				float duManPower = value.getListDuElement().get(vDU).getManPower();
				int duManDay = value.getListDuElement().get(vDU).getManDay();
				float duBillable = value.getListDuElement().get(vDU).getBillRate();
				float duManpowerBillable =  value.getListDuElement().get(vDU).getManPowerBillable();
				
				total_billable += duBillable;
				
				float duUltilizedRate = duManPower != 0 ? Math.round(((float)duManDay*100/(totalManpower*wokingDayInMonth))*100)/100 : 0;
				float duBillableRate = duManpowerBillable != 0 ? Math.round(((float)duBillable*100/duManpowerBillable)*100)/100 : 0;
				
				value.getListDuElement().get(vDU).setManPower(duManPower);
				value.getListDuElement().get(vDU).setUltilizedRate(duUltilizedRate > 100 && duUltilizedRate <= 105 ? 100 : duUltilizedRate);
				value.getListDuElement().get(vDU).setBillRate(duBillableRate);
				total_man_power += duManPower;
				total_man_day += duManDay;
				total_person += value.getListDuElement().get(vDU).getNumberOfUser();
				total_booked += value.getListDuElement().get(vDU).getBooked();
				total_available += value.getListDuElement().get(vDU).getAvailable();
				total_manpower_bill += duManpowerBillable;
			}
			
			
			value.getListDuElement().get(0).setManPower(total_man_power);
			value.getListDuElement().get(0).setManDay(total_man_day);
			value.getListDuElement().get(0).setNumberOfUser(total_person);
			value.getListDuElement().get(0).setBooked(total_booked);
			value.getListDuElement().get(0).setAvailable(total_available);
			value.getListDuElement().get(0).setManPowerBillable(total_manpower_bill);
			
			float duUltilizedRate = total_man_power != 0 ? Math.round(((float)total_man_day*100/(totalManpower*wokingDayInMonth))*100)/100 : 0;
			float duBillableRate = total_manpower_bill != 0 ? Math.round(((float)total_billable*100/total_manpower_bill)*100)/100 : 0;
			
			value.getListDuElement().get(0).setUltilizedRate(duUltilizedRate > 100 && duUltilizedRate <= 105 ? 100 : duUltilizedRate);
			value.getListDuElement().get(0).setBillRate(duBillableRate);
			
			DUStatistic duStatistic = duStatisticRepository.findByGroupIdAndMonth(Integer.parseInt(group_statistic),monthDate);
			if (duStatistic == null) {
				DUStatistic new_duStatistic = new DUStatistic();
				new_duStatistic.setGroupId(Integer.parseInt(group_statistic));
				new_duStatistic.setMonth(monthDate);
				Gson gson = new Gson();
				String details = gson.toJson(value.getListDuElement());
				System.out.println(details);
				new_duStatistic.setStatisticDetails(details);
				
				try {
					duStatistic = duStatisticRepository.save(new_duStatistic);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				Gson gson = new Gson();
				String details = gson.toJson(value.getListDuElement());
				System.out.println(details);
				duStatistic.setStatisticDetails(details);
				try {
					duStatisticRepository.save(duStatistic);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
				
		return null;
	}
	
    public DeliveryUnitServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
		this.listUserBooked = new ArrayList<>();
		this.listUserAvailable = new ArrayList<>();
		this.listUserProject = new ArrayList<>();
	}
	public static int getIndexUserInList(List<Object> listUser,int userId) {
    	for(int i = 0 ; i < listUser.size() ; i++) {
    		if(userId == Integer.parseInt(((Object[])listUser.get(i))[0].toString())) {
    			return i;
    		}
    	}
    	return -1;
    }
    public static int getIndexEndInList(List<Object> list,int userId,int index) {
    	for(int i = index ;i < list.size(); i++) {
    		if(userId != Integer.parseInt(((Object[])list.get(i))[0].toString())){
    			return i - 1;
    		}
    	}
    	return list.size() - 1;
    }
    public static int getIndexProjectEnd(List<Object> listUserAndManDay,int indexEnd,int index,int projectId) {
    	for(int i = index ;i <= indexEnd; i++) {
    		if(projectId != Integer.parseInt(((Object[])listUserAndManDay.get(i))[1].toString())) {
    			return i - 1;
    		}
    	}
    	return  indexEnd;
    }
	public float getManPower(int projectType, int groupId, int month, int year) {
		float result = 0;
		CalculateClass calculateClass = new CalculateClass();
		int workDay = calculateClass.calculateWorkingDay(month, year);
			List<Object> getAllUserDeliveryUnitOfProjectType = deliveryUnitRepository
					.getAllUserDeliveryUnit(projectType, groupId, month, year);
			for (Object object : getAllUserDeliveryUnitOfProjectType) {
				int a = object == null ? 0 : Integer.parseInt(object.toString());
				if (a == 0) {
					return result = 0.0f;
				}
				try {
					Float manDayOfUser = deliveryUnitRepository.getManDayUserOfDu(projectType, a, month, year) == null
							? 0.0f
							: deliveryUnitRepository.getManDayUserOfDu(projectType, a, month, year);
					Float manPowerOfUser = deliveryUnitRepository.getManpowerUserOfDu(projectType, a, month,
							year) == null ? 0.0f
									: deliveryUnitRepository.getManpowerUserOfDu(projectType, a, month, year);
					float manDayUserOfProjectType = workDay == 0 ? 0 : manDayOfUser / workDay;
					if (manPowerOfUser == 0.0f) {
						return result = 0.0f;
					} else {
						result += manDayUserOfProjectType / manPowerOfUser;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

		return ((float) Math.round(result * 100) / 100);
	}

	public int getNumberOfUserDU(int projectType, int groupId, int month, int year) {
		int result = 0;
		List<Object> getAllUserDeliveryUnitOfProjectType1 = deliveryUnitRepository.getAllUserDeliveryUnit(1, groupId,
				month, year);
		List<Object> getAllUserDeliveryUnitOfProjectType2 = deliveryUnitRepository.getAllUserDeliveryUnit(2, groupId,
				month, year);
		if (projectType == 1) {
			result = getAllUserDeliveryUnitOfProjectType1.size();
		} else if (projectType == 2) {
			int count = 0;
			for (Object object : getAllUserDeliveryUnitOfProjectType2) {
				int a = object == null ? 0 : Integer.parseInt(object.toString());
				for (Object objectObj : getAllUserDeliveryUnitOfProjectType1) {
					int b = objectObj == null ? 0 : Integer.parseInt(objectObj.toString());
					if (a == b) {
						count++;
						result = getAllUserDeliveryUnitOfProjectType2.size() - count;
					} else {
						result = getAllUserDeliveryUnitOfProjectType2.size();
					}
				}
			}
		} else if (projectType == 3) {
			int totalUserDU = deliveryUnitRepository.getAllUserDu(groupId);
			int totalBookedDu = this.getBooked(projectType, groupId, month, year);
			int x = totalUserDU - 2 * totalBookedDu - getAllUserDeliveryUnitOfProjectType1.size()
					- getAllUserDeliveryUnitOfProjectType2.size();
			result = totalBookedDu + x;
			return result;
		} else if (projectType == 4) {
			return 0;
		}
		return result;
	}

	public int getNumberUserInternal(int projectType, int groupId, int month, int year) {
		int result = 0;
		
		List<Object> getAllUserDeliveryUnitOfProjectType1 = deliveryUnitRepository.getAllUserDeliveryUnit(1, groupId,
				month, year);
		List<Object> getAllUserDeliveryUnitOfProjectType2 = deliveryUnitRepository.getAllUserDeliveryUnit(2, groupId,
				month, year);
		if (projectType == 1) {
			result = getAllUserDeliveryUnitOfProjectType1.size();
		} else if (projectType == 2) {
			int count = 0;
			for (Object object : getAllUserDeliveryUnitOfProjectType2) {
				int a = object == null ? 0 : Integer.parseInt(object.toString());
				for (Object objectObj : getAllUserDeliveryUnitOfProjectType1) {
					int b = objectObj == null ? 0 : Integer.parseInt(objectObj.toString());
					if (a == b) {
						count++;
						result = getAllUserDeliveryUnitOfProjectType2.size() - count;
					} else {
						result = getAllUserDeliveryUnitOfProjectType2.size();
					}
				}
			}
		} else if (projectType == 3) {
			int totalBookedDu = this.getBooked(projectType, groupId, month, year);
			result = totalBookedDu;
			return result;
		} else if (projectType == 4) {
			List<Object> totalUserDU = deliveryUnitRepository.getAllUserDuInternal(groupId);
			int totalBookedDu = this.getBooked(projectType, groupId, month, year);
			result = totalUserDU.size() - getAllUserDeliveryUnitOfProjectType1.size()
					- getAllUserDeliveryUnitOfProjectType2.size() - totalBookedDu;
			return result;
		}
		return result;
	}

	public float getUltilizedRate(int projectType, int groupId, int month, int year) {
		CalculateClass calculateClass = new CalculateClass();
		Float effort = deliveryUnitRepository.getEffortEfficiencyByDeliveryUnit(projectType, groupId, month, year);
		// Float manPower = deliveryUnitRepository.getAllDeliveryManpower(projectType,
		// groupId, month, year);
		float manPowerDu = this.getManPower(1, groupId, month, year) + this.getManPower(2, groupId, month, year)
				+ this.getManPower(3, groupId, month, year) + this.getManPower(4, groupId, month, year);
		int workDay = calculateClass.calculateWorkingDay(month, year);
		if (manPowerDu == 0.0f) {
			return 0.0f;
		} else {
			float result = ((effort == null ? 0.0f : effort) / (manPowerDu * workDay));
			return ((float) Math.round(result * 100) / 100);
		}
	}

	public float getBillRate(int projectType, int groupId, int month, int year) {

		CalculateClass calculateClass = new CalculateClass();
		Float billable = deliveryUnitRepository.getDeliveryUnitProjectBillable(projectType, groupId, month, year);
//			Float manPower = deliveryUnitRepository.getAllDeliveryManpower(projectType, groupId, month, year);
		float manPowerDu = this.getManPower(1, groupId, month, year) + this.getManPower(2, groupId, month, year)
				+ this.getManPower(3, groupId, month, year) + this.getManPower(4, groupId, month, year);
		int workDay = calculateClass.calculateWorkingDay(month, year);
		if (manPowerDu == 0.0f) {
			return 0.0f;
		} else {
			float result = ((billable == null ? 0.0f : billable) / (manPowerDu * workDay));
			return ((float) Math.round(result * 100) / 100);
		}
	}

	public int getAvailable(int projectType, int groupId, int month, int year) {
		int available = 0;
		List<Object> totalUserDU = deliveryUnitRepository.getAllUserDuInternal(groupId);
		int totalBookedDu = deliveryUnitRepository.getBookedUserAllDu(3, groupId, month, year);
		List<Object> getAllUserDeliveryUnitOfProjectType1 = deliveryUnitRepository.getAllUserDeliveryUnit(1, groupId,
				month, year);
		List<Object> getAllUserDeliveryUnitOfProjectType2 = deliveryUnitRepository.getAllUserDeliveryUnit(2, groupId,
				month, year);
		available = totalUserDU.size() - getAllUserDeliveryUnitOfProjectType1.size()
				- getAllUserDeliveryUnitOfProjectType2.size() - totalBookedDu;
		return available;
	}

	public int getBooked(int projectType, int groupId, int month, int year) {
		Integer booked = deliveryUnitRepository.getBookedUserAllDu(projectType, groupId, month, year);
		return booked == null ? 0 : booked;
	}

	private List<String> splitPossibleValue(String data) {
		List<String> lstPossible = new LinkedList<>();
		String[] possible = data.split(CustomValueUtil.SPLIT_CHAR);
		for (String element : possible) {
			if (!element.trim().isEmpty()) {
				lstPossible.add(element.trim());
			}
		}

		return lstPossible;
	}

	@Override
	public Page<ProjectInDeliveryUnitDto> getProjectsInDeliveryUnit(int page, int size, String duName) {

		LocalDate date = LocalDate.now();
		Map<String, String> getDayOfDate = MethodUtil.getDayOfDate(date.getMonthValue(), date.getYear());
		String startDate = getDayOfDate.get(CustomValueUtil.START_DATE_KEY);
		String endDate = getDayOfDate.get(CustomValueUtil.END_DATE_KEY);

		Pageable pageable = new PageRequest(page - 1, size, null);
		Page<ProjectInDeliveryUnitDto> projectInDeliveryUnitDtos = this.getProjectInDeliveryUnitDtos(
				projectQmsRepository.getProjectsByDeliveryUnit(duName, startDate, endDate, pageable), pageable);

		if (!projectInDeliveryUnitDtos.getContent().isEmpty()) {
			this.addMemberToProject(projectInDeliveryUnitDtos);
			return projectInDeliveryUnitDtos;
		}

		return new PageImpl<>(new ArrayList<>(), null, 0);
	}

	/**
	 * 
	 * add member to project
	 * 
	 * @param projectInDeliveryUnitDtos void
	 * @author: LXLinh
	 */
	private void addMemberToProject(Page<ProjectInDeliveryUnitDto> projectInDeliveryUnitDtos) {
		projectInDeliveryUnitDtos.getContent().forEach(projectInDeliveryUnitDto -> {
			List<String> members = null;
			members = reMemberQmsRepository.getMembersByProjectId(projectInDeliveryUnitDto.getProjectId());
			if (members != null && !members.isEmpty())
				projectInDeliveryUnitDto.setMember(members);

		});
	}

	/**
	 * 
	 * projects in delivery unit
	 * 
	 * @param pageObjects
	 * @return Page<ProjectInDeliveryUnitDto>
	 * @author: LXLinh
	 */
	private Page<ProjectInDeliveryUnitDto> getProjectInDeliveryUnitDtos(Page<Object> pageObjects, Pageable pageable) {
		List<ProjectInDeliveryUnitDto> projectInDeliveryUnitDtos = new ArrayList<>();
		pageObjects.getContent().forEach(object -> {
			Object[] obj = (Object[]) object;
			ProjectInDeliveryUnitDto projectInDeliveryUnitDto = new ProjectInDeliveryUnitDto();
			projectInDeliveryUnitDto.setProjectId(obj[0] != null ? Integer.parseInt(obj[0].toString()) : 0);
			projectInDeliveryUnitDto.setProjectName(obj[1] != null ? obj[1].toString() : null);
			projectInDeliveryUnitDto.setStatus(obj[2] != null ? Integer.parseInt(obj[2].toString()) : null);
			projectInDeliveryUnitDto.setStartDate(obj[3] != null ? obj[3].toString() : null);
			projectInDeliveryUnitDto.setEndDate(obj[4] != null ? obj[4].toString() : null);
			projectInDeliveryUnitDto.setProjectType(obj[5] != null ? obj[5].toString() : null);
			projectInDeliveryUnitDto.setDeliveryUnit(obj[6] != null ? obj[6].toString() : null);
			projectInDeliveryUnitDto.setProjectCode(obj[7] != null ? obj[7].toString() : null);
			projectInDeliveryUnitDtos.add(projectInDeliveryUnitDto);
		});
		return new PageImpl<>(projectInDeliveryUnitDtos, pageable, pageObjects.getTotalElements());

	}

	/**
	 * Get total user by delivery unit
	 *
	 * @return List<Object>
	 * @author: HungNC
	 * @created: 2018-03-29
	 */
	@Override
	public List<DeliveryUnitDTO> getTotalUserByDeliveryUnit() {
		List<Object> objects = userQmsRepository.getTotalUserByDeliveryUnit();
		List<DeliveryUnitDTO> totalUserByDU = new ArrayList<>();

		for (Object object : objects) {
			// Object[] obj = (Object[]) object;
			// DeliveryUnitDTO du = new DeliveryUnitDTO(obj[0] == null ? "" :
			// obj[0].toString());
			// du.setTotalUser(obj[1] == null ? 0 : Float.parseFloat(obj[1].toString()));
			// totalUserByDU.add(du);
		}

		return totalUserByDU;
	}

	@Override
	public List<DeliveryUnitDTO> getManPowerByDeliveryUnit(String time) {

		List<UserManPowerDTO> list = manPowerService.getManPowerByUserIdOfAllDU(time);

		List<DeliveryUnitDTO> deliveryUnitsManPower = new ArrayList<>();

		list.stream().collect(Collectors.groupingBy(UserManPowerDTO::getDeliveryUnit,
				Collectors.summingDouble(UserManPowerDTO::getManPower))).forEach((user, manPower) -> {
					DeliveryUnitDTO deliveryUnitDTO = new DeliveryUnitDTO();
					deliveryUnitDTO.setName(user);
					deliveryUnitDTO.setTotalManPower(manPower.floatValue());
					deliveryUnitsManPower.add(deliveryUnitDTO);
				});

		return deliveryUnitsManPower;
	}

	@Override
	public List<DeliveryUnitDTO> getPageDeliveryUnitInfo(String column, String sort, int month, int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DuInternalDuDTO getTotalDuInfo(int id, int month, int year) {
		List<DuInternalDuDTO> listDu = this.GetDeliveryUnitById(month, year);
		BiConsumer<DuInternalDuDTO, DuInternalDuDTO> reducer = (o1, o2) -> {
			o1.setTotalUser(o1.getTotalUser() + o2.getTotalUser());
			o1.setUtilized(o1.getUtilized() +   o2.getUtilized());
			o1.setBillable(o1.getBillable() +   o2.getBillable());
			o1.setManday(o1.getManday() +   o2.getManday());
			o1.setProfit(new DuStatisticDTO((o1.getProfit().getManPower() + o2.getProfit().getManPower()),
					(o1.getProfit().getNumberOfUser() + o2.getProfit().getNumberOfUser()),
					(o1.getProfit().getUltilizedRate() + o2.getProfit().getUltilizedRate()),
					(o1.getProfit().getBillRate() + o2.getProfit().getBillRate()),
					(o1.getProfit().getBooked() + o2.getProfit().getBooked()),
					(o1.getProfit().getAvailable() + o2.getProfit().getAvailable())));
			o1.setStart(new DuStatisticDTO((o1.getStart().getManPower() + o2.getStart().getManPower()),
					(o1.getStart().getNumberOfUser() + o2.getStart().getNumberOfUser()),
					(o1.getStart().getUltilizedRate() + o2.getStart().getUltilizedRate()),
					(o1.getStart().getBillRate() + o2.getStart().getBillRate()),
					(o1.getStart().getBooked() + o2.getStart().getBooked()),
					(o1.getStart().getAvailable() + o2.getStart().getAvailable())));
			o1.setPool(new DuStatisticDTO((o1.getPool().getManPower() + o2.getPool().getManPower()),
					(o1.getPool().getNumberOfUser() + o2.getPool().getNumberOfUser()),
					(o1.getPool().getUltilizedRate() + o2.getPool().getUltilizedRate()),
					(o1.getPool().getBillRate() + o2.getPool().getBillRate()),
					(o1.getPool().getBooked() + o2.getPool().getBooked()),
					(o1.getPool().getAvailable() + o2.getPool().getAvailable())));
			o1.setInvestment(new DuStatisticDTO((o1.getInvestment().getManPower() + o2.getInvestment().getManPower()),
					(o1.getInvestment().getNumberOfUser() + o2.getInvestment().getNumberOfUser()),
					(o1.getInvestment().getUltilizedRate() + o2.getInvestment().getUltilizedRate()),
					(o1.getInvestment().getBillRate() + o2.getInvestment().getBillRate()),
					(o1.getInvestment().getBooked() + o2.getInvestment().getBooked()),
					(o1.getInvestment().getAvailable() + o2.getInvestment().getAvailable())));
		};

		DuInternalDuDTO totals = listDu.stream().collect(() -> new DuInternalDuDTO(-1, "ALL_DELIVERY_UNIT", 0,
				new DuStatisticDTO(), new DuStatisticDTO(), new DuStatisticDTO(), new DuStatisticDTO()), reducer,
				reducer);
		CalculateClass calculateClass = new CalculateClass();
  		int workDay = calculateClass.calculateWorkingDay(month, year);
  		totals.setWorkDay(workDay);
		return totals;
	}

	public List<BookResourcesDTO> getListUserBooked() {
		return listUserBooked;
	}
	public void setListUserBooked(List<BookResourcesDTO> listUserBooked) {
		this.listUserBooked = listUserBooked;
	}
	public List<Integer> getListUserAvailable() {
		return listUserAvailable;
	}
	public void setListUserAvailable(List<Integer> listUserAvailable) {
		this.listUserAvailable = listUserAvailable;
	}
	@Override
	public List<DeliveryUnitDTO> getTotalAllocationByDeliveryUnit(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeliveryUnitDTO> getEffortEfficiencyByDeliveryUnit(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DuInternalDuDTO GetDeliveryUnitByDuId(int id, int month, int year) {
		// TODO Auto-generated method stub
		List<DuInternalDuDTO> listDu = this.GetDeliveryUnitById(month, year);
		List<DuInternalDuDTO> result = listDu.stream().filter(item -> (item.getId() == id))
				.collect(Collectors.toList());
		return result.get(0);
	}

	@Override
	public List<DeliveryUnitDTO> getTotalSpentTimeByDeliveryUnit(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeliveryUnitDTO> getAllocationToOtherDeliveryUnit(String time) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unused")
	@Override
	public ResourcesAvailableDTO getResourcesAvailable() {
		Set<String> skills = this.convertSkill(skillRepository.getListSkillOfUserAndPlan());
		
		List<Object> skillOfUser = skillRepository.getSkillOfUser();
		for(int i = 0 ; i< skillOfUser.size() ; i++) {
			int userId = Integer.parseInt(((Object[])skillOfUser.get(i))[0].toString());
			if(userId == 0) continue;
			int indexUserStart = getIndexUserInList(skillOfUser,userId);
			int indexUserEnd = getIndexEndInList(skillOfUser,userId,indexUserStart);
			String skill ="";
			for(int j =indexUserStart ; j<= indexUserEnd ; j++) {
			    skill += ((Object[])skillOfUser.get(j))[1].toString()+",";
			}
			skill = skill.substring(0, skill.length() - 1);
			Set<String> temp = skillRepository.getSkillNameByListId(skill);
			skills.add(converSetToString(temp));
		}
		Set<String> levels = userRepository.getListLevelUser();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		List<Object> listUserOfDu = deliveryUnitRepository.getAllUser((month + 1) % 12, year);
		List<Object> listUserInProject = deliveryUnitRepository.getListAllUserInProject((month + 1) % 12, year);
		calculateBookAndAvailable(listUserOfDu,listUserInProject,year,(month + 1) % 12 );
		Set<BookResourcesDTO> userBooked = new HashSet<BookResourcesDTO>();
		userBooked.addAll(this.getListUserBooked());
		Set<Integer> listUserAvailable = new HashSet<>(this.getListUserAvailable());
		Map<String,Integer> book = new HashMap<>();
		Map<String,Integer> avaiable = new HashMap<>();
		calculateResourcesAvailable(skills,levels,userBooked,listUserAvailable,book,avaiable);
		return new ResourcesAvailableDTO(skills,levels,book,avaiable);
	}
	public void calculateBookAndAvailable(List<Object> listUserOfDu,List<Object> listUserInProject,int year,int month) {
		for(int i = 0 ; i < listUserOfDu.size() ; i++) {
			int userId = Integer.parseInt((listUserOfDu.get(i).toString()));
			int indexUserStartInListUserInProject = getIndexUserInList(listUserInProject,userId);
			if(indexUserStartInListUserInProject == -1) {continue;}
			int indexUserEndInListUserInProject = getIndexEndInList(listUserInProject,userId,indexUserStartInListUserInProject);
			List<Integer> listType = new ArrayList<>();
			String skill ="";
			for(int j = indexUserStartInListUserInProject; j <= indexUserEndInListUserInProject ; j++) {
				int projectId = Integer.parseInt(((Object[])listUserInProject.get(j))[1].toString());
				skill = ((Object[])listUserInProject.get(j))[2].toString();
				Project project = projectRepository.getProjectByProjectId(projectId);
				if(project != null) {
				int type = project.getType();
				listType.add(type);
				if(type == 4) {
					listUserBooked.add(new BookResourcesDTO(skill,userId));
				  }
				} else {
					break;
				}
			}
			Calendar calendar = Calendar.getInstance();
			 calendar.set(year, month - 1  , 1);
			 calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			 Date date = calendar.getTime();
			 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 String endDate = format.format(date);
			 LocalDate temp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 LocalDate before7days = temp.minusDays(7);
			 String fromDate = before7days.toString();
			 Integer checkLast7Day = deliveryUnitRepository.checkAllLast7Day(fromDate,endDate,userId);
			 if(checkLast7Day == 0) {
				 if(month + 1 > 12) {
					 year ++;
				 }
				 Integer isWork = deliveryUnitRepository.checkAllNextMonthIsWork((month + 1) % 12,year,userId);
				if(isWork != 0) {
					listUserBooked.add(new BookResourcesDTO(skill,userId));
				} else if(!listType.contains(Constants.Numbers.PROJECT_TYPE_PROFIT) && !listType.contains(Constants.Numbers.PROJECT_TYPE_START)) {
					listUserAvailable.add(userId);
				}
			}
			 if(listType.contains(Constants.Numbers.PROJECT_TYPE_PROFIT)) {
					listUserProject.add(userId);
				} else if (listType.contains(Constants.Numbers.PROJECT_TYPE_START)) {
					listUserProject.add(userId);
				} else if (listType.contains(Constants.Numbers.PROJECT_TYPE_POOL)) {
					listUserProject.add(userId);
				} else {
					listUserProject.add(userId);
				  }
		  }
		for(Object obj : listUserOfDu) {
			Integer id = Integer.parseInt(obj.toString());
			if(!listUserProject.contains(id)) {
				listUserAvailable.add(id);
			}
		}
	}
	
	public void calculateResourcesAvailable(Set<String> skills,Set<String> levels,Set<BookResourcesDTO> userBooked , Set<Integer> userAvaiable,final Map<String,Integer> book,final Map<String,Integer> available) {
		for(BookResourcesDTO obj : userBooked) {
			User user = userRepository.getUserByUserId(obj.getId());
			String skill = obj.getSkill();
			Set<String> listSkill = skillRepository.getSkillNameByListId(skill);
			skill = converSetToString(listSkill);
			int lv = user.getLevel();
			String level ="";
			level= (lv == 1 ? "Fresher" : lv == 2 ? "Junior" : lv == 3 ? "Senior1" : lv == 0 ? "None" :"Senior2");
			String key = "[" + skill +"]"+"."+level;
			int value = (book.get(key) == null ? 0 : book.get(key));
			value ++;
			book.put(key, value);
		}
		for(Integer id : userAvaiable) {
			User user = userRepository.getUserByUserId(id);
			int lv = user.getLevel();
			String level ="";
			level= (lv == 1 ? "Fresher" : lv == 2 ? "Junior" : lv == 3 ? "Senior1" : lv == 0 ? "None" :"Senior2");
			Set<Skill> sk = user.getSkill();
			String skill = "";
			for(Skill s : sk) {
				skill += s.getName() + ",";
			}
			if(skill.length() > 0) {
			   skill =skill.substring(0, skill.length() - 1);
			}
			String key = "[" + skill +"]"+"."+level;
			int value = (available.get(key) == null ? 0 : available.get(key));
			value ++;
			available.put(key, value);	
		}
	}
	public Set<String> convertSkill(Set<String> skills){
		Set<String> result = new HashSet<>();
		for(String skill : skills) {
			if(!skill.equals("0")) {
			Set<String> listSkill = skillRepository.getSkillNameByListId(skill);
			result.add(converSetToString(listSkill));
		  }
		}
		return result;	
	}
	public static String converSetToString(Set<String> skills) {
		String result ="";
		for(String sk : skills) {
			result += sk + ",";
		}
		if("".equals(result)) return result;
		return result.substring(0, result.length()-1);
	}
	@Override
	public List<RiskDTO> getAllRisk(int projectId, int month, int year, int groupId) {
		List<RiskDTO> riskDTOs =new ArrayList<RiskDTO>();
		List<Object> data=null;
		String rexGroup = groupId == -1 ? ".*" : ("^" + groupId + "$");
		String rexProject = projectId == -1 ? ".*" : ("^" + projectId + "$");
		String rexMonth = month == -1 ? ".*" : ("^" + month + "$");
		String rexYear = year == -1 ? ".*" : ("^" + year + "$");
		try {
			 data = deliveryUnitRepository.getAllRisk(rexProject, rexMonth, rexYear,rexGroup);

			 for (Iterator<Object> it = data.iterator(); it.hasNext();) {
					Object[] object = (Object[]) it.next();
					RiskDTO riskDTO=new RiskDTO();
					riskDTO.setRiskId((int)object[0]);
					riskDTO.setRegisteredDate((Date)object[1]);
					riskDTO.setRiskTitle((String) object[2]);
					riskDTO.setRiskIndicator((String) object[3]);
					riskDTO.setFullName((String) object[4]);
					riskDTO.setRiskCategoryDesciption((String) object[5]);
					riskDTO.setRiskSubCategoryDesciption((String) object[6]);
					riskDTO.setRiskImpactDescription((String) object[7]);
					riskDTO.setRiskDescription((String) object[8]);
					riskDTO.setRiskStatusDesciption((String) object[9]);
					riskDTO.setProjectName((String) object[10]);
					User user = userRepository.getUserByUserId((int) object[11]);
					if(user != null) {
					riskDTO.setOwnerName(user.getFullName());
					}
					else {
						riskDTO.setOwnerName("");
					}
					riskDTO.setRisk_color((Byte) object[12]);
					riskDTO.setProject_id((int)object[13]);
					riskDTOs.add(riskDTO);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
//		List<Risk> risklist= new ArrayList<>();
//		for (Iterator<Object> it = data.iterator(); it.hasNext();) {
//			Object[] object = (Object[]) it.next();
//			
//			try {
//				RiskCategory category =  riskcategoryRepository.getOne((Integer) object[12]);
//				RiskSubCategory subcategory = risksubcategoryRepository.getOne((Integer) object[17]);
//				User user = userRepository.getOne((Integer) object[11]);
//				RiskImpact impact = riskimpactRepository.getOne((Integer) object[14]);
//				RiskStatus status = riskstatusRepository.getOne((Integer) object[16]);
//				Risk pdt = new Risk();
//				pdt.setRiskId((Integer) object[0]);
//				pdt.setRiskTitle((String) object[9]);
//				pdt.setRegisteredDate((Date) object[6]);
//				pdt.setRiskCategoryId(category);
//				pdt.setRiskDescription((String) object[7]);
//				pdt.setRiskSubCategoryId(subcategory);
//				pdt.setRegisteredBy(user);
//				pdt.setRiskIndicator((String) object[8]);
//				pdt.setRiskStatusId(status);
//				risklist.add(pdt);
//			} catch (Exception e) {
//				System.out.println((Integer) object[0]);
//			}
//
//		}
		
//		if (data == null) {
//			return risklist;
//		}
//	for (Object risk : data) {
//		riskDTOs.add(new RiskDTO(risk));
//	}
		return riskDTOs;
	}

}
