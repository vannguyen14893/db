/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author: NNDuy
 * @Date: May 25, 2018
 */
public class TimeProjectDTO {

  private int projectId;
  private String projectName;
  private String startDate;
  private String endDate;

  /**
   * Constructure
   */
  public TimeProjectDTO(List<Object> rows, int indexProjectFrom, int indexProjectTo) {
    // set projectId
    projectId = getFieldProjectId(ResourceRestDTO.getObject(rows, indexProjectFrom));
    projectName = getFieldProjectName(ResourceRestDTO.getObject(rows, indexProjectFrom));
    // set startDate and endDate
    for (int i = indexProjectFrom; i <= indexProjectTo; i++) {
      Object[] row = ResourceRestDTO.getObject(rows, i);
        if(row != null) {
           startDate = getFieldDate(row);
           endDate = getFieldCustomFieldId(row);
        }
      }
  }

  /**
   * Constructure
   */
  public TimeProjectDTO() {

  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * get field CustomFieldId
   * 
   * @param row
   * @return String
   * @author: NNDuy
   */
  private String getFieldCustomFieldId(Object[] row) {
    return null == row[3] ? null : row[3].toString();
  }

  /**
   * get field date
   * 
   * @param row
   * @return String
   * @author: NNDuy
   */
  private String getFieldDate(Object[] row) {
    return null == row[2] ? null : row[2].toString();
  }

  /**
   * get field projectId
   * 
   * @param row
   * @return String
   * @author: NNDuy
   */
  private int getFieldProjectId(Object[] row) {
    return null == row[0] ? null : Integer.parseInt(row[0].toString());
  }

  private String getFieldProjectName(Object[] row) {
    return null == row[1] ? null : row[1].toString();
  }

}
