/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.ArrayList;
import java.util.List;

import com.cmc.dashboard.util.MethodUtil;

/**
 * @author: NNDuy
 * @Date: Apr 26, 2018
 */
public class ResourceProjectPlanTimeRestDTO {

  private String time;
  private List<ResourceProjectPlanTimeDetailRestDTO> details;
  private float totalHours;

  /**
   * Constructure
   */
  public ResourceProjectPlanTimeRestDTO(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime) {
    this.time = ResourceProjectRestDto.getTimeForEstimateTimeAndPlanTime(objects, indexTimeFrom,
        typeTime);
    details = new ArrayList<>();
    totalHours = 0;

    setPlanTimes(objects, indexTimeFrom, indexTimeTo, typeTime);
  }

  /**
   * set plans time
   * 
   * @param objects
   * @param indexTimeFrom
   * @param indexTimeTo
   * @param typeTime
   *          void
   * @author: NNDuy
   */
  public void setPlanTimes(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime) {
    for (int i = indexTimeFrom; i <= indexTimeTo; i++) {
      Object[] object = ResourceRestDTO.getObject(objects, i);
      details.add(new ResourceProjectPlanTimeDetailRestDTO(object));
      // get last element in details
      totalHours += details.get(details.size() - 1).getTotalHours();
    }
  }

  public String getTime() {
    return time;
  }

  public List<ResourceProjectPlanTimeDetailRestDTO> getDetails() {
    return details;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }
}
