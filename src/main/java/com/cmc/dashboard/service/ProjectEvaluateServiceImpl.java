package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cmc.dashboard.dto.EvaluateCommentDTO;
import com.cmc.dashboard.dto.EvaluateDTO;

import com.cmc.dashboard.dto.DuMonitoringDTO;
import com.cmc.dashboard.dto.ProjectEvaluateDTO;
import com.cmc.dashboard.dto.ProjectEvaluateWeekDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectEvaluate;
import com.cmc.dashboard.model.ProjectEvaluateComment;
import com.cmc.dashboard.model.ProjectEvaluateLevel;
import com.cmc.dashboard.model.ProjectUser;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsMember;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.EvaluateLevelRepository;
import com.cmc.dashboard.repository.ProjectEvaluateCommentRepository;
import com.cmc.dashboard.repository.ProjectEvaluateRepository;
import com.cmc.dashboard.repository.ProjectRepository;
import com.cmc.dashboard.repository.ProjectRoleRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MessageUtil;
import com.google.gson.JsonObject;

/**
 * 
 * @author: LXLinh
 * @Date: May 6, 2018
 */
@Service
public class ProjectEvaluateServiceImpl implements ProjectEvaluateService {

	@Autowired
	private ProjectEvaluateRepository projectEvaluateRepository;

	@Autowired
	private EvaluateLevelRepository evaluateLevelRepository;
	
	@Autowired
	private ProjectEvaluateCommentRepository evaluateCommentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MemberQmsRepository memberQmsRepository;

	@Autowired
	private UserQmsRepository userQmsRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	private ProjectRepository projectRepository;

	/*
	 * (non-Javadoc) LXLinh
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectEvaluateService#saveProjectEvaluate(com.cmc.
	 * dashboard.dto.ProjectEvaluateDTO)
	 */
	@Override
	public ProjectEvaluate saveProjectEvaluate(ProjectEvaluateDTO projectEvaluateDTO) {

		JsonObject jsonObject = new JsonObject();
		Optional<ProjectEvaluate> projectEvaluate = Optional.empty();
		if (!Optional
				.ofNullable(
						getMemberEvluateProject(projectEvaluateDTO.getUserName(), projectEvaluateDTO.getProjectId()))
				.isPresent()) {
			jsonObject.addProperty(MessageUtil.MessageEvaluateProject.MEMBER,
					MessageUtil.MessageEvaluateProject.MEMBER_OF_PROJECT);
			throw new BusinessException(jsonObject.toString());
		}
		projectEvaluateDTO.setStartDate(convertLocalDateToDate(
				this.convertToLocalDate(projectEvaluateDTO.getStartDate()).with(DayOfWeek.MONDAY)));
		projectEvaluateDTO.setEndDate(convertLocalDateToDate(
				this.convertToLocalDate(projectEvaluateDTO.getStartDate()).with(DayOfWeek.FRIDAY)));
		// project audit has not been evaluated
		Optional<List<ProjectEvaluate>> projectEvaluates = Optional.ofNullable(projectEvaluateRepository
				.findByProjectIdAndStartDate(projectEvaluateDTO.getProjectId(), convertLocalDateToDate(
						this.convertToLocalDate(projectEvaluateDTO.getStartDate()).with(DayOfWeek.MONDAY))));
		if (projectEvaluates.isPresent()) {
			projectEvaluate = projectEvaluates.get().stream().filter(user -> user.getUserName() == null).findFirst();
		}
		if (projectEvaluate.isPresent()) {
			projectEvaluateDTO.setProjectEvaluateId(projectEvaluate.get().getId());
		}
		// check exist project are evaluated by the user and update data for each week
		projectEvaluate = Optional.ofNullable(getUserEvaluateOfProjectByWeek(projectEvaluateDTO.getProjectId(),
				projectEvaluateDTO.getUserName(), convertLocalDateToDate(
						this.convertToLocalDate(projectEvaluateDTO.getStartDate()).with(DayOfWeek.MONDAY))));
		if (projectEvaluate.isPresent()) {
			projectEvaluateDTO.setProjectEvaluateId(projectEvaluate.get().getId());
		}
		return projectEvaluateRepository.save(projectEvaluateDTO.toEntity());
	}

	/*
	 * (non-Javadoc) LXLinh
	 * 
	 * @see com.cmc.dashboard.service.ProjectEvaluateService#findById(int)
	 */

	@Override
	public Optional<ProjectEvaluateLevel> findById(int id) {

		return evaluateLevelRepository.findById(id);
	}

	/*
	 * (non-Javadoc) LXLinh
	 * 
	 * @see com.cmc.dashboard.service.ProjectEvaluateService#
	 * getUserEvaluateOfProjectByWeek(int, int, java.util.Date, java.util.Date)
	 */
	@Override
	public ProjectEvaluate getUserEvaluateOfProjectByWeek(int projectId, String userName, Date startDate) {

		Optional<List<ProjectEvaluate>> projectEvaluates = Optional
				.ofNullable(projectEvaluateRepository.findByProjectIdAndStartDate(projectId,
						convertLocalDateToDate(this.convertToLocalDate(startDate).with(DayOfWeek.MONDAY))));
		Optional<ProjectEvaluate> projectEvaluate = Optional.empty();
		if (projectEvaluates.isPresent())
			projectEvaluate = projectEvaluates.get().stream().filter(pre -> pre.getUserName() != null)
					.filter(pre -> pre.getUserName().equals(userName)).findFirst();
		return projectEvaluate.isPresent() ? projectEvaluate.get() : null;
	}

	/*
	 * (non-Javadoc) LXLinh
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectEvaluateService#getUserEvaluateByProject(
	 * int, java.util.Date, java.util.Date)
	 */
	@Override
	public List<UserInfoDTO> getUserEvaluateByProject(int projectId, Date startDate) {
		Optional<List<ProjectEvaluate>> projectEvaluates = Optional
				.ofNullable(projectEvaluateRepository.findByProjectIdAndStartDate(projectId,
						convertLocalDateToDate(this.convertToLocalDate(startDate).with(DayOfWeek.MONDAY))));
		List<UserInfoDTO> userDTOs = new ArrayList<>();
		if (projectEvaluates.isPresent()) {
			List<String> listIdsUsers = projectEvaluates.get().stream().filter(user -> user.getUserName() != null)
					.map(ProjectEvaluate::getUserName).distinct().collect(Collectors.toList());
			if (!listIdsUsers.isEmpty())
				userRepository.findByUserNameIn(listIdsUsers).forEach(user -> userDTOs.add(new UserInfoDTO(user)));
		}
		return userDTOs;
	}

	@Override
	public List<EvaluateDTO> getEvaluateProjects(int projectId,int month,int year) {
		List<EvaluateDTO> result = new ArrayList<EvaluateDTO>();
		// gen week evaluate project
//		List<ProjectEvaluate> listProjectEvaluate = new ArrayList<>();
//		List<ProjectEvaluateWeekDTO> listProjectEvaluateWeekDTO = new ArrayList<>();
//		try {
//			listProjectEvaluate = projectEvaluateRepository.findProjectId(projectId);
//			ProjectEvaluateWeekDTO projectEvaluateWeekDTO = new ProjectEvaluateWeekDTO();
//			for (ProjectEvaluate projectEvaluate : listProjectEvaluate) {
//				projectEvaluateWeekDTO.setDelivery(projectEvaluate.getDelivery());
//				projectEvaluateWeekDTO.setEndDate(projectEvaluate.getEndDate());
//				projectEvaluateWeekDTO.setProcess(projectEvaluate.getProcess());
//				projectEvaluateWeekDTO.setQuality(projectEvaluate.getQuality());
//				projectEvaluateWeekDTO.setStartDate(projectEvaluate.getStartDate());
//				projectEvaluateWeekDTO.setSubject(projectEvaluate.getComment());
//				projectEvaluateWeekDTO.setComment(projectEvaluate.getComment());
//				projectEvaluateWeekDTO.setProjectEvaluates(listProjectEvaluate);
//				listProjectEvaluateWeekDTO.add(projectEvaluateWeekDTO);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		 List<ProjectEvaluate> listEva = projectEvaluateRepository.findByProjectId(projectId, month, year);
		 for(ProjectEvaluate eva : listEva) {
			 List<EvaluateCommentDTO> listComment = this.getListCommentEvaluate(eva.getId());
			 ProjectEvaluateComment tem =evaluateCommentRepository.getLastCommentPmOrQa(eva.getId(),2);
			 String lastCommentQA = tem!=null?tem.getComment():"";
			 tem =evaluateCommentRepository.getLastCommentPmOrQa(eva.getId(),1);
			 String lastCommentPM=tem!=null?tem.getComment():"";	 
			 EvaluateDTO dto= new  EvaluateDTO(eva.getId(),eva.getProjectId(),"" ,eva.getQualityId(), eva.getDeliveryId(), eva.getProcessId(),eva.getSubject(),eva.getEndDate(),eva.getStartDate(), listComment, lastCommentQA, lastCommentPM) ;
			 result.add(dto);
		 }
		 return result;
	}

	/**
	 * 
	 * merge info evaluate project user
	 * 
	 * @return List<ProjectEvaluateWeekDTO>
	 * @author: LXLinh
	 */
	private List<ProjectEvaluateWeekDTO> getMergInfoEvalueteProjectByWeek(List<ProjectEvaluate> evaluateProjects) {
		List<ProjectEvaluateWeekDTO> projectEvaluateWeekDTOs = new ArrayList<>();
		// get user join evaluate project
		List<UserInfoDTO> userInfoDTOs = this.getUsersEvaluateProject(evaluateProjects);
		this.getPrjectEvaluateGroupWeek(evaluateProjects).entrySet().forEach(evaluateProject -> {
			ProjectEvaluateWeekDTO projectEvaluateWeekDTO = new ProjectEvaluateWeekDTO();
			projectEvaluateWeekDTO.setProjectEvaluates(evaluateProject.getValue());
			ProjectEvaluate projEvaluate = getResultProjectEvaluate(evaluateProject.getValue());
			projectEvaluateWeekDTO.setStartDate(evaluateProject.getKey());
			projectEvaluateWeekDTO.setEndDate(evaluateProject.getValue().stream().findFirst().get().getEndDate());
			if (!userInfoDTOs.isEmpty()) {
				projectEvaluateWeekDTO.setUserInfoDTOs(userInfoDTOs.stream()
						.filter(userInfo -> evaluateProject.getValue().stream().filter(pv -> pv.getUserName() != null)
								.anyMatch(ev -> userInfo.getUserName().equals(ev.getUserName())))
						.collect(Collectors.toList()));
			}
			projectEvaluateWeekDTO.setQuality(projEvaluate.getQuality());
			projectEvaluateWeekDTO.setCost(projEvaluate.getCost());
			projectEvaluateWeekDTO.setDelivery(projEvaluate.getDelivery());
			projectEvaluateWeekDTO.setProcess(projEvaluate.getProcess());
			projectEvaluateWeekDTO.setSubject(evaluateProject.getValue().get(0).getSubject());
			projectEvaluateWeekDTOs.add(projectEvaluateWeekDTO);
		});
		return projectEvaluateWeekDTOs.stream()
				.sorted(Comparator.comparing(ProjectEvaluateWeekDTO::getStartDate).reversed())
				.collect(Collectors.toList());

	}

	/**
	 * 
	 * group evaluate project by week
	 * 
	 * @param projectEvaluates
	 * @return Map<Date,List<ProjectEvaluate>>
	 * @author: LXLinh
	 */
	private Map<Date, List<ProjectEvaluate>> getPrjectEvaluateGroupWeek(List<ProjectEvaluate> projectEvaluates) {
		return projectEvaluates.stream().sorted(Comparator.comparing(ProjectEvaluate::getStartDate))
				.collect(Collectors.groupingBy(ProjectEvaluate::getStartDate));
	}

	/**
	 * 
	 * calculate result evaluate project by week
	 * 
	 * @param projectEvaluates
	 * @return ProjectEvaluateLevel
	 * @author: LXLinh
	 */
	private ProjectEvaluate getResultProjectEvaluate(List<ProjectEvaluate> projectEvaluates) {
		ProjectEvaluate projectEvaluate = new ProjectEvaluate();
		List<ProjectEvaluateLevel> projectEvaluateLevels = projectEvaluates.stream()
				.filter(quality -> quality.getQualityId() != null).map(ProjectEvaluate::getQuality)
				.collect(Collectors.toList());
		if (!projectEvaluateLevels.isEmpty())
			projectEvaluate.setQuality(getResultByfieldEvalute(projectEvaluateLevels));

		projectEvaluateLevels = projectEvaluates.stream().filter(cost -> cost.getCostId() != null)
				.map(ProjectEvaluate::getCost).collect(Collectors.toList());
		if (!projectEvaluateLevels.isEmpty())
			projectEvaluate.setCost(getResultByfieldEvalute(projectEvaluateLevels));

		projectEvaluateLevels = projectEvaluates.stream().filter(process -> process.getProcessId() != null)
				.map(ProjectEvaluate::getProcess).collect(Collectors.toList());
		if (!projectEvaluateLevels.isEmpty())
			projectEvaluate.setProcess(getResultByfieldEvalute(projectEvaluateLevels));
		projectEvaluateLevels = projectEvaluates.stream().filter(delivery -> delivery.getDeliveryId() != null)
				.map(ProjectEvaluate::getDelivery).collect(Collectors.toList());
		if (!projectEvaluateLevels.isEmpty())
			projectEvaluate.setDelivery(getResultByfieldEvalute(projectEvaluateLevels));

		return projectEvaluate;
	}

	/**
	 * 
	 * get reSult field
	 * 
	 * @return ProjectEvaluateLevel
	 * @author: LXLinh
	 */
	private ProjectEvaluateLevel getResultByfieldEvalute(List<ProjectEvaluateLevel> projectEvaluateLevels) {
		Optional<ProjectEvaluateLevel> projectEvaluateLevel = projectEvaluateLevels.stream()
				.sorted(Comparator.comparing(ProjectEvaluateLevel::getId).reversed()).findFirst();
		return (projectEvaluateLevel.isPresent()) ? projectEvaluateLevel.get() : null;
	}

	/**
	 * 
	 * get list info user evaluate project
	 * 
	 * @param projectEvaluates
	 * @return List<UserInfoDTO>
	 * @author: LXLinh
	 */
	private List<UserInfoDTO> getUsersEvaluateProject(List<ProjectEvaluate> projectEvaluates) {
		List<UserInfoDTO> userDTOs = new ArrayList<>();
		Optional<List<String>> prEvaluates = Optional.ofNullable(projectEvaluates.stream()
				.map(ProjectEvaluate::getUserName).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
		Optional<List<User>> users = Optional.empty();
		if (prEvaluates.isPresent()) {
			users = Optional.ofNullable(userRepository.findByUserNameIn(prEvaluates.get()));
		}
		if (users.isPresent()) {
			users.get().forEach(user -> userDTOs.add(new UserInfoDTO(user)));
		}
		return userDTOs;
	}

	/**
	 * 
	 * add evaluate project by week
	 * 
	 * @param projectId void
	 * @author: LXLinh
	 */
	private void addEvaluateProjectWeekLy(int projectId) {

		LocalDate endDate = LocalDate.now().with(DayOfWeek.MONDAY);
		LocalDate endDateProject = LocalDate.parse(projectRepository.getProjectStartDateById(projectId));

		try {
			List<LocalDate> evaluateWeeks = getBetweenWeekDaysLocalDate(
					LocalDate.parse(projectRepository.getProjectEndDateById(projectId)).with(DayOfWeek.MONDAY),
					endDate.isAfter(endDateProject) ? endDateProject.with(DayOfWeek.MONDAY) : endDate);
			Map<Date, List<ProjectEvaluate>> projectEvaluates = this
					.getPrjectEvaluateGroupWeek(projectEvaluateRepository.findByProjectId(projectId));
			evaluateWeeks.forEach(localDate -> {
				if (projectEvaluates.get(convertLocalDateToDate(localDate)) == null) {
					ProjectEvaluate projectEvaluate = new ProjectEvaluate();
					projectEvaluate.setProjectId(projectId);
					projectEvaluate.setStartDate(convertLocalDateToDate(localDate));
					projectEvaluate.setEndDate(convertLocalDateToDate(localDate.with(DayOfWeek.FRIDAY)));
					projectEvaluateRepository.save(projectEvaluate);
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 
	 * convert to local date
	 * 
	 * @param date
	 * @return LocalDate
	 * @author: LXLinh
	 */
	private LocalDate convertToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 
	 * get local date by week
	 * 
	 * @param start
	 * @param end
	 * @return List<LocalDate>
	 * @author: LXLinh
	 */
	private List<LocalDate> getBetweenWeekDaysLocalDate(LocalDate start, LocalDate end) {
		List<DayOfWeek> ignore = new ArrayList<>();
		ignore.add(DayOfWeek.MONDAY);
		ignore.add(DayOfWeek.SUNDAY);
		LocalDate calEnd = end.plusWeeks(1);
		return Stream.iterate(start, d -> d.plusDays(7)).limit(start.until(calEnd, ChronoUnit.WEEKS))
				.collect(Collectors.toList());

	}

	@Override
	public QmsUser getMemberEvluateProject(String userName, int projectId) {
		Optional<QmsUser> user = Optional.ofNullable(userQmsRepository.findUserByLogin(userName.trim()));
		if (user.isPresent()) {
			Optional<QmsMember> member = Optional
					.ofNullable(memberQmsRepository.findByUserIdAndProjectId(user.get().getId(), projectId));
			if (member.isPresent()) {
				return user.get();
			}
		}
		return null;
	}

	@Override
	public User getMemberEvluateOfProject(int userId, int projectId) {
		// TODO Auto-generated method stub
		User user = userRepository.getMemberOfProject(userId, projectId);
		return user;
	}

	@Override
	public ProjectEvaluate save(ProjectEvaluate projectEvaluate) {
		// TODO Auto-generated method stub
		return projectEvaluateRepository.save(projectEvaluate);
	}

	@Transactional()
	@Override
	public ProjectEvaluate createProjectEvaluate(String subject, int qualityId, int deliveryId, int processId,
			int projectId, String comment, String userName, String startDate, String endDate) {
		List<ProjectEvaluate> listProjectEvaluate = projectEvaluateRepository.findByProjectId(projectId);
		for (int i = 0; i < listProjectEvaluate.size(); i++) {
			ProjectEvaluate projectLastEVL = listProjectEvaluate.get(listProjectEvaluate.size() - 1);
			LocalDate date1 = LocalDate.parse(projectLastEVL.getStartDate().toString());
			LocalDate date2 = LocalDate.parse(startDate);
			if (date2.isBefore(date1) || date2.isEqual(date1)) {
				return null;
			}
		}
		ProjectEvaluate addProjectEvaluate = new ProjectEvaluate();
		addProjectEvaluate.setSubject(subject);
		addProjectEvaluate.setQualityId(qualityId);
		addProjectEvaluate.setDeliveryId(deliveryId);
		addProjectEvaluate.setProcessId(processId);
		addProjectEvaluate.setProjectId(projectId);
		addProjectEvaluate.setComment(comment);
		SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = simpleDate1.parse(startDate);
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
			SimpleDateFormat simpleDate2 = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = simpleDate2.parse(endDate);
			java.sql.Date sqlendDate = new java.sql.Date(date1.getTime());
			addProjectEvaluate.setEndDate(sqlendDate);
			addProjectEvaluate.setStartDate(sqlStartDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addProjectEvaluate.setCostId(1);
		addProjectEvaluate.setUserName(userName);
		return projectEvaluateRepository.save(addProjectEvaluate);
	}

	@Override

	public ProjectEvaluateComment saveComment(ProjectEvaluateComment projectEvaluateComment) {
	
		return evaluateCommentRepository.save(projectEvaluateComment);
	}

	@Override
	public List<EvaluateCommentDTO> getListCommentEvaluate(int id) {
		CalculateClass cal= new CalculateClass();
		List<ProjectEvaluateComment> listComment= evaluateCommentRepository.findByEvalutedId(id);
		List<EvaluateCommentDTO> listResult = new ArrayList<EvaluateCommentDTO>();
		try {
		for(ProjectEvaluateComment pr : listComment) {
			User u =userRepository.findOne(pr.getCreatorId());
			listResult.add(new EvaluateCommentDTO(pr.getId(), pr.getComment(), pr.getType(), cal.convertDateToString(pr.getCreatedDate()), cal.convertDateToString(pr.getUpdatedDate()),u.getFullName(),pr.getCreatorId()));
			
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return listResult;
	}

	@Override
	public EvaluateDTO getEvaluateProject(ProjectEvaluate pe) {
		 List<EvaluateCommentDTO> listComment = this.getListCommentEvaluate(pe.getId());
		 ProjectEvaluateComment tem =evaluateCommentRepository.getLastCommentPmOrQa(pe.getId(),2);
		 String lastCommentQA = tem!=null?tem.getComment():"";
		 tem =evaluateCommentRepository.getLastCommentPmOrQa(pe.getId(),1);
		 String lastCommentPM=tem!=null?tem.getComment():"";	 
		 EvaluateDTO dto= new  EvaluateDTO(pe.getId(),pe.getProjectId(),"" ,pe.getQualityId(), pe.getDeliveryId(), pe.getProcessId(),pe.getSubject(),pe.getEndDate(),pe.getStartDate(), listComment, lastCommentQA, lastCommentPM) ;
		 return dto;
	}
	public List<DuMonitoringDTO> getListMonitoring(Date startDate, Date endDate, List<String> groupId) {
		List<DuMonitoringDTO> duMonitoringDTOs = new ArrayList<>();
		List<Object> objects = projectEvaluateRepository.getListMonitoring( startDate,endDate, groupId);
		for (Object obj : objects) {
			Object[] oobj = (Object[]) obj;
			DuMonitoringDTO duMonitoringDTO = new DuMonitoringDTO();
			duMonitoringDTO.setProjectName(oobj[0].toString());
			duMonitoringDTO.setDuName(oobj[1].toString());
			System.out.println(oobj[2].toString());
			try {
				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(oobj[2].toString());
				duMonitoringDTO.setStartDate(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			try {
				Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(oobj[3].toString());
				duMonitoringDTO.setEndDate(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<EvaluateCommentDTO> listComment= this.getListCommentEvaluate(Integer.parseInt(oobj[8].toString()));
			duMonitoringDTO.setQualityId(oobj[4] == null ? 0 : Integer.parseInt(oobj[4].toString()));
			duMonitoringDTO.setDeliveryId(oobj[5] == null ? 0 : Integer.parseInt(oobj[5].toString()));
			duMonitoringDTO.setProcessId(oobj[6] == null ? 0 : Integer.parseInt(oobj[6].toString()));
			duMonitoringDTO.setSubject(oobj[7] == null ? "" : oobj[7].toString());
			duMonitoringDTO.setListComment(listComment);
			duMonitoringDTOs.add(duMonitoringDTO);
		}
		return duMonitoringDTOs;
	}

	@Override
	public String getEvaluateLevel(int id) {
		return projectEvaluateRepository.getEvaluateLevel(id);
	}

	@Override
	public List<DuMonitoringDTO> getAllMonitoring(Date startDate, Date endDate) {
		List<DuMonitoringDTO> duMonitoringDTOs = new ArrayList<>();
		List<Object> objects = projectEvaluateRepository.getAllMonitoring(startDate, endDate);
		for (Object obj : objects) {
			Object[] oobj = (Object[]) obj;
			DuMonitoringDTO duMonitoringDTO = new DuMonitoringDTO();
			duMonitoringDTO.setProjectName(oobj[0]!=null?(String) oobj[0]:null);
			duMonitoringDTO.setDuName(oobj[1]!=null?(String)oobj[1]:null);
			try {
				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(oobj[2].toString());
				duMonitoringDTO.setStartDate(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
			try {
				Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(oobj[3].toString());
				duMonitoringDTO.setEndDate(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<EvaluateCommentDTO> listComment= this.getListCommentEvaluate(Integer.parseInt(oobj[8].toString()));
			duMonitoringDTO.setQualityId(oobj[4] == null ? 0 : Integer.parseInt(oobj[4].toString()));
			duMonitoringDTO.setDeliveryId(oobj[5] == null ? 0 : Integer.parseInt(oobj[5].toString()));
			duMonitoringDTO.setProcessId(oobj[6] == null ? 0 : Integer.parseInt(oobj[6].toString()));
			duMonitoringDTO.setSubject(oobj[7] == null ? "" : oobj[7].toString());
			duMonitoringDTO.setListComment(listComment);
			duMonitoringDTOs.add(duMonitoringDTO);
		}
		return duMonitoringDTOs;
	}
}
