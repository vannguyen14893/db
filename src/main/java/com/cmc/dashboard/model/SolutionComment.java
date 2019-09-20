package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "solution_comment")
public class SolutionComment implements Serializable {
	private static final long serialVersionUID = -1751949502588846057L;

	@TableGenerator(name = "SolutionComment_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "solution_comment_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SolutionComment_Gen")
	@Column(name = "solution_comment_id")
	private Integer id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "solution_id",referencedColumnName = "solution_id", nullable = false)
	private Solution solutionId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "create_by")
	private User createBy;
	
	@Column(name = "comment")
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean isDeleted;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Solution getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Solution solutionId) {
		this.solutionId = solutionId;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
