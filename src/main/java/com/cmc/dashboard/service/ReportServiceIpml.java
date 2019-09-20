package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.DashboardDTO;
import com.cmc.dashboard.dto.EfficiencyDTO;
import com.cmc.dashboard.dto.EffortEfficiencyDetails;
import com.cmc.dashboard.dto.ProjectCssDetailDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.ResultType;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.model.UserPlanDetail;
import com.cmc.dashboard.qms.model.QmsCustomValue;
import com.cmc.dashboard.qms.model.QmsMember;
import com.cmc.dashboard.qms.model.QmsProject;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.DeliveryUnitRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectTypeRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author nahung
 *
 */
/**
 * @author: CMC-GLOBAL
 * @Date: Mar 12, 2018
 */
@Service
@Transactional
public class ReportServiceIpml implements ReportService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	CustomValueRepository customValueRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	MemberQmsRepository memberQmsRepository;

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	UserPlanDetailRepository detailPlanRepository;

	@Autowired
	ProjectBillableRepository projectBillableRepository;
    
	@Autowired
	ProjectTypeRepository projectTypeRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private DeliveryUnitRepository deliveryRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getChartCSSService(int, int,
	 * int)
	 */
	@Override
	public List<CssChartDTO> getChartCSSService(int month, int year, int userId) {
		List<CssChartDTO> lstCssChartDto = new ArrayList<>();
		List<String> lstUnitName = this.getDeliveryUnit();
		List<String> lstUnitId = null;
		try {
		lstUnitId = this.getDeliveryUnitId();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for(String elem : lstUnitId) {
			System.out.println(elem);
		}
		if (lstUnitName.isEmpty()) {
			return new ArrayList<>();
		}
		List<CssChartDTO> lstCss = null;
		try {
		lstCss = projectCssRepository.getAvgCss(month, year, lstUnitId);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (lstCss == null)
			return new ArrayList<>();
		for (int i = 0; i < lstUnitId.size(); i++) {
			lstCssChartDto.add(this.getListCssChart(lstCss, lstUnitId.get(i), lstUnitName , i));
		}
		return lstCssChartDto;
	}

	/**
	 * 
	 * get Css chart each DU
	 * 
	 * @param lstCss
	 * @param unit
	 * @return CssChartDTO
	 * @author: CMC-GLOBAL
	 */
	private CssChartDTO getListCssChart(List<CssChartDTO> lstCss, String unit,List<String> listUnitName,int index) {
		CssChartDTO cssChartDTO = new CssChartDTO();
		cssChartDTO.setDuName(listUnitName.get(index));
		for (int i = 0; i < lstCss.size(); i++) {
			if (lstCss.get(i).getDuName().equals(unit)) {
				cssChartDTO.setAverageCss(lstCss.get(i).getAverageCss());
				return cssChartDTO;
			}
		}
		return cssChartDTO;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getAllProject()
	 */

	@Override
	public List<CssChartDTO> getProjectCSSService(int month, int year, int userId) {
		List<CssChartDTO> listCssChartDto = new ArrayList<>();
		List<String> listDU = projectCssRepository.getAllDeliveryUnitCSS();
		if (listDU.isEmpty()) {
			return listCssChartDto;
		} else {
			CssChartDTO cssChartDTO = null;
			for (String deliveryUnit : listDU) {
				cssChartDTO = new CssChartDTO(deliveryUnit, 0);
				listCssChartDto.add(cssChartDTO);
			}
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY, 0));
			listCssChartDto = this.getListCssChart(month, year, listDU);
		}
		listCssChartDto = this.addObjectTotal(listCssChartDto, month, year);

		String groupName = null;

		try {
			groupName = userQmsRepository.findGroup(userId).getLastname();
		} catch (NullPointerException e) {
			return listCssChartDto;
		}

		if (groupName == null || groupName.isEmpty() || groupName.length() <= 3) {
			return listCssChartDto;
		}

		String deliveryUnit = groupName.substring(groupName.lastIndexOf(CustomValueUtil.DA_PREFIX) + 3);

		switch (deliveryUnit) {
		case CustomValueUtil.BOD:
			return listCssChartDto;
		case CustomValueUtil.QA:
			return listCssChartDto;
		case CustomValueUtil.PROJECT_MANAGER:
			return listCssChartDto;
		default:
			return this.setListCssChartByDU(listCssChartDto, deliveryUnit);
		}
	}

	private List<CssChartDTO> getListCssChart(int month, int year, List<String> deliveryUnits) {
		List<CssChartDTO> cssChartDTOs = new ArrayList<>();
		CssChartDTO cssChartDTO = null;
		double avgCss = 0;
		for (String deliveryUnit : deliveryUnits) {
			avgCss = projectCssRepository.getAvgCssByDeliveryUnit(month, year, deliveryUnit);
			cssChartDTO = new CssChartDTO(deliveryUnit, avgCss);
			cssChartDTOs.add(cssChartDTO);
		}
		return cssChartDTOs;
	}

	/**
	 * Set list Css chart if user login is DU Lead
	 * 
	 * @param listCssChartDto
	 * @param deliveryUnit
	 * @return List<CssChartDTO>
	 * @author: DVNgoc
	 */
	private List<CssChartDTO> setListCssChartByDU(List<CssChartDTO> listCssChartDto, String deliveryUnit) {
		List<CssChartDTO> listCssChartDtoDU = new ArrayList<>();
		for (CssChartDTO chartDTO : listCssChartDto) {
			if (chartDTO.getDuName().trim().equals(deliveryUnit.trim())) {
				listCssChartDtoDU.add(chartDTO);
			}
		}
		return listCssChartDtoDU;
	}

	/**
	 * Add Object Total Css for Company
	 * 
	 * @param listCssChartDto
	 * @param month
	 * @param year
	 * @return List<CssChartDTO>
	 * @author: DVNgoc
	 */
	private List<CssChartDTO> addObjectTotal(List<CssChartDTO> listCssChartDto, int month, int year) {
		if (listCssChartDto.isEmpty()) {
			List<String> deliveryUnits = projectQmsRepository.getListDeliveryUnit();
			for (String deliveryUnit : deliveryUnits) {
				listCssChartDto.add(new CssChartDTO(deliveryUnit, 0));
			}
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY, 0));
			return listCssChartDto;
		} else {
			listCssChartDto.add(new CssChartDTO(CustomValueUtil.COMPANY,
					MethodUtil.formatDoubleNumberType(projectCssRepository.getAvgCssByDeliveryUnit(month, year, ""))));
			return listCssChartDto;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getCssDetails(java.lang.String,
	 * int, int)
	 */
	@Override
	public List<ProjectCssDetailDTO> getCssDetails(String deliveryUnit, int month, int year) {

		if (deliveryUnit.trim().equals(CustomValueUtil.COMPANY)) {
			deliveryUnit = "";
		}

		List<Integer> listProjectId = projectCssRepository.getListProjectId(deliveryUnit, month, year);

		if (listProjectId.isEmpty()) {
			return new ArrayList<>();
		}

		List<ProjectCssDetailDTO> listResult = new ArrayList<>();
		int maxCssValue = projectCssRepository.getMaxOfCssValueNumber(deliveryUnit, month, year);
		ProjectCssDetailDTO projectCssDetailDto;
		List<QmsProject> listProjectQms = projectQmsRepository.findAll();
		List<QmsCustomValue> listCustomValue = customValueRepository.getAllValueProject();
		List<QmsUser> users = userQmsRepository.getUserIsPm();
		List<QmsMember> members = memberQmsRepository.getAllMemberRolePM();

		for (Integer projectId : listProjectId) {
			projectCssDetailDto = new ProjectCssDetailDTO();
			projectCssDetailDto.setProjectId(projectId);
			projectCssDetailDto.setProjectName(listProjectQms);
			projectCssDetailDto.setProjectManager(users, members);
			projectCssDetailDto.setStartDate(listCustomValue);
			projectCssDetailDto.setEndDate(listCustomValue);
			projectCssDetailDto.setCss(projectCssRepository.getCssTimeByProjectId(projectId, month, year), maxCssValue);
			listResult.add(projectCssDetailDto);
		}

		return (listResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getProjectByType(int, int, int)
	 */
	@Override
	public List<DashboardDTO<Integer>> getProjectByType(int month, int year, int userId) {
		List<DashboardDTO<Integer>> lstMapType = new LinkedList<>();

		// Get group info from qms db
//		String groupName =this.getGroupUser(userId);
//		if (groupName==null|| !MethodUtil.isValidDate(month, year)) {
//			return lstMapType;
//		}
//		Set<String> units = new LinkedHashSet<>(this.getDeliveryUnit(groupName, userId));
		try {
        Set<String> units =new LinkedHashSet<>(this.getDeliveryUnit());
		if (units.isEmpty()) {
			return lstMapType;
		}
		
		Map<String, String> map = MethodUtil.getDayOfDate(month, year);
		String startDate = map.get(CustomValueUtil.START_DATE_KEY);
		String endDate = map.get(CustomValueUtil.END_DATE_KEY);

//		final String possibleValue = customValueRepository.getPossibleValue(CustomValueUtil.PROJECT_TYPE_ID);
//		Set<String> projectType = new LinkedHashSet<>(this.splitPossibleValue(possibleValue));
		Set<String> projectType = projectTypeRepository.getListNameProjectType();
		List<Object> projects = projectRepository.getProjectByType(units, startDate, endDate);
		lstMapType = this.countProjectByType(projects, units, projectType);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return lstMapType;
	}

	/**
	 * Count project by type for each unit
	 * 
	 * @param objects
	 * @param units
	 * @param projectType
	 * @param groupName
	 * @return List<Map<String,String>>
	 * @author: NVKhoa
	 */
	public List<DashboardDTO<Integer>> countProjectByType(List<Object> objects, Set<String> units,
			Set<String> projectType) {
		List<DashboardDTO<Integer>> projectByType = new LinkedList<>();

		List<ResultType<Integer>> resultType;
		for (String unit : units) {

			int total = 0;
			resultType = new LinkedList<>();
			for (String type : projectType) {
				int count = this.countProject(unit, type, objects);
				resultType.add(new ResultType<Integer>(type, count));
				total += count;
			}

			projectByType.add(new DashboardDTO<>(unit, resultType, total));
		}
		return projectByType;
	}
	
	
	private List<String> getDeliveryUnit(){
		List<String> listUnit = new LinkedList<>();
		listUnit =deliveryRepository.getAllDeliveruUnitName();
		return listUnit;
	}
	private List<String> getDeliveryUnitId(){
		List<String> listUnit = new LinkedList<>();
		listUnit =deliveryRepository.getAllDeliveruUnitId();
		return listUnit;
	}
	/**
	 * Total project by type for each delivery unit
	 * 
	 * @param unit
	 * @param projectType
	 * @param list
	 * @return int
	 * @author: CMC-GLOBAL
	 */
	private int countProject(String unit, String projectType, List<Object> objects) {
		int count = 0;
		for (Object object : objects) {

			Object[] obj = (Object[]) object;

			if (unit.equals(obj[0].toString()) && projectType.equals(obj[1].toString())) {
				count = Integer.parseInt(obj[2].toString());
			} else if (unit.equals(CustomValueUtil.COMPANY) && projectType.equals(obj[1].toString())) {
				count += Integer.parseInt(obj[2].toString());
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getProjectListByType(java.lang.
	 * String, java.util.List, int, int)
	 */
	@Override
	public List<ProjectDTO> getProjectListByType(String duName, String projectType, int month, int year) {
		Map<String, String> map = MethodUtil.getDayOfDate(month, year);
		String startDate = map.get(CustomValueUtil.START_DATE_KEY);
		String endDate = map.get(CustomValueUtil.END_DATE_KEY);
		return this.getProjectQms(duName, projectType, startDate, endDate);
	}

	/**
	 * Query list of projects by type, delivery unit
	 * 
	 * @param duName
	 * @param projectType
	 * @param month
	 * @param year
	 * @return List<ProjectDTO>
	 * @author: NVKhoa
	 */
	private List<ProjectDTO> getProjectQms(String duName, String projectType, String startDate, String endDate) {

		List<ProjectDTO> projects = new ArrayList<>();

		List<Object> objects = projectQmsRepository.getListProjectByType(duName, projectType, startDate, endDate);
		ProjectDTO project;
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			project = new ProjectDTO();
			project.setProjectId(Integer.parseInt(obj[0].toString()));
			project.setProjectName(obj[1].toString());
			project.setProjectManager(obj[2] == null ? "" : obj[2].toString());
			project.setProjectSize(obj[3] == null ? "0" : obj[3].toString());
			project.setStatus(obj[4] == null ? 0 : Integer.parseInt(obj[4].toString()));
			project.setStartDate(obj[5] == null ? "" : obj[5].toString());
			project.setEndDate(obj[6] == null ? "" : obj[6].toString());

			projects.add(project);
		}
		return projects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ReportService#getEffortEfficiency(int, int,
	 * int)
	 */

	@Override
	public List<EfficiencyDTO> getEffortEfficiency(int month, int year, int userId) {
		List<EfficiencyDTO> lstEfficiency = new ArrayList<>();
		if (!MethodUtil.isValidDate(month, year)) {
			return lstEfficiency;
		}
		LocalDate date = LocalDate.of(year, month, 1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM-yyyy");

		final String fullYear = date.format(formatter);
		final String monthYear = date.format(formatter1);
		try {
			List<ProjectBillable> lstProjectBillable = projectBillableRepository.getBillableByMonth(fullYear);
			List<UserPlanDetail> lstUserPlanDetail = detailPlanRepository.getUserPlanDetailByPlanMonth(monthYear);
			// Get group info from qms db
			//String groupName =this.getGroupUser(userId);	
//			if (groupName == null) {
//			return lstEfficiency;
//		     }
			List<String> lstUnit = this.getDeliveryUnit();
			List<String> lsUnitId = this.getDeliveryUnitId();
			EfficiencyDTO efficiency;
            int lengthlsUnit = lstUnit.size();
			for (int i=0; i< lengthlsUnit;i++) {
				efficiency = new EfficiencyDTO();
				efficiency.setUnit(lstUnit.get(i));
				efficiency.setEfficiency(calculateEfficiency(lsUnitId.get(i), lstProjectBillable, lstUserPlanDetail,lstUnit.get(i)));
				lstEfficiency.add(efficiency);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return lstEfficiency;
		}
		return lstEfficiency;
	}

	/**
	 * Calculate effort efficiency
	 * 
	 * @param deliveryUnit
	 * @param lstProjectBillable
	 * @param lstUserPlanDetail
	 * @return int
	 * @author: NVKhoa
	 * @modifier:LXLinh
	 */
	private float calculateEfficiency(String deliveryUnitId, final List<ProjectBillable> lstProjectBillable,
			final List<UserPlanDetail> lstUserPlanDetail,String deliveryUnitName) {

		float efficiency = 0;
		float totalBillable = 0;
		float totalManDay = 0;
		if (lstProjectBillable != null && lstUserPlanDetail != null) {
			for (ProjectBillable projectBillable : lstProjectBillable) {
				if (!MethodUtil.isNull(projectBillable.getGroupId())
						&& String.valueOf(projectBillable.getGroupId()).equals(deliveryUnitId)) {
					totalBillable += projectBillable.getBillableValue();
				}
			}

			for (UserPlanDetail userPlanDetail : lstUserPlanDetail) {
				if (!MethodUtil.isNull(userPlanDetail.getDeliveryUnit())
						&& userPlanDetail.getDeliveryUnit().equals(deliveryUnitName)) {
					totalManDay += userPlanDetail.getManDay();
				}
			}
		}
		if (totalManDay > 0) {
			efficiency = (totalBillable / totalManDay) * 100;
		}

		return efficiency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ReportService#getListProjectBillabeByDU(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<EffortEfficiencyDetails> getListProjectBillabeByDU(String duName, String billableMonth) {
		List<EffortEfficiencyDetails> listEffortEfficiencyDetails = new ArrayList<>();
		try {
			List<Object> listOfObjects;
			if (duName.contains(CustomValueUtil.COMPANY.trim())) {
				listOfObjects = detailPlanRepository.getListUserPlanBillableDetail(billableMonth);
			} else {
				listOfObjects = detailPlanRepository.getListUserPlanBillableDetail(duName, billableMonth);
			}

			EffortEfficiencyDetails effortEfficiencyObj;
			for (Object object : listOfObjects) {
				Object[] listProps = (Object[]) object;
				String projectName = listProps[0].toString();
				String pmName = listProps[1].toString();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = formatter.parse(listProps[2].toString());
				Date endDate = formatter.parse(listProps[3].toString());
				float billableValue = Float.parseFloat(listProps[4].toString());
				float manDay = Float.parseFloat(listProps[5].toString());
				float effortEfficiency = Float.parseFloat(listProps[6].toString());

				effortEfficiencyObj = new EffortEfficiencyDetails(projectName, pmName, startDate, endDate,
						billableValue, manDay, effortEfficiency);
				listEffortEfficiencyDetails.add(effortEfficiencyObj);
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
			return listEffortEfficiencyDetails;
		}

		return listEffortEfficiencyDetails;
	}

	/**
	 * Get list of unit by group's name and user identify
	 * 
	 * @param groupName
	 * @param userId
	 * @return List<String>
	 * @author: NVKhoa
	 */
	private List<String> getDeliveryUnit(final String groupName, final int userId) {

		List<String> lstUnit = new LinkedList<>();
		if (groupName == null) {
			return lstUnit;
		}
		if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)|| groupName.contains(CustomValueUtil.PROJECT_MANAGER)) {
			String strDeliveryUnit = customValueRepository.getPossibleValue(CustomValueUtil.DELIVERY_UNIT_ID);
			lstUnit.addAll(this.splitPossibleValue(strDeliveryUnit));
		} else if (groupName.contains(CustomValueUtil.DU_LEAD)) {
			lstUnit = userQmsRepository.getDeliveryUnitByUser(userId);
		}
		return lstUnit;
	}

	/**
	 * Split possible string from custom field
	 * 
	 * @param possible
	 * @return List<String>
	 * @author: NVKhoa
	 */
	public List<String> splitPossibleValue(String data) {
		List<String> lstPossible = new LinkedList<>();
		String[] possible = data.split(CustomValueUtil.SPLIT_CHAR);
		for (String element : possible) {
			if (!element.trim().isEmpty()) {
				lstPossible.add(element.trim());
			}
		}

		return lstPossible;
	}

	private Group getGroupUserDashboard(int userId) {
		Optional<Group> groupUser = userRepository.getGroupByUser(userId);

		return groupUser.isPresent() ? groupUser.get() : null;

	}

      /**
       * 
       * get group name of dasboard by from user id red mine
       * @param userId is user id of redmine
       * @return String 
       * @author: LXLinh
       */
	private String getGroupUser(int userId) {
		 User user=this.getUserByUserName(userId);
		 if(user!=null)
		 {
			 Group  group =this.getGroupUserDashboard(user.getUserId()) ;
			 if(group!=null)
				 return group.getGroupName();
		 }
		return  null;
	}

	/**
	 * 
	 * get user id dashboard from user id of readmine
	 * 
	 * @author: LXLinh
	 */
	private User getUserByUserName(int id) {
		Optional<User> user = Optional.empty();
		Optional<QmsUser> userQms = Optional.ofNullable(userQmsRepository.findOne(id));
		if (userQms.isPresent()) {
			user = Optional.ofNullable(userRepository.findByUserName(userQms.get().getLogin()));
		}
		return user.isPresent() ? user.get() : null;
	}

}
