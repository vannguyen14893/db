package com.cmc.dashboard.dto;

import java.util.List;

public class ProjectAllocationDTO {
	private int projectId;
	private String duPic;
	private String projectName;
	private List<DuAllocation> duAllocation;
	public ProjectAllocationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectAllocationDTO(int projectId, String duPic, String projectName, List<DuAllocation> duAllocation) {
		super();
		this.projectId = projectId;
		this.duPic = duPic;
		this.projectName = projectName;
		this.duAllocation = duAllocation;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getDuPic() {
		return duPic;
	}
	public void setDuPic(String duPic) {
		this.duPic = duPic;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<DuAllocation> getDuAllocation() {
		return duAllocation;
	}
	public void setDuAllocation(List<DuAllocation> duAllocation) {
		this.duAllocation = duAllocation;
	}
	
	
	

}
