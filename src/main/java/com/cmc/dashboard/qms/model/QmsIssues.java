/**
 * dashboard-phase2-backend- - com.cmc.dashboard.qms.model
 */
package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: GiangTM
 * @Date: Feb 26, 2018
 */
@Entity
@Table(name = "redmine_db.issues")
public class QmsIssues implements Serializable {
	
	private static final long serialVersionUID = 2409787267295981330L;
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "tracker_id")
	private int trackerId;

	/**
	 * Constructure
	 */
	public QmsIssues() {
		super();
	}

	/**
	 * Constructure
	 */
	public QmsIssues(int id, int projectId, int trackerId) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.trackerId = trackerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTrackerId() {
		return this.trackerId;
	}

	public void setTrackerId(int trackerId) {
		this.trackerId = trackerId;
	}

}
