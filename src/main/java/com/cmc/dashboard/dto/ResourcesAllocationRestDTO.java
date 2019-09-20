/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cmc.dashboard.model.UserPlan;
import com.cmc.dashboard.util.Constants;
import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: May 17, 2018
 */
public class ResourcesAllocationRestDTO {

  @NotNull
  @Min(1)
  private Integer userId;

  @NotNull
  @Min(1)
  private Integer projectId;

  @NotNull
  @Min(1)
  @Max(24)
  private float hourPerDay;

  @NotNull
  private String from;

  @NotNull
  private String to;
  
  @NotNull
  private Boolean checkWeekend;

  private Integer planId;
  private String skill;
  private String role;
  
  public Integer getPlanId() {
	return planId;
}

public void setPlanId(Integer planId) {
	this.planId = planId;
}

public Boolean getCheckWeekend() {
	return checkWeekend;
}

public void setCheckWeekend(Boolean checkWeekend) {
	this.checkWeekend = checkWeekend;
}

public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public Float getHourPerDay() {
    return hourPerDay;
  }

  public void setHourPerDay(Float hourPerDay) {
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

public void setTo(String to) {
    this.to = to;
  }

  public UserPlan toEntity(boolean weekend) {
    return new UserPlan(userId, projectId, hourPerDay, MethodUtil.convertStringToDate(from),
        MethodUtil.convertStringToDate(to), weekend ,skill ,role);
  }

  /**
   * Validate format date
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  public static ResponseEntity<Object> validateFormatDate(String from, String to) {

    // Validate date format
    if (!MethodUtil.isValidDate(from) || !MethodUtil.isValidDate(to)) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.DATE_INVALID, HttpStatus.BAD_REQUEST);
    }

    final Date fromDate = MethodUtil.convertStringToDate(from);
    final Date toDate = MethodUtil.convertStringToDate(to);

    // validate from < to
    if (fromDate.compareTo(toDate) > 0) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.FROM_TO_DATE_INVALID,
          HttpStatus.BAD_REQUEST);
    }
    return null;
  }

  /**
   * validate DateFrom < DateStartProject and DateEnd < DateEndProject
   * 
   * @return ResponseEntity<Object>
   * @author: NNDuy
   */
  public static ResponseEntity<Object> validateDateCompareProject(TimeProjectDTO timeProject, String from, String to) {
    final Date fromDate = MethodUtil.convertStringToDate(from);
    final Date toDate = MethodUtil.convertStringToDate(to);

    // Validate from >= startDate of project
    if (null != timeProject.getStartDate()
        && fromDate.compareTo(MethodUtil.convertStringToDate(timeProject.getStartDate())) < 0) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.START_DATE_INVALID,
          HttpStatus.BAD_REQUEST);
    }

    // Validate to <= endDate of project
    if (null != timeProject.getEndDate()
        && toDate.compareTo(MethodUtil.convertStringToDate(timeProject.getEndDate())) > 0) {
      return new ResponseEntity<>(Constants.HTTP_STATUS_MSG.END_DATE_INVALID,
          HttpStatus.BAD_REQUEST);
    }
    return null;
  }
//  public ResponeEntity<Object> validateEffortPerDay(String)

}
