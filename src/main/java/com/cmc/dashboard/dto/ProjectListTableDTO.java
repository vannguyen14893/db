package com.cmc.dashboard.dto;

import java.util.List;
import java.util.Set;

public class ProjectListTableDTO {
	  private List<ProjectTableDTO> listProject;
	     private Set<String> PM;
	     private Set<String> DU;
	     private Set<String> typeProject;
	     private long totalElements;
	     
		public ProjectListTableDTO(List<ProjectTableDTO> listProject, Set<String> pM, Set<String> dU,
				Set<String> typeProject, long totalElements) {
			super();
			this.listProject = listProject;
			PM = pM;
			DU = dU;
			this.typeProject = typeProject;
			this.totalElements = totalElements;
		}
		public List<ProjectTableDTO> getListProject() {
			return listProject;
		}
		public void setListProject(List<ProjectTableDTO> listProject) {
			this.listProject = listProject;
		}
		public Set<String> getPM() {
			return PM;
		}
		public void setPM(Set<String> pM) {
			PM = pM;
		}
		public Set<String> getDU() {
			return DU;
		}
		public void setDU(Set<String> dU) {
			DU = dU;
		}
		public Set<String> getTypeProject() {
			return typeProject;
		}
		public void setTypeProject(Set<String> typeProject) {
			this.typeProject = typeProject;
		}
		public long getTotalElements() {
			return totalElements;
		}
		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}
}
