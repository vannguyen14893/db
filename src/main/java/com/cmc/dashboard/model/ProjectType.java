package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "project_type")
public class ProjectType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9055246179766677871L;

	@TableGenerator(name = "Project_type_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_type_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Project_type_Gen")
	@Column(name = "project_type_id", columnDefinition = "int default 0", nullable = false)
	private int id;
	
	@Column(name="name",columnDefinition="varchar(50)", nullable=false)
	private String name;
	
	@OneToMany(mappedBy = "projectType")
	@JsonIgnore
	private List<Project> projects;
	 
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

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
	

}
