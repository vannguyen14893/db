package com.cmc.dashboard.dto;

import java.util.List;

public class ListProjectTaskDTO {
	private List<ProjectTaskDTO> data;
	private int total;
	private List<String> names;
	private List<String> status;
	
	public ListProjectTaskDTO() {
		super();
	}

	public ListProjectTaskDTO(List<ProjectTaskDTO> data, int total) {
		super();
		this.data = data;
		this.total = total;
	}
	
	public ListProjectTaskDTO(List<ProjectTaskDTO> data, int total, List<String> names, List<String> status) {
		super();
		this.data = data;
		this.total = total;
		this.names = names;
		this.status = status;
	}

	public List<ProjectTaskDTO> getData() {
		return data;
	}
	
	public void setData(List<ProjectTaskDTO> data) {
		this.data = data;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}
	
	
	
	

}
