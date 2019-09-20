package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {

	private static final long serialVersionUID = 1180166962575246146L;

	@TableGenerator(name = "Role_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "role_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Role_Gen")
	@Column(name = "role_id")
	private int roleId;

	@Column(name = "role_name")
	private String roleName;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE },mappedBy="role")
	private Set<User> user;

	@Column(name = "description")
	private String description;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name="role_permission", joinColumns=@JoinColumn(name="role_Id")
	, inverseJoinColumns = @JoinColumn(name="permission_Id"))
	private Set<Permission> permissions;
	
	// bi-directional many-to-one association to RolePermission
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "role")
	private List<RolePermission> rolePermissions;

	public Role() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public Role(int roleId, String roleName, List<Group> groups, List<RolePermission> rolePermissions) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.rolePermissions = rolePermissions;
	}
	public void addUsers(User user) {
		this.user.add(user);
	}
	public Set<User> getUser() {
		return user;
	}
	public void addPermission(Permission permission) {
		this.permissions.add(permission);
		permission.addRole(this);	
	}
	public void removePermission(Permission permission) {
		this.permissions.remove(permission);
		permission.getRole().remove(this);
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}