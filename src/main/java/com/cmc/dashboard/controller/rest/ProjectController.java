package com.cmc.dashboard.controller.rest;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;


import javax.validation.Valid;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.DuMonitoringDTO;
import com.cmc.dashboard.dto.EditProjectDTO;
import com.cmc.dashboard.dto.EvaluateCommentDTO;
import com.cmc.dashboard.dto.EvaluateDTO;
import com.cmc.dashboard.dto.HistoryDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.ListProjectTaskDTO;
import com.cmc.dashboard.dto.MemberDTO;
import com.cmc.dashboard.dto.NoncomplianceTasksDto;
import com.cmc.dashboard.dto.OverdueTasksDto;
import com.cmc.dashboard.dto.ProjectBasicInfoDTO;
import com.cmc.dashboard.dto.ProjectCssRestResponse;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.ProjectInfoDTO;
import com.cmc.dashboard.dto.ProjectJiraDTO;
import com.cmc.dashboard.dto.ProjectKpiDTO;
import com.cmc.dashboard.dto.ProjectListDTO;
import com.cmc.dashboard.dto.ProjectListTableDTO;
import com.cmc.dashboard.dto.ProjectMemberSolutionDTO;
import com.cmc.dashboard.dto.ProjectTableDTO;
import com.cmc.dashboard.dto.ProjectTaskFilterDTO;
import com.cmc.dashboard.dto.RiskDTO;
import com.cmc.dashboard.dto.RiskEditDTO;
import com.cmc.dashboard.dto.TeamHourByActivityDto;
import com.cmc.dashboard.dto.ThroughtputBurndownDto;
import com.cmc.dashboard.dto.UserDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.dto.UserTimeSheetDTO;
import com.cmc.dashboard.dto.WorkProgressDTO;
import com.cmc.dashboard.exception.BusinessException;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.HistorySyncData;
import com.cmc.dashboard.model.PriorityRank;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.model.ProjectEvaluate;
import com.cmc.dashboard.model.ProjectEvaluateComment;
import com.cmc.dashboard.model.ProjectPcvRate;
import com.cmc.dashboard.model.ProjectRole;
import com.cmc.dashboard.model.ProjectType;
import com.cmc.dashboard.model.ProjectTypeLog;
import com.cmc.dashboard.model.Risk;
import com.cmc.dashboard.model.RiskCategory;
import com.cmc.dashboard.model.RiskHandlingOptions;
import com.cmc.dashboard.model.RiskImpact;
import com.cmc.dashboard.model.RiskLikelihood;
import com.cmc.dashboard.model.RiskStatus;
import com.cmc.dashboard.model.RiskSubCategory;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.repository.HistorySyncDataRepository;
import com.cmc.dashboard.repository.ProjectEvaluateCommentRepository;
import com.cmc.dashboard.repository.ProjectEvaluateRepository;
import com.cmc.dashboard.repository.ProjectTypeLogRepository;
import com.cmc.dashboard.service.GroupService;
import com.cmc.dashboard.service.HistoryDataService;
import com.cmc.dashboard.service.ProjectBillableSevice;
import com.cmc.dashboard.service.ProjectCssService;
import com.cmc.dashboard.service.ProjectEvaluateService;
import com.cmc.dashboard.service.ProjectKpiService;
import com.cmc.dashboard.service.ProjectPcvRateService;
import com.cmc.dashboard.service.ProjectRiskService;
import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.ProjectTaskService;
import com.cmc.dashboard.service.ProjectTypeLogService;
import com.cmc.dashboard.service.ProjectTypeService;
import com.cmc.dashboard.service.UserService;
import com.cmc.dashboard.service.qms.ProjectMemberService;
import com.cmc.dashboard.util.ApiMessage;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.Constants.StringPool;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectCssService projectCssService;

	@Autowired
	private ProjectTaskService projectTask;

	@Autowired
	private ProjectRiskService projectRiskService;

	@Autowired
	private ProjectEvaluateService projectEvaluateService;

	@Autowired
	private HistoryDataService historyDataService;

	@Autowired
	private ProjectMemberService projectMemberService;

	@Autowired
	private ProjectPcvRateService projectPcvRateService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectTypeService projectTypeService;

	@Autowired
	private GroupService groupService;

//	@Autowired
//	private ProjectUserService projectUserService;

	@Autowired
	private ProjectBillableSevice projectBillableSevice;

	@Autowired
	private ProjectEvaluateRepository projectEvaluateRepository;
	@Autowired
	private ProjectEvaluateCommentRepository evaluateCommentRepository;
	@Autowired
//	private ProjectEvaluateService evaluateService;;
//	@Autowired
	private HistorySyncDataRepository historySyncDataRepository;

	@Autowired
	ProjectKpiService projectKpiService;
	
    @Autowired
    private ProjectTypeLogService projectTypeLogService;
    @Autowired
    private ProjectTypeLogRepository projectTypeLogRepository;
//	private static final Logger logger = Logger.getLogger(ProjectController.class);
	/**
	 * call service get list css value of selected project0 call service get list
	 * css value of selected project
	 *
	 * @param projectId
	 * @return ResponseEntity<List<ProjectCssUtilizationDto>>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.GET)
	public ResponseEntity<ProjectCssRestResponse> getProjectCssDetail(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectCssService.getByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Call service add project's css value
	 *
	 * @param json
	 * @return ResponseEntity<String>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.POST)
	public ResponseEntity<ProjectCssRestResponse> createProjectCss(@RequestBody ProjectCss projectCss) {
		return new ResponseEntity<>(projectCssService.create(projectCss), HttpStatus.OK);
	}

	/**
	 * Call service update project's css value
	 * 
	 * @param json
	 * @return
	 * @throws ParseException
	 */

	@RequestMapping(value = "/project/auth/css/update", method = RequestMethod.POST)
	public ResponseEntity<ProjectCssRestResponse> updateProjectCss(@RequestBody ProjectCss projectCss) {
		// TypeToken<ProjectCss> token = new TypeToken<ProjectCss>() {};
		// ProjectCss projectCss = new Gson().fromJson(json, token.getType());
//		JSONObject object= new JSONObject(json);
//		int id=projectCss.getProjectCssId();
		int value = (int) projectCss.getScoreValue();
		int projectId = projectCss.getProjectId();
		ProjectCss projectCsss = projectCssService.getById(projectCss.getProjectCssId());
		projectCsss.setScoreValue(value);
		projectCssService.update(projectCsss);
		return new ResponseEntity<ProjectCssRestResponse>(projectCssService.getByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Call service to delete css value of project
	 *
	 * @param projectCssId
	 * @return ResponseEntity<String>
	 * @author: DVNgoc
	 */
	@RequestMapping(value = "/project/auth/css", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ProjectCssRestResponse> deleteProjectCss(@RequestParam("projectCssId") int projectCssId) {
		if (!projectCssService.checkExist(projectCssId))
			return new ResponseEntity<>(ProjectCssRestResponse.error(MessageUtil.DATA_NOT_EXIST),
					HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(projectCssService.delete(projectCssId), HttpStatus.OK);
	}

	/**
	 * Return list project by user
	 *
	 * @param userId
	 * @return ResponseEntity<ProjectListDTO>
	 * @author: ntquy
	 */
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/user/projects", method = RequestMethod.GET)
	public ResponseEntity<ProjectListDTO> getProjectListByUser(@RequestParam("login") String login,
			@RequestParam("userId") int userId, @RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("column") String column, @RequestParam("sort") String sort,
			@RequestParam("projectName") String projectName, @RequestParam("status") String status,
			@RequestParam("PM") String pm, @RequestParam("startDate") String startDate,
			@RequestParam("startDateEqual") String startDateEqual, @RequestParam("endDate") String endDate,
			@RequestParam("endDateEqual") String endDateEqual, @RequestParam("DU") String du,
			@RequestParam("typeProject") String typeProject) {
		String projects = projectName.replaceAll(StringPool.UNDERSCORE_CHAR, StringPool.ESCAPE_UNDERSCORE_CHAR)
				.replaceAll(StringPool.PERCENT_CHAR, StringPool.ESCAPE_PERCENT_CHAR).trim();
		ArrayList<String> listDate = MethodUtil.getDateFilterProjectList(startDateEqual, endDateEqual, startDate,
				endDate);
		User user = userService.findByUserName(login);
		if (userService.checkRoleViewListProject(userId).contains(BigInteger.valueOf(1))) {
			ProjectListDTO lstProjectDto = projectService.getProjectListByUser(userId, page, size, column, sort,
					projects, status, pm, listDate.get(0), listDate.get(1), listDate.get(2), listDate.get(3), du,
					typeProject, user.getGroup() == null ? null : user.getGroup().getGroupName(), 1);
			return new ResponseEntity<>(lstProjectDto, HttpStatus.OK);
		} else {
			ProjectListDTO lstProjectDto = projectService.getProjectListByUser(userId, page, size, column, sort,
					projects, status, pm, listDate.get(0), listDate.get(1), listDate.get(2), listDate.get(3), du,
					typeProject, user.getGroup() == null ? null : user.getGroup().getGroupName(), 0);
			return new ResponseEntity<>(lstProjectDto, HttpStatus.OK);

		}
	}

	@RequestMapping(value = "/all/projects", method = RequestMethod.GET)
	public ResponseEntity<ProjectListTableDTO> getAllProject(@RequestParam(value ="login",required = false) String login,
			@RequestParam("userId") int userId, @RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("column") String column, @RequestParam("sort") String sort,
			@RequestParam("projectName") String projectName, @RequestParam("status") String status,
			@RequestParam("PM") String pm, @RequestParam("startDate") String startDate,
			@RequestParam("startDateEqual") String startDateEqual, @RequestParam("endDate") String endDate,
			@RequestParam("endDateEqual") String endDateEqual, @RequestParam("DU") String du,
			@RequestParam("typeProject") String typeProject,@RequestParam("type") String type) {

		String projects = projectName.replaceAll(StringPool.UNDERSCORE_CHAR, StringPool.ESCAPE_UNDERSCORE_CHAR)
				.replaceAll(StringPool.PERCENT_CHAR, StringPool.ESCAPE_PERCENT_CHAR).trim();
		ArrayList<String> listDate = MethodUtil.getDateFilterProjectList(startDateEqual, endDateEqual, startDate,
				endDate);
//		User user= userService.findByUserName(login);

//		ProjectListTableDTO lstProjectDto = projectService.getProjectListByUser(userId, page, size, column, sort, projects,
//				status, pm, listDate.get(0), listDate.get(1), listDate.get(2), listDate.get(3), du, typeProject,user.getGroup() == null ? null : user.getGroup().getGroupName());
		ProjectListTableDTO lstProjectDto = projectService.getProjectListAll(userId, page, size, column, sort, projects,
				status, pm, listDate.get(0), listDate.get(1), listDate.get(2), listDate.get(3), du, typeProject,type);

		return new ResponseEntity<>(lstProjectDto, HttpStatus.OK);

	}

	/**
	 * Get project's billable by project
	 *
	 * @param projectId
	 * @return ResponseEntity<List<ProjectBillable>>
	 * @author: nvkhoa
	 * @modifier: HungNC
	 * @modifyDate: 26-02-2018
	 */
//	@RequestMapping(value = "/project/auth/billable", method = RequestMethod.GET)
//	public ResponseEntity<Map<String, Object>> getBillableByProject(@RequestParam("projectId") int projectId) {
//		Map<String, Object> map = new HashMap<>();
//		ProjectDTO projectDTO = projectService.getProjectInfo(projectId);
//		List<BillableDTO> listBillable = projectService.getBillableByProject(projectId);
//		map.put("listBillable", listBillable);
//		map.put("projectBillable", projectDTO);
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}
	@RequestMapping(value = "/project/auth/billable", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getBillableByProject(@RequestParam("projectId") int projectId) {
		Map<String, Object> map = new HashMap<>();
		ProjectDTO projectDTO = projectService.getProjectInfo(projectId);
		List<BillableDTO> listBillable = projectBillableSevice.getBillableByProject(projectId);
		map.put("listBillable", listBillable);
		map.put("projectBillable", projectDTO);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * update list project billable by list id
	 *
	 * @param listBillable
	 * @return
	 * @author LXLinh
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/project/auth/billable", method = RequestMethod.PUT)
	public ResponseEntity<ListBillableDTO> updateProjectBillable(@RequestBody List<BillableDTO> listBillables) {
		JsonObject validDate = MethodUtil.validateListBillable(listBillables);
		if (validDate != null) {
			throw new BusinessException(validDate.toString());
		}
		return new ResponseEntity<>(projectService.updateProjectBillable(listBillables), HttpStatus.OK);
	}

	/**
	 * get list project task
	 *
	 * @param projectId
	 * @return ResponseEntity<ProjectTaskDTO>
	 * @author: tvdung
	 */
	@RequestMapping(value = "/project/auth/task", method = RequestMethod.GET)
	public ResponseEntity<ListProjectTaskDTO> getProjectTask(@RequestParam(value = "projectId") int projectId,
			@RequestParam(value = "search") String search, @RequestParam(value = "page") int page,
			@RequestParam(value = "sort") String sort, @RequestParam(value = "typeSort") String typeSort,
			@RequestParam(value = "assignee") String assignee, @RequestParam(value = "status") String status,
			@RequestParam(value = "minstartDate") String minstartDate,
			@RequestParam(value = "maxstartDate") String maxstartDate,
			@RequestParam(value = "mindueDate") String mindueDate,
			@RequestParam(value = "maxdueDate") String maxdueDate) {
		ProjectTaskFilterDTO filterDTO = new ProjectTaskFilterDTO(assignee, status, minstartDate, maxstartDate,
				mindueDate, maxdueDate);
		return new ResponseEntity<>(projectTask.getListTask(projectId, search, page, sort, typeSort, filterDTO),
				HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all risk of project by projectId to API
	 *
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk", method = RequestMethod.GET)
	public ResponseEntity<List<RiskDTO>> getAllRiskOfProject(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectRiskService.getAllRiskOfProject(projectId), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return a risk detail
	 *
	 * @param riskId
	 * @return ResponseEntity<RiskDTO>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk-detail", method = RequestMethod.GET)
	public ResponseEntity<RiskDTO> getById(@RequestParam("riskId") int riskId) {
		return new ResponseEntity<>(projectRiskService.getById(riskId), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will save new risk
	 *
	 * @param riskDTO
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk", method = RequestMethod.POST)
	public ResponseEntity<?> saveRisk(@Valid @RequestBody RiskDTO riskDTO) {
		if (!MethodUtil.isNull(riskDTO.getEarliestImpactDate()) && !MethodUtil.isNull(riskDTO.getLatestImpactDate())
				&& riskDTO.getEarliestImpactDate().compareTo(riskDTO.getLatestImpactDate()) > 0) {
			return new ResponseEntity<ApiMessage>(
					new ApiMessage(HttpStatus.UNPROCESSABLE_ENTITY, MessageUtil.MessageProjectRisk.IMPACTDATE),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if ((MethodUtil.isNull(riskDTO.getEarliestImpactDate()) && !MethodUtil.isNull(riskDTO.getLatestImpactDate()))
				|| (!MethodUtil.isNull(riskDTO.getEarliestImpactDate())
						&& MethodUtil.isNull(riskDTO.getLatestImpactDate()))) {
			return new ResponseEntity<ApiMessage>(
					new ApiMessage(HttpStatus.UNPROCESSABLE_ENTITY, MessageUtil.MessageProjectRisk.MISSINGIMPACTDATE),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		riskDTO.setRiskTitle(riskDTO.getRiskTitle().trim());
		riskDTO.setRiskDescription(riskDTO.getRiskDescription().trim());
		if (!MethodUtil.isNull(riskDTO.getRiskIndicator())) {
			riskDTO.setRiskIndicator(riskDTO.getRiskIndicator().trim());
		}
		if (!MethodUtil.isNull(riskDTO.getReasons())) {
			riskDTO.setReasons(riskDTO.getReasons().trim());
		}
		if (!MethodUtil.isNull(riskDTO.getRiskHandlingOptionsId()) && MethodUtil.isNull(riskDTO.getReasons())) {
			return new ResponseEntity<ApiMessage>(
					new ApiMessage(HttpStatus.UNPROCESSABLE_ENTITY, MessageUtil.MessageProjectRisk.HANDLINGISACTIVE),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (MethodUtil.isNull(riskDTO.getRiskHandlingOptionsId()) && !MethodUtil.isNull(riskDTO.getReasons())) {
			return new ResponseEntity<ApiMessage>(
					new ApiMessage(HttpStatus.UNPROCESSABLE_ENTITY, MessageUtil.MessageProjectRisk.REASONISACTIVE),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		riskDTO.setRegisteredDate(new Date());
		return new ResponseEntity<Risk>(projectRiskService.save(riskDTO.toEntity()), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all priority rank
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/priority-rank", method = RequestMethod.GET)
	public ResponseEntity<List<PriorityRank>> findAllPriorityRank() {
		return new ResponseEntity<>(projectRiskService.findAllPriorityRank(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all risk category
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/category", method = RequestMethod.GET)
	public ResponseEntity<List<RiskCategory>> findAllRiskCategory() {
		return new ResponseEntity<>(projectRiskService.findAllRiskCategory(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all Risk Handling Options
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/handling-options", method = RequestMethod.GET)
	public ResponseEntity<List<RiskHandlingOptions>> findAllRiskHandlingOptions() {
		return new ResponseEntity<>(projectRiskService.findAllRiskHandlingOptions(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all Risk Impact
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/impact", method = RequestMethod.GET)
	public ResponseEntity<List<RiskImpact>> findAllRiskImpact() {
		return new ResponseEntity<>(projectRiskService.findAllRiskImpact(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all Risk Likelihood
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/likelihood", method = RequestMethod.GET)
	public ResponseEntity<List<RiskLikelihood>> findAllRiskLikelihood() {
		return new ResponseEntity<>(projectRiskService.findAllRiskLikelihood(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all Risk Status
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/status", method = RequestMethod.GET)
	public ResponseEntity<List<RiskStatus>> findAllRiskStatus() {
		return new ResponseEntity<>(projectRiskService.findAllRiskStatus(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function will return all Risk Sub-Category
	 *
	 * @return ResponseEntity<?>
	 * @author: nvangoc
	 */
	@RequestMapping(value = "/project/risk/sub-category", method = RequestMethod.GET)
	public ResponseEntity<List<RiskSubCategory>> findAllRiskSubCategory() {
		return new ResponseEntity<>(projectRiskService.findAllRiskSubCategory(), HttpStatus.OK);
	}

	/**
	 *
	 * description this function edit risk
	 *
	 * @return ResponseEntity<?>
	 * @author: ntquy
	 */
	@RequestMapping(value = "/project/risk/edit", method = RequestMethod.PUT)
	public ResponseEntity<RiskDTO> editRisk(@RequestBody RiskEditDTO riskEdit) {
		return new ResponseEntity<>(projectRiskService.save(riskEdit), HttpStatus.OK);
	}

	/**
	 * Get ProjectBasicInfo
	 *
	 * @param projectId
	 * @return ResponseEntity<ProjectDTO>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/project-basic-info", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectBasicInfoDTO>> getProjectBasicInfo(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getProjectBasicInfoByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Get WorkProgress
	 *
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/work-progress", method = RequestMethod.GET)
	public ResponseEntity<WorkProgressDTO> getWorkProgress(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getWorkProgressByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 *
	 * create new evaluate project
	 *
	 * @param projectEvaluateDTO
	 * @return ResponseEntity<?>
	 * @author: LXLinh
	 * @throws ParseException
	 */
	@RequestMapping(value = "/project/edit-evaluate-project", method = RequestMethod.POST)
	public ResponseEntity<EvaluateDTO> updateProjectEvaluateDTO(
			@RequestParam("projectEvaluate") String projectEvaluate) throws ParseException {
		 EvaluateDTO dto= new EvaluateDTO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject obj = new JSONObject(projectEvaluate);
		String subject = obj.getString("subject");
		int qualityId = obj.getInt("quality");
		int deliveryId = obj.getInt("delivery");
		int processId = obj.getInt("process");
		String comment = obj.getString("comment").trim();
		int projectId= obj.getInt("projectId");
		String startDate=obj.getString("startDate");
		String endDate=obj.getString("endDate");
		int creatorId=obj.getInt("userId");
		int type = obj.getInt("type");
		//int projectEvaluateId = obj.getInt("projectEvaluateId");
		ProjectEvaluate findByprojectEvaluateId = projectEvaluateRepository.findProjectId(projectId,startDate,endDate);
		if (findByprojectEvaluateId == null) {
			ProjectEvaluate newEvalute= new ProjectEvaluate();
			newEvalute.setComment("");
			newEvalute.setQualityId(qualityId);
			newEvalute.setDeliveryId(deliveryId);
			newEvalute.setProcessId(processId);
			newEvalute.setSubject(subject);
			newEvalute.setStartDate(formatter.parse(startDate));
			newEvalute.setEndDate(formatter.parse(endDate));
			newEvalute.setProjectId(projectId);
			projectEvaluateService.save(newEvalute);
			ProjectEvaluate result = projectEvaluateRepository.findProjectId(projectId,startDate,endDate);
			try {
				if("".equals(comment)==false)
				{
			ProjectEvaluateComment newComment= new ProjectEvaluateComment(result,comment,type,new Date(),new Date(),creatorId);
		    projectEvaluateService.saveComment(newComment);
		    }
			} catch(Exception e) {
				e.printStackTrace();
			        }
		   dto =projectEvaluateService.getEvaluateProject(result);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		if ("".equals(subject)) {
			findByprojectEvaluateId.setSubject("");
		} else {
			findByprojectEvaluateId.setSubject(subject);
		}
		if ("".equals(comment)) {
			findByprojectEvaluateId.setComment("");
		} else {
			findByprojectEvaluateId.setComment(comment);
		}
		
		findByprojectEvaluateId.setQualityId(qualityId);
		findByprojectEvaluateId.setDeliveryId(deliveryId);
		findByprojectEvaluateId.setProcessId(processId);
		
		ProjectEvaluate result = projectEvaluateService.save(findByprojectEvaluateId);
		try {
			if("".equals(comment)==false)
			{
		ProjectEvaluateComment newComment= new ProjectEvaluateComment(result,comment,type,new Date(),new Date(),creatorId);
	    projectEvaluateService.saveComment(newComment);
	    }
		} catch(Exception e) {
			e.printStackTrace();
		}
		  dto =projectEvaluateService.getEvaluateProject(result);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	@PostMapping(value = "/project/evaluate-project/evaluate-comment/update")
	public ResponseEntity<EvaluateCommentDTO>  updateCommentEvaluate(@RequestParam("editComment") String editComment) throws ParseException, UnsupportedEncodingException {
		CalculateClass cal = new CalculateClass();
		JSONObject obj = new JSONObject(editComment);
		int idComment= obj.getInt("idComment");
		String content= obj.getString("content");
		byte ptext[] = content.getBytes("ISO-8859-1"); 
		String value = new String(ptext, "UTF-8");
		ProjectEvaluateComment comment = evaluateCommentRepository.findOne(idComment) ;
		comment.setComment(value);
		projectEvaluateService.saveComment(comment);
		User u= userService.findUserById(comment.getCreatorId());
		EvaluateCommentDTO result= new EvaluateCommentDTO(comment.getId(), comment.getComment(), comment.getType(), cal.convertDateToString(comment.getCreatedDate()),  cal.convertDateToString(comment.getUpdatedDate()), u.getFullName(),comment.getCreatorId());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/project/insert-evaluate-project")
	public ResponseEntity<ProjectEvaluate> insertProjectEvaluateDTO(
			@RequestParam("projectEvaluate") String projectEvaluate) throws ParseException {
		JSONObject obj = new JSONObject(projectEvaluate);
		String subject = obj.getString("subject");
		int qualityId = obj.getInt("quality");
		int deliveryId = obj.getInt("delivery");
		int processId = obj.getInt("process");
		String comment = obj.getString("comment");
		int projectId = obj.getInt("projectId");
		String userName = obj.getString("userName");
		String startDate = obj.getString("startDate");
		String endDate = obj.getString("endDate");
		ProjectEvaluate checkProjectEvaluate = projectEvaluateRepository.findProjectId(projectId, startDate, endDate);
		if (checkProjectEvaluate != null) {
			new ResponseEntity<>("Đã tồn tại", HttpStatus.OK);
		}		
		return new ResponseEntity<>(projectEvaluateService.createProjectEvaluate(subject, qualityId, deliveryId, processId, projectId, comment, userName, startDate, endDate), HttpStatus.OK);
	}

	/**
	 * Get Project Progress
	 *
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/project-progress", method = RequestMethod.GET)
	public ResponseEntity<ProjectDTO> getProjectProgress(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getProjectProgressByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Get Overdue tasks
	 *
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/overdue-tasks", method = RequestMethod.GET)
	public ResponseEntity<List<OverdueTasksDto>> getOverdueTasks(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getOverdueTasksByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 *
	 * get user evaluate project by project
	 *
	 * @param projectId
	 * @return ResponseEntity<?>
	 * @author: LXLinh
	 */
	@RequestMapping(value = "/project/evaluate-project/users", method = RequestMethod.GET)
	public ResponseEntity<Collection<UserInfoDTO>> getUsersEvaluateProject(@RequestParam("projectId") int projectId,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
		return new ResponseEntity<>(projectEvaluateService.getUserEvaluateByProject(projectId, startDate),
				HttpStatus.OK);
	}

	/**
	 *
	 * get user evaluate of project by week
	 *
	 * @param projectId
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return ResponseEntity<?>
	 * @author: LXLinh
	 */
	@RequestMapping(value = "/project/evaluate-project/user-evaluate-project", method = RequestMethod.GET)
	public ResponseEntity<ProjectEvaluate> getUserEvaluateOfProjectByWeek(@RequestParam("projectId") int projectId,
			@RequestParam("userName") String userName,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
		return new ResponseEntity<>(
				projectEvaluateService.getUserEvaluateOfProjectByWeek(projectId, userName.trim(), startDate),
				HttpStatus.OK);
	}

	/**
	 *
	 * get list projects evaluate by week
	 *
	 * @param projectId
	 * @param userId
	 * @return ResponseEntity<?>
	 * @author: LXLinh
	 */
	@RequestMapping(value = "/project/evaluate-project/evaluate-project-week", method = RequestMethod.GET)
	public ResponseEntity<List<EvaluateDTO>> getProjectsByWeek(@RequestParam("projectId") int projectId,@RequestParam("month") int month,@RequestParam("year") int year) {
		List<EvaluateDTO> result=projectEvaluateService.getEvaluateProjects(projectId,month,year);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/project/evaluate-project/evaluate-comment", method = RequestMethod.GET)
	public ResponseEntity<List<EvaluateCommentDTO>> getCommentEvaluate(@RequestParam("evaluateId") int evaluateId) {
		List<EvaluateCommentDTO> result=projectEvaluateService.getListCommentEvaluate(evaluateId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	/**
	 * Get Project Timesheets
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<ProjectTimesheetsDto>>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/project-timesheets", method = RequestMethod.GET)
	public ResponseEntity<List<UserTimeSheetDTO>> getProjectTimesheets(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getUserTimeSheetLastWeekAndThisWeekByProjectId(projectId),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/project/project-overview", method = RequestMethod.GET)
	public ResponseEntity<Object> getProjectOverview(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getProjectOverviewByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Get Noncompliance tasks
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<NoncomplianceTasksDto>>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/noncompliance-tasks", method = RequestMethod.GET)
	public ResponseEntity<List<NoncomplianceTasksDto>> getNoncomplianceTasks(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getNoncomplianceTasksByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Get Members With Last Activity
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<MemberDto>>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/members-last-activity", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getMembersWithLastActivity(@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getMembersWithLastActivityByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 * Get TeamHour By Activities
	 * 
	 * @param projectId
	 * @return ResponseEntity<List<TeamHourByActivityDto>>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/team-hour-by-activities", method = RequestMethod.GET)
	public ResponseEntity<List<TeamHourByActivityDto>> getTeamHourByActivities(
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getTeamHourByActivitiesByProjectId(projectId), HttpStatus.OK);
	}

	/**
	 *
	 * get member evaluate evaluate project
	 *
	 * @param projectId
	 * @param userName
	 * @return ResponseEntity<QmsUser>
	 * @author: LXLinh
	 */
	@RequestMapping(value = "/project/evaluate-project/member-evaluate-project", method = RequestMethod.GET)
	public ResponseEntity<Object> getMemberEvluateProjectByUserId(@RequestParam("userId") int userId,
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectEvaluateService.getMemberEvluateOfProject(userId, projectId), HttpStatus.OK);
	}

	/**
	 * Return list project by user
	 *
	 * @param userId
	 * @return ResponseEntity<Object>
	 * @author: NNDuy
	 */
	@RequestMapping(value = "/users/projects", method = RequestMethod.GET)
	public ResponseEntity<Object> getProjectByUser(@RequestParam("userId") int userId) {
		return new ResponseEntity<>(projectService.getAllProjectByUser(userId), HttpStatus.OK);
	}

	/**
	 * Return time of projects active
	 *
	 * @param project
	 * @return ResponseEntity<Object>
	 * @author: NNDuy
	 */
	@RequestMapping(value = "/users/projects/active/times", method = RequestMethod.GET)
	public ResponseEntity<Object> getTimeOfListProjectActive(@RequestParam("userId") int userId) {
		return new ResponseEntity<>(projectService.getTimeOfListProjectActiveByUserId(userId), HttpStatus.OK);
	}

	/**
	 * delete risk
	 * 
	 * @author duyhieu
	 * @param riskId
	 * @return
	 */
	@RequestMapping(value = "/project/risk/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteRisk(@RequestParam("riskId") Integer riskId) {
		projectRiskService.deleteRisk(riskId);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	/**
	 * Get Throughtput Burndown By ProjectId
	 * 
	 * @param projectId
	 * @return ResponseEntity<ThroughtputBurndownDto>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/project/throughtput-burndown", method = RequestMethod.GET)
	public ResponseEntity<List<ThroughtputBurndownDto>> getThroughtputBurndownByProjectId(
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getThroughtputBurndownByProjectId(projectId), HttpStatus.OK);
	}

	@RequestMapping(value = "/project/risk-history", method = RequestMethod.GET)
	public ResponseEntity<List<HistoryDTO>> getRiskHistory(@RequestParam("riskId") int riskId) {
		return new ResponseEntity<>(historyDataService.getHistoryByRiskId(riskId), HttpStatus.OK);
	}

	/**
	 * @author duyhieu
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/project/member", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectMemberSolutionDTO>> getMemberByProjectId(
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectMemberService.getProjectMembers(projectId), HttpStatus.OK);
	}

	/**
	 * Get Project PcvRate List By ProjectId
	 * 
	 * @param projectId
	 * @return List<ProjectPcvRate>
	 * @author: GiangTM
	 */
	@RequestMapping(value = "/projects/{projectId}/pcv-rates", method = RequestMethod.GET)
	public ResponseEntity<List<ProjectPcvRate>> getProjectPcvRatesByProjectId(
			@PathVariable("projectId") int theProjectId) {
		return new ResponseEntity<>(projectPcvRateService.getProjectPcvRatesByProjectId(theProjectId), HttpStatus.OK);
	}

	@RequestMapping(value = "/project/project-timesheets-test", method = RequestMethod.GET)
	public ResponseEntity<List<UserTimeSheetDTO>> getUserTimeSheetLastWeekAndThisWeekByProjectId(
			@RequestParam("projectId") int projectId) {
		return new ResponseEntity<>(projectService.getUserTimeSheetLastWeekAndThisWeekByProjectId(projectId),
				HttpStatus.OK);
	}

	@PostMapping(value = "/project/edit")
	public ResponseEntity<EditProjectDTO> getEditProject(@RequestParam("data") String data) {
		JSONObject jsonObject = new JSONObject(data);
		int projectId = jsonObject.getInt("projectId");
		EditProjectDTO editProject = projectService.getEditProject(projectId);
		return new ResponseEntity<>(editProject, HttpStatus.OK);
	}

	@PostMapping(value = "/project/update")
	public ResponseEntity<Project> updateProject(@RequestParam("data") String data) throws JSONException, ParseException {
		JSONObject jsonObject = new JSONObject(data);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int projectId = jsonObject.getInt("projectId");
		Group group = groupService.findById(jsonObject.getInt("DU"));
		ProjectType pt = projectTypeService.findById(jsonObject.getInt("projectType"));
		String startDate = jsonObject.getString("startDate");
		String endDate = jsonObject.getString("endDate");
		String deleteSkill = jsonObject.getString("skillDelete").trim();
		String insertSkill = jsonObject.getString("skillInsert").trim();
//		String deleteMember = jsonObject.getString("memberDelete").trim();
		String updateMember = jsonObject.getString("memberUpdate").trim();
		String deletePM = jsonObject.getString("pmDelete").trim();
		String insertPM = jsonObject.getString("pmInsert").trim();
		JSONObject target =jsonObject.getJSONObject("target");
		String startDateType = jsonObject.getString("startDateType");
		String endDateType = jsonObject.getString("endDateType");
		String action = jsonObject.getString("action");
		int idProjectTypeLog = jsonObject.getInt("idTypeUpdate");
		
//		JSONArray array = jsonObject.getJSONArray("listMember");
//		String pm = "";
//		int len = array.length();
//		if (len > 0) {
//			for (int i = 0; i < len; i++) {
//				JSONObject object = array.getJSONObject(i);
//				int projectRoleId = object.getInt("projectRoleId");
//				if (projectRoleId == 1) {
//					if ("".equals(pm))
//						pm += object.getString("fullName");
//					else
//						pm += "," + object.getString("fullName");
//				}
//				int userId = object.getInt("userId");
//				String startDateUser = object.getString("startDate");
//				int skillId = object.getInt("skillId");
//				projectUserService.update(projectRoleId, skillId, startDateUser, projectId, userId);
//			}
//		}
		projectService.deleteSkill(projectId, deleteSkill);
		projectService.insertSkill(projectId, insertSkill);
//		projectService.deleteMember(projectId, deleteMember);
		projectService.updateMember(projectId, updateMember);
		projectService.deletePM(projectId, deletePM);
		projectService.insertPM(projectId, insertPM);
		Project p = projectService.findProjectById(projectId);
		p.setGroup(group);
		p.setProjectType(pt);
		p.setType(jsonObject.getInt("type"));
		p.setProjectManager(projectService.getNamePm(projectId));
		//set target
		p.setPcvRate((float) target.getDouble("pcvRate"));
		p.setBillableRate((float) target.getDouble("billableRate"));
		p.setBugRate((float) target.getDouble("bugRate"));
		p.setTimeliness((float) target.getDouble("timeliness"));
		p.setCss((float) target.getDouble("css"));
		p.setEffortDeviation((float) target.getDouble("effortDeviation"));
		p.setProductivity((float) target.getDouble("productivity"));
		p.setLeakageRate((float) target.getDouble("leakageRate"));
		//
		try {
			if ("".equals(startDate) == false) {
				p.setStartDate(formatter.parse(startDate));
			}
			if ("".equals(endDate) == false) {
				p.setEndDate(formatter.parse(endDate));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("".equals(startDateType)==false && "".equals(endDateType)==false)
			if("insert".equals(action)) 
		    projectTypeLogService.save(new ProjectTypeLog( projectId, jsonObject.getInt("type"), formatter.parse(startDateType),formatter.parse(endDateType), jsonObject.getInt("editorId")));
			else if("update".equals(action)) 
			{
				ProjectTypeLog updateTypeLog = projectTypeLogRepository.findOne(idProjectTypeLog);
				updateTypeLog.setStartDate(formatter.parse(startDateType));
				updateTypeLog.setEndDate(formatter.parse(endDateType));
				updateTypeLog.setEditorId(jsonObject.getInt("editorId"));
				updateTypeLog.setProjectTypeId(jsonObject.getInt("type"));
				projectTypeLogService.save(updateTypeLog);
			}
			
		projectService.update(p);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@GetMapping(value = "project/project-type/get")
	public ResponseEntity<List<ProjectType>> getListProjectType(
			@RequestParam(value = "data", required = false) String data) {
		return new ResponseEntity<>(projectTypeService.getAll(), HttpStatus.OK);
	}

	@PostMapping(value = "project/project-type/create")
	public ResponseEntity<ProjectType> createProjectType(@RequestBody String nameProjectType) {
		nameProjectType = nameProjectType.substring(1, nameProjectType.length()-1);
		return new ResponseEntity<>(projectTypeService.insert(nameProjectType), HttpStatus.OK);
	}

	@GetMapping(value = "project/project-type/update")
	public ResponseEntity<ProjectType> updateProjectType(@RequestParam int idProjectType,
			@RequestParam String nameProjectType) {
		ProjectType prt = projectTypeService.getProjecTypeById(idProjectType);
		prt.setName(nameProjectType);
		return new ResponseEntity<>(projectTypeService.update(prt), HttpStatus.OK);
	}

	@GetMapping(value = "project/project-type/{id}")
	public ResponseEntity<ProjectType> getProjectById(@PathVariable int id) {
		return new ResponseEntity<>(projectTypeService.getProjecTypeById(id), HttpStatus.OK);
	}

	@DeleteMapping(value = "project/project-type/delete/{id}")
	public ResponseEntity<Boolean> deleteProjectType(@PathVariable int id) {
		return new ResponseEntity<>(projectTypeService.delete(id), HttpStatus.OK);
	}

	@PostMapping(value = "project/changeStatus")
	public ResponseEntity<Integer> changeStatusProject(@RequestParam(value = "data") String data) {
		JSONObject jsonObject = new JSONObject(data);
		int projectId = jsonObject.getInt("projectId");
		int status = jsonObject.getInt("status");
		int resultId = projectService.changeStatus(projectId, status);
		return new ResponseEntity<>(resultId, HttpStatus.OK);
	}

	@GetMapping(value = "project/project-role/get")
	public ResponseEntity<List<ProjectRole>> getListProjectRole() {
		return new ResponseEntity<>(projectService.getAllProjectRole(), HttpStatus.OK);
	}

	/**
	 * Update Man Power of user
	 * 
	 * @param userId
	 * @return ResponseEntity<?>
	 */
	@RequestMapping(value = "/project/line-of-code", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLineOfCode(@RequestBody String json) {
		Gson gson = new Gson();
		TypeToken<Project> token = new TypeToken<Project>() {
		};
		Project project = gson.fromJson(json, token.getType());

		Project projectOld = projectService.findProjectById(project.getId());

		if (projectOld == null) {
			return new ResponseEntity<String>(MessageUtil.DATA_NOT_EXIST, HttpStatus.NOT_FOUND);
		}
		projectOld.setLineCode(project.getLineCode());
		projectOld.setUpdatedAt(new Date());
		projectService.updateLineOfCode(projectOld);
		return new ResponseEntity<List<ProjectKpiDTO>>(projectKpiService.getProjectKpiByProjectId(project.getId()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/project")
	public ResponseEntity<?> projectByProjectId(@RequestParam("projectId") int projectId) {
		Project project = projectService.getProjectByProjectId(projectId);
		return new ResponseEntity<>(project, HttpStatus.OK);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	//@GetMapping(value = "api")
	public void getData() {
		HistorySyncData logStart= new HistorySyncData(Constants.TypeLogger.saveProjectFromUser,"Bắt đầu save data!", 1, new Date()); 
		historySyncDataRepository.save(logStart);
		 final String uri =
		 "https://pms.cmcglobal.com.vn/plugins/servlet/projectuserissueservlet";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProjectJiraDTO[]> result = null;
		try {
			result = ((RestTemplate) restTemplate).getForEntity(uri, ProjectJiraDTO[].class);
			ProjectJiraDTO[] array = result.getBody();
			projectService.saveDataFromJira(array);
		} catch (Exception e) {
			e.printStackTrace();
			HistorySyncData logEnd= new HistorySyncData(Constants.TypeLogger.saveProjectFromUser,e.getMessage(), 0, new Date()); 
			historySyncDataRepository.save(logEnd);
		}
	
//		return result;
 	}
	
	@GetMapping(value = "/project/edit/alluser")
	public ResponseEntity<List<MemberDTO>> getUserAllUser() {
		List<MemberDTO> result = projectService.getUserAllEditProject();
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	@GetMapping(value = "/project/du")
	public ResponseEntity<List<ProjectTableDTO>> getProjectByDu(@RequestParam("groupId") int groupId) {
		List<ProjectTableDTO> projectTableDTOs=projectService.getProjectByDu(groupId);
		return new ResponseEntity<>(projectTableDTOs,HttpStatus.OK);
	}
	@GetMapping(value = "/delivery-unit/monitoring")
	public ResponseEntity<List<DuMonitoringDTO>> getListMonitoring(@RequestParam(value = "groupId", required = false) String groupId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) throws ParseException {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
		Date start = (startDate==""?formatter.parse("1970-01-01"):formatter.parse(startDate));
		Date end = (endDate==""?new Date():formatter.parse(endDate));
		
		if(groupId == "") {
			return new ResponseEntity<List<DuMonitoringDTO>>(projectEvaluateService.getAllMonitoring(start, end),HttpStatus.OK);
		} else {
			ArrayList<String>  group_id = new ArrayList<String>(Arrays.asList(groupId.split(",")));;
			return new ResponseEntity<List<DuMonitoringDTO>>(projectEvaluateService.getListMonitoring(start, end, group_id),HttpStatus.OK);
		}
	}

	@GetMapping(value = "/api1")
	public ResponseEntity<Object> setProjectTypeLog() {
		projectTypeLogService.setProjectTypeLog();
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	@GetMapping(value = "/apiGetAllProject")
	public ResponseEntity<List<ProjectInfoDTO>> getListProject(){
		List<ProjectInfoDTO> listProject = projectService.getAllProject();

		return new ResponseEntity<>(listProject, HttpStatus.OK);

	}
}
