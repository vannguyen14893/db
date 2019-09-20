package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

public class DUOverviewDTO {
private int groupId;
private String month;
private int year;
private float totalManpower;
public float getTotalManpower() {
	return totalManpower;
}
public void setTotalManpower(float totalManpower) {
	this.totalManpower = totalManpower;
}
List<DeliveryUnitProjectDTO> listDuElement;
public int getGroupId() {
	return groupId;
}
public void setGroupId(int groupId) {
	this.groupId = groupId;
}
public String getMonth() {
	return month;
}
public DUOverviewDTO() {
	this.listDuElement = new ArrayList<>();
	for(int i = 0 ; i < 5;i++) {
	    DeliveryUnitProjectDTO du = new DeliveryUnitProjectDTO();
	    du.setType(i);
	    this.listDuElement.add(du);
	}
}
public void setMonth(String month) {
	this.month = month;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public List<DeliveryUnitProjectDTO> getListDuElement() {
	return listDuElement;
}
public void setListDuElement(List<DeliveryUnitProjectDTO> listDuElement) {
	this.listDuElement = listDuElement;
}
}
