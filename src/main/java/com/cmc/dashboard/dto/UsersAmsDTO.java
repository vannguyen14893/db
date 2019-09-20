package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.util.Date;

public class UsersAmsDTO implements Serializable {
	private int userId;
	private String email;
	private String fullName;
	private String img;
	private byte isSalary;
	private byte status;
	private String userName;
	private Date startDate;
	private Date endDate;
	private int groupId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIng() {
		return img;
	}
	public void setIng(String ing) {
		this.img = ing;
	}
	public byte getIsSalary() {
		return isSalary;
	}
	public void setIsSalary(byte isSalary) {
		this.isSalary = isSalary;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public UsersAmsDTO() {
		super();
	}
	public UsersAmsDTO(int userId, String email, String fullName, String img, byte isSalary, byte status,
			String userName, Date startDate, Date endDate, int groupId) {
		super();
		this.userId = userId;
		this.email = email;
		this.fullName = fullName;
		this.img = img;
		this.isSalary = isSalary;
		this.status = status;
		this.userName = userName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.groupId = groupId;
	}
	
	
}
