package com.cmc.dashboard.controller.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cmc.dashboard.dto.AmsUserDTO;
import com.cmc.dashboard.dto.DUOverviewDTO;
import com.cmc.dashboard.dto.DuInternalDuDTO;
import com.cmc.dashboard.dto.LoginParameterObject;
import com.cmc.dashboard.dto.ProjectJiraDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.dto.UserTableDTO;
import com.cmc.dashboard.dto.UsersAmsDTO;
import com.cmc.dashboard.model.DUStatistic;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.HistorySyncData;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.service.DeliveryUnitService;
import com.cmc.dashboard.service.GroupService;
import com.cmc.dashboard.service.RoleService;
import com.cmc.dashboard.service.SkillService;
import com.cmc.dashboard.service.UploadFileService;
import com.cmc.dashboard.service.UserService;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UploadFileService uploadService;

	@Autowired
	private PasswordEncoder passEncode;

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private DeliveryUnitService deliveryUnitService;

	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
	public ResponseEntity<Object> listUsers() {
		return new ResponseEntity<Object>(userService.getAllUser(), HttpStatus.OK);
	}

	@RequestMapping(value = "/login/{userId}", method = RequestMethod.GET)
	public ResponseEntity<LoginParameterObject> getUser(@PathVariable int userId) {
		if (userId == 1) {
			throw new IllegalArgumentException("The 'name' parameter must not be null or empty");
		}
		LoginParameterObject loginObj = userService.getUserById(userId);
		return new ResponseEntity<>(loginObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/postlogin", method = RequestMethod.POST)
	public ResponseEntity<LoginParameterObject> login(@RequestBody String loginParams) {
		LoginParameterObject lpObj = MethodUtil.getLoginParamsFromString(loginParams);
		LoginParameterObject loginObj = new LoginParameterObject(lpObj.getUsername(), lpObj.getPassword());
		return new ResponseEntity<>(loginObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/postCreate", method = RequestMethod.POST)
	public ResponseEntity<LoginParameterObject> create(@RequestBody String loginParams) {
		LoginParameterObject lpObj = MethodUtil.getLoginParamsFromString(loginParams);
		LoginParameterObject loginObj = new LoginParameterObject(lpObj.getUsername(), lpObj.getPassword());
		return new ResponseEntity<>(loginObj, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ResponseEntity<UserTableDTO> getUserByPage(@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows, @RequestParam("column") String column,
			@RequestParam("sort") String orderBy, @RequestParam("fullName") String fullNames,
			@RequestParam("userName") String userNames, @RequestParam("groupId") String groupId,
			@RequestParam("roleId") String roleId, @RequestParam("statusId") String statusId) {
		logger.info("Listing User with page: {}, rows: {} , account : {} , column : {} ,fullName :{} ,userName :{} ",
				page, rows, account, column, fullNames, userNames);
		// Process order by
		Sort sort = null;
		if (Constants.SortType.ASCENDING.equals(orderBy)) {
			sort = new Sort(Sort.Direction.ASC, column);
		} else
			sort = new Sort(Sort.Direction.DESC, column);

		PageRequest pageRequest = new PageRequest(page - 1, rows, sort);
		Page<User> userPage = userService.findByAccount(account, pageRequest, fullNames, userNames, groupId,
				statusId);
		UserTableDTO userTableDTO = new UserTableDTO();
		userTableDTO.setCurrentPage(userPage.getNumber() + 1);
		userTableDTO.setTotalPages(userPage.getTotalPages());
		userTableDTO.setTotalRecords(userPage.getTotalElements());
		userTableDTO.setUserData(Lists.newArrayList(userPage.iterator()));
		userTableDTO.setGroups(Lists.newArrayList(groupService.getListGroups().iterator()));
		userTableDTO.setRoles(Lists.newArrayList(roleService.getAll()));
		return new ResponseEntity<>(userTableDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@RequestParam(value = "user", required = true) Integer userId) {
		logger.info("Find User with Id {}", userId);
		return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<Object> update(@RequestParam String json,
			@RequestParam(required = false) MultipartFile image) {
		JSONObject obj = new JSONObject(json);
		String username = obj.getString("userNamePar");
		User usersFinByUserName = userService.findByUserName(username);
		usersFinByUserName.setFullName(obj.getString("fullNamePar"));
		int userId = obj.getInt("userId");
		usersFinByUserName.setStatus((byte) obj.getInt("activePar"));
		int level = obj.getInt("levelPer");
		usersFinByUserName.setLevel(level);
		String array = obj.getString("skillPar");
		if (!"xoa_het".equals(array)) {
			userService.removeUserSkill(userId);
			Set<Skill> addSkill = new HashSet<>();
			String[] arrayString = array.split(",");
			for (String s : arrayString) {
				int number = Integer.parseInt(s);
				Skill skill = skillService.findById(number);
				addSkill.add(skill);
			}
			usersFinByUserName.setSkill(addSkill);
		} else if ("xoa_het".equals(array)) {
			userService.removeUserSkill(userId);
		}
		int groupId = obj.getInt("groupPar");
		if (groupId > 0) {
			Group group = groupService.getById(groupId);
			usersFinByUserName.setGroup(group);
		}
		if (image != null) {
			usersFinByUserName.setImg(uploadService.saveFileVer(image, "resources/images/user/"));

		} else
			usersFinByUserName.setImg(usersFinByUserName.getImg());
		userService.updateUser(usersFinByUserName);
		logger.info("Update User with infor {}", json);
		return new ResponseEntity<>(usersFinByUserName, HttpStatus.OK);
	}

//	@PostMapping(value = "/user/insert")
//	public ResponseEntity<Object> insert(@RequestParam String userName,@RequestParam String fullName,@RequestParam String email, @RequestParam int groupId) {
//		User findUserByUserName = userService.findByUserName(userName);
//		User findUserByEmail = userService.findByEmail(email);
//		if (findUserByEmail != null) {
//			return new ResponseEntity<>("emailcoroi", HttpStatus.OK);
//		} else if (findUserByUserName != null) {
//			return new ResponseEntity<>("usernamecoroi", HttpStatus.OK);
//		} else {
//			User insertUser = new User();
//			insertUser.setFullName(fullName);
//			insertUser.setUserName(userName);
//			insertUser.setEmail(email);
//			insertUser.setStatus((byte) 0);
//			Group group = groupService.findById(groupId);
//			insertUser.setGroup(group);
//
//			java.util.Date date = new java.util.Date();
//			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//			insertUser.setCreatedOn(sqlDate);
//			boolean isSuccess = userService.insert(insertUser);
//			return new ResponseEntity<>(insertUser, HttpStatus.OK);
//		}
//	}

	public User profileUser(@RequestBody String json) {
		JSONObject obj = new JSONObject(json);
		String username = obj.getString("username");
		User userProfile = userService.findByUserName(username);
		return userProfile;
	}

	@PostMapping(value = "/user/changepassword")
	public ResponseEntity<Object> changePassWord(@RequestParam String json) {
		JSONObject obj = new JSONObject(json);
		String username = obj.getString("userNamePar");
		User findUserByUserName = userService.findByUserName(username);
		boolean isSuccess = userService.edit(findUserByUserName);
		return new ResponseEntity<Object>(isSuccess, HttpStatus.OK);
	}

	@GetMapping(value = "/user/list-skill")
	public List<Object> listSkillOfUser(@RequestParam int id) {
		return userService.listSkillOfUser(id);
	}

	@PostMapping(value = "user/add/role", produces = "application/json")
	public ResponseEntity<Map<String, String>> addUserRole(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		String userName = obj.getString("userName");
		String roleName = obj.getString("roleName");
		Map<String, String> map = new HashMap<>();
		map.put("userName", userName);
		map.put("roleName", roleName);
		HttpStatus httpStatus = null;
		User users = userService.findByUserName(userName);
		com.cmc.dashboard.model.Role role = roleService.findByName(roleName);
		users.addRole(role);
		boolean isSuccess = userService.edit(users);
		httpStatus = HttpStatus.OK;

		return new ResponseEntity<>(map, httpStatus);
	}

	@PostMapping(value = "/user/role/id")
	public ResponseEntity<Object> getOnePermission(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		int roleId = obj.getInt("roleId");
		return new ResponseEntity<Object>(roleService.findById(roleId), HttpStatus.OK);
	}

	@GetMapping(value = "user/listRole")
	public ResponseEntity<Object> getAllUserRole() {
		return new ResponseEntity<Object>(userService.getAllUserRole(), HttpStatus.OK);
	}

	@PostMapping(value = "user/remove/role", produces = "application/json")
	public ResponseEntity<Map<String, String>> removeUserRole(@RequestParam("json") String json) {
		JSONObject obj = new JSONObject(json);
		String userName = obj.getString("userName");
		String roleName = obj.getString("roleName");
		Map<String, String> map = new HashMap<>();
		map.put("userName", userName);
		map.put("roleName", roleName);
		HttpStatus httpStatus = null;
		User users = userService.findByUserName(userName);
		com.cmc.dashboard.model.Role role = roleService.findByName(roleName);
		users.removeRole(role);
		boolean isSuccess = userService.edit(users);
		httpStatus = HttpStatus.OK;
		return new ResponseEntity<>(map, httpStatus);
	}

	@PostMapping(value = "user/ams/save", produces = "application/json")
	public ResponseEntity<UserInfoDTO> addUserAms(@RequestParam("json") String json) {
		User user = new User();
		JSONObject obj = new JSONObject(json);
//		try {
//		if (userService.findByUserName(obj.getString("userName")) == null) {
//			String email = obj.getString("enail");
//			String fullName = obj.getString("fullName");
//			String token = obj.getString("token");
//			String userName = obj.getString("userName");
//			Group group = groupService.findByGroupName(obj.getString("groupName"));
//			int groupId = 0;
//			if (group != null) {
//				groupId = group.getGroupId();
//			}
//			try {
//				userService.saveAndFlush(email, fullName, "", (byte) 1, userName, String.valueOf(groupId), token);
//				user = userService.findByUserName(obj.getString("userName"));
//				userService.insertRole(user.getUserId(), Constants.Role.MEMBER);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return new ResponseEntity<>(userService.loadInfo(obj.getString("userName")), HttpStatus.OK);
//		} else {
//			user = userService.findByUserName(obj.getString("userName"));
//			int userId = user.getUserId();
//			if (userService.checkIsExistRole(userId) == 0) {
//				userService.insertRole(user.getUserId(), Constants.Role.MEMBER);
//			}
//			String email = obj.getString("enail");
//			String fullName = obj.getString("fullName");
//			String token = obj.getString("token");
//			String userName = obj.getString("userName");
//			Group group = groupService.findByGroupName(obj.getString("groupName"));
//
//			int groupId = 0;
//			if (group != null) {
//				groupId = group.getGroupId();
//			}
//			try {
//				userService.updateUser(email, fullName, "", (byte) 1, userName, String.valueOf(groupId), token);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
			return new ResponseEntity<>(userService.loadInfo(obj.getString("userName")), HttpStatus.OK);
		}
	
	//PNTHANH
	@RequestMapping(value = "/users/ams", method = RequestMethod.POST)
	public ResponseEntity<?> getUserFromAms(@RequestBody String json){
		Gson gson = new Gson();
		TypeToken<UsersAmsDTO[]> token = new TypeToken<UsersAmsDTO[]>() {
		};
		UsersAmsDTO[] usersAmsDTOs = gson.fromJson(json, token.getType());
		return new ResponseEntity<>(userService.getUsersFromAms(usersAmsDTOs), HttpStatus.OK);
	}
	@Scheduled(cron = "0 0 1 * * ?")
//	@GetMapping(value = "api")
	public void updateUserFromAms() {
//		 final String uri =
//		 "https://tms.cmcglobal.com.vn:8088/api/synchronize/GetAllUserForFirstRun?systemCode=6ce4874c-4cf9-49d3-b5f0-9fddc3b5aa0f";
		
		final String uri =
				 "https://ams.cmcglobal.com.vn:8088/api/synchronize/GetAllUserForFirstRun?systemCode=6ce4874c-4cf9-49d3-b5f0-9fddc3b5aa0f";
//		  HttpHeaders headers = new HttpHeaders();
//		  headers.setContentType(MediaType.APPLICATION_JSON);
//		  headers.set("systemCode","63ec924c-2e01-4d08-9fca-90dbf8c3970e");
//		  headers.set("lastDateUpdated", "2019-03-27");
//		  headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		  List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		    interceptors.add(new HttpHeaderInterceptor("ContentType", MediaType.APPLICATION_JSON_VALUE));
		    interceptors.add(new HttpHeaderInterceptor("systemCode", "63ec924c-2e01-4d08-9fca-90dbf8c3970e"));
		    interceptors.add(new HttpHeaderInterceptor("lastDateUpdated", "2019-03-27"));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);
		ResponseEntity<AmsUserDTO[]> result = null;
		try {
			result = ((RestTemplate) restTemplate).getForEntity(uri, AmsUserDTO[].class);
			AmsUserDTO[] array = result.getBody();
		    userService.saveUserAms(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/update/du-statistic", method = RequestMethod.GET)
	public void DuStatistic(@RequestParam("month") int month, @RequestParam("year") int year) throws ParseException {
		LocalDate localDate = LocalDate.of(year, month, 1);
		LocalDate start_date_local = localDate.withDayOfMonth(1);
		LocalDate end_date_local = localDate.with(TemporalAdjusters.lastDayOfMonth());
		
		String start_date = start_date_local.toString();
		String end_date = end_date_local.toString();
		String monthDate = month < 10 ? "0"+month+"-"+year : month+"-"+year; 
		
		deliveryUnitService.GetDUStatistic(start_date, end_date, monthDate);
	}
}
