package com.cmc.dashboard.dto;

import java.text.SimpleDateFormat;
import com.cmc.dashboard.model.Issue;
import com.cmc.dashboard.util.Constants;

public class IssueHistoryDTO {
	
		private int issueId;
		private String createdBy;
		private String issueStatus;
		private String issuePriority;
		private String closedBy;
		private String openDate;
		private String issueType;
		private String issueSource;
		private String assignee;
		private String dueDate;
		private String issueServerity;
		private String issueCategory;
		private String cause;
		private String suggestionOfQa;
		private String description;
		private String action;
		public int getIssueId() {
			return issueId;
		}
		public void setIssueId(int issueId) {
			this.issueId = issueId;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getIssueStatus() {
			return issueStatus;
		}
		public void setIssueStatus(String issueStatus) {
			this.issueStatus = issueStatus;
		}
		public String getIssuePriority() {
			return issuePriority;
		}
		public void setIssuePriority(String issuePriority) {
			this.issuePriority = issuePriority;
		}
		public String getClosedBy() {
			return closedBy;
		}
		public void setClosedBy(String closedBy) {
			this.closedBy = closedBy;
		}
		public String getOpenDate() {
			return openDate;
		}
		public void setOpenDate(String openDate) {
			this.openDate = openDate;
		}
		public String getIssueType() {
			return issueType;
		}
		public void setIssueType(String issueType) {
			this.issueType = issueType;
		}
		public String getIssueSource() {
			return issueSource;
		}
		public void setIssueSource(String issueSource) {
			this.issueSource = issueSource;
		}
		public String getAssignee() {
			return assignee;
		}
		public void setAssignee(String assignee) {
			this.assignee = assignee;
		}
		public String getDueDate() {
			return dueDate;
		}
		public void setDueDate(String dueDate) {
			this.dueDate = dueDate;
		}
		public String getIssueServerity() {
			return issueServerity;
		}
		public void setIssueServerity(String issueServerity) {
			this.issueServerity = issueServerity;
		}
		public String getIssueCategory() {
			return issueCategory;
		}
		public void setIssueCategory(String issueCategory) {
			this.issueCategory = issueCategory;
		}
		public String getCause() {
			return cause;
		}
		public void setCause(String cause) {
			this.cause = cause;
		}
		public String getSuggestionOfQa() {
			return suggestionOfQa;
		}
		public void setSuggestionOfQa(String suggestionOfQa) {
			this.suggestionOfQa = suggestionOfQa;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public IssueHistoryDTO()
		{
		}
		
		public IssueHistoryDTO(Issue issue)
		{
			
			this.assignee=issue.getAssignee()!=null?issue.getAssignee().toString():"";
			this.closedBy=issue.getUsersByClosedBy()!=null?issue.getUsersByClosedBy().getFullName():"";
			this.createdBy=issue.getUsersByCreatedBy()!=null?issue.getUsersByCreatedBy().getFullName():"";
			this.dueDate=issue.getDueDate()!=null?new SimpleDateFormat(Constants.DATE_FORMAT_PARAMS).format(issue.getDueDate()):"";
			this.issueCategory=issue.getIssueCategory()!=null?issue.getIssueCategory().getValue():"";
			this.issueId=issue.getIssueId();
			this.issuePriority=issue.getIssuePriority()!=null?issue.getIssuePriority().getValue():"";			
			this.issueServerity=issue.getIssueServerity()!=null?issue.getIssueServerity().getValue():"";
			this.issueSource=issue.getIssueSource()!=null?issue.getIssueSource().getValue():"";
			this.issueStatus=issue.getIssueStatus()!=null?issue.getIssueStatus().getValue():"";
			this.issueType=issue.getIssueType()!=null?issue.getIssueType().getValue():"";
			this.openDate=issue.getOpenDate()!=null?issue.getOpenDate().toString():"";
			this.cause=issue.getCause()!=null?issue.getCause():"";
			this.action=issue.getAction()!=null?issue.getAction():"";
			this.suggestionOfQa=issue.getSuggestionOfQa()!=null?issue.getSuggestionOfQa():"";
			this.description=issue.getDescription()!=null?issue.getDescription():"";
		}
		
		 public static IssueHistoryDTO  coppy(Issue issue) {
			 IssueHistoryDTO issueHistoryDTO=new IssueHistoryDTO();
			 issueHistoryDTO.assignee=issue.getAssignee()!=null?issue.getAssignee().toString():"";
			 issueHistoryDTO.closedBy=issue.getUsersByClosedBy()!=null?issue.getUsersByClosedBy().getFullName():"";
			 issueHistoryDTO.createdBy=issue.getUsersByCreatedBy()!=null?issue.getUsersByCreatedBy().getFullName():"";
			 issueHistoryDTO.dueDate=issue.getDueDate()!=null?new SimpleDateFormat(Constants.DATE_FORMAT_PARAMS).format(issue.getDueDate()):"";
			 issueHistoryDTO.issueCategory=issue.getIssueCategory()!=null?issue.getIssueCategory().getValue():"";
			 issueHistoryDTO.issueId=issue.getIssueId();
			 issueHistoryDTO.issuePriority=issue.getIssuePriority()!=null?issue.getIssuePriority().getValue():"";			
			 issueHistoryDTO.issueServerity=issue.getIssueServerity()!=null?issue.getIssueServerity().getValue():"";
			 issueHistoryDTO.issueSource=issue.getIssueSource()!=null?issue.getIssueSource().getValue():"";
			 issueHistoryDTO.issueStatus=issue.getIssueStatus()!=null?issue.getIssueStatus().getValue():"";
			 issueHistoryDTO.issueType=issue.getIssueType()!=null?issue.getIssueType().getValue():"";
			 issueHistoryDTO.openDate=issue.getOpenDate()!=null?issue.getOpenDate().toString():"";
			 issueHistoryDTO.cause=issue.getCause()!=null?issue.getCause():"";
			 issueHistoryDTO.action=issue.getAction()!=null?issue.getAction():"";
			 issueHistoryDTO.suggestionOfQa=issue.getSuggestionOfQa()!=null?issue.getSuggestionOfQa():"";
			 issueHistoryDTO.description=issue.getDescription()!=null?issue.getDescription():"";
			 return issueHistoryDTO;
		 }
		

		
		
}
