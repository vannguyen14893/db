package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "project_task")
public class ProjectTask implements Serializable{
	private static final long serialVersionUID = 9055246179766677871L;

	@TableGenerator(name = "Project_Task_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_task_id", allocationSize = 1)
	@Id
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "Project_Task_Gen")
	@Column(name = "project_task_id", nullable = false)
	private int id;
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name="subject")
	private String subject;
	
	@Column(name="project_id", columnDefinition = "int(10) default 0")
	private Integer projectId;
	
	@Column(name="assignee_id", columnDefinition = "int(10) default 0")
	private Integer assigneeId ;
	
	@Column(name="percent_done", columnDefinition = "int(10) default 0")
	private Integer percentDone;
	
	@Column(name="estimate_time")
	private Integer estimateTime ;
	@Column(name="issue_type", columnDefinition="varchar(20)")
	private String issueType;
	
	@Column(name="priority")
	private String priority;
	
	//seconds
	@Column(name="logworktime", columnDefinition = "int(10) default 0")
	private Integer logworktime;
	
	
	
	@Column(name="status")
	private String status ;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date startDate;
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_at")
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getPercentDone() {
		return percentDone;
	}

	public void setPercentDone(Integer percentDone) {
		this.percentDone = percentDone;
	}

	public Integer getEstimateTime() {
		return estimateTime;
	}

	public void setEstimateTime(Integer estimateTime) {
		this.estimateTime = estimateTime;
	}

	public Integer getLogworktime() {
		return logworktime;
	}

	public void setLogworktime(Integer logworktime) {
		this.logworktime = logworktime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	
	
}
