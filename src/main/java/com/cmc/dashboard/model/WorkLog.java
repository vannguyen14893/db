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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name ="worklog")
public class WorkLog implements Serializable {
	private static final long serialVersionUID = 9055246179766677871L;

	@TableGenerator(name = "Work_Log_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "id", allocationSize = 1)
	@Id
	@Column(name="id")
	//@GeneratedValue(strategy = GenerationType.TABLE,generator = "Work_Log_Gen")
    private Integer id;
	@Column(name ="project_task_id")
	private Integer projectTaskId;
	@Column(name="user_id")
	private Integer user_id;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_at")
	private Date create_at;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="update_at")
	private Date update_at;
	public Date getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(Date update_at) {
		this.update_at = update_at;
	}
	@Column(name="logWorkTime")
	private int logWorkTime;
	public WorkLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkLog(Integer id, Integer project_task_id, Integer user_id, Date create_at, int logWorkTime,Date update_at) {
		super();
		this.id = id;
		this.projectTaskId = project_task_id;
		this.user_id = user_id;
		this.create_at = create_at;
		this.logWorkTime = logWorkTime;
		this.update_at=update_at;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProject_task_id() {
		return projectTaskId;
	}
	public void setProject_task_id(Integer project_task_id) {
		this.projectTaskId = project_task_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	public int getLogWorkTime() {
		return logWorkTime;
	}
	public void setLogWorkTime(int logWorkTime) {
		this.logWorkTime = logWorkTime;
	} 
	
}
