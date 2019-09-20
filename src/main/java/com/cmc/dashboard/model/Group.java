package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name = "groups")
@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
public class Group implements Serializable {

	private static final long serialVersionUID = 6893208879174379359L;
	@TableGenerator(name = "Group_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "group_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Group_Gen")
	@Id
	@Column(name = "group_id", columnDefinition = "int default 0")
	private int groupId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "group_desc")
	private String groupDesc;

	@Column(name = "group_name")
	private String groupName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "group_status")
	private Boolean status;

	@Column(name = "development_unit")
	private boolean developmentUnit;

	@Column(name = "internal_du")
	private boolean internalDu;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "group")
	private List<User> users;

	@OneToMany(mappedBy = "group")
	private Set<Project> projects;
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name = "parent_id")
	private int parentId;

	public Group() {
	}

	public Group(int groupId, Date createdOn, String groupDesc, String groupName, Date updatedOn, Integer groupRank,
			Boolean status, List<User> users) {
		super();
		this.groupId = groupId;
		this.createdOn = createdOn;
		this.groupDesc = groupDesc;
		this.groupName = groupName;
		this.updatedOn = updatedOn;
		this.users = users;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@JsonIgnore
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isDevelopmentUnit() {
		return developmentUnit;
	}

	public void setDevelopmentUnit(boolean developmentUnit) {
		this.developmentUnit = developmentUnit;
	}

	public boolean isInternalDu() {
		return internalDu;
	}

	public void setInternalDu(boolean internalDu) {
		this.internalDu = internalDu;
	}

}