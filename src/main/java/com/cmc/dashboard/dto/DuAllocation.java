package com.cmc.dashboard.dto;

public class DuAllocation {

	private String name;
	private float allocation;
	private float billAble;
	
	public DuAllocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuAllocation(String name, float allocation, float billAble) {
		super();
		this.name = name;
		this.allocation = allocation;
		this.billAble = billAble;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAllocation() {
		return allocation;
	}

	public void setAllocation(float allocation) {
		this.allocation = allocation;
	}

	public float getBillAble() {
		return billAble;
	}

	public void setBillAble(float billAble) {
		this.billAble = billAble;
	}

	
	
}
