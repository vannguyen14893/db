/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.List;

/**
 * @author: GiangTM
 * @Date: Feb 23, 2018
 */
public class ProjectKpiDTO {
	private int code;
	private String name;
	private String unit;
	private Float value;
	private ProjectKpiDetailDTO projectKpiDetailDTO;
	private List<?> projectKpiDetails;
	private float target;
	private Float numerator;
	private Float denominator;

	public Float getNumerator() {
		return numerator;
	}

	public void setNumerator(Float numerator) {
		this.numerator = numerator;
	}

	public Float getDenominator() {
		return denominator;
	}

	public void setDenominator(Float denominator) {
		this.denominator = denominator;
	}

	public float getTarget() {
		return target;
	}

	public void setTarget(float target) {
		this.target = target;
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDTO() {
		
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDTO(String name, String unit, Float value,float target,Float numerator,Float denominator) {
		this.name = name;
		this.unit = unit;
		this.value = value;
		this.target=target;
		this.numerator=numerator;
		this.denominator=denominator;
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDTO(int code, String name, String unit, Float value,float target,Float numerator,Float denominator) {
		this.code = code;
		this.name = name;
		this.unit = unit;
		this.value = value;
		this.target=target;
		this.numerator=numerator;
		this.denominator=denominator;
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDTO(int code, String name, String unit, Float value, ProjectKpiDetailDTO projectKpiDetailDTO,float target,Float numerator,Float denominator) {
		this.code = code;
		this.name = name;
		this.unit = unit;
		this.value = value;
		this.projectKpiDetailDTO = projectKpiDetailDTO;
		this.target=target;
		this.numerator=numerator;
		this.denominator=denominator;
	}

	/**
	 * Constructure
	 */
	public ProjectKpiDTO(int code, String name, String unit, Float value, List<?> projectKpiDetails,float target,Float numerator,Float denominator) {
		this.code = code;
		this.name = name;
		this.unit = unit;
		this.value = value;
		this.projectKpiDetails = projectKpiDetails;
		this.numerator=numerator;
		this.denominator=denominator;
		this.target=target;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public ProjectKpiDetailDTO getProjectKpiDetailDTO() {
		return projectKpiDetailDTO;
	}

	public void setProjectKpiDetailDTO(ProjectKpiDetailDTO projectKpiDetailDTO) {
		this.projectKpiDetailDTO = projectKpiDetailDTO;
	}

	public List<?> getProjectKpiDetails() {
		return projectKpiDetails;
	}

	public void setProjectKpiDetails(List<?> projectKpiDetails) {
		this.projectKpiDetails = projectKpiDetails;
	}

}
