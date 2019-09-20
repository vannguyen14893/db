/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: Apr 17, 2018
 */
public class ResourceManPowerDetailRestDTO {
  private int id;
  private String fromDate;
  private String toDate;
  private float totalHours;
  private float hoursPerDay;

  /**
   * Constructure
   */
  public ResourceManPowerDetailRestDTO(List<Object> objects, int indexManPower, int indexUserTo,
      String toDate) {
    Object[] object = ResourceRestDTO.getObject(objects, indexManPower);

    this.id = getIdFromObjectResource(object);
    this.fromDate = ResourceRestDTO.getFromDateOfManPower(object);
    this.toDate = ResourceRestDTO.getToDateOfManPower(objects, indexManPower, indexUserTo, toDate);
    this.hoursPerDay = getAllocationValue(object) * 8;
    this.totalHours = this.hoursPerDay * MethodUtil.getTotalWorkingDaysBetweenDate(
        MethodUtil.convertStringToDate(fromDate), MethodUtil.convertStringToDate(this.toDate));
  }

  public int getId() {
    return id;
  }

  /**
   * get field id
   * 
   * @param object
   * @return String
   * @author: NNDuy
   */
  private int getIdFromObjectResource(Object[] object) {
    return null == object[1] ? 0 : Integer.parseInt(object[1].toString());
  }

  /**
   * get Allocation Value
   * 
   * @param object
   * @return float
   * @author: NNDuy
   */
  private float getAllocationValue(Object[] object) {
    return null == object[2] ? 0 : Float.parseFloat(object[2].toString());
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
}
