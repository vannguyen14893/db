package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "solution")
public class Solution implements Serializable {
	private static final long serialVersionUID = 2004726189900116890L;
	@TableGenerator(name = "Solution_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "solution_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Solution_Gen")
	@Column(name = "solution_id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private int type;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private int status;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "risk_id",referencedColumnName = "risk_id", nullable = false)
	private Risk riskId;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "end_date")
	private Date endDate;

	@ManyToOne(optional = true)
	@JoinColumn(name = "assignee",referencedColumnName = "user_id")
	private User userAssignee;

	
	@ManyToOne(optional = false)
	@JoinColumn(name = "create_by",referencedColumnName = "user_id", nullable = false)
	private User userCreateBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "solutionId")
	@JsonIgnore
	private Collection<SolutionComment> solutionComment;

	public Solution() {
		super();
	}

	public Solution(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Risk getRiskId() {
		return riskId;
	}

	public void setRiskId(Risk riskId) {
		this.riskId = riskId;
	}

	public User getUserAssignee() {
		return userAssignee;
	}

	public void setUserAssignee(User userAssignee) {
		this.userAssignee = userAssignee;
	}

	public User getUserCreateBy() {
		return userCreateBy;
	}

	public void setUserCreateBy(User userCreateBy) {
		this.userCreateBy = userCreateBy;
	}

	public Collection<SolutionComment> getSolutionComment() {
		return solutionComment;
	}

	public void setSolutionComment(Collection<SolutionComment> solutionComment) {
		this.solutionComment = solutionComment;
	}

	public Solution(Integer id, String name, int type, String description, int status, Date startDate,
			Date endDate, User userAssignee, User userCreateBy, Collection<SolutionComment> solutionComment) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userAssignee = userAssignee;
		this.userCreateBy = userCreateBy;
		this.solutionComment = solutionComment;
	}
	
	
	
	
}
