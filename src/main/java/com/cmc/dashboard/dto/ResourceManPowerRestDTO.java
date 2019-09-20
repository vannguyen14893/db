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
public class ResourceManPowerRestDTO {

  private String time;
  private List<ResourceManPowerDetailRestDTO> details;
  private float totalHours;

  /**
   * Constructure
   */
  public ResourceManPowerRestDTO(List<Object> objects, int indexTimeFrom, int indexTimeTo,
      String typeTime, int indexUserTo, String toDate) {
    this.time = ResourceRestDTO.getTimeOfManPower(objects, indexTimeFrom, typeTime, indexUserTo,
        toDate);
    details = new ArrayList<>();
    totalHours = 0;

    for (int i = indexTimeFrom; i <= indexTimeTo; i++) {
      details.add(new ResourceManPowerDetailRestDTO(objects, i, indexUserTo, toDate));
      // get last item of details
      totalHours += details.get(details.size() - 1).getTotalHours();
    }
  }

  public String getTime() {
    return time;
  }

  // @JsonIgnore
  public List<ResourceManPowerDetailRestDTO> getDetails() {
    return details;
  }

  public float getTotalHours() {
    return MethodUtil.formatFloatTimeResource(totalHours);
  }
}
