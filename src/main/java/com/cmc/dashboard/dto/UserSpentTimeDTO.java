package com.cmc.dashboard.dto;

public class UserSpentTimeDTO {

  private int projectId;
  private int userId;
  private float hours;
  private int tmonth;
  private int tyear;

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public float getHours() {
    return hours;
  }

  public void setHours(float hours) {
    this.hours = hours;
  }

  public int getTmonth() {
    return tmonth;
  }

  public void setTmonth(int tmonth) {
    this.tmonth = tmonth;
  }

  public int getTyear() {
    return tyear;
  }

  public void setTyear(int tyear) {
    this.tyear = tyear;
  }

  public UserSpentTimeDTO(int projectId, int userId, float hours, int tmonth, int tyear) {
    super();
    this.projectId = projectId;
    this.userId = userId;
    this.hours = hours;
    this.tmonth = tmonth;
    this.tyear = tyear;
  }

  
}
