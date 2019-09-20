package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity()
@Subselect(value = "")
public class ResourceAllocationQms implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private int user_id;
	private int project_id;
	private String name;
	private int status;
	private String duPic;
	private String sdateP;
	private String edateP;
	private String projectCode;
	private String projectType;
	private String role;
	private String du;
	private String username;
	private float allocation;
	private float planAllocation;
	
	public ResourceAllocationQms() {
		super();
	}

	public ResourceAllocationQms(int user_id, int project_id, String name, int status, String duPic, String sdateP,
			String edateP, String projectCode, String projectType, String role, String du, String username,
			float allocation, float planAllocation) {
		super();
		this.user_id = user_id;
		this.project_id = project_id;
		this.name = name;
		this.status = status;
		this.duPic = duPic;
		this.sdateP = sdateP;
		this.edateP = edateP;
		this.projectCode = projectCode;
		this.projectType = projectType;
		this.role = role;
		this.du = du;
		this.username = username;
		this.allocation = allocation;
		this.planAllocation = planAllocation;
	}



	public float getAllocation() {
		return allocation;
	}


	public void setAllocation(float allocation) {
		this.allocation = allocation;
	}


	public float getPlanAllocation() {
		return planAllocation;
	}


	public void setPlanAllocation(float planAllocation) {
		this.planAllocation = planAllocation;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getProject_id() {
		return project_id;
	}


	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getDuPic() {
		return duPic;
	}

	public void setDuPic(String duPic) {
		this.duPic = duPic;
	}

	public String getSdateP() {
		return sdateP;
	}

	public void setSdateP(String sdateP) {
		this.sdateP = sdateP;
	}

	public String getEdateP() {
		return edateP;
	}

	public void setEdateP(String edateP) {
		this.edateP = edateP;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDu() {
		return du;
	}

	public void setDu(String du) {
		this.du = du;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
