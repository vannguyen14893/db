package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.ListResourceDetailDTO;
import com.cmc.dashboard.dto.ProjectTimeDTO;
import com.cmc.dashboard.dto.RemoveResourcePlanDTO;
import com.cmc.dashboard.dto.ResourceAllocationDTO;
import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.dto.TimesheetDTO;
import com.cmc.dashboard.dto.UpdateResourceDTO;
import com.cmc.dashboard.dto.UserSpentTimeDTO;
import com.cmc.dashboard.model.GroupUser;
import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.TimeEntriesRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.DeliveryUnitRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.GroupUserRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Service
public class UserPlanServiceImpl implements UserPlanService {

	@Autowired
	protected UserPlanRepository userPlanRepository;

	@Autowired
	private UserPlanDetailRepository userPlanDetailRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected UserQmsRepository userQmsRepository;

	@Autowired
	protected UserPlanService userPlanService;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	protected ProjectBillableRepository projectBillableRepository;

	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected TimeEntriesRepository timeEntriesRepository;
	
    @Autowired
    private DeliveryUnitRepository deliverUnitRepository;
    
    @Autowired
    private GroupUserRepository groupUserRepository;
    
    @Autowired
    private GroupRepository groupRepository;
	/**
	 * description get list resource project by projectId
	 * 
	 * @param projectId
	 * @return ResourceAllocationDTO
	 * @author: DuyHieu
	 */
	@Override
	public List<ResourceAllocationDTO> getListResourcesByProjectId(int projectId) {
		List<ResourceAllocationDTO> resourceAllocationDTOs = new ArrayList<ResourceAllocationDTO>();
		List<UserPlan> userPlans;
		ProjectTimeDTO pTimeDTO = null;
		projectService.getBillableByProject(projectId);
		resourceAllocationDTOs = this.getListMemberOfProject(projectId);
		pTimeDTO = projectBillableRepository.getDateProject(projectId);
		userPlans = userPlanRepository.getAllUserEachProject(projectId);

		if (null == pTimeDTO)
			return null;

		List<String> listMonths = MethodUtil.getMonthBetween2Date(pTimeDTO.getStartDate().toString(),
				pTimeDTO.getEndDate().toString());
		if (MethodUtil.checkList(listMonths) || MethodUtil.checkList(resourceAllocationDTOs)) {
			return resourceAllocationDTOs;
		}

		for (int j = 0; j < resourceAllocationDTOs.size(); j++) {
			ResourceAllocationDTO dto = resourceAllocationDTOs.get(j);
			dto.setProjectId(projectId);
			dto.setUserPlanDetails(userPlans, listMonths);

			List<UserPlanDetail> list = dto.getUserPlanDetails();

			List<UserSpentTimeDTO> userSpentTimeDTOs = new ArrayList<UserSpentTimeDTO>();
			userSpentTimeDTOs = getTimeEntriesByProjectId(projectId, dto.getUserId());
			list.forEach((userPlanDetail) -> {
			});
			for (int i = 0; i < list.size(); i++) {
				UserPlanDetail userPlanDetail = list.get(i);
				if (equalMonthOfPlanMonth(userPlanDetail.getPlanMonth())) {
					userPlanDetail.setManDay(Constants.Numbers.MANDAY_ZERO);
					for (int index = 0; index < userSpentTimeDTOs.size(); index++) {
						UserSpentTimeDTO userSpentTimeDTO = userSpentTimeDTOs.get(index);

						if (compareMonth(userPlanDetail, userSpentTimeDTO)) {
							userPlanDetail.setManDay(userSpentTimeDTO.getHours() / Constants.Numbers.HOURS_PER_DAY);
							break;
						}
					}
				}
				list.set(i, userPlanDetail);
			}
			dto.setUserPlanDetails(list);
			resourceAllocationDTOs.set(j, dto);
		}
		return resourceAllocationDTOs;
	}

	/**
	 * description Compare the previous month to the current month
	 * 
	 * @param planMonth
	 * @return boolean
	 * @author: DuyHieu
	 */
	private boolean equalMonthOfPlanMonth(String planMonth) {

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern(Constants.DateFormart.DATE_MM_YYYY);
		localDate.format(monthYearFormatter);
		String[] strMonth = planMonth.split("-");
		LocalDate dateBefore = LocalDate.of(Integer.parseInt(strMonth[1]), Integer.parseInt(strMonth[0]), 1);
		localDate = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1);
		return localDate.isAfter(dateBefore);
	}

	/**
	 * 
	 * description Compare 2 month in UserPlanDetail and UserSpentTimeDTO
	 * 
	 * @param userPlanDetail
	 * @param userSpentTimeDTO
	 * @return boolean
	 * @author: DuyHieu
	 */
	private boolean compareMonth(UserPlanDetail userPlanDetail, UserSpentTimeDTO userSpentTimeDTO) {
		String month;
		if (userSpentTimeDTO.getTmonth() < 10) {
			month = "0" + userSpentTimeDTO.getTmonth() + "-" + userSpentTimeDTO.getTyear();
		} else {
			month = userSpentTimeDTO.getTmonth() + "-" + userSpentTimeDTO.getTyear();
		}
		if (month.equals(userPlanDetail.getPlanMonth().trim()))
			return true;
		return false;
	}

	/**
	 * description: Convert Object to UserSpentTimeDTO
	 * 
	 * @param projectId
	 * @param userId
	 * @return List<UserSpentTimeDTO>
	 * @author: DuyHieu
	 */
	private List<UserSpentTimeDTO> getTimeEntriesByProjectId(int projectId, int userId) {
		List<UserSpentTimeDTO> userSpentTimeDTOs = new ArrayList<UserSpentTimeDTO>();
		List<Object> objects = timeEntriesRepository.getTimeEntriesByProjectId(projectId, userId);
		for (Object object : objects) {
			Object[] oobj = (Object[]) object;
			if (null != oobj[0] && null != oobj[1]) {
				UserSpentTimeDTO userSpentTimeDTO = new UserSpentTimeDTO(Integer.parseInt(oobj[0].toString()),
						Integer.parseInt(oobj[1].toString()), Float.parseFloat(oobj[2].toString()),
						Integer.parseInt(oobj[3].toString()), Integer.parseInt(oobj[4].toString()));
				userSpentTimeDTOs.add(userSpentTimeDTO);
			}
		}
		return userSpentTimeDTOs;
	}

	@Override
	public UpdateResourceDTO updatePlan(String plan) {
		Gson gson = new Gson();
		TypeToken<List<ResourceDTO>> token = new TypeToken<List<ResourceDTO>>() {
		};
		UpdateResourceDTO result = UpdateResourceDTO.error(MessageUtil.DATA_INVALID);
		List<ResourceDTO> resourceDTOs = new ArrayList<ResourceDTO>();

		try {
			resourceDTOs = gson.fromJson(plan, token.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return result;
		} catch (JsonParseException e) {
			e.printStackTrace();
			return result;
		}

		List<UserPlan> userPlans = new ArrayList<UserPlan>();
		if (!resourceDTOs.isEmpty()) {
			UserPlan userPlanTemp = null;
			List<ResourceDTO> totalUserPlan = this.getResourcePlanByUser(resourceDTOs.get(0).getUserId(),
					resourceDTOs.get(0).getProjectId());
			for (ResourceDTO dto : resourceDTOs) {
				float effortPerDay = MethodUtil.formatFloatNumberType((dto.getManDay()
						/ MethodUtil.getTotalWorkingDaysBetweenDate(dto.getFromDate(), dto.getToDate()))
						* CustomValueUtil.WORKING_DAY);
				userPlanTemp = new UserPlan(dto.getUserPlanId(), effortPerDay, dto.getFromDate(), dto.getManDay(),
						dto.getProjectId(), dto.getToDate(), dto.getUserId(), new Date());
				if (this.getUpdatePlanId(userPlanTemp, totalUserPlan)) {
					userPlans.add(userPlanTemp);
				}
			}
		} else {
			return result;
		}

		List<Integer> userPlanIds = new ArrayList<Integer>();
		for (UserPlan userPlan : userPlans) {
			userPlanIds.add(userPlan.getUserPlanId());
			if (!validateUserPlan(userPlan)) {
				return result;
			}
		}

		List<UserPlan> listUserPlans = new ArrayList<UserPlan>();

		if (MethodUtil.checkList(userPlanIds)) {
			return UpdateResourceDTO.success(
					this.getResourcePlanByUser(resourceDTOs.get(0).getUserId(), resourceDTOs.get(0).getProjectId()));
		}
		try {
			userPlanDetailRepository.deleteListUserPlanDetail(userPlanIds);
			listUserPlans = userPlanRepository.save(userPlans);
			for (UserPlan userPlan : listUserPlans) {
				this.insertUserPlanDetail(userPlan);
			}
		} catch (ParseException e) {
			return result;
		}
		return UpdateResourceDTO.success(
				this.getResourcePlanByUser(listUserPlans.get(0).getUserId(), listUserPlans.get(0).getProjectId()));
	}

	/**
	 * method get plan has update.
	 * 
	 * @param userPlan
	 * @param uPlanUpdates
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	private boolean getUpdatePlanId(UserPlan userPlan, List<ResourceDTO> uPlanUpdates) {
		for (ResourceDTO userPlanTemp : uPlanUpdates) {
			if (userPlan.getUserPlanId() == userPlanTemp.getUserPlanId()) {
				if (userPlan.getFromDate().equals(userPlanTemp.getFromDate())
						&& userPlan.getToDate().equals(userPlanTemp.getToDate())
						&& userPlan.getManDay() == userPlanTemp.getManDay()) {
				} else {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * validateUserPlan
	 * 
	 * @param userPlan
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	private boolean validateUserPlan(UserPlan userPlan) {

		Date fromDate = userPlan.getFromDate();
		Date toDate = userPlan.getToDate();
		long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate);

		ProjectTimeDTO pTimeDTO = null;
		try {
			pTimeDTO = projectBillableRepository.getDateProject(userPlan.getProjectId());
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}

		if (pTimeDTO == null) {
			return false;
		}

		if (!fromDate.after(pTimeDTO.getStartDate()) || (!toDate.before(pTimeDTO.getEndDate()))
				? (!toDate.equals(pTimeDTO.getEndDate()))
				: (!toDate.before(pTimeDTO.getEndDate()))) {
			return false;
		}

		if (userPlan.getManDay() > totalWorkingDay || userPlan.getManDay() <= 0) {
			return false;
		}

		return true;
	}

	@Override
	public RestResponse saveNewPlan(String plan) {

		if (MethodUtil.isNull(plan) || !MethodUtil.validateJson(plan)) {
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_UNFORMAT, MessageUtil.DATA_UNFORMAT);
		}
		JsonObject jObj;
		UserPlan userPlan;
		try {
			jObj = MethodUtil.getJsonObjectByString(plan);
			userPlan = getUserPlanByJson(jObj);
			if (MethodUtil.isNull(userPlan)) {
				return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_UNFORMAT, MessageUtil.DATA_UNFORMAT);
			}
			ProjectTimeDTO pTimeDTO;

			pTimeDTO = projectBillableRepository.getDateProject(userPlan.getProjectId());

			if (MethodUtil.isNull(pTimeDTO)) {
				return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_UNFORMAT, MessageUtil.DATA_UNFORMAT);
			}

			List<ResourceDTO> results;

			if (userPlan.getFromDate().before(pTimeDTO.getStartDate())) {
				return RestResponse.error(HttpStatus.BAD_REQUEST, MessageUtil.PLAN_ERROR_START);
			} else if (userPlan.getToDate().after(pTimeDTO.getEndDate())) {
				return RestResponse.error(HttpStatus.BAD_REQUEST, MessageUtil.PLAN_ERROR_END);
			} else if (userPlan.getFromDate().after(userPlan.getToDate())) {
				return RestResponse.error(HttpStatus.BAD_REQUEST, MessageUtil.PLAN_ERROR_FROMANDTO);
			}

			UserPlan savedPlan = userPlanRepository.save(userPlan);
			this.insertUserPlanDetail(savedPlan);
			results = this.getResourcePlanByUser(userPlan.getUserId(), userPlan.getProjectId());
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.SUCCESS, results);
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(HttpStatus.BAD_REQUEST, MessageUtil.PLAN_ERROR_UNKNOW);
		}
	}

	@Override
	public RemoveResourcePlanDTO removePlan(int planId, int userId, int projectId) {
		RemoveResourcePlanDTO removeResourceError;
		removeResourceError = new RemoveResourcePlanDTO(MessageUtil.ERROR, new ArrayList<>());
		UserPlan uPlan = null;
		try {
			uPlan = userPlanRepository.findOne(planId);
			if (uPlan.getUserId() != userId || uPlan.getProjectId() != projectId) {
				return removeResourceError;
			}
			userPlanDetailRepository.deleteUserPlanDetail(planId);
			userPlanRepository.delete(planId);
			return new RemoveResourcePlanDTO(MessageUtil.SUCCESS, this.getResourcePlanByUser(userId, projectId));
		} catch (IllegalArgumentException e) {
			return removeResourceError;
		} catch (EmptyResultDataAccessException e) {
			return removeResourceError;
		} catch (NullPointerException e) {
			return removeResourceError;
		}
	}

	/**
	 * Method return UserPlan object from json element.
	 * 
	 * @param jsonElement
	 * @return UserPlan
	 * @author: Hoai-Nam
	 */
	public UserPlan getUserPlanByJson(JsonElement jsonElement) {
		JsonObject userPlan = jsonElement.getAsJsonObject();
		UserPlan uPlan = null;

		try {
			float manDay = Float.parseFloat(MethodUtil.getStringValue(userPlan, "manDay"));
			if (manDay <= 0) {
				return uPlan;
			}
			int projectId = Integer.parseInt(MethodUtil.getStringValue(userPlan, "projectId"));
			int id = Integer.parseInt(MethodUtil.getStringValue(userPlan, "userId"));

			String jsUserPlanId = MethodUtil.getStringValue(userPlan, "userPlanId");
			String role = MethodUtil.getStringValue(userPlan, "role");
			int userPlanId = MethodUtil.isNull(jsUserPlanId) ? 0 : Integer.parseInt(jsUserPlanId);
			Date fromDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "fromDate"));
			Date toDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "toDate"));
			long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate);
			if (manDay > totalWorkingDay) {
				return uPlan;
			}
			Date curDate = MethodUtil.getDate(MethodUtil.getCurrentDate());
			if ((fromDate.before(toDate) || fromDate.equals(toDate))
					&& (fromDate.equals(curDate) || fromDate.after(curDate))) {
				uPlan = new UserPlan(userPlanId, fromDate, manDay, projectId, toDate, id, role);
				uPlan.setEffortPerDay(MethodUtil
						.formatFloatNumberType((manDay / MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate))
								* CustomValueUtil.WORKING_DAY));
				uPlan.setCreatedOn(new Date());
				uPlan.setUpdatedOn(new Date());
			}
			return uPlan;
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		} catch (DateTimeException dateException) {
			dateException.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public UserPlan getUserPlanUpdateByJson(JsonElement jsonElement) {
		JsonObject userPlan = jsonElement.getAsJsonObject();
		UserPlan uPlan = null;

		try {
			ProjectTimeDTO pTimeDTO = null;
			int projectId = Integer.parseInt(MethodUtil.getStringValue(userPlan, "projectId"));
			pTimeDTO = projectBillableRepository.getDateProject(projectId);
			if (MethodUtil.isNull(pTimeDTO)) {
				return uPlan;
			}

			float manDay = Float.parseFloat(MethodUtil.getStringValue(userPlan, "manDay"));
			if (manDay <= 0) {
				return uPlan;
			}
			int id = Integer.parseInt(MethodUtil.getStringValue(userPlan, "userId"));
			String jsUserPlanId = MethodUtil.getStringValue(userPlan, "userPlanId");
			String role = MethodUtil.getStringValue(userPlan, "role");
			int userPlanId = MethodUtil.isNull(jsUserPlanId) ? 0 : Integer.parseInt(jsUserPlanId);
			Date fromDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "fromDate"));
			Date toDate = MethodUtil.convertStringToDate(MethodUtil.getStringValue(userPlan, "toDate"));
			long totalWorkingDay = MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate);
			if (manDay > totalWorkingDay) {
				return uPlan;
			}
			boolean checkAfterDate = fromDate.before(pTimeDTO.getStartDate());
			if (checkAfterDate == true) {
				return uPlan;
			}
			if (fromDate.equals(pTimeDTO.getStartDate())
					|| fromDate.before(toDate) && toDate.before(pTimeDTO.getEndDate())
					|| toDate.equals(pTimeDTO.getEndDate())) {
				uPlan = new UserPlan(userPlanId, fromDate, manDay, projectId, toDate, id, role);
				uPlan.setEffortPerDay(MethodUtil
						.formatFloatNumberType((manDay / MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate))
								* CustomValueUtil.WORKING_DAY));
			}
			return uPlan;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		} catch (DateTimeException dateException) {
			dateException.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public ListResourceDetailDTO getResourceDetail(String userLoginId, String userId, String planMonth) {
		return null;
	}

	public List<UserPlanDetail> insertUserPlanDetail(UserPlan userPlan) throws ParseException {

		Map<String, Float> mapsPlanDate = MethodUtil.getEffortForEachMonth(userPlan.getFromDate(), userPlan.getToDate(),
				userPlan.getManDay());
		String duName = deliverUnitRepository.getDuNameByProjectId(userPlan.getProjectId());
		int userId = userPlan.getUserId();
		String resduName = userRepository.getDeliveryUnitByUserId(userId);
		UserPlanDetail userPlanDetail;
		List<UserPlanDetail> listOfPlanDetails = new ArrayList<UserPlanDetail>();
		List<String> planMonths = mapsPlanDate.keySet().stream().collect(Collectors.toList());

		for (String planMonth : planMonths) {
			userPlanDetail = new UserPlanDetail(planMonth, mapsPlanDate.get(planMonth), duName, resduName, userPlan);
			listOfPlanDetails.add(userPlanDetail);
		}
		return userPlanDetailRepository.save(listOfPlanDetails);
	}


	public List<ResourceDTO> getResourcePlanByUser(int userId, int projectId) {
		List<ResourceDTO> listOfResourcePlans = new ArrayList<ResourceDTO>();
		listOfResourcePlans = userPlanRepository.getResourcePlanByUserId(userId);
		return this.setOverloaded(listOfResourcePlans, projectId);
	}

	private List<ResourceDTO> setOverloaded(List<ResourceDTO> resourceDTOs, int projectId) {
		Map<LocalDate, List<ResourceDTO>> mapOfPlansByDate = new HashMap<>();
		Map<Integer, ResourceDTO> mapResourceByPlanId = new HashMap<>();
		Map<Integer, Float> mapOfEffortPerDay = new HashMap<>();
		for (ResourceDTO resource : resourceDTOs) {
			mapOfEffortPerDay.put(resource.getUserPlanId(), resource.getEffortPerDay());
			mapResourceByPlanId.put(resource.getUserPlanId(), resource);
			List<LocalDate> dates = getDatesBetween(resource.getFromDate(), resource.getToDate());
			dates.forEach(d -> {
				if (mapOfPlansByDate.containsKey(d)) {
					List<ResourceDTO> userPlanIdList = mapOfPlansByDate.get(d);
					userPlanIdList.add(resource);
					mapOfPlansByDate.put(d, userPlanIdList);
				} else {
					List<ResourceDTO> userPlanIdList = new ArrayList<>();
					userPlanIdList.add(resource);
					mapOfPlansByDate.put(d, userPlanIdList);
				}
			});

		}

		Map<Integer, Set<LocalDate>> listDateOverloadedByPlan = new HashMap<>();
		mapOfPlansByDate.entrySet().forEach(e -> {
			List<Integer> userPlanIdList = e.getValue().stream().sorted(Comparator.comparing(ResourceDTO::getUpdatedOn))
					.map(r -> Integer.valueOf(r.getUserPlanId())).collect(Collectors.toCollection(LinkedList::new));
			if (mapOfEffortPerDay.get(userPlanIdList.get(0)) > STANDARD_EFFORT_PER_DAY) {
				addDateToListOrverload(listDateOverloadedByPlan, userPlanIdList.get(0), e.getKey());
			}
			for (int i = 1; i < userPlanIdList.size(); i++) {
				float totalEffort = 0;
				for (int j = 0; j <= i; j++) {
					totalEffort += mapOfEffortPerDay.get(userPlanIdList.get(j));
				}
				if (totalEffort > STANDARD_EFFORT_PER_DAY) {
					addDateToListOrverload(listDateOverloadedByPlan, userPlanIdList.get(i), e.getKey());
				}
			}
		});

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CustomValueUtil.DATE_D_M_Y_FORMAT);
		listDateOverloadedByPlan.entrySet().forEach(e -> {
			Set<LocalDate> listDateOverloaded = e.getValue().stream().sorted()
					.collect(Collectors.toCollection(LinkedHashSet::new));

			ResourceDTO resource = mapResourceByPlanId.get(e.getKey());
			Iterator<LocalDate> listDateIterator = listDateOverloaded.iterator();
			LocalDate startDate = listDateIterator.next();
			LocalDate endDate = startDate;
			resource.setOverloadedMsg("Overloaded periods: " + formatter.format(startDate));
			while (listDateIterator.hasNext()) {
				LocalDate currentItemDate = listDateIterator.next();
				if (currentItemDate.compareTo(endDate) > 1) {
					if (endDate.isAfter(startDate)) {
						resource.setOverloadedMsg(
								resource.getOverloadedMsg().concat(" -> " + formatter.format(endDate)));
					}
					startDate = currentItemDate;
					endDate = currentItemDate;
					resource.setOverloadedMsg(resource.getOverloadedMsg().concat(" & " + formatter.format(startDate)));
				} else {
					endDate = currentItemDate;
				}
			}

			if (endDate.isAfter(startDate)) {
				resource.setOverloadedMsg(resource.getOverloadedMsg().concat(" -> " + formatter.format(endDate)));
			}
		});
		List<ResourceDTO> filteredResources = new ArrayList<>();
		for (ResourceDTO resDTO : resourceDTOs) {
			if (resDTO.getProjectId() == projectId) {
				filteredResources.add(resDTO);
			}
		}
		return filteredResources;
	}

	private void addDateToListOrverload(Map<Integer, Set<LocalDate>> listDateOverloadedByPlan, Integer userPlanId,
			LocalDate date) {
		if (listDateOverloadedByPlan.containsKey(userPlanId)) {
			Set<LocalDate> listDateOverloaded = listDateOverloadedByPlan.get(userPlanId);
			listDateOverloaded.add(date);
			listDateOverloadedByPlan.put(userPlanId, listDateOverloaded);
		} else {
			Set<LocalDate> listDateOverloaded = new HashSet<>();
			listDateOverloaded.add(date);
			listDateOverloadedByPlan.put(userPlanId, listDateOverloaded);
		}
	}

	private List<LocalDate> getDatesBetween(Date startDate, Date endDate) {
		LocalDate startDateByLocal = convertToLocalDate(startDate);
		LocalDate endDateByLocal = convertToLocalDate(endDate).plusDays(1);

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDateByLocal, endDateByLocal);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDateByLocal.plusDays(i))
				.collect(Collectors.toList());
	}

	private LocalDate convertToLocalDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}

	private List<ResourceAllocationDTO> getListMemberOfProject(int projectId) {
		List<ResourceAllocationDTO> listResource = new ArrayList<>();
		List<Object> listMembers;
		listMembers = userQmsRepository.getAllUserEachProject(projectId);
		for (Object member : listMembers) {
			Object[] memberProperties = (Object[]) member;
			int userId = Integer.parseInt(memberProperties[0].toString());
			String fullName = memberProperties[1].toString();
			String role = memberProperties[2].toString();
			listResource.add(new ResourceAllocationDTO(userId, fullName, role));
		}
		return listResource;
	}

	private final int STANDARD_EFFORT_PER_DAY = 8;

  /* (non-Javadoc)
   * @see com.cmc.dashboard.service.UserPlanService#isExistPlanId(java.lang.Integer)
   */
  @Override
  public boolean isExistPlanId(Integer planId) {
    return userPlanRepository.exists(planId);
  }

  /* (non-Javadoc)
   * @see com.cmc.dashboard.service.UserPlanService#getProjectIdFromUserPlan(java.lang.Integer)
   */
  @Override
  public int getProjectIdFromUserPlan(Integer planId) {
    return userPlanRepository.findOne(planId).getProjectId();
  }

@Override
public Map<String, Object> getListTimeSheetByUserId(int userId, int month, int year,String projectName, int activePage, int size) {
	Map<String, Object> map = new HashMap<>();
	 List<TimesheetDTO> listResult= new ArrayList<TimesheetDTO>();
	 List<String> listProjectName = userPlanRepository.getListNameProjectTimesheet(userId, month, year);
	 Sort sortable = null;
	 Pageable pageable = new PageRequest(activePage-1, size,sortable);
	 Page<Object> pageTimeSheet=null;
	 try {
	pageTimeSheet = userPlanRepository.getListTimesheet(userId, month, year, projectName, pageable);
	List<Object> listObject = pageTimeSheet.getContent();
	for(Object object : listObject) {
		Object[] ob  = (Object[]) object;
		String projectTaskId= ((Integer)ob[1]).toString();
		int spentTime=0;
		String sumSpentTime=userPlanRepository.sumSpentTime(projectTaskId);
		if(sumSpentTime!=null) { 
			spentTime=Integer.parseInt(sumSpentTime);
		}
		TimesheetDTO dto= new TimesheetDTO((String)ob[0],projectTaskId,(String)ob[2],"",(Integer)ob[3]==null?0:(float)(Integer)ob[3]/3600,(float)spentTime/3600,(String)ob[4]);
		listResult.add(dto);
	}
	 }
	 catch(Exception e) {
		 e.printStackTrace();
	 }
		map.put("listTimesheet", listResult);
		map.put("listProjectName",listProjectName);
		map.put("total",pageTimeSheet.getTotalElements());
	return map;
}

@Override
public List<Integer> getSumEffortPerDayByDay(String date,int userId) {
	// TODO Auto-generated method stub
	return userPlanRepository.getSumEffortPerDayByDate(date,userId);
}

@Override
public UserPlan getPlanById(int planId) {
	// TODO Auto-generated method stub
	return userPlanRepository.findOne(planId);
}

@Override
public List<String> getListProjectTimeSheetByUserId(int userId, int month, int year) {
	
	return 	userPlanRepository.getListNameProjectTimesheet(userId, month, year);
}

@Override
public  ResponseEntity<Object> checkUserPlan(int userId, String from, String to) {
	List<GroupUser> listGroupUser = groupUserRepository.findByUserId(userId);
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	//SimpleDateFormat outputFormater=new SimpleDateFormat("dd-MM-yyyy");
	String result ="";
	//String sche="";
	int check = 0;
	try {
		Date fromDate =  formatter.parse(from);
		Date toDate = formatter.parse(to);
		if(listGroupUser.size()>0) {
			for(GroupUser gu : listGroupUser) {
				if(fromDate.getTime()>=gu.getStartDate().getTime()&& toDate.getTime()<=gu.getEndDate().getTime()) 
					check=1;
			//	sche += "\t\n"+"Start: "+ outputFormater.format(gu.getStartDate())+" End: "+outputFormater.format(gu.getEndDate()) + " Group: "+ groupRepository.findOne(gu.getGroupId()).getGroupName();
			}
			if(check==0) 
			result = Constants.HTTP_STATUS_MSG.USER_PLAN_NOT_COMFORTABLE;
			//+"\t\n"+ sche;
		} else {
			result = Constants.HTTP_STATUS_MSG.USER_PLAN_NOT_COMFORTABLE;
		}
	} catch (ParseException e) {
		e.printStackTrace();
	}
	if("".equals(result)) return null;
	return  new ResponseEntity<>(result,
	          HttpStatus.BAD_REQUEST);
}
}
