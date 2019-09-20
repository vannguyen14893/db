package com.cmc.dashboard.model;

import java.util.Collection;

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
@Table(name = "evaluate_level")
public class ProjectEvaluateLevel {

	@TableGenerator(name = "ProjectEvaluateLevel_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_evaluate_level_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProjectEvaluateLevel_Gen")
	@Column(name = "project_evaluate_level_id", unique = true, nullable = false)
	private int id;

	@Column(name = "value", length = 100)
	private String value;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quality")
	@JsonIgnore
	private Collection<ProjectEvaluate> projectEvaluateByQuality;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cost")
	@JsonIgnore
	private Collection<ProjectEvaluate> projectEvaluateByCost;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "delivery")
	@JsonIgnore
	private Collection<ProjectEvaluate> projectEvaluateByDelivery;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "process")
	@JsonIgnore
	private Collection<ProjectEvaluate> projectEvaluateByProcess;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Collection<ProjectEvaluate> getProjectEvaluateByQuality() {
		return projectEvaluateByQuality;
	}

	public void setProjectEvaluateByQuality(Collection<ProjectEvaluate> projectEvaluateByQuality) {
		this.projectEvaluateByQuality = projectEvaluateByQuality;
	}

	public Collection<ProjectEvaluate> getProjectEvaluateByCost() {
		return projectEvaluateByCost;
	}

	public void setProjectEvaluateByCost(Collection<ProjectEvaluate> projectEvaluateByCost) {
		this.projectEvaluateByCost = projectEvaluateByCost;
	}

	public Collection<ProjectEvaluate> getProjectEvaluateByDelivery() {
		return projectEvaluateByDelivery;
	}

	public void setProjectEvaluateByDelivery(Collection<ProjectEvaluate> projectEvaluateByDelivery) {
		this.projectEvaluateByDelivery = projectEvaluateByDelivery;
	}

	public Collection<ProjectEvaluate> getProjectEvaluateByProcess() {
		return projectEvaluateByProcess;
	}

	public void setProjectEvaluateByProcess(Collection<ProjectEvaluate> projectEvaluateByProcess) {
		this.projectEvaluateByProcess = projectEvaluateByProcess;
	}

	@Override
	public String toString() {
		return "ProjectEvaluateLevel [id=" + id + ", value=" + value + "]";
	}

}
