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

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: GiangTM
 * @Date: Feb 27, 2018
 */
@Entity
@Table(name = "project_delivery")
@NamedQuery(name = "ProjectDelivery.findAll", query = "SELECT p FROM ProjectDelivery p")
public class ProjectDelivery implements Serializable {

	private static final long serialVersionUID = 3523383774691930936L;

	@TableGenerator(name = "ProjectDelivery_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_delivery_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ProjectDelivery_Gen")
	@Column(name = "project_delivery_id")
	private int projectDeliveryId;
	
	@Column(name = "project_id")
	private int projectId;
	
	@Column(name="name", length = 5000, nullable=false)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "schedule_date", nullable=false)
	private Date scheduleDate;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "real_date")
	private Date realDate;

	
	@Column(name = "ontime")
	
	private Boolean ontime;
	
	@Column(name = "comment",length = 5000)
	private String comment;

	/**
	 * Constructure
	 */
	public ProjectDelivery() {
		super();
	}
	
	/**
	 * Constructure
	 */

	public ProjectDelivery(int projectDeliveryId, int projectId, String name, Date scheduleDate, Date realDate,
			Boolean ontime, String comment) {
		super();
		this.projectDeliveryId = projectDeliveryId;
		this.projectId = projectId;
		this.name = name;
		this.scheduleDate = scheduleDate;
		this.realDate = realDate;
		this.ontime = ontime;
		this.comment = comment;
	}



	public int getProjectDeliveryId() {
		return projectDeliveryId;
	}

	public void setProjectDeliveryId(int projectDeliveryId) {
		this.projectDeliveryId = projectDeliveryId;
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

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getRealDate() {
		return realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public Boolean isOntime() {
		return ontime;
	}

	public void setOntime(Boolean ontime) {
		this.ontime = ontime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
