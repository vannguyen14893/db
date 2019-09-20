package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.util.Constants;

public class DeliveryUnitDTO  {

	private int id;
	private String name;
	private float totalManPower;
	private float totalUltilizedRate;
	private float totalBillRate;
	private int totalAvailable;
	private int totalUser;
	private int totalBooked;
	private List<DeliveryUnitProjectDTO> duProjects;
	
	public DeliveryUnitDTO() {
		super();
		// TODO Auto-generated constructor stub
		this.duProjects = new ArrayList<>();
		for(int i = 1 ; i <= 5;i++) {
		    DeliveryUnitProjectDTO du = new DeliveryUnitProjectDTO();
		    this.duProjects.add(du);
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getTotalManPower() {
		return totalManPower;
	}
	public void setTotalManPower(float totalManPower) {
		this.totalManPower = totalManPower;
	}
	public float getTotalUltilizedRate() {
		return totalUltilizedRate;
	}
	public void setTotalUltilizedRate(float totalUltilizedRate) {
		this.totalUltilizedRate = totalUltilizedRate;
	}
	public float getTotalBillRate() {
		return totalBillRate;
	}
	public void setTotalBillRate(float totalBillRate) {
		this.totalBillRate = totalBillRate;
	}
	public int getTotalAvailable() {
		return totalAvailable;
	}
	public void setTotalAvailable(int totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public int getTotalBooked() {
		return totalBooked;
	}
	public void setTotalBooked(int totalBooked) {
		this.totalBooked = totalBooked;
	}
	public List<DeliveryUnitProjectDTO> getDuProjects() {
		return duProjects;
	}
	public void setDuProjects(List<DeliveryUnitProjectDTO> duProjects) {
		this.duProjects = duProjects;
	}
	public void setDuProject(DeliveryUnitProjectDTO du) {
		if(this.duProjects == null) {
			this.duProjects = new ArrayList<>();
		}
		this.duProjects.add(du);
	}
	

}
