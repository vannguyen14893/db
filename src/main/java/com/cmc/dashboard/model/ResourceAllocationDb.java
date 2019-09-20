package com.cmc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity
@Subselect(value = "")
public class ResourceAllocationDb implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private int user_id;
	private int project_id;
	private float allocation;
	private float planAllocation;
	
	public ResourceAllocationDb() {
		super();
	}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int userId) {
		this.user_id = userId;
	}

	public int getProjectId() {
		return project_id;
	}

	public void setProjectId(int projectId) {
		this.project_id = projectId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
