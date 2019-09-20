package com.cmc.dashboard.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.AmsUserDTO;
import com.cmc.dashboard.dto.LoginParameterObject;
import com.cmc.dashboard.dto.ProjectUserDTO;
import com.cmc.dashboard.dto.RolePermissionDTO;
import com.cmc.dashboard.dto.UserInfoDTO;
import com.cmc.dashboard.dto.UserJiraDTO;
import com.cmc.dashboard.dto.UserRoleDTO;
import com.cmc.dashboard.dto.UsersAmsDTO;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.util.ResultUtil;

public interface UserService {

	public ResultUtil<User> loginUser(LoginParameterObject obj);

	public List<User> getAllUser();

	public LoginParameterObject getUserById(Integer id);

	public User findByUserName(String id);

	public User createUser(User newUser);

	public User findUserById(Integer userID);

	public Page<User> findByAccount(String account, PageRequest pageRequest, String fullNames, String userNames, String groupId, String statusId);

	public User updateUser(User updateUser);

	public boolean insert(User user);

	public boolean edit(User user);

	public User findByEmail(String email);
	
	public List<Object> listSkillOfUser(int id);
	
	void removeUserSkill(int userId);
	
	public List<RolePermission> listRolePermissionOfUser(int userId);
	
	public UserInfoDTO loadInfo(String username);
	

	List<ProjectUserDTO> getListUserByProjectId(int projectId);

//	List<Object[]> getAllUserRole();
	
	void deleteUserRole(int userId, int roleId);
	
	public void saveAndFlush(String email,String fullName,String img,Byte status,String userName,String groupId,String token);
	
	public User saveAndFlush(User user);
	
	public List<Object[]> searchUser(String userName, int groupId);
	
	public List<Object[]> searchUserByUserName(String userName);
	
	public List<Object[]> searchUserByGroupId(int groupId);
	
	public List<Integer> checkRoleViewListProject(int userId);
	
	public List<UserRoleDTO> getAllUserRole();
	
	public boolean saveUserJira(List<UserJiraDTO> listUser,int projectId);
	
	public void updateUser(String email,String fullName,String img,Byte status,String userName,String groupId,String token);
	
	public void insertRole(int userId,int roleId);
	
	public int checkIsExistRole(int userId);
	//PNTHANH
	public boolean getUsersFromAms(UsersAmsDTO[] usersAmsDTOs);
	
	public Set<String> getListGroupByPm(int userId);
	
	public boolean saveUserAms(AmsUserDTO[] array);
}
