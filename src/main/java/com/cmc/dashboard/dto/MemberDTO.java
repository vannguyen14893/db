package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.Set;

import com.cmc.dashboard.model.Skill;

public class MemberDTO {
private int userId;
private  String fullName;
private  String userName;
private int display;
private int remove;
private int status;
private String email;
private Date startDate;
private int projectRoleId;
private int skillId;
private String DU;
public MemberDTO(int userId,String fullName, String email, String dU,String userName,int display,int remove, int status) {
	super();
	this.fullName = fullName;
	this.email = email;
	this.DU = dU;
	this.display=display;
	this.remove=remove;
	this.status=status;
	this.userName=userName;
	this.userId=userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getDisplay() {
	return display;
}
public void setDisplay(int display) {
	this.display = display;
}
public int getRemove() {
	return remove;
}
public void setRemove(int remove) {
	this.remove = remove;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getDU() {
	return DU;
}
public void setDU(String dU) {
	DU = dU;
}
private Set<Skill> skills;
public int getSkillId() {
	return skillId;
}
public void setSkillId(int skillId) {
	this.skillId = skillId;
}
public int getProjectRoleId() {
	return projectRoleId;
}
public void setProjectRoleId(int projectRoleId) {
	this.projectRoleId = projectRoleId;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public MemberDTO(int userId, String fullName, String email, Date startDate,int projectRoleId,int skillId,Set<Skill> skills) {
	super();
	this.userId = userId;
	this.fullName = fullName;
	this.email = email;
	if(startDate!=null)
	this.startDate =startDate;
	this.projectRoleId=projectRoleId;
	this.skillId=skillId;
	this.skills=skills;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Set<Skill> getSkills() {
	return skills;
}
public void setSkills(Set<Skill> skills) {
	this.skills = skills;
}

}
