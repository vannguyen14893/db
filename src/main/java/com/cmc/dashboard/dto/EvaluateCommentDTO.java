package com.cmc.dashboard.dto;

import java.util.Date;

public class EvaluateCommentDTO {
	private int id;
	private String comment;
	private int type;
	private int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public EvaluateCommentDTO(int id, String comment, int type, String createdDate, String updatedDate, String userName,int userId) {
		super();
		this.id = id;
		this.comment = comment;
		this.type = type;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.userName = userName;
		this.userId= userId;
	}
	private String createdDate;
	private String updatedDate;
	private String  userName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
