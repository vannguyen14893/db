package com.cmc.dashboard.dto;

import java.util.List;

public class ReAllocationTableDTO {
  List<ReAllocationDTO> listAllo;
  List<String> listDu;
  List<String> listDuPic;
  List<String> listProject;
  private long totalElements;
public long getTotalElements() {
	return totalElements;
}
public void setTotalElements(long totalElements) {
	this.totalElements = totalElements;
}
public List<ReAllocationDTO> getListAllo() {
	return listAllo;
}
public void setListAllo(List<ReAllocationDTO> listAllo) {
	this.listAllo = listAllo;
}
public List<String> getListDu() {
	return listDu;
}
public void setListDu(List<String> listDu) {
	this.listDu = listDu;
}
public List<String> getListDuPic() {
	return listDuPic;
}
public void setListDuPic(List<String> listDuPic) {
	this.listDuPic = listDuPic;
}
public List<String> getListProject() {
	return listProject;
}
public void setListProject(List<String> listProject) {
	this.listProject = listProject;
}
public ReAllocationTableDTO(List<ReAllocationDTO> listAllo, List<String> listDu, List<String> listDuPic,
		List<String> listProject,long totalElements) {
	super();
	this.listAllo = listAllo;
	this.listDu = listDu;
	this.listDuPic = listDuPic;
	this.listProject = listProject;
	this.totalElements=totalElements;
}
public ReAllocationTableDTO() {

}
  
}
