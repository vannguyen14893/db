package com.cmc.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "project_role")
public class ProjectRole {
	private static final long serialVersionUID = 9055246179766677871L;

	@TableGenerator(name = "Project_role_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_role_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Project_role_Gen")
	@Column(name = "project_role_id", columnDefinition = "int", nullable = false)
	private int id;
	
	@Column(name="name",columnDefinition="varchar(50)", nullable=false)
	private String name;
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectRole(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ProjectRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
