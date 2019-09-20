package com.cmc.dashboard.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cmc.dashboard.model.Issue;

public class IssueDashboardDTO {
	private int issueId;
	private String issueCategory;
	private String issuePriority;
	private String issueServerity;
	private String issueSource;
	private String issueStatus;
	private String issueType;
	private String closedBy;
	private String approvedBy;
	private String createdBy;
	private String projectName;
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@NotNull
	@Size(max = 1000)
	private String description;
	@NotNull
	@Size(max = 1000)
	private String cause;
	private Integer projectId;
	private Date openDate;
	private Date dueDate;
	private String suggestionOfQa;
	private String action;
	private String comment;
	private String impact;
	private Integer assignee;
	private Integer updateBy;
	@NotNull
	private Integer issueCategoryId;

	@NotNull
	private Integer issuePriorityId;

	@NotNull
	private Integer issueServerityId;

	@NotNull
	private Integer issueSourceId;

	private Integer issueStatusId;

	@NotNull
	private Integer issueTypeId;

	@NotNull
	private Integer createdById;

	private Integer closedById;

	private int approvedById;

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getIssueCategory() {
		return issueCategory;
	}

	public void setIssueCategory(String issueCategory) {
		this.issueCategory = issueCategory;
	}

	public String getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}

	public String getIssueServerity() {
		return issueServerity;
	}

	public void setIssueServerity(String issueServerity) {
		this.issueServerity = issueServerity;
	}

	public String getIssueSource() {
		return issueSource;
	}

	public void setIssueSource(String issueSource) {
		this.issueSource = issueSource;
	}

	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getSuggestionOfQa() {
		return suggestionOfQa;
	}

	public void setSuggestionOfQa(String suggestionOfQa) {
		this.suggestionOfQa = suggestionOfQa;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public Integer getIssueCategoryId() {
		return issueCategoryId;
	}

	public void setIssueCategoryId(Integer issueCategoryId) {
		this.issueCategoryId = issueCategoryId;
	}

	public Integer getIssuePriorityId() {
		return issuePriorityId;
	}

	public void setIssuePriorityId(Integer issuePriorityId) {
		this.issuePriorityId = issuePriorityId;
	}

	public Integer getIssueServerityId() {
		return issueServerityId;
	}

	public void setIssueServerityId(Integer issueServerityId) {
		this.issueServerityId = issueServerityId;
	}

	public Integer getIssueSourceId() {
		return issueSourceId;
	}

	public void setIssueSourceId(Integer issueSourceId) {
		this.issueSourceId = issueSourceId;
	}

	public Integer getIssueStatusId() {
		return issueStatusId;
	}

	public void setIssueStatusId(Integer issueStatusId) {
		this.issueStatusId = issueStatusId;
	}

	public Integer getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(Integer issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public Integer getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Integer createdById) {
		this.createdById = createdById;
	}

	public Integer getClosedById() {
		return closedById;
	}

	public void setClosedById(Integer closedById) {
		this.closedById = closedById;
	}

	public int getApprovedById() {
		return approvedById;
	}

	public void setApprovedById(int approvedById) {
		this.approvedById = approvedById;
	}

	public Integer getAssignee() {
		return assignee;
	}

	public void setAssignee(Integer assignee) {
		this.assignee = assignee;
	}
    
	
	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public IssueDashboardDTO() {
		super();
	}

	public IssueDashboardDTO(Issue issue) {
		this.issueId = issue.getIssueId();
		this.description = issue.getDescription();
		this.cause = issue.getCause();
		this.issueType = issue.getIssueType().getValue();
		this.issueTypeId = issue.getIssueType().getId();
		this.issueStatus = issue.getIssueStatus().getValue();
		this.issueStatusId = issue.getIssueStatus().getId();
		this.issuePriority = issue.getIssuePriority().getValue();
		this.issuePriorityId = issue.getIssuePriority().getId();
		this.issueServerity = issue.getIssueServerity().getValue();
		this.issueServerityId = issue.getIssueServerity().getId();
		this.issueCategory = issue.getIssueCategory().getValue();
		this.issueCategoryId = issue.getIssueCategory().getId();
		this.issueSource = issue.getIssueSource().getValue();
		this.issueSourceId = issue.getIssueSource().getId();
		this.openDate = issue.getOpenDate();
		this.dueDate = issue.getDueDate();
		this.createdById = issue.getUsersByCreatedBy() == null ? 0 : issue.getUsersByCreatedBy().getUserId();
		this.createdBy = issue.getUsersByCreatedBy() == null ? "" : issue.getUsersByCreatedBy().getFullName();
		this.closedBy = issue.getUsersByClosedBy() == null ? "" : issue.getUsersByClosedBy().getFullName();
		this.approvedBy = issue.getUsersByApprovedBy() == null ? "" : issue.getUsersByApprovedBy().getFullName();
		this.action = issue.getAction() == null ? "" : issue.getAction();
		this.suggestionOfQa = issue.getSuggestionOfQa() == null ? "" : issue.getSuggestionOfQa();
		this.assignee = issue.getAssignee() == null ? 0 : issue.getAssignee();
		this.projectId=issue.getProjectId();
	}

}
