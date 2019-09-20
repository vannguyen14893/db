package com.cmc.dashboard.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.EditProjectDTO;
import com.cmc.dashboard.dto.IssueJiraDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.MemberDTO;
import com.cmc.dashboard.dto.NoncomplianceTasksDto;
import com.cmc.dashboard.dto.OverdueTasksDto;
import com.cmc.dashboard.dto.ProjectBasicInfoDTO;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.ProjectInfoDTO;
import com.cmc.dashboard.dto.ProjectJiraDTO;
import com.cmc.dashboard.dto.ProjectListDTO;
import com.cmc.dashboard.dto.ProjectListTableDTO;
import com.cmc.dashboard.dto.ProjectProgressDto;
import com.cmc.dashboard.dto.ProjectTableDTO;
import com.cmc.dashboard.dto.ProjectTimesheetsDto;
import com.cmc.dashboard.dto.ProjectTypeLogDTO;
import com.cmc.dashboard.dto.ResourceFilterDto;
import com.cmc.dashboard.dto.TeamHourByActivityDto;
import com.cmc.dashboard.dto.ThroughtputBurndownDto;
import com.cmc.dashboard.dto.TimeEntriesDTO;
import com.cmc.dashboard.dto.TimeProjectDTO;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserJiraDTO;
import com.cmc.dashboard.dto.UserLogTimeDTO;
import com.cmc.dashboard.dto.UserTimeSheetDTO;
import com.cmc.dashboard.dto.WorkProgressDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.HistorySyncData;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.model.ProjectDTOTemp;
import com.cmc.dashboard.model.ProjectRole;
import com.cmc.dashboard.model.ProjectType;
import com.cmc.dashboard.model.ProjectUser;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsCustomValue;
import com.cmc.dashboard.qms.model.QmsMember;
import com.cmc.dashboard.qms.model.QmsProject;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.IssuesRepository;
import com.cmc.dashboard.qms.repository.JournalsRepository;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectListQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.TimeEntriesRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.DeliveryUnitRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.HistorySyncDataRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectRiskRepository;
import com.cmc.dashboard.repository.ProjectRoleRepository;
import com.cmc.dashboard.repository.ProjectTypeRepository;
import com.cmc.dashboard.repository.ProjectUserRepository;
import com.cmc.dashboard.repository.SkillRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.ConvertDto;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.RegularExpressions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	CustomValueRepository customValueRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	MemberQmsRepository memberQmsRepository;

	@Autowired
	UserPlanDetailRepository userPlanDetailRepository;

	@Autowired
	TimeEntriesRepository timeEntriesRepository;

	@Autowired
	IssuesRepository issuesRepository;

	@Autowired
	ProjectListQmsRepository projectListQmsRepository;

	@Autowired
	private JournalsRepository journalsRepository;

	@Autowired
	private ProjectRiskRepository projectRiskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectUserRepository projectUserRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private ProjectTypeRepository projectTypeRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private ProjectRoleRepository projectRoleRepository;

	@Autowired
	private DeliveryUnitRepository deliverUnitRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private HistorySyncDataRepository historySyncDataRepository;
	@Autowired
	private ProjectTypeLogService projectTypeLogService;
	@Override
	public ProjectListTableDTO getProjectListAll(int userId, int page, int size, String column, String sort,
			String projectName, String status, String PM, String startDateFrom, String startDateTo, String endDateFrom,
			String endDateTo, String DU, String typeProject, String type) {
//		Page<ProjectListQms> pageQms = new PageImpl<>(new ArrayList<>(), null, 0);
//		Set<String> listPM = new HashSet<String>() ;
//	//	QmsUser qmsUser = userQmsRepository.findGroup(userId);
//	//	String groupName = qmsUser == null ? null : qmsUser.getLastname();
//		Pageable pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, column));
//		if (CustomValueUtil.ASC.equalsIgnoreCase(sort)) {
//			pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, column));
//		}
//		if (groupName != null && !groupName.isEmpty()) {
//			pageQms = this.getProjectByDu(groupName, userId, pageable, projectName, status, PM, startDateFrom,
//					startDateTo, endDateFrom, endDateTo, DU, typeProject);
//			listPM = getAllPM(userId, groupName);
//		}
//		
//		List<String> listDu = customValueRepository.getAllDu();
//		List<String> listType = customValueRepository.getAllProjectType();

// nvtiep2);
		List<ProjectTableDTO> listDTO = new ArrayList<ProjectTableDTO>();
		Set<String> du = null;
		Set<String> pm = null;
		Set<String> projectType = null;
		String sortColumn = "";
		if ("name".equals(column))
			sortColumn = "project_name";
		else if ("deliveryUnit".equals(column))
			sortColumn = "g.group_name";
		else if ("startDate".equals(column))
			sortColumn = "start_date";
		else
			sortColumn = "end_date";
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = new Sort(Sort.Direction.ASC, sortColumn);
		} else {
			sortable = new Sort(Sort.Direction.DESC, sortColumn);
		}
		Pageable pageable = new PageRequest(page - 1, size, sortable);
		Page<Object> pageProject = null;
		try {
			pageProject = projectRepository.getAllProject(projectName, status, PM, startDateFrom, startDateTo,
					endDateFrom, endDateTo, DU, typeProject, type, pageable);
			List<Object> listProjectObject = pageProject.getContent();
			// List<String> listProject= pageProject.getContent();

			du = groupRepository.getListNameDu();
			pm = projectUserRepository.getListNamePM(1);
			projectType = projectTypeRepository.getListNameProjectType();

			for (Iterator<Object> it = listProjectObject.iterator(); it.hasNext();) {
				Object[] object = (Object[]) it.next();
				try {
					Float i = projectBillableRepository.getBillableEffortsByProjectId((Integer) object[0]);
					ProjectTableDTO pdt = new ProjectTableDTO((Integer) object[0], (String) object[1],
							(String) object[2], (String) object[3], (String) object[4], (byte) object[5],
							object[6] == null ? null : (Date) object[6], object[6] == null ? null : (Date) object[7],
							(String) object[8], (Byte) object[9],i == null? 0:i);
					listDTO.add(pdt);
				} catch (Exception e) {
					System.out.println((Integer) object[0]);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ProjectListTableDTO(listDTO, pm, du, projectType, pageProject.getTotalElements());

	}

	/**
	 * Get All project manager
	 *
	 * @param name of project manager
	 * @return
	 * @author NTQuy
	 * @return
	 */

	public Set<String> getAllPM(int userId, String groupName) {
		// QmsUser qmsUser = userQmsRepository.findGroup(userId);
		// if (qmsUser == null || qmsUser.getLastname() == null) {
		// return new HashSet<>();
		// }
		// String groupName = qmsUser.getLastname();

		List<QmsUser> users = userQmsRepository.getUserIsPm();
		List<QmsMember> members = memberQmsRepository.getAllMemberRolePM();
		Set<String> allPM = new TreeSet<>(Comparator.comparing(String::toString));
		List<QmsProject> projects = this.getAllProjectByDuWithoutPM(groupName, userId);
		for (QmsProject projectQms : projects) {
			for (QmsMember member : members) {
				for (QmsUser user : users) {
					if (member.getProjectId() == projectQms.getId() && member.getUserId() == user.getId()) {
						allPM.add(user.getLastname() + " " + user.getFirstname());
					}
				}
			}
		}
		return allPM;
	}

	private List<QmsProject> getAllProjectByDuWithoutPM(String groupName, int userId) {

		List<QmsProject> projects = new ArrayList<>();

		if (groupName.contains(CustomValueUtil.PROJECT_MANAGER) || groupName.contains(CustomValueUtil.MEMBER)) {

			projects = projectQmsRepository.getProjectListByUserWithoutPMPage(userId);

		} else if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)) {

			projects = projectQmsRepository.getAllProjectWithoutPMPage();

		} else if (groupName.contains(CustomValueUtil.DU_LEAD)) {

//			String duName = MethodUtil.getUnitFromDUL(groupName);
			List<String> duName = userQmsRepository.getDeliveryUnitByUser(userId);
			projects = projectQmsRepository.getProjectByDeliveryUnitWithoutPMPage(duName);
		}

		return projects;
	}

	/**
	 * Get list qms project by duName or user identify
	 *
	 * @param groupName
	 * @param userId
	 * @return List<ProjectQms>
	 * @author: ntquy
	 */

//	private Page<ProjectDTOTemp> getProjectByDu(String groupName, int userId, Pageable pageRequest, String projectName,
//			String status, String PM, String startDateFrom, String startDateTo, String endDateFrom, String endDateTo,
//			String DU, String typeProject,int type) {
//
//		Page<Object> projects = new PageImpl<>(new ArrayList<>());
//		List<ProjectDTOTemp> listProjectDto = new ArrayList<>();
//			if(type == 0) {
//			   projects = projectRepository.getProjectListByUser(userId, pageRequest, projectName, status, PM,
//					startDateFrom, startDateTo, endDateFrom, endDateTo, DU, typeProject);
//			} else {
//				projects = projectRepository.getProjectListByUserAdminOrPmoOrQa(pageRequest, projectName, status, PM, startDateFrom, startDateTo, endDateFrom, endDateTo, DU, typeProject);
//			}
//			  List<Object> listProjectObject=projects.getContent();
//			  ProjectDTOTemp projectDtoTemp = null;
//			for(Iterator<Object> it = listProjectObject.iterator(); it.hasNext();) {
//		    	Object[] data = (Object[]) it.next();
//		    	projectDtoTemp = new ProjectDTOTemp(Integer.parseInt(data[0].toString()), data[1] == null ? "" :data[1].toString(),
//						data[2] ==null ? "" : data[2].toString(),data[3] == null ? "": data[3].toString(),data[4] == null ? "" : data[4].toString(),
//						Integer.parseInt(data[5].toString()), data[6] == null ? null : (Date) data[6], data[7] == null ? null : (Date) data[7], data[8] == null ? "" : data[8].toString());
//				listProjectDto.add(projectDtoTemp);
//		    }
////		} else if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)) {
////
////			projects = projectListQmsRepository.getAllProject(pageRequest, projectName, status, PM, startDateFrom,
////					startDateTo, endDateFrom, endDateTo, DU, typeProject);
////
////		} else if (groupName.contains(CustomValueUtil.DU_LEAD)) {
////
////			List<String> duName = userQmsRepository.getDeliveryUnitByUser(userId);
////			projects = projectListQmsRepository.getProjectByDeliveryUnit(duName, pageRequest, projectName, status, PM,
////					startDateFrom, startDateTo, endDateFrom, endDateTo, DU, typeProject);
////		}
//		Page<ProjectDTOTemp> projects1 = new PageImpl<>(new ArrayList<>(listProjectDto));
//		return projects1;
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.cmc.dashboard.service.ProjectService#getBillableByProject(int)
	 */
	@Override
	public List<BillableDTO> getBillableByProject(int projectId) {
		List<BillableDTO> lstBillable = new ArrayList<>();
		List<TimeEntriesDTO> lstTimeEntries = new ArrayList<>();
		try {
			List<QmsCustomValue> customValues = customValueRepository.findByCustomizeId(projectId).get();
			// QmsProject project =
			// Optional.ofNullable(projectQmsRepository.findOne(projectId)).get();
			Project project = Optional.ofNullable(projectRepository.findOne(projectId)).get();

			Set<String> projectManager = new HashSet<>(
					Optional.ofNullable(projectRepository.getProjectManagerById(projectId)).get());

			ProjectDTO projectDto = new ProjectDTO(projectId, project.getName(), projectManager);

			projectDto.setCustomValue(customValues);

			// đồng bộ dữ liệu với redmine
			this.addListOfBillable(projectDto);
			Optional<List<Object>> optionalObject = projectBillableRepository.getListBillable(projectId);
			List<Object> objects = null;
			if (optionalObject.isPresent())
				objects = optionalObject.get();
			List<Object> objects2 = timeEntriesRepository.getManMonthOfResource(projectId);

			// Set value to billableDto
			BillableDTO billableDto;

			if (null == objects)
				return null;

			for (Object object : objects) {
				Object[] ob = (Object[]) object;
				billableDto = new BillableDTO();
				billableDto.setProjectBillableId(Integer.parseInt(ob[0].toString()));
				billableDto.setBillableMonth(ob[1].toString());
				billableDto.setBillableValue(ob[2] == null ? 0 : Float.parseFloat(ob[2].toString()));
				billableDto.setIssueCode(ob[3] == null ? null : ob[3].toString());
				billableDto.setManMonth(ob[4] == null ? 0 : Double.parseDouble(ob[4].toString()));
				lstBillable.add(billableDto);
			}

			// Set value to timeEntriesDTO
			TimeEntriesDTO timeEntriesDto;
			for (Object et : objects2) {
				Object[] ob = (Object[]) et;
				timeEntriesDto = new TimeEntriesDTO();
				timeEntriesDto.setSpentOn(ob[0].toString());
				timeEntriesDto.setManMonth(ob[1] == null ? 0 : Double.parseDouble(ob[1].toString()));
				lstTimeEntries.add(timeEntriesDto);
			}

			// Set manMonth from listEntries to listBillable
			for (TimeEntriesDTO timeEntriesDTO : lstTimeEntries) {
				for (BillableDTO billableDTO : lstBillable) {
					if (timeEntriesDTO.getSpentOn().equals(billableDTO.getBillableMonth())) {
						billableDTO.setManMonth(timeEntriesDTO.getManMonth());
						break;
					}
				}
			}

			// Get project info from redmine
			ProjectDTO projectInfo = this.getProjectInfo(projectId);

			// Get list month of Project from startDate to End date
			List<String> listMonthOfProject = this.getMonthByStartDateEndDateOfProject(projectInfo.getStartDate(),
					projectInfo.getEndDate());
			for (BillableDTO billableDTO : lstBillable) {
				for (String month : listMonthOfProject) {
					if (billableDTO.getBillableMonth().equals(month)) {
						billableDTO.setInactive(false);
						break;
					} else {
						billableDTO.setInactive(true);
					}
				}
			}

			return lstBillable;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return lstBillable;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return lstBillable;
		}

	}

	/**
	 * Create list of project billable
	 *
	 * @param projectDto
	 * @return
	 * @throws SQLException List<ProjectBillable>
	 * @author: NVKhoa
	 */
	private void addListOfBillable(ProjectDTO projectDto) {
		List<ProjectBillable> lstBillable = new ArrayList<ProjectBillable>();

		List<String> lstMonth = MethodUtil.getMonthBetween2Date(projectDto.getStartDate(), projectDto.getEndDate());
		ProjectBillable projectBillable;
		for (String month : lstMonth) {
			projectBillable = new ProjectBillable();
			projectBillable.setBillableMonth(month);
			projectBillable.setProjectId(projectDto.getProjectId());
			projectBillable.setPmName(projectDto.toStringManager());
//			if (projectDto.getDeliveryUnit() == null) {
//				projectBillable.setDeliveryUnit("");
//			} else {
//				projectBillable.setDeliveryUnit(projectDto.getDeliveryUnit());
//			}
			projectBillable.setStartDate(MethodUtil.convertStringToDate(projectDto.getStartDate()));
			projectBillable.setEndDate(MethodUtil.convertStringToDate(projectDto.getEndDate()));
			lstBillable.add(projectBillable);
		}
		this.getValueOfBillable(lstBillable, projectDto.getProjectId());
	}

	/**
	 * Synchronize project billable data between two database
	 *
	 * @param lstBillable
	 * @param projectId
	 * @return
	 * @throws SQLException List<ProjectBillable>
	 * @author: NVKhoa
	 */
	private void getValueOfBillable(List<ProjectBillable> lstBillable, int projectId) {
		List<ProjectBillable> billables = projectBillableRepository.getBillableByProject(projectId);

		List<ProjectBillable> list = new ArrayList<>();
		for (ProjectBillable element : lstBillable) {
			boolean isExist = false;
			for (ProjectBillable item : billables) {
				if (element.getBillableMonth().equals(item.getBillableMonth())) {
					item.setStartDate(element.getStartDate());
					item.setEndDate(element.getEndDate());
					item.setPmName(element.getPmName());
					isExist = true;
				}
			}
			if (!isExist) {
				list.add(element);
			}
		}
		billables.addAll(list);
		projectBillableRepository.save(billables);

		for (ProjectBillable element : billables) {
			boolean isExist = false;
			for (ProjectBillable item : lstBillable) {
				if (item.getBillableMonth().equals(element.getBillableMonth())) {
					isExist = true;
				}
			}
			if (!isExist) {
				projectBillableRepository.delete(element);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.cmc.dashboard.service.ProjectService#updateProjectBillable(java.lang.
	 * String)
	 */
	@Override
	public ListBillableDTO updateProjectBillable(String json) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonArray trade = element.getAsJsonArray();
		List<ProjectBillable> projectBillables = new ArrayList<>();
		List<BillableDTO> listBillable = new ArrayList<>();
		try {
			ProjectBillable projectBillable;
			for (JsonElement item : trade) {
				JsonObject object = item.getAsJsonObject();

				int pBillableId = Integer.parseInt(MethodUtil.getStringValue(object, "projectBillableId"));

				projectBillable = projectBillableRepository.findOne(pBillableId);
				int billableValue = Integer.parseInt(MethodUtil.getStringValue(object, "billableValue"));
				if (CustomValueUtil.BILLABLE_MAX_VALUE < billableValue
						|| billableValue < CustomValueUtil.BILLABLE_MIN_VALUE) {
					return new ListBillableDTO(MessageUtil.DATA_INVALID, listBillable);
				}
				projectBillable.setBillableValue(billableValue);
				if (MethodUtil.getStringValue(object, "issueCode").length() == 0) {
					return new ListBillableDTO(MessageUtil.DATA_EMPTY, listBillable);
				}
				if (!MethodUtil.validateLoginParams(MethodUtil.getStringValue(object, "issueCode"),
						RegularExpressions.STRING_PATTERN)) {
					return new ListBillableDTO(MessageUtil.DATA_UNFORMAT, listBillable);
				}
				projectBillable.setIssueCode(MethodUtil.getStringValue(object, "issueCode"));
				projectBillable.setUpdatedOn(new Date());

				projectBillables.add(projectBillable);

			}
			projectBillableRepository.save(projectBillables);
			listBillable = this.getBillableByProject(projectBillables.get(0).getProjectId());
			return new ListBillableDTO(MessageUtil.UPDATE_SUCCESS, listBillable);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ListBillableDTO(MessageUtil.DATA_NOT_EXIST, listBillable);

		} catch (NumberFormatException e) {

			e.printStackTrace();
			return new ListBillableDTO(MessageUtil.DATA_INVALID, listBillable);

		} catch (DataAccessException e) {

			return new ListBillableDTO(MessageUtil.UPDATE_ERROR, listBillable);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.cmc.dashboard.service.ProjectService#updateProjectBillable(java.lang.
	 * String)
	 */
	@Override
	public ListBillableDTO updateProjectBillable(List<BillableDTO> lstprojectBillable) {

		List<Integer> lstBillableId = lstprojectBillable.stream().map(BillableDTO::getProjectBillableId)
				.collect(Collectors.toList());
		List<BillableDTO> listBillables = new ArrayList<>();
		List<ProjectBillable> projectBillables = projectBillableRepository.getBillableByListId(lstBillableId);
		if (!projectBillables.isEmpty()) {
			// repeat attribute BillableValue and attribute IssueCode with the value was
			// updated
			projectBillables.stream()
					.forEach(proBillable -> lstprojectBillable.stream().filter(
							billableDto -> billableDto.getProjectBillableId() == proBillable.getProjectBillableId())
							.forEach(billableDto -> {
								proBillable.setBillableValue(billableDto.getBillableValue());
								proBillable.setIssueCode(billableDto.getIssueCode());
								proBillable.setUpdatedOn(new Date());
							}));
			projectBillableRepository.save(projectBillables);
			listBillables = this.getBillableByProject(projectBillables.get(0).getProjectId());
			return new ListBillableDTO(MessageUtil.UPDATE_SUCCESS, listBillables);
		}

		return new ListBillableDTO(MessageUtil.DATA_NOT_EXIST, listBillables);

	}

	@Override
	public List<ProjectBillable> getProjectBillableByProjectId(int projectId) {
		return projectBillableRepository.getProjectBillableByProjectId(projectId);
	}

	@Override
	public ProjectDTO getProjectInfo(int projectId) {

		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setStartDate(projectRepository.getProjectStartDateById(projectId).toString());
		projectDTO.setEndDate(projectRepository.getProjectEndDateById(projectId).toString());
		projectDTO.setProjectName(projectRepository.findOne(projectId).getName());

		return projectDTO;
	}

	@Override
	public List<String> getMonthByStartDateEndDateOfProject(String startDate, String endDate) {
		return MethodUtil.getMonthBetween2Date(startDate, endDate);
	}

	@Override
	public List<ProjectBasicInfoDTO> getProjectBasicInfoByProjectId(int projectId) {
		List<ProjectBasicInfoDTO> projectDTO = new ArrayList<>();
		try {
			List<Object> listObjInfo = projectRepository.getProjectBasicInfoByProjectId(projectId);
			if (listObjInfo == null) {
				return null;
			} else {
				ProjectBasicInfoDTO projectBaseInfo = new ProjectBasicInfoDTO();
				for (Object object : listObjInfo) {
					Object[] obj = (Object[]) object;
					projectBaseInfo.setProjectId(projectId);
					projectBaseInfo.setProjectName(obj[1] != null ? obj[1].toString() : "");
					projectBaseInfo.setProjectType(obj[2] != null ? obj[2].toString() : "");
					projectBaseInfo.setDeliveryUnit(obj[3] != null ? obj[3].toString() : "");
					projectBaseInfo.setStatus(obj[4] == null ? 0 : Integer.parseInt(obj[4].toString()));
					projectBaseInfo.setProjectCode(obj[5] != null ? obj[5].toString() : "");
					List<Skill> listSkill = new ArrayList<>();
					try {
						listSkill = skillRepository.listSkillOfProject(projectId);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					projectBaseInfo.setSkillName(listSkill);
					projectBaseInfo.setQualityId(obj[7] == null ? 0 : Integer.parseInt(obj[7].toString()));
					projectBaseInfo.setProcessId(obj[8] == null ? 0 : Integer.parseInt(obj[8].toString()));
					projectBaseInfo.setDeliveryId(obj[9] == null ? 0 : Integer.parseInt(obj[9].toString()));
					projectBaseInfo.setStartDate(obj[10] == null ? null : obj[10].toString());
					projectBaseInfo.setEndDate(obj[11] == null ? null : obj[11].toString());
					projectBaseInfo.setComment(obj[12] == null ? null : obj[12].toString());
					projectBaseInfo.setpStartDate(obj[13].toString());
					projectBaseInfo.setpEndDate(obj[14].toString());
					projectDTO.add(projectBaseInfo);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return projectDTO;
	}

	@Override
	public WorkProgressDTO getWorkProgressByProjectId(int projectId) {
		DecimalFormat df = new DecimalFormat("0.00");
		Double estimatedTime = (double) projectRepository.getProjectEstimatedTime(projectId);
		Double avgCss = (double) this.getProjectAvgCss(projectId);
		Double avgBillable = (double) this.getProjectAvgBillable(projectId);

		return new WorkProgressDTO(Float.parseFloat(df.format(estimatedTime)),
				projectRepository.getProjectOpenBugs(projectId), projectRepository.getProjectOpenRisks(projectId),
				Float.parseFloat(df.format(avgCss)), Float.parseFloat(df.format(avgBillable)));
	}

	/**
	 * Count tasks: status = inprogress || new, due_date = today
	 *
	 * @param projectId
	 * @return int
	 * @author: GiangTM
	 */
	private int getAttentionTasksTotalByProjectId(int projectId) {
		return issuesRepository.getAttentionTasksTotalByProjectId(projectId, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_IN_PROGRESS, CustomValueUtil.ISSUE_STATUS_ID_NEW);
	}

	/**
	 * Sum estimated time of task: status = inprogress || (status = new & due_date =
	 * today)
	 *
	 * @param projectId
	 * @return float
	 * @author: GiangTM
	 */
	private float getTodayEstimatedTimeTotalByProjectId(int projectId) {
		return issuesRepository.getTodayEstimatedTimeTotalByProjectId(projectId, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_IN_PROGRESS, CustomValueUtil.ISSUE_STATUS_ID_NEW);
	}

	/**
	 * Count tasks: closed_on = yeterday
	 *
	 * @param projectId
	 * @return int
	 * @author: GiangTM
	 */
	private int getYesterdayClosedTasksTotalByProjectId(int projectId) {
		return issuesRepository.getYesterdayClosedTasksTotal(projectId, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_CLOSED);
	}

	/**
	 * Sum estimated time of task: status = closed, closed_on = yesterday
	 *
	 * @param projectId
	 * @return float
	 * @author: GiangTM
	 */
	private float getYesterdayClosedTasksTimeByProjectId(int projectId) {
		return issuesRepository.getYesterdayClosedTasksTime(projectId, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_CLOSED);
	}

	/**
	 * Count bugs: status = open
	 *
	 * @param projectId
	 * @return int
	 * @author: GiangTM
	 */
	private int getOpenBugsTotalByProjectId(int projectId) {
		return issuesRepository.getOpenBugsTotalByProjectId(projectId, CustomValueUtil.BUG_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_NEW, CustomValueUtil.ISSUE_STATUS_ID_IN_PROGRESS);
	}

	/**
	 * Count bugs: status = closed, closed_on = yesterday
	 *
	 * @param projectId
	 * @return int
	 * @author: GiangTM
	 */
	private int getYesterdayClosedBugsTotalByProjectId(int projectId) {
		return issuesRepository.getYesterdayClosedBugsTotalByProjectId(projectId, CustomValueUtil.BUG_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_CLOSED);
	}

	/**
	 * Count risks: status = open
	 *
	 * @param projectId
	 * @return int
	 * @author: GiangTM
	 */
	private int getOpenRisksTotalByProjectId(int projectId) {
		return projectRiskRepository.getOpenRisksTotalByProjectId(projectId, CustomValueUtil.RISK_STATUS_ID_OPEN);
	}

	@Override
	public ProjectDTO getProjectProgressByProjectId(int projectId) {
		ProjectDTO projectDTO = new ProjectDTO();
		Project prj = projectRepository.getOne(projectId);

		projectDTO.setStartDate(prj.getStartDate().toString());
		projectDTO.setEndDate(prj.getEndDate().toString());
		projectDTO.setProjectId(projectId);
		Object[] workObjs = (Object[]) projectRepository.getProjectWorkingProgress(projectId);
		Object[] timeObjs = (Object[]) projectRepository.getProjectTimeLog(projectId);

		projectDTO.setProjectSize(timeObjs[1].toString());
		projectDTO.setLoggedTimeTotal(Float.parseFloat(timeObjs[0].toString()));
		projectDTO.setDoneTask(Float.parseFloat(workObjs[0].toString()));
		projectDTO.setTotalTask(Float.parseFloat(workObjs[1].toString()));

		projectDTO.setProjectProgressDto(this.calculateProjectProgress(projectDTO));

		return projectDTO;
	}

	/**
	 * Calculate ProjectProgress
	 *
	 * @param projectDTO
	 * @return ProjectProgressDto
	 * @author: GiangTM
	 */
	private ProjectProgressDto calculateProjectProgress(ProjectDTO projectDTO) {
		return new ProjectProgressDto(
				this.calculateTimelines(projectDTO.getStartDate(), projectDTO.getEndDate(), projectDTO.getStatus()),
				this.calculateTimelog(projectDTO.getLoggedTimeTotal(), projectDTO.getProjectSize()),
				this.calculateWorkingProgress(projectDTO.getFeatureTotal(), projectDTO.getResolvedFeatureTotal(),
						projectDTO.getClosedFeatureTotal(), projectDTO.getRejectedFeatureTotal()));
	}

	/**
	 * Calculate Timelines
	 *
	 * @param theStartDate
	 * @param theEndDate
	 * @return float
	 * @author: GiangTM
	 */
	private float calculateTimelines(String theStartDate, String theEndDate, int theStatus) {
		if (null == theStartDate || null == theEndDate)
			return 0f;
		Date startDate = MethodUtil.convertStringToDate(theStartDate);
		Date endDate = MethodUtil.convertStringToDate(theEndDate);
		if (startDate.compareTo(endDate) > 0)
			return 0f;
		Date currentDate = new Date();
		if (currentDate.compareTo(endDate) >= 0 && theStatus != CustomValueUtil.PROJECT_STATUS_OPEN) {
			return 1f;
		} else if (currentDate.compareTo(startDate) <= 0)
			return 0f;
		else
			return (float) MethodUtil.getDayTotalBetweenTwoDate(startDate, currentDate)
					/ MethodUtil.getDayTotalBetweenTwoDate(startDate, endDate);
	}

	/**
	 * Calculate Timelog
	 *
	 * @param theLoggedTimeTotal
	 * @param theProjectSize
	 * @return float
	 * @author: GiangTM
	 */
	private float calculateTimelog(float theLoggedTimeTotal, String theProjectSize) {
		Float projectSize = Float.parseFloat(theProjectSize);
		if (0 == projectSize)
			return 0f;
		return theLoggedTimeTotal / projectSize;
	}

	/**
	 * Calculate WorkingProgress
	 *
	 * @param featureTotal
	 * @param resolvedFeatureTotal
	 * @param closedFeatureTotal
	 * @param rejectedFeatureTotal
	 * @return float
	 * @author: GiangTM
	 */
	private float calculateWorkingProgress(int featureTotal, int resolvedFeatureTotal, int closedFeatureTotal,
			int rejectedFeatureTotal) {
		if (0 == featureTotal)
			return 0f;
		return (float) (resolvedFeatureTotal + closedFeatureTotal + rejectedFeatureTotal) / featureTotal;
	}

	@Override
	public List<OverdueTasksDto> getOverdueTasksByProjectId(int projectId) {
		List<OverdueTasksDto> overdueTasksDtos = new ArrayList<>();
		List<Object> objects = issuesRepository.getOverdueTasksByProjectId(projectId, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_NEW, CustomValueUtil.ISSUE_STATUS_ID_IN_PROGRESS);
		if (null == objects)
			return Collections.emptyList();

		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			OverdueTasksDto overdueTasksDto = new OverdueTasksDto();
			overdueTasksDto.setId(Integer.parseInt(objs[0].toString()));
			if (null != objs[1]) {
				overdueTasksDto.setSubject(objs[1].toString());
			}
			if (null != objs[2]) {
				overdueTasksDto.setAssignee(objs[2].toString());
			}
			overdueTasksDto.setOverdue(Integer.parseInt(objs[3].toString()));
			overdueTasksDto.setEstimation(Float.parseFloat(objs[4].toString()));
			overdueTasksDto.setStatus(objs[5].toString());

			overdueTasksDtos.add(overdueTasksDto);
		}
		return overdueTasksDtos;
	}

	@Override
	public List<ProjectTimesheetsDto> getProjectTimesheetsByProjectId(int projectId) {
		List<ProjectTimesheetsDto> projectTimesheetsDtos = new ArrayList<>();
		List<Object> objects = timeEntriesRepository.getProjectTimesheetsByProjectId(projectId);
		for (Object object : objects) {
			ProjectTimesheetsDto projectTimesheetsDto = new ProjectTimesheetsDto();
			Object[] objs = (Object[]) object;
			projectTimesheetsDto.setUserId(Integer.parseInt(objs[0].toString()));
			if (null != objs[1].toString())
				projectTimesheetsDto.setUsername(objs[1].toString());
			projectTimesheetsDto.setYesterdayLogtime(Float.parseFloat(objs[2].toString()));
			projectTimesheetsDto.setThisWeekLogtime(Float.parseFloat(objs[3].toString()));
			projectTimesheetsDtos.add(projectTimesheetsDto);
		}
		return projectTimesheetsDtos;
	}

	@Override
	public List<NoncomplianceTasksDto> getNoncomplianceTasksByProjectId(int projectId) {
		List<NoncomplianceTasksDto> noncomplianceTasksDtos = new ArrayList<>();
		List<Object> objects = issuesRepository.getNoncomplianceTasksByProjectId(projectId,
				CustomValueUtil.TASK_TRACKER_ID, CustomValueUtil.ISSUE_STATUS_ID_RESOLVED);
		if (null == objects)
			return Collections.emptyList();

		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			NoncomplianceTasksDto noncomplianceTasksDto = new NoncomplianceTasksDto();
			noncomplianceTasksDto.setId(Integer.parseInt(objs[0].toString()));
			noncomplianceTasksDto.setSubject(objs[1].toString());
			noncomplianceTasksDto.setAssignee(objs[2].toString());
			noncomplianceTasksDto.setEstimation(Float.parseFloat(objs[3].toString()));
			noncomplianceTasksDto.setSpentTime(Float.parseFloat(objs[4].toString()));
			noncomplianceTasksDto.setDoneRatio(Integer.parseInt(objs[5].toString()));
			noncomplianceTasksDto.setStatusId(Integer.parseInt(objs[6].toString()));
			noncomplianceTasksDto.setStatus(objs[7].toString());

			noncomplianceTasksDtos.add(noncomplianceTasksDto);
		}
		return noncomplianceTasksDtos;
	}

	@Override
	public List<UserDTO> getUserByProject(int projectId) {
		// List<Object> objects = userQmsRepository.getUserByProject(projectId);
		List<Object> objects = userRepository.getUserByProject(projectId);
		List<UserDTO> users = new ArrayList<>();
		UserDTO user;
		for (Object object : objects) {
			Object[] obj = (Object[]) object;
			user = new UserDTO();
			user.setId(Integer.parseInt(obj[0].toString()));
			user.setFullName(obj[1].toString());
			users.add(user);
		}
		return users;
	}

	@Override
	public List<UserDTO> getMembersWithLastActivityByProjectId(int projectId) {
		return this.getMembersWithRoleByProjectId(projectId);
	}

	/**
	 * Get Members With Role By ProjectId
	 * 
	 * @param theProjectId
	 * @return List<UserDTO>
	 * @author: GiangTM
	 */
	private List<UserDTO> getMembersWithRoleByProjectId(int theProjectId) {
		List<Object> objects = projectRepository.getMembersWithRoleByProjectId(theProjectId);
		if (null == objects)
			return Collections.emptyList();
		List<UserDTO> users = new ArrayList<>();
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			UserDTO user = new UserDTO();
			user.setId(Integer.parseInt(objs[0].toString()));
			user.setUserName(objs[1].toString());
			user.setImg(objs[2].toString());
			user.setRole(objs[3].toString());
			user.setFullName(objs[4].toString());
			users.add(user);
		}
		return users;
	}

	/**
	 * Get Last Creation By UserId And ProjectId
	 * 
	 * @param theUserId
	 * @param theProjectId
	 * @return Object
	 * @author: GiangTM
	 */
	private Object getLastCreationByUserIdAndProjectId(int theUserId, int theProjectId) {
		return issuesRepository.getLastCreationByUserIdAndProjectId(theUserId, theProjectId);
	}

	/**
	 * Get Last Update By UserId And ProjectId
	 * 
	 * @param theUserId
	 * @param theProjectId
	 * @return Object
	 * @author: GiangTM
	 */
	private Object getLastUpdateByUserIdAndProjectId(int theUserId, int theProjectId) {
		return journalsRepository.getLastUpdateByUserIdAndProjectId(theUserId, theProjectId);
	}

	@Override
	public List<TeamHourByActivityDto> getTeamHourByActivitiesByProjectId(int theProjectId) {
		List<Object> objects = timeEntriesRepository.getTeamHourByActivitiesByProjectId(theProjectId);
		if (null == objects)
			return Collections.emptyList();
		List<TeamHourByActivityDto> teamHourByActivityDtos = new ArrayList<>();
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			TeamHourByActivityDto teamHourByActivityDto = new TeamHourByActivityDto();
			teamHourByActivityDto.setActivityId(Integer.parseInt(objs[0].toString()));
			teamHourByActivityDto.setActivityName(objs[1].toString());
			teamHourByActivityDto.setTeamHour(Float.parseFloat(objs[2].toString()));
			teamHourByActivityDtos.add(teamHourByActivityDto);
		}
		return teamHourByActivityDtos;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#getAllProjects(int)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#isExistProjectOfUser(int, int)
	 */
	@Override
	public boolean isExistProjectOfUser(int userId, int projectId) {
		return projectUserRepository.isExistProjectOfUser(userId, projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#getTimeOfProject(int)
	 */
	@Override
	public TimeProjectDTO getTimeOfProject(int projectId) {
		List<TimeProjectDTO> timeProjects = getTimeOfListProject(Arrays.asList(projectId));
		if (!MethodUtil.checkList(timeProjects)) {
			return timeProjects.get(0);
		}
		return new TimeProjectDTO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#getTimeOfListProject(java.util.List)
	 */
	@Override
	public List<TimeProjectDTO> getTimeOfListProject(List<Integer> projectIds) {
		return ConvertDto.convertTimeProjectDto(projectRepository.getTimeOfListProjectActveByUser(projectIds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#getTimeOfListProjectByUserId(int)
	 */
	@Override
	public List<TimeProjectDTO> getTimeOfListProjectActiveByUserId(int userId) {
		// get all projectId of user
		List<Integer> projectIds = userQmsRepository.getAllProjectIdActiveOfUser(Arrays.asList(userId));
		if (MethodUtil.checkList(projectIds)) {
			return new ArrayList<>();
		}
		return ConvertDto.convertTimeProjectDto(projectQmsRepository.getTimeOfListProjectActveByUser(projectIds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#getAllProjects(int)
	 */
	@Override
	public List<ResourceFilterDto> getAllProjectByUser(int userId) {
		return ConvertDto.convertResourceFilterDtos(projectQmsRepository.getAllProjectByUser(userId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#isExistProjectOfUser(int, int)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#getTimeOfProject(int)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#getTimeOfListProject(java.util.List)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#getTimeOfListProjectByUserId(int)
	 */

	@Override
	public List<ThroughtputBurndownDto> getThroughtputBurndownByProjectId(int theProjectId) {
		// check exist project start_date
		String sProjectStartDate = projectQmsRepository.getProjectStartDateById(theProjectId);
		if (null == sProjectStartDate)
			return Collections.emptyList();

		// check current_date before project start_date
		Date projectStartDate = MethodUtil.convertStringToDate(sProjectStartDate);
		Date currentDate = new Date();
		if (currentDate.compareTo(projectStartDate) < 0)
			return Collections.emptyList();

		// if project end_date not exist
		float projectTasksEstimatedHours = issuesRepository.getProjectTasksEstimatedHoursByProjectId(theProjectId,
				CustomValueUtil.TASK_TRACKER_ID);
		String sProjectEndDate = projectQmsRepository.getProjectEndDateById(theProjectId);
		if (null == sProjectEndDate)
			return this.getRealRemainEstimatedHoursByDays(theProjectId, sProjectStartDate,
					MethodUtil.dateToString(currentDate), projectTasksEstimatedHours);

		// if project end_date exist
		Date projectEndDate = MethodUtil.convertStringToDate(sProjectEndDate);

		// check project end_date before project start_date
		if (projectEndDate.compareTo(projectStartDate) < 0)
			return Collections.emptyList();

		// Get Ideal Remain EstimatedHours
		List<ThroughtputBurndownDto> idealThroughtputBurndownDtos = this
				.getIdealRemainEstimatedHoursByDays(projectTasksEstimatedHours, sProjectStartDate, sProjectEndDate);
		// Get Real Remain EstimatedHours
		// check current_date vs project end_date
		List<ThroughtputBurndownDto> realThroughtputBurndownDtos = new ArrayList<>();
		if (currentDate.compareTo(projectEndDate) >= 0)
			realThroughtputBurndownDtos = this.getRealRemainEstimatedHoursByDays(theProjectId, sProjectStartDate,
					sProjectEndDate, projectTasksEstimatedHours);
		else {
			realThroughtputBurndownDtos = this.getRealRemainEstimatedHoursByDays(theProjectId, sProjectStartDate,
					MethodUtil.dateToString(currentDate), projectTasksEstimatedHours);
		}
		// merge idealThroughtputBurndownDtos and realThroughtputBurndownDtos
		int i = 0;
		for (ThroughtputBurndownDto throughtputBurndownDto : idealThroughtputBurndownDtos) {
			throughtputBurndownDto
					.setRealRemainEstimatedHours(realThroughtputBurndownDtos.get(i).getRealRemainEstimatedHours());
			if (i == realThroughtputBurndownDtos.size() - 1)
				break;
			i++;
		}

		return idealThroughtputBurndownDtos;
	}

	/**
	 * Get Real Remain EstimatedHours By Days
	 * 
	 * @param theProjectId
	 * @param theProjectStartDate
	 * @param theEndDate
	 * @param projectTasksEstimatedHours
	 * @return List<ThroughtputBurndownDto>
	 * @author: GiangTM
	 */
	private List<ThroughtputBurndownDto> getRealRemainEstimatedHoursByDays(int theProjectId, String theProjectStartDate,
			String theEndDate, float projectTasksEstimatedHours) {
		List<Object> objects = issuesRepository.getRemainEstimatedHoursByDays(theProjectId, theProjectStartDate,
				theEndDate, projectTasksEstimatedHours, CustomValueUtil.TASK_TRACKER_ID,
				CustomValueUtil.ISSUE_STATUS_ID_CLOSED);
		List<ThroughtputBurndownDto> throughtputBurndownDtos = new ArrayList<>();
		for (Object object : objects) {
			Object[] objs = (Object[]) object;
			ThroughtputBurndownDto throughtputBurndownDto = new ThroughtputBurndownDto();
			throughtputBurndownDto.setSelectedDate(objs[0].toString());
			throughtputBurndownDto.setRealRemainEstimatedHours(Float.parseFloat(objs[1].toString()));
			throughtputBurndownDtos.add(throughtputBurndownDto);
		}
		return throughtputBurndownDtos;
	}

	/**
	 * Get Ideal Remain EstimatedHours By Days
	 * 
	 * @param projectTasksEstimatedHours
	 * @param sProjectStartDate
	 * @param sProjectEndDate
	 * @return
	 */
	private List<ThroughtputBurndownDto> getIdealRemainEstimatedHoursByDays(float projectTasksEstimatedHours,
			String sProjectStartDate, String sProjectEndDate) {
		List<ThroughtputBurndownDto> throughtputBurndownDtos = new ArrayList<>();
		List<String> datesFromStartDateToEndDate = projectQmsRepository
				.getDatesFromStartDateToProjectEndDate(sProjectStartDate, sProjectEndDate);
		float avgTasksEstimatedHours = projectTasksEstimatedHours / (datesFromStartDateToEndDate.size() - 1);
		for (String selectedDate : datesFromStartDateToEndDate) {
			ThroughtputBurndownDto throughtputBurndownDto = new ThroughtputBurndownDto();
			throughtputBurndownDto.setSelectedDate(selectedDate);
			throughtputBurndownDto.setIdealtRemainEstimatedHours(
					projectTasksEstimatedHours - avgTasksEstimatedHours * throughtputBurndownDtos.size());
			throughtputBurndownDtos.add(throughtputBurndownDto);
		}
		return throughtputBurndownDtos;
	}

	/**
	 * Get user in project
	 * 
	 * @param projectId
	 * @return
	 */
	private List<UserDTO> getUserInProject(int projectId) {
		List<UserDTO> users = new ArrayList<>();

		List<Object> objects = projectRepository.getMembersWithRoleByProjectId(projectId);

		if (!objects.equals(null)) {
			for (Object object : objects) {
				Object[] objs = (Object[]) object;
				UserDTO user = new UserDTO();
				user.setId(Integer.parseInt(objs[0].toString()));
				user.setUserName(objs[1].toString());
				users.add(user);
			}
		}

		return users;
	}

	/**
	 * Get user log time last week by projectId
	 * 
	 * @param projectId
	 * @return
	 */
	private List<UserLogTimeDTO> getUserLogTimeLastWeekByProjectId(int projectId) {

		List<UserLogTimeDTO> lastWeekLogTimes = new ArrayList<>();

		List<Object> objects = projectRepository.getLastWeekLogTime(projectId);

		if (!objects.equals(null) && objects.size() > 0) {
			for (Object object : objects) {
				Object[] objs = (Object[]) object;
				UserLogTimeDTO userLogTimeDTO = new UserLogTimeDTO();
				userLogTimeDTO.setId(Integer.parseInt(objs[0].toString()));
				userLogTimeDTO.setHours(Float.parseFloat(objs[2].toString()));
				try {
					// Timestamp ts=new Timestamp(objs[3].toString());
					SimpleDateFormat parseResult = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = parseResult.parse(objs[3].toString());
					SimpleDateFormat toWeekDay = new SimpleDateFormat("EE");
					String day = toWeekDay.format(date);
					userLogTimeDTO.setSpentOn(day.toLowerCase());
				} catch (Exception e) {
					e.printStackTrace();
				}

				lastWeekLogTimes.add(userLogTimeDTO);
			}
		} else {
			UserLogTimeDTO userLogTimeDTO = new UserLogTimeDTO(0, 0, "");
			lastWeekLogTimes.add(userLogTimeDTO);
		}

		return lastWeekLogTimes;
	}

	/**
	 * Get user log time this week by projectId
	 * 
	 * @param projectId
	 * @return
	 */
	private List<UserLogTimeDTO> getUserLogTimeThisWeekByProjectId(int projectId) {

		List<UserLogTimeDTO> thisWeekLogTimes = new ArrayList<>();

		List<Object> objects = projectRepository.getThisWeekLogTime(projectId);

		if (!objects.equals(null) && objects.size() > 0) {
			for (Object object : objects) {
				Object[] objs = (Object[]) object;
				UserLogTimeDTO userLogTimeDTO = new UserLogTimeDTO();
				userLogTimeDTO.setId(Integer.parseInt(objs[0].toString()));
				userLogTimeDTO.setHours(Float.parseFloat(objs[2].toString()));
				try {
					SimpleDateFormat parseResult = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = parseResult.parse(objs[3].toString());
					SimpleDateFormat toWeekDay = new SimpleDateFormat("EE");
					String day = toWeekDay.format(date);
					userLogTimeDTO.setSpentOn(day.toLowerCase());
					thisWeekLogTimes.add(userLogTimeDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else {
			UserLogTimeDTO userLogTimeDTO = new UserLogTimeDTO(0, 0, "");
			thisWeekLogTimes.add(userLogTimeDTO);
		}

		return thisWeekLogTimes;
	}

	private List<UserTimeSheetDTO> getUserTimeSheet(int projectId) {

		List<UserDTO> users = this.getUserInProject(projectId);

		List<UserTimeSheetDTO> userTimeSheets = new ArrayList<>();

		for (UserDTO user : users) {
			UserTimeSheetDTO userTimeSheet = new UserTimeSheetDTO();
			userTimeSheet.setId(user.getId());
			userTimeSheet.setUsername(user.getUserName());
			userTimeSheets.add(userTimeSheet);
		}

		return userTimeSheets;
	}

	// getUserTimeSheetLastWeekAndThisWeekByProjectId
	@Override
	public List<UserTimeSheetDTO> getUserTimeSheetLastWeekAndThisWeekByProjectId(int projectId) {
		List<UserTimeSheetDTO> userTimeSheets = this.getUserTimeSheet(projectId);
		List<UserLogTimeDTO> lastWeekLogTimes = this.getUserLogTimeLastWeekByProjectId(projectId);
		List<UserLogTimeDTO> thisWeekLogTimes = this.getUserLogTimeThisWeekByProjectId(projectId);
		Map<Integer, List<UserLogTimeDTO>> mapLastWeekLogTimes = this.getlastWeekLogTimesByUser(lastWeekLogTimes);
		Map<Integer, List<UserLogTimeDTO>> mapThisWeekLogTimes = this.getlastWeekLogTimesByUser(thisWeekLogTimes);
		userTimeSheets.stream().forEach(userTime -> {
			if (!mapLastWeekLogTimes.isEmpty()) {
				List<UserLogTimeDTO> subLastWeekLogTimes = mapLastWeekLogTimes.get(userTime.getId());
				if (subLastWeekLogTimes != null) {
					this.setLogTimeToUserLogTime(userTime.getLastWeek(), subLastWeekLogTimes);
				}
			}

			if (!mapThisWeekLogTimes.isEmpty()) {
				List<UserLogTimeDTO> subThisWeekLogTimes = mapThisWeekLogTimes.get(userTime.getId());
				if (subThisWeekLogTimes != null) {
					this.setLogTimeToUserLogTime(userTime.getThisWeek(), subThisWeekLogTimes);
				}
			}
		});

		return userTimeSheets;
	}

	private void setLogTimeToUserLogTime(HashMap<String, Float> lastWeek, List<UserLogTimeDTO> lastWeekLogTimes) {
		lastWeek.entrySet().forEach(spentTime -> {
			Optional<UserLogTimeDTO> userLogTimeDTO = lastWeekLogTimes.stream()
					.filter(userLogTime -> userLogTime.getSpentOn().trim().equals(spentTime.getKey())).findFirst();
			if (userLogTimeDTO.isPresent())
				spentTime.setValue(userLogTimeDTO.get().getHours());
		});
	}

	private Map<Integer, List<UserLogTimeDTO>> getlastWeekLogTimesByUser(List<UserLogTimeDTO> lastWeekLogTimes) {
		return lastWeekLogTimes != null
				? lastWeekLogTimes.stream().collect(Collectors.groupingBy(UserLogTimeDTO::getId))
				: null;
	}

	@Override
	public EditProjectDTO getEditProject(int projectId) {
		List<MemberDTO> listMember = new ArrayList<MemberDTO>();
		// List<MemberDTO> listAll = new ArrayList<MemberDTO>();
		List<MemberDTO> listPM = new ArrayList<MemberDTO>();
		Project project = null;
		EditProjectDTO editDTO = null;
		try {
			List<Object> listObjectMember = projectUserRepository.getListUserOfProject(projectId);
			for (Iterator<Object> it = listObjectMember.iterator(); it.hasNext();) {
				Object[] object = (Object[]) it.next();
				// User user = userRepository.findOne((Integer) object[0]);
				MemberDTO member = new MemberDTO((Integer) object[0], (String) object[1], (String) object[2],
						(String) object[3], (String) object[4], object[5] != null ? (Integer) object[5] : 0,
						object[6] != null ? (Integer) object[6] : 0, (Byte) object[7]);
				listMember.add(member);
			}
			List<Object> listObjectPM = projectUserRepository.getListPMOfProject(projectId);
			for (Iterator<Object> it = listObjectPM.iterator(); it.hasNext();) {
				Object[] object = (Object[]) it.next();
				// User user = userRepository.findOne((Integer) object[0]);
				MemberDTO member = new MemberDTO((Integer) object[0], (String) object[1], (String) object[2],
						(String) object[3], (String) object[4], object[5] != null ? (Integer) object[5] : 0,
						object[6] != null ? (Integer) object[6] : 0, (Byte) object[7]);
				listPM.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			project = projectRepository.findById(projectId);
			List<ProjectRole> listRoleProject = projectRoleRepository.findAll();
			List<Group> listGroup = groupRepository.findAll();
			List<ProjectType> listProjectType = projectTypeRepository.findAll();
			List<Skill> listSkill = skillRepository.findAll();
			List<ProjectTypeLogDTO> listProjectTypeLog = projectTypeLogService.getListByprojectId(projectId);
			editDTO = new EditProjectDTO(listGroup, listProjectType, listSkill, project, listRoleProject, listMember,
					null, listPM,listProjectTypeLog);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return editDTO;
	}

	@Override
	public Project findProjectById(int id) {
		Project project = projectRepository.findOne(id);
		return project;
	}

	@Override
	@Transactional
	public void deleteSkill(int projectId, String skill) {
		if ("".equals(skill)) {
			return;
		} else {
			String[] skills = skill.split(",");
			int number;
			for (String s : skills) {
				try {
					number = Integer.parseInt(s);
					projectRepository.deleteSkill(projectId, number);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void insertSkill(int projectId, String skill) {
		if ("".equals(skill)) {
			return;
		} else {
			Project project = projectRepository.findOne(projectId);
			String[] skills = skill.split(",");
			int number;
			for (String s : skills) {
				try {
					number = Integer.parseInt(s);
					Skill sk = skillRepository.findOne(number);
					project.addSkill(sk);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			projectRepository.save(project);
		}
	}

	@Override
	public void update(Project p) {
		projectRepository.save(p);

	}

	@Override
	public ProjectListDTO getProjectListByUser(int userId, int page, int size, String column, String sort,
			String projectName, String status, String PM, String startDateFrom, String startDateTo, String endDateFrom,
			String endDateTo, String DU, String typeProject, String groupName, int type) {
		// TODO Auto-generated method stub
		if (column.equals("group_name")) {
			column = "group_id";
		}
		if (column.equals("name")) {
			column = "project_name";
		}
		Page<ProjectDTOTemp> pageQms = new PageImpl<>(new ArrayList<>(), null, 0);
		Set<String> listPM = new HashSet<String>();
		// QmsUser qmsUser = userQmsRepository.findGroup(userId);
		// String groupName = qmsUser == null ? null : qmsUser.getLastname();
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, column));
		if (CustomValueUtil.ASC.equalsIgnoreCase(sort)) {
			pageable = new PageRequest(page - 1, size, new Sort(Sort.Direction.ASC, column));
		}
		Page<Object> projects = new PageImpl<>(new ArrayList<>());
		List<ProjectDTOTemp> listProjectDto = new ArrayList<>();
		listPM = userRepository.getAllPM();
		if (type == 0) {
			try {
				projects = projectRepository.getProjectListByUser(userId, pageable, projectName, status, PM,
						startDateFrom, startDateTo, endDateFrom, endDateTo, DU, typeProject);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				projects = projectRepository.getProjectListByUserAdminOrPmoOrQa(pageable, projectName, status, PM,
						startDateFrom, startDateTo, endDateFrom, endDateTo, DU, typeProject);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		List<Object> listProjectObject = projects.getContent();
		ProjectDTOTemp projectDtoTemp = null;
		for (Iterator<Object> it = listProjectObject.iterator(); it.hasNext();) {
			Object[] data = (Object[]) it.next();
			Float i = projectBillableRepository.getBillableEffortsByProjectId((Integer) data[0]);
			projectDtoTemp = new ProjectDTOTemp(Integer.parseInt(data[0].toString()),
					data[1] == null ? "" : data[1].toString(), data[2] == null ? "" : data[2].toString(),
					data[3] == null ? "" : data[3].toString(), data[4] == null ? "" : data[4].toString(),
					Integer.parseInt(data[5].toString()), data[6] == null ? null : (Date) data[6],
					data[7] == null ? null : (Date) data[7], data[8] == null ? "" : data[8].toString(), i == null ? 0:(float) Math.round(i * 100) / 100);
			listProjectDto.add(projectDtoTemp);
		}
		pageQms = new PageImpl<>(new ArrayList<>(listProjectDto));
		List<String> listDu = deliverUnitRepository.getAllDeliveruUnitName();
		Set<String> setTypeTemp = projectTypeRepository.getListNameProjectType();
		List<String> listType = new ArrayList<>();
		listType.addAll(setTypeTemp);
		return new ProjectListDTO(pageQms.getContent(), listPM, listDu, listType, projects.getTotalElements());
	}

	@Override

	public int changeStatus(int projectId, int status) {
		Project p = projectRepository.findOne(projectId);
		p.setStatus(status);
		try {
			projectRepository.save(p);
			return projectId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public List<ProjectRole> getAllProjectRole() {
		// TODO Auto-generated method stub
		return projectRoleRepository.findAll();
	}

	@Override
	public void updateLineOfCode(Project project) {
		projectRepository.save(project);
	}

	public Float getProjectAvgCss(int projectId) {
		return projectCssRepository.findAvgCssByProjectId(projectId);
	}

	public Float getProjectAvgBillable(int projectId) {
		return projectBillableRepository.getAvgBillableByProjectId(projectId);
	}

	public Object getProjectOverviewByProjectId(int projectId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("BasicInfo", getProjectBasicInfoByProjectId(projectId).get(0));
		result.put("Stats", getProjectProgressByProjectId(projectId));
		result.put("Members", getMembersWithLastActivityByProjectId(projectId));
		// result.put("Timesheets", getProjectTimesheetsByProjectId(projectId));
		return result;
	}

	@Override
	public Project getProjectByProjectId(int projectId) {
		// TODO Auto-generated method stub
		return projectRepository.getProjectByProjectId(projectId);
	}

	@Override
	public boolean saveDataFromJira(ProjectJiraDTO[] array) {
		List<Integer> projectJira = new ArrayList<Integer>();
		try {
			for (ProjectJiraDTO project : array) {
				int projectId = project.getProjectId().intValue();
				projectJira.add(projectId);
				Project p = projectRepository.findOne(projectId);
				if (p == null) {

					Project pr = new Project();
					pr.setId(projectId);
					pr.setName(project.getProjectName());
					pr.setProjectCode(project.getProjectCode());
					pr.setId(projectId);
					pr.setGroup(null);
					pr.setProjectType(null);
					pr.setType(1);
					// set target
					pr.setBillableRate(0.0f);
					pr.setBugRate(0.0f);
					pr.setCss(0.0f);
					pr.setEffortDeviation(0.0f);
					pr.setLeakageRate(0.0f);
					pr.setPcvRate(0.0f);
					pr.setProductivity(0.0f);
					pr.setTimeliness(0.0f);
					projectRepository.save(pr);

				}
				else {
					p.setName(project.getProjectName());
					projectRepository.save(p);
				}
				List<UserJiraDTO> listUser = project.getUsersDTOS();
				List<IssueJiraDTO> listIssue = project.getIssuesDTOS();
				userService.saveUserJira(listUser, projectId);
				projectTaskService.saveIssueJira(listIssue, projectId);

			}
			HistorySyncData logEnd = new HistorySyncData(Constants.TypeLogger.saveProjectFromUser,
					"Save data thành công!", 1, new Date());
			historySyncDataRepository.save(logEnd);
		} catch (Exception e) {
			e.printStackTrace();
			HistorySyncData logEnd = new HistorySyncData(Constants.TypeLogger.saveProjectFromUser, e.getMessage(), 0,
					new Date());
			historySyncDataRepository.save(logEnd);
		}
		return true;
	}

	@Override
	@Transactional
	public void deleteMember(int projectId, String member) {
		if ("".equals(member)) {
			return;
		} else {
			String[] members = member.split(",");
			for (String s : members) {
				try {
					User user = userRepository.findByEmail(s);
					if (user != null)
						projectUserRepository.deleteUser(projectId, user.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	@Transactional
	public void insertMember(int projectId, String member) {
		if ("".equals(member)) {
			return;
		} else {
			Project project = projectRepository.findOne(projectId);
			String[] members = member.split(",");
			for (String s : members) {
				try {
					User user = userRepository.findByEmail(s);
					if (user != null) {
						ProjectUser pu = new ProjectUser();
						pu.setUser_id(user.getUserId());
						pu.setProject_id(projectId);
						projectUserRepository.save(pu);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			projectRepository.save(project);
		}

	}

	@Override
	@Transactional
	public void deletePM(int projectId, String member) {
		if ("".equals(member)) {
			return;
		} else {
			String[] members = member.split(",");
			for (String s : members) {
				try {
					User user = userRepository.findByEmail(s);
					if (user != null)
						projectUserRepository.updateUser(Constants.ProjectRole.MEMBER, projectId, user.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	@Transactional
	public void insertPM(int projectId, String member) {
		if ("".equals(member)) {
			return;
		} else {
			// Project project = projectRepository.findOne(projectId);
			String[] members = member.split(",");
			for (String s : members) {
				try {
					User user = userRepository.findByEmail(s);
					if (user != null) {
						projectUserRepository.updateUser(Constants.ProjectRole.PM, projectId, user.getUserId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public List<MemberDTO> getUserAllEditProject() {
//		List<User> listUserAll = userRepository.findAll();
//		List<MemberDTO> listAll = new ArrayList<MemberDTO>();
//		for(User u : listUserAll ) {
//			MemberDTO member=new MemberDTO(u.getFullName(),u.getEmail(),u.getGroup()!=null?u.getGroup().getGroupName():"");
//			listAll.add(member);
//		}
		return null;
	}

	@Override
	@Transactional
	public void updateMember(int projectId, String member) {
		if ("".equals(member)) {
			return;
		} else {
			String[] members = member.split(",");
			for (String s : members) {
				try {
					Integer id = Integer.parseInt(s);
					Integer display = projectUserRepository.getDisplay(projectId, id);
					if (display == null)
						projectUserRepository.updateDisplayUser(projectId, id, 1);
					else
						projectUserRepository.updateDisplayUser(projectId, id, 1 - display);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getNamePm(int projectId) {
		String result = "";
		List<String> listNamePm = projectUserRepository.getListNamePMOfProject(projectId);
		int len = listNamePm.size();
		if (len == 0)
			return "";
		for (int i = 0; i < len; i++) {
			if (i == len - 1)
				result += listNamePm.get(i);
			else
				result += listNamePm.get(i) + ",";
		}
		return result;
	}

	@Override
	public List<ProjectTableDTO> getProjectByDu(int groupId) {
		String rexGroup = groupId == -1 ? ".*" : ("^" + groupId + "$");
		List<ProjectTableDTO> projectTableDTOs = new ArrayList<ProjectTableDTO>();
		List<Object> objects = projectRepository.getProjectByDu(rexGroup);
		if (objects != null) {
			for (Iterator<Object> it = objects.iterator(); it.hasNext();) {
				Object[] object = (Object[]) it.next();
				try {
					Float i = projectBillableRepository.getBillableEffortsByProjectId((Integer) object[0]);
					ProjectTableDTO pdt = new ProjectTableDTO((Integer) object[0], (String) object[1],
							(String) object[2], (String) object[3], (String) object[4], (byte) object[5],
							object[6] == null ? null : (Date) object[6], object[6] == null ? null : (Date) object[7],
							(String) object[8], (Byte) object[9],i == null ? 0 : i );
					projectTableDTOs.add(pdt);
				} catch (Exception e) {
					System.out.println((Integer) object[0]);
				}

			}
		}
		return projectTableDTOs;
	}
	@Override
	public List<ProjectInfoDTO> getAllProject(){
		List<ProjectInfoDTO> listDTO = new ArrayList<ProjectInfoDTO>();
		List<Object> listProject = projectRepository.getListProject();
		for (Iterator<Object> it = listProject.iterator(); it.hasNext();) {
			Object[] object = (Object[]) it.next();
			try {
				ProjectInfoDTO pdt = new ProjectInfoDTO((Integer) object[0], (String) object[1],(String) object[2]);
				listDTO.add(pdt);
			} catch (Exception e) {
				System.out.println((Integer) object[0]);
			}

		}
		return listDTO;
		
	}

}
