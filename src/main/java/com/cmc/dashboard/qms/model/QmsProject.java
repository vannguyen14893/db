package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "redmine_db.projects")
public class QmsProject implements Serializable {

	private static final long serialVersionUID = 7467978391057935977L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String projectName;

	@Column(name = "status")
	private int status;

	public QmsProject(int id, String projectName) {
		super();
		this.id = id;
		this.projectName = projectName;
	}

	public QmsProject(int id, String projectName, int status) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.status = status;
	}

	public QmsProject() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
