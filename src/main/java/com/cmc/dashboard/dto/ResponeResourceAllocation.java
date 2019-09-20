package com.cmc.dashboard.dto;

import java.util.List;

public class ResponeResourceAllocation {
	private List<ProjectResourceAllocationDTO> data;
	private DuDTO du;

	public ResponeResourceAllocation() {
		super();
	}

	public ResponeResourceAllocation(List<ProjectResourceAllocationDTO> data, DuDTO du) {
		super();
		this.data = data;
		this.du = du;
	}

	public List<ProjectResourceAllocationDTO> getData() {
		return data;
	}

	public void setData(List<ProjectResourceAllocationDTO> data) {
		this.data = data;
	}

	public DuDTO getDu() {
		return du;
	}

	public void setDu(DuDTO du) {
		this.du = du;
	}
	

}
