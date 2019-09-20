package com.cmc.dashboard.dto;

/**
 * @author: GiangTM
 * @Date: April 09, 2018
 */
public class ProjectKpiDetailDTO {
	private String value;
	private String unit;

	/**
	 * Constructure
	 */
	public ProjectKpiDetailDTO() {
		
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDetailDTO(String value, String unit) {
		this.value = value;
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
