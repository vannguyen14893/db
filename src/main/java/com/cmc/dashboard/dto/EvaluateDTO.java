package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cmc.dashboard.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EvaluateDTO {


	private int id;


	public EvaluateDTO(int id, int projectId, String userName, Integer qualityId, Integer deliveryId, Integer processId,
			String subject, Date endDate, Date startDate, List<EvaluateCommentDTO> listComment, String lastCommentQA,
			String lastCommentPM) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.userName = userName;
		this.qualityId = qualityId;
		this.deliveryId = deliveryId;
		this.processId = processId;
		this.subject = subject;
		this.endDate = endDate;
		this.startDate = startDate;
		this.listComment = listComment;
		this.lastCommentQA = lastCommentQA;
		this.lastCommentPM = lastCommentPM;
	}

	public EvaluateDTO() {
		// TODO Auto-generated constructor stub
	}

	private int projectId;

	private String userName;


	private Integer qualityId;

	private Integer deliveryId;

	private Integer processId;
	
	private String subject;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	private Date endDate;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = Constants.DATE_FORMAT_PARAMS, timezone = Constants.TIME_ZONE)
	private Date startDate;
	
	private List<EvaluateCommentDTO> listComment;
	
	private String lastCommentQA;
	
	private String lastCommentPM;

	public int getId() {
		return id;
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

	public Integer getQualityId() {
		return qualityId;
	}

	public void setQualityId(Integer qualityId) {
		this.qualityId = qualityId;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<EvaluateCommentDTO> getListComment() {
		return listComment;
	}

	public void setListComment(List<EvaluateCommentDTO> listComment) {
		this.listComment = listComment;
	}

	public String getLastCommentQA() {
		return lastCommentQA;
	}

	public void setLastCommentQA(String lastCommentQA) {
		this.lastCommentQA = lastCommentQA;
	}

	public String getLastCommentPM() {
		return lastCommentPM;
	}

	public void setLastCommentPM(String lastCommentPM) {
		this.lastCommentPM = lastCommentPM;
	} 
	
}
