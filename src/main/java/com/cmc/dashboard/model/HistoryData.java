package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author: LXLinh
 * @Date: Jun 5, 2018
 */
@Entity
@Table(name = "history_data")
@JsonIgnoreProperties(value = {  "updatedOn" }, allowGetters = true)
public class HistoryData  implements Serializable{

	private static final long serialVersionUID = -8378680546379204005L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_data_id")
	private long id;
	
	@Column(name = "table_name", nullable = true)
	private String tableName;
	
	@Column(name = "row_id", nullable = true)
	private Long rowId;
	
	@Column(name = "field_name", nullable = true)
	private String fieldName;
	
	@Column(name = "value_old", nullable = true)
	private String valueOld;
	
	@Column(name = "value_new", nullable = true)
	private String valueNew;
	
	@Column(name = "user_id", nullable = true)
	private Integer userId;
	
	@Column(name = "user_name", nullable = true)
	private String userName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", nullable = true)
	@LastModifiedDate
	private Date updatedOn;

	
	@Column(name = "action", nullable = true)
	private String action;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValueOld() {
		return valueOld;
	}

	public void setValueOld(String valueOld) {
		this.valueOld = valueOld;
	}

	public String getValueNew() {
		return valueNew;
	}

	public void setValueNew(String valueNew) {
		this.valueNew = valueNew;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
