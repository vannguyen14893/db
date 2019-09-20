package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "project")
public class Project implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4699828910150231877L;
	@TableGenerator(name = "Project_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_id", allocationSize = 1)
	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Project_Gen")
	@Column(name = "project_id", columnDefinition = "int default 0", nullable = false)
	private int id;
	@Column(name = "project_name")
	private String name;
	
	@Column(name="project_manager")
	private String projectManager;
	
	@Column(name = "status", columnDefinition = "tinyint")
	private int status;
	
	@Column(name = "type", columnDefinition = "tinyint")
	private Integer type;
	
	@Column(name="line_code",columnDefinition = "int default 0")
	private int lineCode;
	
	@Column(name="target_pcv_rate",columnDefinition = "float")
	private float pcvRate;
	
	@Column(name="target_bug_rate",columnDefinition = "float")
	private float bugRate;
	
	@Column(name="target_leakage_rate",columnDefinition = "float")
	private float leakageRate;
	
	@Column(name="target_effort_deviation",columnDefinition = "float")
	private float effortDeviation;
	
	public float getPcvRate() {
		return pcvRate;
	}
	public void setPcvRate(float pcvRate) {
		this.pcvRate = pcvRate;
	}
	public float getBugRate() {
		return bugRate;
	}
	public void setBugRate(float bugRate) {
		this.bugRate = bugRate;
	}
	public float getLeakageRate() {
		return leakageRate;
	}
	public void setLeakageRate(float leakageRate) {
		this.leakageRate = leakageRate;
	}
	public float getEffortDeviation() {
		return effortDeviation;
	}
	public void setEffortDeviation(float effortDeviation) {
		this.effortDeviation = effortDeviation;
	}
	public float getProductivity() {
		return productivity;
	}
	public void setProductivity(float productivity) {
		this.productivity = productivity;
	}
	public float getBillableRate() {
		return billableRate;
	}
	public void setBillableRate(float billableRate) {
		this.billableRate = billableRate;
	}
	public float getTimeliness() {
		return timeliness;
	}
	public void setTimeliness(float timeliness) {
		this.timeliness = timeliness;
	}
	public float getCss() {
		return css;
	}
	public void setCss(float css) {
		this.css = css;
	}
	@Column(name="target_productivity")
	private float productivity;
	
	@Column(name="target_billable_rate",columnDefinition = "float")
	private float billableRate;
	
	@Column(name="target_timeliness",columnDefinition = "float")
	private float timeliness;
	
	@Column(name="target_css",columnDefinition = "float")
	private float css;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Column(name = "project_code")
	private String projectCode;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_type_id")
	private ProjectType projectType;
	
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name="project_skill", joinColumns=@JoinColumn(name="project_id")
	, inverseJoinColumns = @JoinColumn(name="skill_id"))
	private Set<Skill> skill;
	
	public Integer getLineCode() {
		return lineCode;
	}
	public void setLineCode(int lineCode) {
		this.lineCode = lineCode;
	}
	public Set<Skill> getSkill() {
		return skill;
	}
	public void setSkill(Set<Skill> skill) {
		this.skill = skill;
	}
	public ProjectType getProjectType() {
		return projectType;
	}
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;
	
	
	
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
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void addSkill(Skill skill) {
		this.skill.add(skill);
		skill.addProject(this); 
	}	
	
}
