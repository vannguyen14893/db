/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: Apr 17, 2018
 */
public class ResourceProjectSpentTimeDetailRestDTO {
  private int id;
  private String date;
  private float hours;

  /**
   * Constructure
   */
  public ResourceProjectSpentTimeDetailRestDTO(Object[] object) {
    this.id = getIdFromObjectResource(object);
    this.date = getSpentOnFromObjectResource(object);
    this.hours = ResourceProjectSpentTimeRestDTO.getHoursFromObjectResource(object);
  }

  public int getId() {
    return id;
  }

  public String getDate() {
    return date;
  }

  public float getHours() {
    return MethodUtil.formatFloatTimeResource(hours);
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
   * get field spentOn
   * 
   * @param object
   * @return String
   * @author: NNDuy
   */
  private String getSpentOnFromObjectResource(Object[] object) {
    return null == object[3] ? null : object[3].toString();
  }
}
