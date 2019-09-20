package com.cmc.dashboard.dto;

public class ProjectOfDuDto {

	private String values;//id
	private String label;//projectname
	
	public ProjectOfDuDto() {
		super();
	}

	public ProjectOfDuDto(String values, String label) {
		super();
		this.values = values;
		this.label = label;
	}

	public String getValue() {
		return values;
	}

	public void setValue(String values) {
		this.values = values;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
