package com.cmc.dashboard.dto;

public class DuStatisticDTO {

	private float manPower;
	private int numberOfUser;
	private float ultilizedRate;
	private float billRate;
	private int booked;
	private int available;
			
	public DuStatisticDTO() {
		super();
	}
	
	public DuStatisticDTO( float manPower,int numberOfUser, float ultilizedRate, float billRate, int booked,
			int available) {
		super();
		this.numberOfUser = numberOfUser;
		this.manPower = manPower;
		this.ultilizedRate = ultilizedRate;
		this.billRate = billRate;
		this.booked = booked;
		this.available = available;
	}

	public float getManPower() {
		return manPower;
	}
	public void setManPower(float manPower) {
		this.manPower = manPower;
	}
	public float getUltilizedRate() {
		return ultilizedRate;
	}
	public void setUltilizedRate(float ultilizedRate) {
		this.ultilizedRate = ultilizedRate;
	}
	public float getBillRate() {
		return billRate;
	}
	public void setBillRate(float billRate) {
		this.billRate = billRate;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getNumberOfUser() {
		return numberOfUser;
	}
	public void setNumberOfUser(int numberOfUser) {
		this.numberOfUser = numberOfUser;
	}
	public int getBooked() {
		return booked;
	}
	public void setBooked(int booked) {
		this.booked = booked;
	}

	
}
