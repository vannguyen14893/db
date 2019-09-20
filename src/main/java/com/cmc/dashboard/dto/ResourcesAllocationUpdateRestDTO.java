/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;

/**
 * @author: NNDuy
 * @Date: May 17, 2018
 */
public class ResourcesAllocationUpdateRestDTO {

  @NotNull
  @Min(1)
  private Integer planId;

  @NotNull
  @Min(1)
  @Max(24)
  private Integer hourPerDay;

  @NotNull
  private String from;

  @NotNull
  private String to;
  
  private String skill;
  
  public String getSkill() {
	return skill;
}

public void setSkill(String skill) {
	this.skill = skill;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

private String role;

private int projectId;

  public int getProjectId() {
	return projectId;
}

public void setProjectId(int projectId) {
	this.projectId = projectId;
}

public Integer getPlanId() {
    return planId;
  }

  public void setPlanId(Integer planId) {
    this.planId = planId;
  }

  public Integer getHourPerDay() {
    return hourPerDay;
  }

  public void setHourPerDay(Integer hourPerDay) {
    this.hourPerDay = hourPerDay;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  /**
   * Validate format date
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  public ResponseEntity<Object> validateFormatDate() {
    return ResourcesAllocationRestDTO.validateFormatDate(from, to);
  }

  /**
   * validate DateFrom < DateStartProject and DateEnd < DateEndProject
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  public ResponseEntity<Object> validateDateCompareProject(TimeProjectDTO timeProject) {
    return ResourcesAllocationRestDTO.validateDateCompareProject(timeProject, from, to);
  }

}
