package com.cmc.dashboard.dto;

public class ResultType <T> {
	private String type;
	private T value;
	public ResultType() {
		super();
	}
	public ResultType(String type, T value) {
		super();
		this.type = type;
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	
}
