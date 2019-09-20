package com.cmc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the role_permission database table.
 * 
 */
@Entity
@Table(name = "role_permission")
@NamedQuery(name = "RolePermission.findAll", query = "SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {

	private static final long serialVersionUID = 1236146391377581201L;

	@TableGenerator(name = "RolePermission_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "role_permission_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "RolePermission_Gen")
	@Column(name = "role_permission_id")
	private int rolePermissionId;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	// bi-directional many-to-one association to Permission
	@ManyToOne
	@JoinColumn(name = "permission_id")
	private Permission permission;

	@Column(name = "enable", columnDefinition="int default 1")
	private int enable;

	public RolePermission() {
	}

	public int getId() {
		return this.rolePermissionId;
	}

	public void setId(int id) {
		this.rolePermissionId = id;
	}

	public int getEnable() {
		return this.enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@JsonIgnore
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RolePermission(int id, int enable, Permission permission, Role role) {
		super();
		this.rolePermissionId = id;
		this.enable = enable;
		this.permission = permission;
		this.role = role;
	}

	public int getRolePermissionId() {
		return rolePermissionId;
	}

	

}