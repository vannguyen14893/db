package com.cmc.dashboard.dto;

import java.util.List;

public class DashboardDTO<T> {
	private String unit;
	private List<ResultType<T>> data;
	private int total;
	public DashboardDTO() {
		super();
	}
	public DashboardDTO(String unit, List<ResultType<T>> data, int total) {
		super();
		this.unit = unit;
		this.data = data;
		this.total = total;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<ResultType<T>> getData() {
		return data;
	}
	public void setData(List<ResultType<T>> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
