/**
 * dashboard-phase2-backend- - com.cmc.dashboard.model
 */
package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: GiangTM
 * @Date: Feb 27, 2018
 */
@Entity
@Table(name = "project_pcv_rate")
@NamedQuery(name = "ProjectPcvRate.findAll", query = "SELECT p FROM ProjectPcvRate p")
public class ProjectPcvRate implements Serializable {

	private static final long serialVersionUID = 2844341886580828080L;

	@TableGenerator(name = "ProjectPcvRate_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_pcv_rate_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProjectPcvRate_Gen")
	@Column(name = "project_pcv_rate_id")
	private int projectPcvRateId;

	@Column(name = "project_id")
	@NotNull
	private int projectId;

	@Column(name = "name")
	@NotNull
	@Length(max=50)
	private String name;

	@Column(name = "start_time")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@Column(name = "end_time")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@Column(name = "score")
	@NotNull
	@Min(value=0)
	@Max(value=100)
	private float score;
	
	@Column(name = "accumulated")
	@NotNull
	@Min(value=0)
	@Max(value=100)
	private float accumulated;

	@Column(name = "comment")
	@Length(max=500)
	private String comment;

	/**
	 * Constructure
	 */
	public ProjectPcvRate() {
		super();
	}


	public ProjectPcvRate(int projectPcvRateId, int projectId, String name, Date startTime, Date endTime, float score, float accumulated,
			String comment) {
		super();
		this.projectPcvRateId = projectPcvRateId;
		this.projectId = projectId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.score = score;
		this.accumulated = accumulated;
		this.comment = comment;
	}

	public int getProjectPcvRateId() {
		return projectPcvRateId;
	}

	public void setProjectPcvRateId(int projectPcvRateId) {
		this.projectPcvRateId = projectPcvRateId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public float getAccumulated() {
		return accumulated;
	}

	public void setAccumulated(float accumulated) {
		this.accumulated = accumulated;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
