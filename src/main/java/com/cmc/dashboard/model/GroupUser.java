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

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "group_user")
public class GroupUser implements Serializable {

	public GroupUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GroupUser(int groupUserId, int userId, Date startDate, Date endDate, int groupId, Date updateAt) {
		super();
		this.groupUserId = groupUserId;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.groupId = groupId;
		this.updateAt = updateAt;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1902066644085203405L;
	@Id
	@TableGenerator(name = "GroupUser_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "groupUserId", allocationSize = 1)
	//@GeneratedValue(strategy = GenerationType.TABLE, generator = "GroupUser_Gen")
	@Column(name = "group_user_id", unique = true, nullable = false)
	private int groupUserId;
	@Column(name = "user_id", nullable = false)
	private int userId;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@Column(name = "group_id", nullable = false)
	private int groupId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_at", nullable = true)
	private Date updateAt;
	public int getGroupUserId() {
		return groupUserId;
	}
	public void setGroupUserId(int groupUserId) {
		this.groupUserId = groupUserId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
}
