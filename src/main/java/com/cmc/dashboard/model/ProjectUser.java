package com.cmc.dashboard.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "project_user")
public class ProjectUser implements Serializable{

	private static final long serialVersionUID = 9055246179766677871L;

	@TableGenerator(name = "Project_User_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_user_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Project_User_Gen")
	@Column(name = "project_user_id", columnDefinition = "int", nullable = false)
	private int id;
	
	@Column(name="user_id",columnDefinition = "int", nullable = false)
	private int user_id;
	@Column(name="project_id")
	private int project_id;
	@Column(name="project_role_id")
	private int project_role_id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name="effort")
	private int effort;
	public int getId() {
		return id;
	}
	@Column(name="skill_id")
	private int skill_id;
	
	@Column(name="remove")
	private int remove;
	public int getRemove() {
		return remove;
	}
	public void setRemove(int remove) {
		this.remove = remove;
	}
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
	public Date getRemoveAt() {
		return removeAt;
	}
	public void setRemoveAt(Date removeAt) {
		this.removeAt = removeAt;
	}
	@Column(name="display")
	private int display;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "remove_at")
	private Date removeAt;
	public int getSkill_id() {
		return skill_id;
	}
	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public int getProject_role_id() {
		return project_role_id;
	}
	public void setProject_role_id(int project_role_id) {
		this.project_role_id = project_role_id;
	}
	public int getEffort() {
		return effort;
	}
	public void setEffort(int effort) {
		this.effort = effort;
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
	
	
}
