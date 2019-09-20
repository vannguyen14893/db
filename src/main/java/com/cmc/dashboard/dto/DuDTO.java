package com.cmc.dashboard.dto;

import java.util.List;

public class DuDTO {
	private List<ProjectOfDuDto> projectOfDuDto;// project 
	private List<ProjectOfDuDto> du;//du
	private List<ProjectOfDuDto> duPic;// duPic
	
	public DuDTO() {
		super();
		
	}

	public DuDTO(List<ProjectOfDuDto> projectOfDuDto, List<ProjectOfDuDto> du, List<ProjectOfDuDto> duPic) {
		super();
		this.projectOfDuDto = projectOfDuDto;
		this.du = du;
		this.duPic = duPic;
	}

	public List<ProjectOfDuDto> getProjectOfDuDto() {
		return projectOfDuDto;
	}

	public void setProjectOfDuDto(List<ProjectOfDuDto> projectOfDuDto) {
		this.projectOfDuDto = projectOfDuDto;
	}

	public List<ProjectOfDuDto> getDu() {
		return du;
	}

	public void setDu(List<ProjectOfDuDto> du) {
		this.du = du;
	}

	public List<ProjectOfDuDto> getDuPic() {
		return duPic;
	}

	public void setDuPic(List<ProjectOfDuDto> duPic) {
		this.duPic = duPic;
	}

	
	
}
