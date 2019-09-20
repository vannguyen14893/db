/**
 * dashboard-phase2-backend- - com.cmc.dashboard.dto
 */
package com.cmc.dashboard.dto;

import java.util.Date;

/**
 * @author: NNDuy
 * @Date: May 16, 2018
 */
public class ResourceFilterDto {

  private int id;
  private String name;
  private Date startDate;
  int status;
  public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getEndDate() {
	return endDate;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}

private Date endDate;

  /**
   * Constructure
   */
  public ResourceFilterDto(Object[] row) {
    id = getFieldId(row);
    name = getFieldName(row);
    startDate=getFieldDate(row, 2);
    endDate=getFieldDate(row, 3);
    status = getFieldStatus(row);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * get field id
   * 
   * @param row
   * @return int
   * @author: NNDuy
   */
  private int getFieldId(Object[] row) {
    return null == row[0] ? 0 : Integer.parseInt(row[0].toString());
  }

  /**
   * get field name
   * 
   * @param row
   * @return String
   * @author: NNDuy
   */
  private String getFieldName(Object[] row) {
    return null == row[1] ? null : row[1].toString();
  }
  private Date getFieldDate(Object[] row,int number) {
	    return null == row[number] ? null : (Date) row[number];
	  }
  private int getFieldStatus(Object[] row) {
	    return null == row[4] ? 0 : Integer.parseInt(row[4].toString());
	  }
}
