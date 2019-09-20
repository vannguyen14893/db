package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import ch.qos.logback.core.subst.Token.Type;


@Entity
@Table(name = "evaluate_comment")
public class ProjectEvaluateComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1301411118156726023L;
	
	@TableGenerator(name = "ProjectEvaluateComment_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "project_evaluate_comment_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ProjectEvaluateComment_Gen")
	@Column(name = "project_evaluate_comment_id", unique = true, nullable = false)
	private int id;
	@ManyToOne
	@JoinColumn(name = "project_evaluate_id", nullable = false)
	private ProjectEvaluate evaluate;
	@Column(name = "comment",columnDefinition="text")
	private String comment;
	public int getId() {
		return id;
	}public ProjectEvaluateComment(){}
	public ProjectEvaluateComment(ProjectEvaluate evaluate, String comment, int type, Date createdDate,
			Date updatedDate, int creatorId) {
		super();
		this.evaluate = evaluate;
		this.comment = comment;
		this.type = type;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.creatorId = creatorId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProjectEvaluate getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(ProjectEvaluate evaluate) {
		this.evaluate = evaluate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	@Column(name = "type",columnDefinition = "tinyint" )
	private int type;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updatedDate;
	private int  creatorId;

}
