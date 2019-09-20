package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.Project;
import com.cmc.dashboard.model.ProjectRole;
import com.cmc.dashboard.model.ProjectType;
import com.cmc.dashboard.model.Skill;
import com.cmc.dashboard.model.User;

public class EditProjectDTO {
private List<Group> listGroup;
private List<ProjectType> listProjectType;
private List<Skill> listSkill;
private Project project;
private List<MemberDTO> listMember;
private List<ProjectRole> listProjectRole;
private List<MemberDTO> listUserAll;
private List<MemberDTO> listPM;
private ProjectTargetDTO target;
private List<ProjectTypeLogDTO> listProjectTypeLog;

public List<ProjectTypeLogDTO> getListProjectTypeLog() {
	return listProjectTypeLog;
}
public void setListProjectTypeLog(List<ProjectTypeLogDTO> listProjectTypeLog) {
	this.listProjectTypeLog = listProjectTypeLog;
}
public List<MemberDTO> getListUserAll() {
	return listUserAll;
}
public void setListUserAll(List<MemberDTO> listUserAll) {
	this.listUserAll = listUserAll;
}
public EditProjectDTO(List<Group> listGroup, List<ProjectType> listProjectType, List<Skill> listSkill, Project project,List<ProjectRole> listProjectRole,
		List<MemberDTO> listMember,List<MemberDTO> listUserAll,List<MemberDTO> listPM, List<ProjectTypeLogDTO> listProjectTypeLog) {
	super();
	this.listGroup = listGroup;
	this.listProjectType = listProjectType;
	this.listSkill = listSkill;
	this.project = project;
	this.listMember = listMember;
	this.listProjectRole=listProjectRole;
	this.listUserAll=listUserAll;
	this.listPM=listPM;
	this.target=new ProjectTargetDTO(project.getPcvRate(), project.getBugRate(), project.getLeakageRate(), project.getEffortDeviation(), project.getProductivity(), project.getBillableRate(), project.getTimeliness(), project.getCss());
	this.listProjectTypeLog=listProjectTypeLog;
}
public ProjectTargetDTO getTarget() {
	return target;
}
public void setTarget(ProjectTargetDTO target) {
	this.target = target;
}
public List<MemberDTO> getListPM() {
	return listPM;
}
public void setListPM(List<MemberDTO> listPM) {
	this.listPM = listPM;
}
public List<MemberDTO> getListMember() {
	return listMember;
}
public void setListMember(List<MemberDTO> listMember) {
	this.listMember = listMember;
}
public List<Group> getListGroup() {
	return listGroup;
}
public void setListGroup(List<Group> listGroup) {
	this.listGroup = listGroup;
}
public List<ProjectType> getListProjectType() {
	return listProjectType;
}
public void setListProjectType(List<ProjectType> listProjectType) {
	this.listProjectType = listProjectType;
}
public List<Skill> getListSkill() {
	return listSkill;
}
public void setListSkill(List<Skill> listSkill) {
	this.listSkill = listSkill;
}
public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}
public List<ProjectRole> getListProjectRole() {
	return listProjectRole;
}
public void setListProjectRole(List<ProjectRole> listProjectRole) {
	this.listProjectRole = listProjectRole;
}
}
