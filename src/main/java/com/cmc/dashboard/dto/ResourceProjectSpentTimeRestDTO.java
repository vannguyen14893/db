/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: Apr 26, 2018
 */
public class ResourceProjectSpentTimeRestDTO {
  private String time;
  private List<ResourceProjectSpentTimeDetailRestDTO> details;
  private float totalHours;

  /**
   * Constructure
   */
  public ResourceProjectSpentTimeRestDTO(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime) {
    this.time = getTimeFromListObjectResource(objects, indexTimeFrom, indexTimeTo, typeTime);
    details = new ArrayList<>();
    totalHours = 0;
    setSpentTimes(objects, indexTimeFrom, indexTimeTo, typeTime);
  }

  /**
   * set estimate time
   * 
   * @param objects
   * @param indexTimeFrom
   * @param indexTimeTo
   * @param typeTime
   *          void
   * @author: NNDuy
   */
  public void setSpentTimes(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime) {
    for (int i = indexTimeFrom; i <= indexTimeTo; i++) {
      Object[] object = ResourceRestDTO.getObject(objects, i);
      details.add(new ResourceProjectSpentTimeDetailRestDTO(object));
      totalHours += getHoursFromObjectResource(object);
    }
  }

  /**
   * get time of list object
   * 
   * @param objects
   * @param indexTimeFrom
   * @param indexTimeTo
   * @param typeTime
   * @return String
   * @author: NNDuy
   */
  private String getTimeFromListObjectResource(List<Object> objects, int indexTimeFrom,
      int indexTimeTo, String typeTime) {
    return ResourceRestDTO.formatTime(
        ResourceProjectRestDto.getTimeForSpentTime(objects, indexTimeFrom, typeTime),
        ResourceProjectRestDto.getTimeForSpentTime(objects, indexTimeTo, typeTime));
  }

  /**
   * get field hour
   * 
   * @param object
   * @return float
   * @author: NNDuy
   */
  public static float getHoursFromObjectResource(Object[] object) {
    float spentTime= null == object[4] ? 0 : (Float.parseFloat(object[4].toString()));
    DecimalFormat df = new DecimalFormat("#");
    df.setRoundingMode(RoundingMode.CEILING);
    return Float.parseFloat(df.format(spentTime/3600));
  }

  public String getTime() {
    return time;
  }

  public List<ResourceProjectSpentTimeDetailRestDTO> getDetails() {
    return details;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }

}
