package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MethodUtil;

public class ResourceProjectEstimateTimeDetailRestDTO {

  private int id;
  private String fromDate;
  private String toDate;
  private float totalHours;
  private float hoursPerDay;

  /**
   * Constructure
   */
  public ResourceProjectEstimateTimeDetailRestDTO(Object[] object) {
    this.id = getIdFromObjectResource(object);
    this.fromDate = ResourceProjectRestDto.getFromDateOfEstimateTimeAndPlanTime(object);
    this.toDate = ResourceProjectRestDto.getToDateOfEstimateTimeAndPlanTime(object);
    this.totalHours = ResourceProjectEstimateTimeRestDTO.getEstimateTimeFromObjectResource(object);
    this.hoursPerDay = this.totalHours / MethodUtil.getTotalWorkingDaysBetweenDate(
        MethodUtil.convertStringToDate(fromDate), MethodUtil.convertStringToDate(toDate));
  }

  public String getFromDate() {
    return fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }

  public float getHoursPerDay() {
    return MethodUtil.formatFloatTimeResource(hoursPerDay);
  }

  public int getId() {
    return id;
  }

  /**
   * get field id
   * 
   * @param object
   * @return int
   * @author: NNDuy
   */
  private int getIdFromObjectResource(Object[] object) {
    return null == object[1] ? 0 : Integer.parseInt(object[1].toString());
  }
}
