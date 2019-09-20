package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cmc.dashboard.model.Role;
import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.util.MethodUtil;



/**
 * @author USER
 *Modifier: Nvcong
 */
public class UserInfoDTO {

	private int id;
	private String fullName;
	private String userName;
	private String groupName;
	private String token;
	private int groupId;
	private String role;
	private List<Role> roles;
	private List<RolePermission> listRolePermission;
	private List<Skill> listSkill;
	private String img;
	private String currentTime = MethodUtil.getDateNow();

	public String getCurrentTime() {
		return currentTime;
	}

	public UserInfoDTO() {
		super();
	}

	public UserInfoDTO(int id, String fullName, String userName, String groupName, String role, String img) {
		super();
		this.id = id;
		this.userName = userName;
		this.fullName = fullName;
		this.groupName = groupName;
		this.role = role;
		this.img = img;
	}

	public UserInfoDTO(int id, String fullName, String userName, String groupName, int groupId, String role,
			List<Role> roles, List<RolePermission> listRolePermission, List<Skill> listSkill,String img, String currentTime, String token) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.groupName = groupName;
		this.groupId = groupId;
		this.role = role;
		this.roles = roles;
		this.listRolePermission = listRolePermission;
		this.listSkill = listSkill;
		this.img = img;
		this.currentTime = currentTime;
		this.token=token;
	}

	public UserInfoDTO(User user) {
		super();
		this.id = user.getUserId();
		this.fullName = user.getFullName();
		this.userName = user.getUserName();
		this.groupName = user.getGroup() == null ? "" : user.getGroup().getGroupName();
		List<String> listRoleName = new ArrayList<String>();
		Set<Role> setRole = user.getRole();
		for (Role role : setRole) {
			listRoleName.add(role.getRoleName());
		}
		this.role = String.join(" , ", listRoleName);
		this.img = user.getImg();
	}

	public int getId() {
		return id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<RolePermission> getListRolePermission() {
		return listRolePermission;
	}

	public void setListRolePermission(List<RolePermission> listRolePermission) {
		this.listRolePermission = listRolePermission;
	}

	public List<Skill> getListSkill() {
		return listSkill;
	}

	public void setListSkill(List<Skill> listSkill) {
		this.listSkill = listSkill;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}