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
@Table(name = "history_sync_data")
public class HistorySyncData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2817477324494282134L;

	/**
	 * 
	 */

	public HistorySyncData(String type, String comment, int status, Date createdOn) {
		super();
		this.type = type;
		this.comment = comment;
		this.status = status;
		this.createdOn = createdOn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Id
	@TableGenerator(name = "History_sync_data_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "history_sync_data_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "History_sync_data_Gen")
	@Column(name = "history_sync_data_id", columnDefinition = "int default 0")
	private int historySyncDataId;
	
	public int getHistorySyncDataId() {
		return historySyncDataId;
	}

	public void setHistorySyncDataId(int historySyncDataId) {
		this.historySyncDataId = historySyncDataId;
	}

	@Column(name = "type")
	private String type;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "status", columnDefinition = "int default 0")
	private int status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;
}
