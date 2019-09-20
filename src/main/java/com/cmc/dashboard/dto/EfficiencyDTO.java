package com.cmc.dashboard.dto;

import java.text.DecimalFormat;

public class EfficiencyDTO {
	private String unit;
	private float efficiency;
	public EfficiencyDTO() {
		super();
	}
	public EfficiencyDTO(String unit, int efficiency) {
		super();
		this.unit = unit;
		this.efficiency = efficiency;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getEfficiency() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Float.valueOf(formatter.format(efficiency));
	}
	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}
	
}
