package com.cmc.dashboard.dto;

public class ResourceProjectDTO {
	private Integer projectId;
	private String projectName;
	private String projectType;
	private String projectCode;
	private String deliveryUnit;
	private int status;
	private String startDate;
	private String endDate;
	private float planningAllocation;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public float getPlanningAllocation() {
		return planningAllocation;
	}

	public void setPlanningAllocation(float planningAllocation) {
		this.planningAllocation = planningAllocation;
	}

	public ResourceProjectDTO() {
		super();
	}

	public ResourceProjectDTO(Integer projectId, String projectName, String projectType, String projectCode,
			String deliveryUnit, int status, String endDate, String startDate) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectCode = projectCode;
		this.deliveryUnit = deliveryUnit;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * get project by month
	 * 
	 * @param projectId
	 * @param projectName
	 * @param projectType
	 * @param deliveryUnit
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param projectCode
	 * @author duyhieu
	 * @return
	 */
	public ResourceProjectDTO(Integer projectId, String projectName, String projectType, String projectCode,
			String deliveryUnit, int status, String endDate, String startDate, float planningAllocation) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectType = projectType;
		this.deliveryUnit = deliveryUnit;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.projectCode = projectCode;
		this.planningAllocation = planningAllocation;
	}

}
