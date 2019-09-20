package com.cmc.dashboard.dto;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CssChartDTO implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = 521918319285399612L;
	private String duName;
	private double averageCss;

	public CssChartDTO(String duName, double averageCss) {
		this.duName = duName;
		this.averageCss = averageCss;
	}

	public CssChartDTO() {
		super();
	}

	public String getDuName() {
		return duName;
	}

	public void setDuName(String duName) {
		this.duName = duName;
	}

	public double getAverageCss() {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(averageCss));
	}

	public void setAverageCss(double averageCss) {
		this.averageCss = averageCss;
	}

}
