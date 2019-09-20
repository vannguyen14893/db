package com.cmc.dashboard.dto;

public class ProjectTypeLogDTO {
  private int id;
  private String typeName;
  private String editorName;
  private String startDate;
  private String endDate;
public int getId() {
	return id;
}
public ProjectTypeLogDTO(int id, String typeName, String editorName, String startDate, String endDate) {
	super();
	this.id = id;
	this.typeName = typeName;
	this.editorName = editorName;
	this.startDate = startDate;
	this.endDate = endDate;
}
public ProjectTypeLogDTO() {}
public void setId(int id) {
	this.id = id;
}
public String getTypeName() {
	return typeName;
}
public void setTypeName(String typeName) {
	this.typeName = typeName;
}
public String getEditorName() {
	return editorName;
}
public void setEditorName(String editorName) {
	this.editorName = editorName;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
}
