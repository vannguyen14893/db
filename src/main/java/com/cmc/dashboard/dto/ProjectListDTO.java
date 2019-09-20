package com.cmc.dashboard.dto;

import java.util.List;
import java.util.Set;

import com.cmc.dashboard.model.ProjectDTOTemp;

public class ProjectListDTO {
     private List<ProjectDTOTemp> projectQms;
     private Set<String> PM;
     private List<String> DU;
     private List<String> typeProject;
     private long totalElements;
	public List<ProjectDTOTemp> getProjectQms() {
		return projectQms;
	}
	public void setProjectQms(List<ProjectDTOTemp> projectQms) {
		this.projectQms = projectQms;
	}
	public Set<String> getPM() {
		return PM;
	}
	public void setPM(Set<String> pM) {
		PM = pM;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	
	public List<String> getDU() {
		return DU;
	}
	public void setDU(List<String> dU) {
		DU = dU;
	}	
	
	public List<String> getTypeProject() {
		return typeProject;
	}
	public void setTypeProject(List<String> typeProject) {
		this.typeProject = typeProject;
	}
	
	
	public ProjectListDTO(List<ProjectDTOTemp> projectQms, Set<String> pM, List<String> dU, List<String> typeProject,
			long totalElements) {
		super();
		this.projectQms = projectQms;
		PM = pM;
		DU = dU;
		this.typeProject = typeProject;
		this.totalElements = totalElements;
	}
	public ProjectListDTO() {
		super();
	}
     
	
     
     
}
