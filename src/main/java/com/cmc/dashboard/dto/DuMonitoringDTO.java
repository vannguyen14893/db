package com.cmc.dashboard.dto;

import java.util.Date;
import java.util.List;

public class DuMonitoringDTO {
	private String ProjectName;
	private String DuName;
	private Date startDate;
	private Date endDate;
	private int qualityId;
	private int deliveryId;
	public int getQualityId() {
		return qualityId;
	}
	public void setQualityId(int qualityId) {
		this.qualityId = qualityId;
	}
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public List<EvaluateCommentDTO> getListComment() {
		return listComment;
	}
	public void setListComment(List<EvaluateCommentDTO> listComment) {
		this.listComment = listComment;
	}
	private int processId;
	private String subject;
	List<EvaluateCommentDTO> listComment;
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getDuName() {
		return DuName;
	}
	public void setDuName(String duName) {
		DuName = duName;
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
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public DuMonitoringDTO() {
		super();
	}
	
	
}
