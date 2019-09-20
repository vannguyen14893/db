package com.cmc.dashboard.dto;

import java.util.List;

public class UnallocationListDTO {
	private List<UnallocationDTO> unallocationDTO;
	private List<String> DU;
	private long totalElements;

	public UnallocationListDTO() {
		super();
	}

	public UnallocationListDTO(List<UnallocationDTO> unallocationDTO, List<String> dU, long totalElements) {
		super();
		this.unallocationDTO = unallocationDTO;
		DU = dU;
		this.totalElements = totalElements;
	}

	public List<UnallocationDTO> getUnallocationDTO() {
		return unallocationDTO;
	}

	public void setUnallocationDTO(List<UnallocationDTO> unallocationDTO) {
		this.unallocationDTO = unallocationDTO;
	}

	public List<String> getDU() {
		return DU;
	}

	public void setDU(List<String> dU) {
		DU = dU;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
