package com.cmc.dashboard.dto;

import java.util.List;

public class GroupManagerDTO {
private String name;
private String desciption;
private List<UserDTO> manager;
private List<GroupManagerDTO> listChild;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDesciption() {
	return desciption;
}
public void setDesciption(String desciption) {
	this.desciption = desciption;
}
public List<UserDTO> getManager() {
	return manager;
}
public void setManager(List<UserDTO> manager) {
	this.manager = manager;
}
public List<GroupManagerDTO> getListChild() {
	return listChild;
}
public void setListChild(List<GroupManagerDTO> listChild) {
	this.listChild = listChild;
}


}
