package com.cmc.dashboard.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cmc.dashboard.dto.ResourcesAllocationUpdateRestDTO;
import com.cmc.dashboard.util.MethodUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the user_plan database table.
 * 
 */
@Entity
@Table(name = "user_plan")
@NamedQuery(name = "UserPlan.findAll", query = "SELECT u FROM UserPlan u")
public class UserPlan implements Serializable {

	private static final long serialVersionUID = 8385092430824111800L;
	@TableGenerator(name = "UserPlan_Gen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "user_plan_id", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserPlan_Gen")
	@Column(name = "user_plan_id")
	private int userPlanId;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	@Column(name = "created_on", updatable = false)
	private Date createdOn;

	@Column(name = "effort_per_day")
	private float effortPerDay;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "man_day")
	private float manDay;

	@Column(name = "project_id")
	private int projectId;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
	@Column(name = "to_date")
	private Date toDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonIgnore
	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(name = "role")
	private String role;
    @Column(name ="skill")
    private String skill;
	// bi-directional many-to-one association to User
	@Column(name = "user_id", updatable = false)
	private Integer userId;

	@OneToMany(mappedBy = "userPlan")
	private List<UserPlanDetail> userPlanDetails;

	public UserPlan() {
	}

	public UserPlan(int userPlanId, Date fromDate, float manDay, int projectId, Date toDate) {
		super();
		this.userPlanId = userPlanId;
		this.fromDate = fromDate;
		this.manDay = manDay;
		this.projectId = projectId;
		this.toDate = toDate;
	}

	public UserPlan(Date fromDate, float manDay, int projectId, Date toDate, Integer userId) {
		super();
		this.fromDate = fromDate;
		this.manDay = manDay;
		this.projectId = projectId;
		this.toDate = toDate;
		this.userId = userId;
	}

	public UserPlan(int userPlanId, Date fromDate, float manDay, int projectId, Date toDate, Integer userId,
			String role) {
		super();
		this.userPlanId = userPlanId;
		this.fromDate = fromDate;
		this.manDay = manDay;
		this.projectId = projectId;
		this.toDate = toDate;
		this.userId = userId;
		this.role = role;
	}
	public UserPlan(int userPlanId, float effortPerDay, Date fromDate, float manDay,
			int projectId, Date toDate, Integer userId, Date updatedOn) {
		super();
		this.userPlanId = userPlanId;
		this.effortPerDay = effortPerDay;
		this.fromDate = fromDate;
		this.manDay = manDay;
		this.projectId = projectId;
		this.toDate = toDate;
		this.userId = userId;
		this.updatedOn = updatedOn;
	}

	public UserPlan(int userPlanId, String role, Integer userId) {
		super();
		this.userPlanId = userPlanId;
		this.role = role;
		this.userId = userId;
	}
	
  /**
   * Constructure to add plan
   */
  public UserPlan(Integer userId, int projectId, float effortPerDay, Date fromDate, Date toDate, boolean weekend,String skill,String role) {
    this.effortPerDay = effortPerDay;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.manDay = convertHourPerDayToManDayWeekend(effortPerDay, weekend);
    this.projectId = projectId;
    this.userId = userId;
    createdOn = new Date();
    updatedOn = new Date();
    this.skill=skill;
    this.role=role;
  }
  
	public int getUserPlanId() {
		return this.userPlanId;
	}

	public void setUserPlanId(int userPlanId) {
		this.userPlanId = userPlanId;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public float getEffortPerDay() {
		return this.effortPerDay;
	}

	public void setEffortPerDay(float effortPerDay) {
		this.effortPerDay = effortPerDay;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public float getManDay() {
		return this.manDay;
	}

	public void setManDay(float manDay) {
		this.manDay = manDay;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<UserPlanDetail> getUserPlanDetails() {
		return this.userPlanDetails;
	}

	public void setUserPlanDetails(List<UserPlanDetail> userPlanDetails) {
		this.userPlanDetails = userPlanDetails;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

  /**
   * set information userPlan update
   * 
   * @param allocation void 
   * @author: NNDuy
   */
  public void setInforUpdateUserPlan(ResourcesAllocationUpdateRestDTO allocation) {
    this.effortPerDay = allocation.getHourPerDay();
    this.fromDate = MethodUtil.convertStringToDate(allocation.getFrom());
    this.toDate = MethodUtil.convertStringToDate(allocation.getTo());
    this.manDay = convertHourPerDayToManDay(allocation.getHourPerDay());
    this.role = allocation.getRole();
    this.skill = allocation.getSkill();
    this.projectId = allocation.getProjectId();
    updatedOn = new Date();    
  }
  
  /**
   * convert HourPerDay To ManDay
   * 
   * @param hourPerDay
   * @return float 
   * @author: NNDuy
   */
  private float convertHourPerDayToManDay(float hourPerDay) {
    return hourPerDay * MethodUtil.getTotalWorkingDaysBetweenDate(fromDate, toDate) / 8 ;
  }

  private float convertHourPerDayToManDayWeekend(float hourPerDay, boolean weekend) {
	    return hourPerDay * MethodUtil.getTotalWorkingDaysBetweenDateWeekend(fromDate, toDate, weekend) / 8 ;
	  }
}
