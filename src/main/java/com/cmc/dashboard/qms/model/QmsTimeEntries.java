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
@Table(name = "redmine_db.time_entries")
public class QmsTimeEntries implements Serializable {
	
	private static final long serialVersionUID = -3157992040840668673L;
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "hours")
	private float hours;

	/**
	 * Constructure
	 */
	public QmsTimeEntries() {
		super();
	}

	/**
	 * Constructure
	 */
	public QmsTimeEntries(int id, int projectId, float hours) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.hours = hours;
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

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

}
