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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: ngocdv
 * @Date: Mar 21, 2018
 */
@Entity
@Table(name = "man_power")
public class ManPower implements Serializable {

	private static final long serialVersionUID = -8809377048778000898L;

	@TableGenerator(name = "Manpower_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "man_power_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Manpower_Gen")
	@Column(name = "man_power_id")
	private int manPowerId;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "from_date")
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "man_month")
	private String manMonth;
	
	@Column(name = "day_off", columnDefinition = "int default 0")
	private int dayOff;
	
	@Column(name = "allocation_value")
	private float allocationValue;

	@Column(name = "user_id")
	private int userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", nullable = false)
	@LastModifiedDate
	private Date updatedOn;
	
	@Column(name = "group_id")
	private int groupId;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getManPowerId() {
		return manPowerId;
	}

	public void setManPowerId(int manPowerId) {
		this.manPowerId = manPowerId;
	}
	

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getManMonth() {
		return manMonth;
	}

	public void setManMonth(String manMonth) {
		this.manMonth = manMonth;
	}

	public int getDayOff() {
		return dayOff;
	}

	public void setDayOff(int dayOff) {
		this.dayOff = dayOff;
	}

	public float getAllocationValue() {
		return allocationValue;
	}

	public void setAllocationValue(float allocationValue) {
		this.allocationValue = allocationValue;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public ManPower(int manPowerId, Date fromDate, Date toDate, float allocationValue, int userId, Date createdOn,
			Date updatedOn) {
		super();
		this.manPowerId = manPowerId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.allocationValue = allocationValue;
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public ManPower() {
		super();
	}

	@Override
	public String toString() {
		return "ManPower [fromDate=" + fromDate + ", toDate=" + toDate + ", allocationValue=" + allocationValue
				+ ", userId=" + userId + "]";
	}

	
}
