package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "evaluate")
public class ProjectEvaluate implements Serializable {
	private static final long serialVersionUID = 2409787267295981330L;

	@TableGenerator(name = "ProjectEvaluate_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_evaluate_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProjectEvaluate_Gen")
	@Column(name = "project_evaluate_id", unique = true, nullable = false)
	private int id;

	@Column(name = "project_id")
	private int projectId;
	
	@Column(name = "user_name",nullable=true)
	private String userName;

	@Column(name = "quality_id",nullable=true)
	private Integer qualityId;
	

	@JoinColumn(name = "quality_id",insertable = false, updatable = false,nullable=true)
	@ManyToOne(optional = true)
	private ProjectEvaluateLevel quality;

	@Column(name = "cost_id",nullable=true)
	private Integer costId;
	

	@JoinColumn(name = "cost_id",insertable = false, updatable = false,nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true)
	private ProjectEvaluateLevel cost;

	@Column(name = "delivery_id",nullable=true)
	private Integer deliveryId;


	@JoinColumn(name = "delivery_id",insertable = false, updatable = false,nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true)
	private ProjectEvaluateLevel delivery;

	@Column(name = "process_id",nullable=true)
	private Integer processId;
	

	@JoinColumn(name = "process_id", insertable = false, updatable = false,nullable=true)
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = true)
	private ProjectEvaluateLevel process;

	@Column(name = "subject", length = 500,nullable=true)
	private String subject;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "startDate")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "comment", length = 1000,nullable=true)
	private String comment;

	 @OneToMany(fetch = FetchType.LAZY,mappedBy = "evaluate")
	 @JsonIgnore
	 private Set<ProjectEvaluateComment> listComment = new HashSet<>();
	public Set<ProjectEvaluateComment> getListComment() {
		return listComment;
	}


	public void setListComment(Set<ProjectEvaluateComment> listComment) {
		this.listComment = listComment;
	}


	public int getId() {
		return id;
	}

	
	public ProjectEvaluate() {
		super();
	}


	public ProjectEvaluate(int id, int projectId, String userName, Integer qualityId, ProjectEvaluateLevel quality,
			Integer deliveryId, Integer processId, String subject, Date startDate, Date endDate, String comment) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.userName = userName;
		this.qualityId = qualityId;
		this.quality = quality;
		this.deliveryId = deliveryId;
		this.processId = processId;
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comment = comment;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ProjectEvaluateLevel getQuality() {
		return quality;
	}

	public void setQuality(ProjectEvaluateLevel quality) {
		this.quality = quality;
	}

	public ProjectEvaluateLevel getCost() {
		return cost;
	}

	public void setCost(ProjectEvaluateLevel cost) {
		this.cost = cost;
	}

	public ProjectEvaluateLevel getDelivery() {
		return delivery;
	}

	public void setDelivery(ProjectEvaluateLevel delivery) {
		this.delivery = delivery;
	}

	public ProjectEvaluateLevel getProcess() {
		return process;
	}

	public void setProcess(ProjectEvaluateLevel process) {
		this.process = process;
	}

	public Integer getQualityId() {
		return qualityId;
	}

	public void setQualityId(Integer qualityId) {
		this.qualityId = qualityId;
	}

	public Integer getCostId() {
		return costId;
	}

	public void setCostId(Integer costId) {
		this.costId = costId;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
