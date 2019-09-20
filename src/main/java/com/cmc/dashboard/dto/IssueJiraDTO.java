package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.List;

public class IssueJiraDTO {
	private Long id;
	private String assignee;
	private String created;
	private String endDate;
	private String issueType;
	private String priority;
	private String status;
	private String subject;
	private int estimateTime;
	private String updated;
	private List<WorklogDTO> worklogsS;
	public IssueJiraDTO() {
		super();
	}
	public IssueJiraDTO(long id, String assignee, String created, String endDate, String issueType, String priority,
			String status, String subject, String updated, List<WorklogDTO> worklogsS,int estimateTime) {
		super();
		this.id = id;
		this.assignee = assignee;
		this.created = created;
		this.endDate = endDate;
		this.issueType = issueType;
		this.priority = priority;
		this.status = status;
		this.subject = subject;
		this.updated = updated;
		this.worklogsS = worklogsS;
		this.estimateTime=estimateTime;
	}
	public int getEstimateTime() {
		return estimateTime;
	}
	public void setEstimateTime(int estimateTime) {
		this.estimateTime = estimateTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
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
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public List<WorklogDTO> getWorklogsS() {
		return worklogsS;
	}
	public void setWorklogsS(List<WorklogDTO> worklogsS) {
		this.worklogsS = worklogsS;
	}
	
	
}
