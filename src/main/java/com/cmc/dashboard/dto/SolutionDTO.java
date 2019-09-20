package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.cmc.dashboard.model.Solution;
import com.cmc.dashboard.model.SolutionComment;


public class SolutionDTO {
	private Integer id;
	private String name;
	private String riskTitle;
	private int type;
	private String description;
	private int status;
	private Date startDate;
	private Date endDate;
	private String assignee;
	private String creater;
	private int countComment;
	private Date lastUpdateComment;
	private Date startUpdateComment;
	private Integer assigneeId;

	private Integer riskId;
	
	private Integer createrId;

	public String getRiskTitle() {
		return riskTitle;
	}

	public void setRiskTitle(String riskTitle) {
		this.riskTitle = riskTitle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRiskId() {
		return riskId;
	}

	public void setRiskId(Integer riskId) {
		this.riskId = riskId;
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

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public int getCountComment() {
		return countComment;
	}

	public void setCountComment(int countComment) {
		this.countComment = countComment;
	}

	public Date getLastUpdateComment() {
		return lastUpdateComment;
	}

	public void setLastUpdateComment(Date lastUpdateComment) {
		this.lastUpdateComment = lastUpdateComment;
	}

	public Date getStartUpdateComment() {
		return startUpdateComment;
	}

	public void setStartUpdateComment(Date startUpdateComment) {
		this.startUpdateComment = startUpdateComment;
	}

	public SolutionDTO() {
		super();
	}

	public SolutionDTO(Solution solution) {
		this.id = solution.getId();
		this.riskTitle = solution.getRiskId().getRiskTitle();
		this.riskId = solution.getRiskId().getRiskId();
		this.name = solution.getName();
		this.type = solution.getType();
		this.description = solution.getDescription();
		this.status = solution.getStatus();
		this.startDate = solution.getStartDate();
		this.endDate = solution.getEndDate();
		if(this.assigneeId!=null) {
			this.assigneeId = solution.getUserAssignee().getUserId();
		}
		this.createrId = solution.getUserCreateBy().getUserId();
		this.assignee = solution.getUserAssignee() == null ? "" : solution.getUserAssignee().getFullName();
		this.creater = solution.getUserCreateBy() == null ? "" : solution.getUserCreateBy().getFullName();
		this.countComment=0;
		List<Date> dateComment = new ArrayList<>() ;
		for (SolutionComment solutionComment : solution.getSolutionComment()) {
			dateComment.add(solutionComment.getCreateDate());
			if(!solutionComment.getIsDeleted()) {
				countComment++;
			}
		}
		
		if(!dateComment.isEmpty()) {
			Collections.sort(dateComment);
			this.startUpdateComment = dateComment.get(0);
			this.lastUpdateComment = dateComment.get(dateComment.size()-1);
		}
	}
}
