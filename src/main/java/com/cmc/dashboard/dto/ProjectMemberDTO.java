package com.cmc.dashboard.dto;

public class ProjectMemberDTO {

	private String name;
	private String startDate;
	private String endDate;
	private double effortCalendar;
	private double effortActual;
	private String removeDate;
	private String group;
	private int user_id;
	
	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getRemoveDate() {
		return removeDate;
	}


	public void setRemoveDate(String removeDate) {
		this.removeDate = removeDate;
	}


	public ProjectMemberDTO() {
		super();
	}


	public ProjectMemberDTO(String name, String startDate, String endDate, double effortCalendar, double effortActual, String removeDate, String group, int user_id) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.effortCalendar = effortCalendar;
		this.effortActual = effortActual;
		this.removeDate = removeDate;
		this.group = group;
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


	public double getEffortCalendar() {
		return effortCalendar;
	}

	public void setEffortCalendar(double effortCalendar) {
		this.effortCalendar = effortCalendar;
	}


	public double getEffortActual() {
		return effortActual;
	}


	public void setEffortActual(double effortActual) {
		this.effortActual = effortActual;
	}
	
	
	
}
