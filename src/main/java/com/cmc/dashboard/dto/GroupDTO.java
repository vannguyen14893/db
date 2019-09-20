package com.cmc.dashboard.dto;

public class GroupDTO {
	private int group_id;
	private String group_name;
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public GroupDTO(int group_id, String group_name) {
		super();
		this.group_id = group_id;
		this.group_name = group_name;
	}
	public GroupDTO() {
		super();
	}
	
}
