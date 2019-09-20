package com.cmc.dashboard.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.AmsUserDTO;
import com.cmc.dashboard.dto.LoginParameterObject;
import com.cmc.dashboard.dto.ProjectUserDTO;
import com.cmc.dashboard.dto.RoleListDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.dto.UserJiraDTO;
import com.cmc.dashboard.dto.UserRoleDTO;
import com.cmc.dashboard.dto.UsersAmsDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.ProjectUser;
import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.repository.ProjectUserRepository;
import com.cmc.dashboard.repository.RolePermissionRepository;
import com.cmc.dashboard.repository.RoleRepository;
import com.cmc.dashboard.repository.SkillRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CalculateClass;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.ResultUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private ProjectUserRepository projectUserRepository;
	
	
	@Autowired
	private GroupService groupService;
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public LoginParameterObject getUserById(Integer id) {
		LoginParameterObject loginObj = new LoginParameterObject();
		User user = userRepository.findOne(id);
		if (user != null) {
			loginObj.setUsername(user.getUserName());
		}
		return loginObj;
	}

	@Override
	public ResultUtil<User> loginUser(LoginParameterObject obj) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByUserName(String id) {
		return userRepository.findByUserName(id);
	}

	@Transactional()
	@Override
	public User createUser(User newUser) {
		newUser.setGroup(groupRepository.findById(Constants.Group.GROUP_MEMBER));
		return userRepository.save(newUser);
	}

	@Transactional(readOnly = true)
	@Override
	public User findUserById(Integer userID) {
		return userRepository.findOne(userID);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<User> findByAccount(String account, PageRequest pageRequest, String fullNames, String userNames,
			String groupId, String statusId) {
		return userRepository.findListUsers(pageRequest, account, fullNames, userNames, groupId, statusId);
	}

	@Override
	public User updateUser(User updateUser) {
		return userRepository.save(updateUser);
	}
    @Transactional
	@Override
	public boolean insert(User user) {
		// TODO Auto-generated method stub
		try {
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean edit(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
		return true;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public List<Object> listSkillOfUser(int id) {
		// TODO Auto-generated method stub
		return userRepository.listSkillOfUser(id);
	}

	@Transactional
	@Override
	public void removeUserSkill(int userId) {
		// TODO Auto-generated method stub
		userRepository.removeUserSkill(userId);
	}

	@Override
	public List<RolePermission> listRolePermissionOfUser(int userId) {
		// TODO Auto-generated method stub
		return userRepository.listRolePermissionOfUser(userId);
	}

	@Override
	public UserInfoDTO loadInfo(String username) {
		// TODO Auto-generated method stub
		UserInfoDTO userInfo = new UserInfoDTO();
		try {
			User findByUserName = userRepository.findByUserName(username);
			if (findByUserName == null) {
				throw new UsernameNotFoundException("User not found exception");
			}
			userInfo.setId(findByUserName.getUserId());
			userInfo.setFullName(findByUserName.getFullName());
			if(findByUserName.getGroup() != null) {
			   userInfo.setGroupName(findByUserName.getGroup().getGroupName());
			   userInfo.setGroupId(findByUserName.getGroup().getGroupId());
			}
			userInfo.setUserName(findByUserName.getUserName());
			userInfo.setImg(findByUserName.getImg());
			userInfo.setToken(findByUserName.getToken());
			List<Role> listRole = new ArrayList<>();
			List<RolePermission> listRolePermission = new ArrayList<>();
			List<Skill> listSkillOfUser = new ArrayList<>();
			try {
				listRole = roleRepository.listRoleOfUser(findByUserName.getUserId());
				listRolePermission = rolePermissionRepository.listRolePermissionOfUser(findByUserName.getUserId());
				listSkillOfUser = skillRepository.listSkillOfUser(findByUserName.getUserId());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			userInfo.setRoles(listRole);
			userInfo.setListRolePermission(listRolePermission);
			userInfo.setListSkill(listSkillOfUser);
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return userInfo;
	}

	@Override
	public List<ProjectUserDTO> getListUserByProjectId(int projectId) {
		// TODO Auto-generated method stub

		Optional<List<Object>> objects = Optional.ofNullable(userRepository.getListUserByProjectId(projectId));
		List<ProjectUserDTO> projectUserDTOs = new ArrayList<ProjectUserDTO>();
		Set<Integer> listId = new HashSet<>();
		if (objects.isPresent()) {
			for (Object object : objects.get()) {
				Object[] obj = (Object[]) object;
				if (!listId.contains(Integer.parseInt(obj[0].toString()))) {
					projectUserDTOs.add(new ProjectUserDTO(Integer.parseInt(obj[0].toString()), obj[1].toString()));
				}
				listId.add(Integer.parseInt(obj[0].toString()));
			}
		}
		return projectUserDTOs;
	}

	

	@Override
	public List<UserRoleDTO> getAllUserRole() {
		// TODO Auto-generated method stub
		List<User> listUser = userRepository.findAll();
		List<UserRoleDTO> listUserRoleDTO=new ArrayList<UserRoleDTO>();
		for(User user : listUser) {
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			Set<Role> setRole=user.getRole();
			Set<RoleListDTO> setRoleListDTO = new HashSet<RoleListDTO>();
			for(Role role : setRole) {
				RoleListDTO roleListDTO= new RoleListDTO();
				roleListDTO.setRoleId(role.getRoleId());
				roleListDTO.setRoleName(role.getRoleName());
				roleListDTO.setDescription(role.getDescription());
				setRoleListDTO.add(roleListDTO);
			}
			userRoleDTO.setUserId(user.getUserId());
			userRoleDTO.setUserName(user.getUserName());
			userRoleDTO.setGroupName(user.getGroup() == null ? "" : user.getGroup().getGroupName());
			userRoleDTO.setListRole(setRoleListDTO);
			userRoleDTO.setFullName(user.getFullName());
			listUserRoleDTO.add(userRoleDTO);
		}	
		return listUserRoleDTO;
	}
//	public List<Object[]> getAllUserRole() {
//		// TODO Auto-generated method stub
//		try {
//			return userRepository.getAllUserRole();
//		} catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//	}

	@Override
	public void deleteUserRole(int userId, int roleId) {
		// TODO Auto-generated method stub
		try {
			userRepository.removeUserRole(userId, roleId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
    @Transactional
	@Override
	public void saveAndFlush(String email,String fullName,String img,Byte status,String userName,String groupId,String token) {
		// TODO Auto-generated method stub
    	try {
		 userRepository.insertUser(email, fullName, img, status, userName, groupId, token);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
	}

	@Override
	public List<Object[]> searchUser(String userName, int groupId) {
		// TODO Auto-generated method stub
		try {
				return userRepository.searchUser(userName, groupId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object[]> searchUserByUserName(String userName) {
		// TODO Auto-generated method stub
		try {
			return userRepository.searchUserByUserName(userName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Object[]> searchUserByGroupId(int groupId) {
		// TODO Auto-generated method stub
		try {
			return userRepository.searchUserByGroupId(groupId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> checkRoleViewListProject(int userId) {
		// TODO Auto-generated method stub
		return userRepository.checkViewListProject(userId);
	}
	
	@Override
	//@Transactional
	 public boolean saveUserJira(List<UserJiraDTO> listUser,int projectId) {
		  CalculateClass cal = new CalculateClass();
		  try {
			  
		  List<Integer> listUserNew= new ArrayList<Integer>();
		  List<Integer> listUserOld = userRepository.listUserIdByPojectId(projectId);
		  for(UserJiraDTO user : listUser) {
			  User u = null;
			  if("administrator".equals(user.getUserName()))  u = userRepository.findByUserName("admin");
			  else u = userRepository.findByUserName(user.getUserName());
//				if(u==null) {
//					u = new User();
//					u.setUserName(user.getUserName());
//					u.setEmail(user.getUserName()+"@cmc.com.vn");
//					u.setHashedPassword("12345678");
//					u.setIsAdmin((byte) 0);
//					userRepository.save(u);
//					listUserNew.add( userRepository.findByUserName(user.getUserName()).getUserId());
//				}
//				else
				if(u!=null)
					listUserNew.add(u.getUserId());
			}
		  List<Integer> listDelete = cal.differentNumber(listUserOld, listUserNew);
		  List<Integer> listUpdate = cal.sameNumber(listUserOld, listUserNew);
		  List<Integer> listInsert = cal.differentNumber(listUserNew, listUserOld);
		  ProjectUser pu =null;
		  for(Integer userId : listDelete) {
			 // projectUserRepository.updateRemovedUser(projectId, userId,1);
			  pu = projectUserRepository.findByProjectAndUser(projectId, userId);
			  pu.setRemove(1);
			  projectUserRepository.save(pu);
		  }
		  for(Integer userId : listInsert) {
			  int check = userRepository.checkUserInProject(projectId, userId);
			  if(check==0) {
			    pu = new ProjectUser();

		        pu.setProject_id(projectId);
		        pu.setUser_id(userId);
		        pu.setDisplay(0);
		        pu.setRemove(0);
		        projectUserRepository.save(pu);
			  }
		  }
		  for(Integer userId : listUpdate) {
			 // projectUserRepository.updateRemovedUser(projectId, userId,0);
			  pu = projectUserRepository.findByProjectAndUser(projectId, userId);
			  pu.setRemove(0);
			  projectUserRepository.save(pu);
		  }
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		return true;
		 
	  }

	@Override
	public User saveAndFlush(User user) {
		// TODO Auto-generated method stub
		return userRepository.saveAndFlush(user);
	}
    @Transactional
	@Override
	public void updateUser(String email, String fullName, String img, Byte status, String userName, String groupId,
			String token) {
		// TODO Auto-generated method stub
		userRepository.updateUser(email, fullName, img, groupId, token, userName);
		
	}
    @Transactional
	@Override
	public void insertRole(int userId, int roleId) {
		// TODO Auto-generated method stub
		userRepository.insertUserRole(userId, roleId);
	}

	@Override
	public int checkIsExistRole(int userId) {
		// TODO Auto-generated method stub
		return userRepository.checkRoleIsExist(userId);
	}

	@Override

	public boolean getUsersFromAms(UsersAmsDTO[] usersAmsDTOs) {
		if(usersAmsDTOs != null) {
			for (UsersAmsDTO usersAmsDTO : usersAmsDTOs) {
				int userId = usersAmsDTO.getUserId();
				Optional<User> user = Optional.ofNullable(userRepository.findOne(userId));
				if(!user.isPresent()) {
					User userInsert = new User();
					userInsert.setUserId(usersAmsDTO.getUserId());
					userInsert.setEmail(usersAmsDTO.getEmail());
					userInsert.setFullName(usersAmsDTO.getFullName());
					userInsert.setImg(com.cmc.dashboard.util.Constants.User.IMG);
					userInsert.setIsSalary(usersAmsDTO.getIsSalary());
					userInsert.setStatus(usersAmsDTO.getStatus());
					userInsert.setUserName(usersAmsDTO.getUserName());
					userInsert.setStartDate(usersAmsDTO.getStartDate());
					userInsert.setEndDate(usersAmsDTO.getEndDate());
					userRepository.save(userInsert);
				}
			}
			return true;
		}
		return false;
	}

	public Set<String> getListGroupByPm(int userId) {
		// TODO Auto-generated method stub
		 return userRepository.getListUserInProject(userId);

	}

	@Override
	public boolean saveUserAms(AmsUserDTO[] array) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		String type="update";
		try {
		for(AmsUserDTO a : array) {
			System.out.println(a.getDepartmentName());
			Group gr= groupRepository.findByName(a.getDepartmentName());
			if(gr == null) {
				Group newGroup = new Group();
				newGroup.setGroupName(a.getDepartmentName());
				try {
				groupRepository.save(newGroup);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			User u = userRepository.findByEmail(a.getEmail());
			if(u==null) {
				User us = new User();
				Group group= groupRepository.findByName(a.getDepartmentName());
				us.setEmail(a.getEmail());
				us.setFullName(a.getFullName());
				us.setUserName(a.getUserName());
				us.setStatus((byte) (a.isStatus()==true?1:0));
				us.setGroup(group);
				us.setLevel(0);
				try {
					us.setCreatedOn(formatter.parse(a.getCreatedDate()));
					if(a.getEndDate()!=null)
					us.setEndDate(formatter.parse(a.getEndDate()));
					if(a.getStartDate()!=null)
					us.setStartDate(formatter.parse(a.getStartDate()));
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				type="insert";
				u=userRepository.save(us);
				
			}
			else {
				String oldTime = "";
				if(u.getUpdatedOn()!=null)
					oldTime = formatter.format(u.getUpdatedOn());
				if(u.getUpdatedOn()==null||oldTime.equals(a.getUpdatedDate())==false) {
					Group group= groupRepository.findByName(a.getDepartmentName());
					u.setEmail(a.getEmail());
					u.setFullName(a.getFullName());
					u.setStatus((byte) (a.isStatus()==true?1:0));
					u.setGroup(group);
					try {
						if(a.getUpdatedDate()!=null)
						u.setUpdatedOn(formatter.parse(a.getUpdatedDate()));
						if(a.getEndDate()!=null)
						u.setEndDate(formatter.parse(a.getEndDate()));
						if(a.getStartDate()!=null)
						u.setStartDate(formatter.parse(a.getStartDate()));
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					userRepository.save(u);
				}
			}
			groupService.saveHistoryGroup(u.getUserId(), a.getListHistoryUserGroup(),type);
		}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
