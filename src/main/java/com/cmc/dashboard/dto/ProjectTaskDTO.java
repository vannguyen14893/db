package com.cmc.dashboard.dto;

public class ProjectTaskDTO {
	private int taskId;
	private String status;
	private String subject;
	private String assingee;
	private String startDate;
	private String dueDate;
	private String percentDone;
	private float estimateTime;
	
	
	public ProjectTaskDTO(int taskId, String status, String subject, String assingee, String startDate, String dueDate,
			String percentDone, float estimateTime) {
		super();
		this.taskId = taskId;
		this.status = status;
		this.subject = subject;
		this.assingee = assingee;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.percentDone = percentDone;
		this.estimateTime = estimateTime;
	}
	
	


	public ProjectTaskDTO() {
		super();
	}




	public int getTaskId() {
		return taskId;
	}


	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getAssingee() {
		return assingee;
	}


	public void setAssingee(String assingee) {
		this.assingee = assingee;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public String getPercentDone() {
		return percentDone;
	}


	public void setPercentDone(String percentDone) {
		this.percentDone = percentDone;
	}


	public float getEstimateTime() {
		return estimateTime;
	}


	public void setEstimateTime(float estimateTime) {
		this.estimateTime = estimateTime;
	}
	
	
	
	
}
