package com.cmc.dashboard.dto;

public class DeliveryUnitProjectDTO {
	private int type;
	private float manPower;
	private float ultilizedRate;
	private float billRate;
	private float manPowerBillable;
	private int available;
	private int numberOfUser;
	private int booked;
	private int manDay;
	public int getManDay() {
		return manDay;
	}
	public void setManDay(int manDay) {
		this.manDay = manDay;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public float getManPowerBillable() {
		return manPowerBillable;
	}
	public void setManPowerBillable(float manPowerBillable) {
		this.manPowerBillable = manPowerBillable;
	}
	
}
