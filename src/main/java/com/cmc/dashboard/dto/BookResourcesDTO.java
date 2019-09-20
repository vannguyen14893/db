package com.cmc.dashboard.dto;

public class BookResourcesDTO {
  private String skill;
  private Integer id;
public BookResourcesDTO() {
	super();
	// TODO Auto-generated constructor stub
}
public String getSkill() {
	return skill;
}
public void setSkill(String skill) {
	this.skill = skill;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public BookResourcesDTO(String skill, Integer id) {
	super();
	this.skill = skill;
	this.id = id;
}
@Override
public boolean equals(Object arg0) {
	 if(arg0 instanceof BookResourcesDTO)
	    {
		 BookResourcesDTO temp = (BookResourcesDTO) arg0;
	        if(this.id== temp.id)
	            return true;
	    }
	    return false;
}
@Override
public int hashCode() {
    return this.id;
}
  
}
