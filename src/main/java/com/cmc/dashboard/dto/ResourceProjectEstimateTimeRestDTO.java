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
 * @Date: May 7, 2018
 */
public class ResourceProjectEstimateTimeRestDTO {

  private String time;
  private List<ResourceProjectEstimateTimeDetailRestDTO> details;
  private float totalHours;

  /**
   * Constructure
   */
  public ResourceProjectEstimateTimeRestDTO(List<Object> objects, int indexTimeFrom,
      int indexTimeTo, String typeTime) {
    this.time = ResourceProjectRestDto.getTimeForEstimateTimeAndPlanTime(objects, indexTimeFrom,
        typeTime);
    details = new ArrayList<>();
    totalHours = 0;

    setEstimateTimes(objects, indexTimeFrom, indexTimeTo, typeTime);
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
  public void setEstimateTimes(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime) {
    for (int i = indexTimeFrom; i <= indexTimeTo; i++) {
      Object[] object = ResourceRestDTO.getObject(objects, i);
      details.add(new ResourceProjectEstimateTimeDetailRestDTO(object));
      totalHours += getEstimateTimeFromObjectResource(object);
    }
  }

  /**
   * get field estimate time
   * 
   * @param object
   * @return float
   * @author: NNDuy
   */
  public static float getEstimateTimeFromObjectResource(Object[] object) {
	  float estimateTime= null == object[5] ? 0 : (Float.parseFloat(object[5].toString()));
	    DecimalFormat df = new DecimalFormat("#");
	    df.setRoundingMode(RoundingMode.CEILING);
	    return Float.parseFloat(df.format(estimateTime/3600));
  }

  public String getTime() {
    return time;
  }

  public List<ResourceProjectEstimateTimeDetailRestDTO> getDetails() {
    return details;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }

}
