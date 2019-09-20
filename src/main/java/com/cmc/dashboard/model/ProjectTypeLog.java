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

@Entity
@Table(name = "project_type_log")
public class ProjectTypeLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1747626558095344436L;
	@Id
	@TableGenerator(name = "ProjectTypeLog_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_type_log_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ProjectTypeLog_Gen")
	@Column(name = "project_type_log_id", unique = true, nullable = false)
	private int projectTypeLogId;
	
	@Column(name = "project_id", nullable = false)
	private Integer projectId;
	
	@Column(name = "project_type_id", nullable = false)
	private Integer projectTypeId;	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 10)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", length = 10)
	private Date endDate;
	
	@Column(name = "editor_id", nullable = false)
	private Integer editorId;

	public ProjectTypeLog(Integer projectId, Integer projectTypeId, Date startDate, Date endDate,
			Integer editorId) {
		super();
		this.projectId = projectId;
		this.projectTypeId = projectTypeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.editorId = editorId;
	}
	public ProjectTypeLog()
	{
		
	}
	public int getProjectTypeLogId() {
		return projectTypeLogId;
	}

	public void setProjectTypeLogId(int projectTypeLogId) {
		this.projectTypeLogId = projectTypeLogId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(Integer projectTypeId) {
		this.projectTypeId = projectTypeId;
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

	public Integer getEditorId() {
		return editorId;
	}

	public void setEditorId(Integer editorId) {
		this.editorId = editorId;
	}
}
