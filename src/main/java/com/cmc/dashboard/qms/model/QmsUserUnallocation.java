package com.cmc.dashboard.qms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;

@Entity
@Subselect(value = "")
public class QmsUserUnallocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private String resourceName;
	private String duName;

	public QmsUserUnallocation() {
		super();
	}

	public QmsUserUnallocation(int id, String resourceName, String duName) {
		super();
		this.id = id;
		this.resourceName = resourceName;
		this.duName = duName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDuName() {
		return duName;
	}

	public void setDuName(String duName) {
		this.duName = duName;
	}

}
