package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the permissions database table.
 * 
 */
@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

	private static final long serialVersionUID = -2196096290553983312L;
	@Id
	@Column(name = "permission_id")
	private int permissionId;

	@Column(name = "permission_name")
	private String permissionName;
	
	@Column(name = "permisison_parent")
	private int permisison_parent;
	
	@Column(name = "permisison_depth")
	private int permisison_depth;

	@Column(name = "permission_desc")
	private String permissionDesc;

	@Column(name = "permission_url")
	private String permission_url;
	
	// bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy = "permission")
	private List<RolePermission> rolePermissions;

	@JsonIgnore
	@ManyToMany(mappedBy="permissions")
	private Set<Role> role;
	
	public Permission() {
	}

	

	public Permission(int permissionId, String permissionName, int permisison_parent, int permisison_depth,
			String permissionDesc, String permission_url, List<RolePermission> rolePermissions, Set<Role> role) {
		super();
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.permisison_parent = permisison_parent;
		this.permisison_depth = permisison_depth;
		this.permissionDesc = permissionDesc;
		this.permission_url = permission_url;
		this.rolePermissions = rolePermissions;
		this.role = role;
	}


	public int getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}	
	
	public int getPermisison_parent() {
		return permisison_parent;
	}


	public void setPermisison_parent(int permisison_parent) {
		this.permisison_parent = permisison_parent;
	}



	public int getPermisison_depth() {
		return permisison_depth;
	}



	public void setPermisison_depth(int permisison_depth) {
		this.permisison_depth = permisison_depth;
	}



	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	@JsonIgnore
	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
	public void addRole(Role role) {
		this.role.add(role);
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getPermission_url() {
		return permission_url;
	}

	public void setPermission_url(String permission_url) {
		this.permission_url = permission_url;
	}
	
}