package com.cmc.dashboard.dto;

public class DuInternalDuDTO {


	private int id;
	private String group_name;
	private int totalUser;
	private DuStatisticDTO profit;
	private DuStatisticDTO start;
	private DuStatisticDTO pool;
	private DuStatisticDTO investment;
	private float utilized;
	private int workDay;
	public float getManday() {
		return manday;
	}
	public void setManday(float manday) {
		this.manday = manday;
	}
	public float getBillable() {
		return billable;
	}
	public void setBillable(float billable) {
		this.billable = billable;
	}
	public int getWorkDay() {
		return workDay;
	}
	public void setWorkDay(int workDay) {
		this.workDay = workDay;
	}
	private float manday;
	private float billable;
	public DuInternalDuDTO() {
		super();
		this.utilized = 0.0f;
		this.totalUser = 0;
	}
	public DuInternalDuDTO(int id, String group_name,int totalUser, DuStatisticDTO profit, DuStatisticDTO start, DuStatisticDTO pool, DuStatisticDTO investment) {
		super();
		this.id = id;
		this.group_name = group_name;
		this.totalUser = totalUser;
		this.profit = profit;
		this.start = start;
		this.pool = pool;
		this.investment = investment;
	}
	public int getId() {
		return id;
	}
	public float getUtilized() {
		return utilized;
	}
	public void setUtilized(float utilized) {
		this.utilized = utilized;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public DuStatisticDTO getProfit() {
		return profit;
	}
	public void setProfit(DuStatisticDTO profit) {
		this.profit = profit;
	}
	public DuStatisticDTO getStart() {
		return start;
	}
	public void setStart(DuStatisticDTO start) {
		this.start = start;
	}
	public DuStatisticDTO getPool() {
		return pool;
	}
	public void setPool(DuStatisticDTO pool) {
		this.pool = pool;
	}
	public DuStatisticDTO getInvestment() {
		return investment;
	}
	public void setInvestment(DuStatisticDTO investment) {
		this.investment = investment;
	}
	
	
}
