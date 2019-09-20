package com.cmc.dashboard.dto;

import javax.persistence.Column;

public class ProjectTargetDTO {

	private float pcvRate;
	
	private float bugRate;
	
	private float leakageRate;
	
	private float effortDeviation;
	
	
	private float productivity;
	

	private float billableRate;
	
	
	private float timeliness;
	
	private float css;
	
	public ProjectTargetDTO(float pcvRate, float bugRate, float leakageRate, float effortDeviation, float productivity,
			float billableRate, float timeliness, float css) {
		super();
		this.pcvRate = pcvRate;
		this.bugRate = bugRate;
		this.leakageRate = leakageRate;
		this.effortDeviation = effortDeviation;
		this.productivity = productivity;
		this.billableRate = billableRate;
		this.timeliness = timeliness;
		this.css = css;
	}


	public float getPcvRate() {
		return pcvRate;
	}


	public void setPcvRate(float pcvRate) {
		this.pcvRate = pcvRate;
	}


	public float getBugRate() {
		return bugRate;
	}


	public void setBugRate(float bugRate) {
		this.bugRate = bugRate;
	}


	public float getLeakageRate() {
		return leakageRate;
	}


	public void setLeakageRate(float leakageRate) {
		this.leakageRate = leakageRate;
	}


	public float getEffortDeviation() {
		return effortDeviation;
	}


	public void setEffortDeviation(float effortDeviation) {
		this.effortDeviation = effortDeviation;
	}


	public float getProductivity() {
		return productivity;
	}


	public void setProductivity(float productivity) {
		this.productivity = productivity;
	}


	public float getBillableRate() {
		return billableRate;
	}


	public void setBillableRate(float billableRate) {
		this.billableRate = billableRate;
	}


	public float getTimeliness() {
		return timeliness;
	}


	public void setTimeliness(float timeliness) {
		this.timeliness = timeliness;
	}


	public float getCss() {
		return css;
	}


	public void setCss(float css) {
		this.css = css;
	}


	
}
